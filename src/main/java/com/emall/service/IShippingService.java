package com.emall.service;

import com.emall.common.ServerResponse;
import com.emall.pojo.Shipping;
import com.github.pagehelper.PageInfo;

/**
 * @author Harry Chou
 * @date 2019/6/12
 */
public interface IShippingService {

    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse<String> del(Integer userId, Integer shippingId);

    ServerResponse update(Integer userId, Shipping shipping);

    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
