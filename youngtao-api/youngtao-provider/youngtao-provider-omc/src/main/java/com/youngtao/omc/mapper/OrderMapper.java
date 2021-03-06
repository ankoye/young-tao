package com.youngtao.omc.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.omc.model.domain.OrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {

    List<OrderDO> selectByUserIdAndStatus(@Param("userId") String userId, @Param("status") Integer status, @Param("deleted") Boolean deleted);

    List<OrderDO> selectByMerIdAndStatus(@Param("merchantId") String merchantId, @Param("status") Integer status);

    int batchInsert(List<OrderDO> orderList);

    int updateStatus(@Param("paymentId") String paymentId, @Param("status") Integer status);

    int paySuccess(@Param("paymentId") String paymentId, @Param("payType") Integer payType, @Param("status") int status);

    int deleteOrder(String orderId);

    int recoverOrder(String orderId);

    int updateStatusByMerchant(@Param("orderId") String orderId, @Param("merchantId") String merchantId, @Param("status") Integer status);

    int updateStatusByUser(@Param("orderId") String orderId, @Param("userId") String userId, @Param("status") Integer status);
}