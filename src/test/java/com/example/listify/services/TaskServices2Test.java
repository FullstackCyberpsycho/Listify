package com.example.listify.services;

import com.example.listify.models.Task;
import com.example.listify.repository.ListifyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskServices2Test {
    @Mock
    private ListifyRepository repository;

    @InjectMocks
    private TaskServices2 services;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Task task = new Task(1, "Test task");
        services.save(task);

        verify(repository).save(task);
    }

    @Test
    void testFindAll() {
        List<Task> tasks= new ArrayList<>();
        tasks.add(new Task(1, "Task1"));
        tasks.add(new Task(2, "Task2"));

        when(repository.findAll()).thenReturn(tasks);

        List<Task> res = services.findAll();

        assertAll(
                () -> assertEquals(2, res.size()),
                () -> assertEquals("Task1", res.get(0).getName())
        );
    }

    @Test
    void testFindById() {
        Task task = new Task(1, "Task");
        when(repository.findById((long)1)).thenReturn(Optional.of(task));

        Task res = services.findById(1);

        assertEquals("Task", res.getName());
    }
}
