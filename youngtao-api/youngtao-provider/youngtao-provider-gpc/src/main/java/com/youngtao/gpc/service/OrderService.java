package com.youngtao.gpc.service;

import com.youngtao.gpc.model.req.CreateOrderReq;

/**
 * @author ankoye@qq.com
 * @date 2020/12/27
 */
public interface OrderService {
    /**
     * 验证信息
     * @param request request
     */
    String createOrder(CreateOrderReq request);
}
