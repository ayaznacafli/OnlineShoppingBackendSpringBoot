package com.ayaz.onlineshopping.service.impl;

import com.ayaz.onlineshopping.model.Product;
import com.ayaz.onlineshopping.repository.ProductRepository;
import com.ayaz.onlineshopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    public Product get(int productId) {
        return productRepository.findById(productId).get();
    }

    @Override
    public List<Product> list() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public boolean add(Product product) {
        try {
            productRepository.save(product);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        try {
            productRepository.save(product);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        try {
            productRepository.delete(product);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Product> getProductsByParam(String param, int count) {
        String query = "FROM Product WHERE active = true ORDER BY" + param + " DESC";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setMaxResults(count);
        nativeQuery.setFirstResult(0);
        final List<Object[]> resultList = nativeQuery.getResultList();
        List<Product> products = new ArrayList<Product>();
        resultList.forEach(objects -> products.add((Product) resultList));
        return products;
    }

    @Override
    public List<Product> listActiveProducts() {
        return productRepository.findActiveProducts(true);
    }

    @Override
    public List<Product> listActiveProductsByCategory(int categoryId) {
        return productRepository.findActiveProductsByCategory(true,categoryId);
    }

    @Override
    public List<Product> getLatestActiveProducts(int count) {
        String query = "FROM Product WHERE active = :active ORDER BY id";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("active",true);
        nativeQuery.setMaxResults(count);
        nativeQuery.setFirstResult(0);
        final List<Object[]> resultList = nativeQuery.getResultList();
        List<Product> products = new ArrayList<Product>();
        resultList.forEach(objects -> products.add((Product) resultList));
        return products;
    }
}
