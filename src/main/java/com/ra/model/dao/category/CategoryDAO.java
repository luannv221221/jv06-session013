package com.ra.model.dao.category;

import com.ra.model.entity.Category;

import java.util.List;

public interface CategoryDAO {
    List<Category> findAll();
    Category findById(Integer id);
    Boolean saveOrUpdate(Category category);
    void delete(Integer id);
}
