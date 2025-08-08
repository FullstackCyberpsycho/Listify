package com.example.listify.controllers;

import com.example.listify.models.Task;
import com.example.listify.services.TaskServices2;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listify")
public class ListifyRestController {
    private TaskServices2 services;

    @Autowired
    public ListifyRestController(TaskServices2 services) {
        this.services = services;
    }

    @GetMapping("/list-task")
    public List<Task> getListTask() {
        return services.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Task addTask(@RequestBody @Valid Task task) {
       return services.save(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") long id) {
        services.deleteById(id);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable("id") long id) {
        return services.findById(id);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable("id") long id, @RequestBody @Valid Task task) {
        return services.update(id, task.getName());
    }
}
