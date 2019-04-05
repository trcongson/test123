package com.deha.ecommerceapp.repository;

import com.deha.ecommerceapp.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
