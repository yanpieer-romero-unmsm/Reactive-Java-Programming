package com.springboot.webflux.app.models.services;

import com.springboot.webflux.app.models.documents.Category;
import com.springboot.webflux.app.models.documents.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
	
	public Flux<Product> findAll();
	
	public Flux<Product> findAllWithNameUpperCase();
	
	public Flux<Product> findAllWithNameUpperCaseRepeat();
	
	public Mono<Product> findById(String id);
	
	public Mono<Product> save(Product product);
	
	public Mono<Void> delete(Product product);
	
	public Flux<Category> findAllCategory();
	
	public Mono<Category> findCategoryById(String id);
	
	public Mono<Category> saveCategory(Category category);

	public Mono<Product> findByName(String name);

	public Mono<Category> findCategoryByName(String name);

}
