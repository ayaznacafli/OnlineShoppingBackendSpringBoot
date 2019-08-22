package com.ayaz.onlineshopping.service.impl;

import com.ayaz.onlineshopping.model.Category;
import com.ayaz.onlineshopping.repository.CategoryRepository;
import com.ayaz.onlineshopping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category get(int id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public List<Category> list() {
        return (List<Category>)categoryRepository.findAll();
    }

    @Override
    public boolean add(Category category) {
        try {
            categoryRepository.save(category);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Category category) {
        try {
            categoryRepository.save(category);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Category category) {
        try {
            categoryRepository.delete(category);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
