package com.deha.ecommerceapp.repository;

import com.deha.ecommerceapp.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
}
