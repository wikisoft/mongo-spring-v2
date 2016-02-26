package com.rac.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rac.model.Post;

public interface PostRepository extends MongoRepository<Post, String> {

}
