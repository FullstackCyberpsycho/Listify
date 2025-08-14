package com.example.listify.controllers;

import com.example.listify.models.Task;
//import com.example.listify.repository.ListifyRepository;
//import com.example.listify.models.User;
//import com.example.listify.repository.UserRepository;
import com.example.listify.services.TaskServices;
import jakarta.validation.Valid;
//import jakarta.validation.Validation;
//import jakarta.validation.constraints.Past;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public String addTask(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult,
                          Principal principal) {

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

/*@Controller
@RequestMapping("/listify")
public class ListifyController {
    private final TaskServices services;
    private final UserRepository userRepository;

    @Autowired
    public ListifyController(TaskServices services, UserRepository userRepository) {
        this.services = services;
        this.userRepository = userRepository;
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

//    @GetMapping("/list-task")
//    public String getListTask(Model model, Principal principal) {
//        model.addAttribute("tasks", services.findAll(principal.getName()));
//        return "listify/list-task";
//    }

    @GetMapping("/list-task")
    public String getListTask(Model model, Principal principal) {
        model.addAttribute("tasks", services.findAll(principal.getName()));
        return "listify/list-task";
    }

    @PostMapping()
    public String addTask(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult,
                          Principal principal) {
        if (bindingResult.hasErrors()) {
            return "listify/add-task";
        } else {
            User user = userRepository.findByUsername(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            task.setUser(user);
            services.save(task);
            return "redirect:/listify/list-task";
        }
    }

    private Task getTaskForUser(long id, Principal principal) {
        Task task = services.findById(id);
        if (task == null) {
            throw new RuntimeException("Задача не найдена");
        }
        if (!task.getUser().getUsername().equals(principal.getName())) {
            throw new RuntimeException("Нет доступа к этой задаче");
        }
        return task;
    }

//    private Task getTaskForUser(long id, Principal principal) {
//        return services.findAll(id, principal.getName())
//                .orElseThrow(() -> new RuntimeException("Задача не найдена или нет доступа"));
//    }



    @DeleteMapping("/{id}/delete")
    public String deleteTask(@PathVariable("id") long id, Principal principal) {
        Task task = getTaskForUser(id, principal);
        services.deleteById(task.getId());
        return "redirect:/listify/list-task";
    }

    @GetMapping("/{id}")
    public String getTask(@PathVariable("id") long id, Model model, Principal principal) {
        model.addAttribute("task", getTaskForUser(id, principal));
        return "listify/task";
    }

    @GetMapping("/{id}/update")
    public String getUpdateTask(@PathVariable("id") long id, Model model, Principal principal) {
        model.addAttribute("task", getTaskForUser(id, principal));
        return "listify/update";
    }

    @PutMapping("/{id}/update")
    public String updateTask(@PathVariable("id") long id,
                             @ModelAttribute("task") @Valid Task task,
                             BindingResult bindingResult,
                             Principal principal) {
        Task existingTask = getTaskForUser(id, principal);

        if (bindingResult.hasErrors()) {
            return "listify/update";
        }

        existingTask.setName(task.getName());
        services.save(existingTask);

        return "redirect:/listify/list-task";
    }
}
*/
