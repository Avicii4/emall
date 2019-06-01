package com.emall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 定义常量
 *
 * @author Harry Chou
 * @date 2019/5/24
 */
public class Const {
    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";

    public static final String USERNAME = "username";

    public interface Role {
        // 普通用户
        int ROLE_CUSTOMER = 0;
        // 管理员
        int ROLE_ADMIN = 1;
    }

    public interface Cart {
        // 购物车中选中状态
        int CHECKED = 1;
        // 购物车中未选中状态
        int UNCHECKED = 0;

        String LIMIT_NUM_FAIL="LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS="LIMIT_NUM_SUCCESS";
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC= Sets.newHashSet("price_asc","price_desc");
    }

    public enum ProductStatusEnum {
        ON_SALE(1, "在线");

        private int code;
        private String value;

        ProductStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }
}
