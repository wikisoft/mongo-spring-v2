package com.rac.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rac.dao.AuthorRepository;
import com.rac.dao.CommentRepository;
import com.rac.dao.PostRepository;
import com.rac.model.Author;
import com.rac.model.Comment;
import com.rac.model.Post;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Value("custom.myValue")
    private String customValue;

    // posts
    public List<Post> getPosts() {
	return postRepository.findAll();
    }

    public Post getPost(final String id) {
	return postRepository.findOne(id);
    }

    public void save(final Post post) {
	postRepository.save(post);
    }

    public void deleteAllPosts() {
	postRepository.deleteAll();
    }

    public void deletePost(final Post post) {
	postRepository.delete(post);
    }

    // Comments
    public List<Comment> getComments() {
	return commentRepository.findAll();
    }

    public Comment getComment(final String id) {
	return commentRepository.findOne(id);
    }

    public void save(final Comment comment) {
	commentRepository.save(comment);
    }

    public void deleteAllComments() {
	commentRepository.deleteAll();
    }

    public void deleteComment(final Comment comment) {
	commentRepository.delete(comment);
    }

    // authors
    public List<Author> getAuthors() {
	return authorRepository.findAll();
    }

    public Author getAuthor(final String id) {
	return authorRepository.findOne(id);
    }

    public void save(final Author author) {
	authorRepository.save(author);
    }

    public void deleteAllAuthors() {
	authorRepository.deleteAll();
    }

    public void deleteAuthor(final Author author) {
	authorRepository.delete(author);
    }

    public String convertDate(Date date) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
	return dateFormat.format(date);
    }

    public String newValue() {

	return "value";
    }

}
