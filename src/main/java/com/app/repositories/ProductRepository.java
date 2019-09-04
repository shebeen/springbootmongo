package com.app.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
	public Product findByProductName(String productName);
	public Product findById(long id);
    public List<Product> findByProductPrice(int productPrice);
}
