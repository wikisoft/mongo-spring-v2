package com.rac.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Comment {

    private String id;

    private String comment;

    private int rating;

    private Date date;

    @DBRef
    private Post post;

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

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public int getRating() {
	return rating;
    }

    public void setRating(int rating) {
	this.rating = rating;
    }

    public Post getPost() {
	return post;
    }

    public void setPost(Post post) {
	this.post = post;
    }

    @Override
    public String toString() {
	return "Comment [id=" + id + ", comment=" + comment + ", rating="
		+ rating + ", date=" + date + "]";
    }

}
