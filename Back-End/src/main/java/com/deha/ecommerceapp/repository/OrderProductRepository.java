package com.deha.ecommerceapp.repository;

import com.deha.ecommerceapp.model.OrderProduct;
import com.deha.ecommerceapp.model.OrderProductPK;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct, OrderProductPK> {
}
