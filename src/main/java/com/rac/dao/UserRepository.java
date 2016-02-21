package com.rac.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rac.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    // public User findByFirstName(String firstName);
    public List<User> findByUsername(String username);

}