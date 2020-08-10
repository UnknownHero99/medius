package com.medius.student.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "problems")
public class Problem {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Matrix problem;

    public Problem(List<List<Boolean>> problem) {
        this.problem = new Matrix(problem);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<List<Boolean>> getProblem() {
        return problem.getMatrix();
    }

    public void setProblem(List<List<Boolean>> problem) {
        this.problem = new Matrix(problem);
    }

}
