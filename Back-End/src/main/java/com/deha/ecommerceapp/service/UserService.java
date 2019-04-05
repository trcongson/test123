package com.deha.ecommerceapp.service;

import com.deha.ecommerceapp.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    void delete(int id);

    User findOne(String username);

    User findById(int id);
}

