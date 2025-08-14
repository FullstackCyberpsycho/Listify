package com.example.listify.services;

import com.example.listify.models.Task;
import com.example.listify.repository.ListifyRepository;
import jakarta.persistence.EntityNotFoundException;
//import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServices {
    private ListifyRepository repository;

    public TaskServices(ListifyRepository repository) {
        this.repository = repository;
    }

    //@CacheEvict(value = "tasks", allEntries = true)
    //@CacheEvict("tasks")
    @CacheEvict(value = "tasks", allEntries = true)
    public void save(Task task) {
        repository.save(task);
    }

    //@Cacheable(value = "tasks")
    @Cacheable("tasks")
    public List<Task> findAll() {
        return repository.findAll();
        //return repository.findAllByUserUsername(username);
    }

    //@Cacheable(value = "task", key = "#id")
    @Cacheable(value = "task", key = "#id", unless = "#result == null or #result.name == null")
    public Task findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));
    }

    //@CacheEvict(value = "tasks", allEntries = true)
    //@CacheEvict(value = "task", key = "#id")

    //@CacheEvict(value = "tasks", allEntries = true)      // очистить кэш списка
    //@CacheEvict(value = "task", key = "#id")
    @CacheEvict(value = {"task", "tasks"}, key = "#id", allEntries = true)
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    //@CacheEvict(value = "tasks", allEntries = true)
    //@CacheEvict(value = "task", key = "#id")
    @CacheEvict(value = {"task", "tasks"}, key = "#id", allEntries = true)
    public void update(long id, String newName) {
        Optional<Task> optionalTask = repository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setName(newName);
            repository.save(task);
        } else {
            throw new EntityNotFoundException("Задача не найдена");
        }
    }
}
