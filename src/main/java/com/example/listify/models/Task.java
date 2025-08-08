package com.example.listify.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
//import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
//import org.springframework.beans.factory.annotation.Value;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "tasks")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Название задачи не должно быть пустым")
    private String name;
}
