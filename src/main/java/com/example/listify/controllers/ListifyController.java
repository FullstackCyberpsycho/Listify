package com.example.listify.controllers;

import com.example.listify.models.Task;
//import com.example.listify.repository.ListifyRepository;
import com.example.listify.services.TaskServices;
import jakarta.validation.Valid;
//import jakarta.validation.Validation;
//import jakarta.validation.constraints.Past;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/listify")
public class ListifyController {
    private TaskServices services;

    @Autowired
    public ListifyController(TaskServices services) {
        this.services = services;
    }

    @GetMapping
    public String getMainMenu() {
        return "listify/mainMenu";
    }

    @GetMapping("/add-task")
    public String getAddTask(Model model) {
        model.addAttribute("task", new Task());
        return "listify/add-task";
    }

    @GetMapping("/list-task")
    public String getListTask(Model model) {
        model.addAttribute("tasks", services.findAll());
        return "listify/list-task";
    }

    @PostMapping()
    public String addTask(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "listify/add-task";
        } else {
            services.save(task);
            return "redirect:/listify/list-task";
        }
    }

    @GetMapping("/{id}/delete")
    public String getDeleteTask(@PathVariable("id") long id, Model model) {
        model.addAttribute("task", services.findById(id));
        return "listify/delete-task";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteTask(@PathVariable("id") long id) {
        services.deleteById(id);
        return "redirect:/listify/list-task";
    }

    @GetMapping("/{id}")
    public String getTask(@PathVariable("id") long id, Model model) {
        model.addAttribute("task", services.findById(id));
        return "listify/task";
    }

    @GetMapping("/{id}/update")
    public String getUpdateTask(@PathVariable("id") long id, Model model) {
        model.addAttribute("task", services.findById(id));
        return "listify/update";
    }

    @PutMapping("/{id}/update")
    public String updateTask(@PathVariable("id") long id, @ModelAttribute("task") @Valid Task task,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "listify/update";
        } else {
            services.update(id, task.getName());
            return "redirect:/listify/list-task";
        }
    }
}
