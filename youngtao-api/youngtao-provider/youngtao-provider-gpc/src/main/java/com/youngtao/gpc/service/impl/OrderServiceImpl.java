package com.youngtao.gpc.service.impl;

import com.google.common.collect.Lists;
import com.youngtao.core.context.AuthContext;
import com.youngtao.core.exception.CastException;
import com.youngtao.core.result.RestResCode;
import com.youngtao.core.util.RocketMQUtils;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.gpc.api.constant.GpcMQTagConsts;
import com.youngtao.gpc.common.constant.RedisKey;
import com.youngtao.gpc.common.util.DateUtils;
import com.youngtao.gpc.common.util.IdUtils;
import com.youngtao.gpc.model.req.CreateOrderReq;
import com.youngtao.gpc.service.OrderService;
import com.youngtao.omc.api.constant.OmcRedisKey;
import com.youngtao.omc.api.constant.OrderStatus;
import com.youngtao.omc.api.model.msg.CreateOrderMessage;
import com.youngtao.web.cache.DCacheManager;
import com.youngtao.web.cache.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author ankoye@qq.com
 * @date 2020/12/27
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RedisManager<String> redisManager;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private DCacheManager<String> dCacheManager;
    @Autowired
    private SpuFeign spuFeign;

    @Value("${order-topic}")
    private String orderTopic;

    @Override
    public String createOrder(CreateOrderReq request) {
        String menu = DateUtils.currentMenu();
        String skuId = request.getSkuId();
        // 1 redis扣减库存
        long count = redisManager.decrement(RedisKey.SKU_COUNT_KEY.format(menu, skuId));
        if (count < 0) {
            redisManager.increment(RedisKey.SKU_COUNT_KEY.format(menu, skuId));
            CastException.cast("库存不足，或商品不存在");
        }
        // 2 设置订单状态
        String paymentId = IdUtils.orderId();
        redisManager.set(OmcRedisKey.ORDER_STATUS.format(paymentId), OrderStatus.CREATING);
        // 3 封装并发送消息
        CreateOrderMessage msg = convertToMessage(request, menu);
        msg.setUserId(AuthContext.get().getUserId());
        msg.setPaymentId(paymentId);
        SendResult sendResult = rocketMQTemplate.syncSendOrderly(RocketMQUtils.withTag(orderTopic, GpcMQTagConsts.CREATE_ORDER), msg, skuId);
        if (sendResult == null) {
            log.warn("prepareOrder syncSendOrderly fail, data = {}", request);
            redisManager.increment(RedisKey.SKU_COUNT_KEY.format(menu, skuId));
            CastException.cast(RestResCode.SERVICE_ERROR);
        }
        return paymentId;
    }

    private CreateOrderMessage convertToMessage(CreateOrderReq request, String menu) {
        CreateOrderMessage msg = new CreateOrderMessage();
        msg.setIsCart(false);
        msg.setShippingAddressId(request.getShippingAddressId());

        CreateOrderMessage.OrderItem orderItem = new CreateOrderMessage.OrderItem();
        orderItem.setMenu(menu);
        orderItem.setSkuId(request.getSkuId());
        orderItem.setCount(1);
        CreateOrderMessage.Order order = new CreateOrderMessage.Order();
        order.setOrderItem(Lists.newArrayList(orderItem));
        order.setRemark(request.getRemark());
        msg.setOrderList(Lists.newArrayList(order));
        return msg;
    }
}
