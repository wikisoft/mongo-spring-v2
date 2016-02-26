package com.rac.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rac.model.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

}
