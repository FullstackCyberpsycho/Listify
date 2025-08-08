package com.example.listify.services;

import com.example.listify.models.Task;
import com.example.listify.repository.ListifyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServices2 {
    private ListifyRepository repository;

    @Autowired
    public TaskServices2(ListifyRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public Task save(Task task) {
        return repository.save(task);
    }

    @Cacheable("tasks")
    public List<Task> findAll() {
        System.out.println("Выполняется запрос к базе данных...");
        return repository.findAll();
    }

    @Cacheable(value = "task", key = "#id", unless = "#result == null or #result.name == null")
    public Task findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));
    }

    @CacheEvict(value = {"task", "tasks"}, key = "#id", allEntries = true)
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @CacheEvict(value = {"task", "tasks"}, key = "#id", allEntries = true)
    public Task update(long id, String newName) {
        Optional<Task> optionalTask = repository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setName(newName);

            return repository.save(task);
        } else {
            throw new EntityNotFoundException("Задача не найдена");
        }
    }
}
