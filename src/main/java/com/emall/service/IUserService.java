package com.emall.service;

import com.emall.common.ServerResponse;
import com.emall.pojo.User;

/**
 * @author Harry Chou
 * @date 2019/5/23
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str,String type);

    ServerResponse<String> selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse<String> forgetResetPassword(String username, String newPassword, String forgetToken);
}
