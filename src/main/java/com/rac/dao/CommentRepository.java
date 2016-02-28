package com.rac.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rac.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

    public List<Comment> findCommentByPostId(String postId);

}
