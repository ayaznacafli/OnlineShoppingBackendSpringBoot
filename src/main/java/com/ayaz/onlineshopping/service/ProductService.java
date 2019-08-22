package com.ayaz.onlineshopping.service;

import com.ayaz.onlineshopping.model.Product;

import java.util.List;

public interface ProductService {

    Product get(int productId);
    List<Product> list();
    boolean add(Product product);
    boolean update(Product product);
    boolean delete(Product product);

    List<Product> getProductsByParam(String param, int count);
    List<Product> listActiveProducts();
    List<Product> listActiveProductsByCategory(int categoryId);
    List<Product> getLatestActiveProducts(int count);


}
