package com.emall.service;

import com.emall.common.ServerResponse;
import com.emall.pojo.Category;

import java.util.List;

/**
 * @author Harry Chou
 * @date 2019/5/27
 */
public interface ICategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(String categoryName, Integer categoryId);

    ServerResponse<List<Category>> getParallelChildrenCategory(Integer categoryId);

    ServerResponse selectDeepChildrenCategory(Integer categoryId);
}
