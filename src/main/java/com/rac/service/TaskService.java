package com.rac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.rac.dao.TaskRepository;
import com.rac.dao.UserRepository;
import com.rac.model.Task;
import com.rac.model.User;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
	List<User> users = userRepository.findAll();
	return users;
    }

    public List<Task> getTasks() {
	return Lists.newArrayList(taskRepository.findAll());
    }

    public List<Task> findAllOrderByTitleAsc() {
	return Lists.newArrayList(taskRepository.findAllByOrderByTitleAsc());
    }

    private Sort sortByIdAsc() {
	return new Sort(Sort.Direction.ASC, "id");
    }

    public Task getTask(final String id) {
	return taskRepository.findOne(id);
    }

    public void save(final Task task) {
	taskRepository.save(task);
    }

    public void deleteAllTasks() {
	taskRepository.deleteAll();
    }

    public void deleteTask(final Task task) {
	taskRepository.delete(task);
    }

}
