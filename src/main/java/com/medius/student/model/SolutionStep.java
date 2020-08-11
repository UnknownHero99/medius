package com.medius.student.model;

import javax.persistence.*;

@Entity
@Table(name = "solution_steps")
public class SolutionStep {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Matrix operation;
    int step;

}
