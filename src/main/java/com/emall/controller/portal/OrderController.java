package com.emall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.emall.common.Const;
import com.emall.common.ResponseCode;
import com.emall.common.ServerResponse;
import com.emall.pojo.User;
import com.emall.service.IOrderService;
import com.emall.util.CookieUtil;
import com.emall.util.JsonUtil;
import com.emall.util.RedisShardedPoolUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Harry Chou
 * @date 2019/6/13
 */
@Slf4j
@Controller
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;

    /**
     * 创建订单
     *
     * @param shippingId 地址对象ID
     * @return 创建信息
     */
    @RequestMapping("create.do")
    @ResponseBody
    public ServerResponse create(HttpServletRequest request, Integer shippingId) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.createOrder(user.getId(), shippingId);
    }

    /**
     * 取消订单
     *
     * @param orderNo 订单号
     * @return 操作反馈
     */
    @RequestMapping("cancel.do")
    @ResponseBody
    public ServerResponse cancel(HttpServletRequest request,Long orderNo) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.cancelOrder(user.getId(), orderNo);
    }

    /**
     * 查看购物车中已选中的商品
     *
     * @return 反馈
     */
    @RequestMapping("get_order_cart_product.do")
    @ResponseBody
    public ServerResponse getOrderCartProduct(HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderCartProduct(user.getId());
    }

    /**
     * 用户查看订单详情
     *
     * @param orderNo 订单号
     * @return 反馈信息
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse detail(HttpServletRequest request,Long orderNo) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderDetail(user.getId(), orderNo);
    }

    /**
     * 用户查询订单列表
     *
     * @param pageNum  页码
     * @param pageSize 页面容量
     * @return 反馈信息
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpServletRequest request, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderList(user.getId(), pageNum, pageSize);
    }


    /**
     * 支付方法
     *
     * @param orderNo 订单号
     * @param request 为了获取路径信息
     * @return 反馈信息
     */
    @RequestMapping("pay.do")
    @ResponseBody
    public ServerResponse pay(Long orderNo, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        return iOrderService.pay(user.getId(), orderNo, path);
    }


    /**
     * 支付宝回调方法
     *
     * @param request
     * @return 回调信息
     */
    @RequestMapping("alipay_callback.do")
    @ResponseBody
    public Object alipayCallback(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        log.info("支付宝回调，sign:{}，trade_status:{}，参数:{}", params.get("sign"), params.get("trade_status"), params.toString());

        // 验证回调正确性，是否为支付宝发出
        params.remove("sign_type");
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
            if (!alipayRSACheckedV2) {
                return ServerResponse.createByErrorMessage("非法请求，请立刻停止恶意请求！");
            }
        } catch (AlipayApiException e) {
            log.error("支付宝验证回调异常", e);
        }
        // 验证各个数据字段

        ServerResponse serverResponse = iOrderService.alipayCallback(params);
        if (serverResponse.isSuccessful()) {
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;
    }

    /**
     * 查询订单状态
     *
     * @param orderNo 订单号
     * @return 查询反馈信息
     */
    @RequestMapping("query_order_pay_status.do")
    @ResponseBody
    public ServerResponse<Boolean> queryOrderPayStatus(HttpServletRequest request,Long orderNo) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        ServerResponse serverResponse = iOrderService.queryOrderPayStatus(user.getId(), orderNo);
        if (serverResponse.isSuccessful()) {
            return ServerResponse.createBySuccess(true);
        } else {
            return ServerResponse.createBySuccess(false);
        }
    }


}
