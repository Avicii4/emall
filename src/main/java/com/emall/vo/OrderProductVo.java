package com.emall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Harry Chou
 * @date 2019/6/15
 */
public class OrderProductVo {
    private List<OrderItemVo> orderItemVoList;

    private BigDecimal productTotalPrice;

    private String imageHost;

    public List<OrderItemVo> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
