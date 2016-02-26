package com.rac.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Post {

    @Id
    private String id;

    private String title;

    private String body;

    @DBRef
    private Author author;

    private List<Comment> comments;

    private int upvotes;

    public Post() {
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getBody() {
	return body;
    }

    public void setBody(String body) {
	this.body = body;
    }

    public Author getAuthor() {
	return author;
    }

    public void setAuthor(Author author) {
	this.author = author;
    }

    public List<Comment> getComments() {
	return comments;
    }

    public void setComments(List<Comment> comments) {
	this.comments = comments;
    }

    public int getUpvotes() {
	return upvotes;
    }

    public void setUpvotes(int upvotes) {
	this.upvotes = upvotes;
    }

}