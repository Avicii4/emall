package com.emall.service;

import com.emall.common.ServerResponse;

import java.util.Map;

/**
 * @author Harry Chou
 * @date 2019/6/13
 */
public interface IOrderService {

    ServerResponse pay(Integer userId, Long orderNo, String path);

    ServerResponse alipayCallback(Map<String, String> params);

    ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);
}
