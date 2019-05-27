package com.emall.common;

/**
 * 定义常量
 *
 * @author Harry Chou
 * @date 2019/5/24
 */
public class Const {
    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL="email";

    public static final String USERNAME="username";

    public interface Role{
        // 普通用户
        int ROLE_CUSTOMER=0;
        // 管理员
        int ROLE_ADMIN=1;
    }

    public interface Cart{
        // 购物车中选中状态
        int CHECKED=1;
        // 购物车中未选中状态
        int UNCHECKED=0;
    }
}
