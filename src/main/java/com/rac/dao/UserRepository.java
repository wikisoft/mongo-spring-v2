package com.rac.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.rac.model.User;

@RepositoryRestResource
public interface UserRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);

}