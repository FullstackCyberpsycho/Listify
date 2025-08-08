package com.example.listify.repository;

import com.example.listify.models.Task;
//import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ListifyRepository extends JpaRepository<Task, Long> {
}