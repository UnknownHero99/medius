package com.medius.student.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "problems")
public class Problem implements Cloneable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Matrix matrix;

    public Problem(List<List<Boolean>> problem) {
        this.matrix = new Matrix(problem);
    }

    public Problem() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<List<Boolean>> getProblem() {
        return matrix.getMatrix();
    }

    public List<List<Boolean>> getProblemClone(List<List<Boolean>> original) {
        return new ArrayList<>(original.stream().map(x -> new ArrayList<>(x)).collect(Collectors.toList()));
    }

    public void setProblem(List<List<Boolean>> problem) {
        this.matrix = new Matrix(problem);
    }

}
