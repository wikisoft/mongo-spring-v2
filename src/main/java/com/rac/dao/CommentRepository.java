package com.rac.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rac.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

}
