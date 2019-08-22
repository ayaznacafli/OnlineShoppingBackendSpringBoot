package com.ayaz.onlineshopping.service;

import com.ayaz.onlineshopping.model.Category;

import java.util.List;

public interface CategoryService {
    Category get(int id);
    List<Category> list();
    boolean add(Category category);
    boolean update(Category category);
    boolean delete(Category category);
}
