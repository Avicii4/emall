package com.emall.controller.backend;

import com.emall.common.Const;
import com.emall.common.ResponseCode;
import com.emall.common.ServerResponse;
import com.emall.pojo.User;
import com.emall.service.ICategoryService;
import com.emall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author Harry Chou
 * @date 2019/5/27
 */
@Controller
@RequestMapping("manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 后台增加品类
     *
     * @param session
     * @param categoryName 品类名称
     * @param parentId     父级类别ID
     * @return 添加的操作信息反馈
     */
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        ServerResponse response = iUserService.checkAdminRole(user);
        if (response.isSuccessful()) {
            // 是管理员，则可添加品类
            return iCategoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * 后台修改品类名称
     *
     * @param session
     * @param categoryName 品类名称
     * @param categoryId   品类ID
     * @return 修改操作的反馈信息
     */
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session, String categoryName, Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        ServerResponse response = iUserService.checkAdminRole(user);
        if (response.isSuccessful()) {
            // 是管理员，则可修改品类名
            return iCategoryService.updateCategoryName(categoryName, categoryId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * 获取品类子节点且不递归深入
     *
     * @param session
     * @param categoryId 当前分类ID
     * @return 查询结果返回
     */
    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getParallelChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") int categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        ServerResponse response = iUserService.checkAdminRole(user);
        if (response.isSuccessful()) {
            // 是管理员，获取子品类，不递归深入
            return iCategoryService.getParallelChildrenCategory(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * 获取当前分类ID及递归子节点
     *
     * @param session
     * @param categoryId 当前分类ID
     * @return 获取的分类ID信息
     */
    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getDeepChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") int categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        ServerResponse response = iUserService.checkAdminRole(user);
        if (response.isSuccessful()) {
            // 是管理员，递归获取各子品类
            return iCategoryService.selectDeepChildrenCategoryById(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }
}
