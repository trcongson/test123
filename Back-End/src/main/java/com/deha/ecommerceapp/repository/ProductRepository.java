package com.deha.ecommerceapp.repository;

import com.deha.ecommerceapp.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}