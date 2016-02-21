package com.rac.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rac.model.Task;

public interface TaskRepository extends MongoRepository<Task, String> {

    public List<Task> findAllByOrderByTitleAsc();

}
