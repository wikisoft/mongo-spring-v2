package com.rac.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Comment {

    private String id;

    private String comment;

    @DBRef
    private Author author;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getComment() {
	return comment;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

    public Author getAuthor() {
	return author;
    }

    public void setAuthor(Author author) {
	this.author = author;
    }

}
