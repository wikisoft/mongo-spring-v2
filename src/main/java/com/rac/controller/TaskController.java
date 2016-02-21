package com.rac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rac.model.Task;
import com.rac.service.TaskService;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/taskSize", method = RequestMethod.GET)
    public @ResponseBody int getTaskCount() {
	return taskService.getTasks().size();
    }

    @RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
    public @ResponseBody Task getTask(@PathVariable("id") final String id) {
	return taskService.getTask(id);
    }

    @RequestMapping(value = "/taskList", method = RequestMethod.GET)
    public @ResponseBody List<Task> getTaskList() {
	return taskService.findAllOrderByTitleAsc();
    }

    @RequestMapping(value = "/saveTask", method = RequestMethod.POST)
    public @ResponseBody List<Task> saveTask(@RequestBody final Task task) {
	taskService.save(task);
	return taskService.findAllOrderByTitleAsc();
    }

    @RequestMapping(value = "/deleteTask", method = RequestMethod.POST)
    public @ResponseBody List<Task> deleteTask(@RequestBody final Task task) {
	taskService.deleteTask(task);
	return taskService.findAllOrderByTitleAsc();
    }

}
