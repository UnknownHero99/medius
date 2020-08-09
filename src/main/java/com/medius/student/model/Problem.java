package com.medius.student.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "problems")
public class Problem {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private List<List<Boolean>> problem;

    @OneToOne(mappedBy = "player")
    private String username; //foreign key

    public Problem(List<List<Boolean>> problem, String username) {
        this.problem = problem;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<List<Boolean>> getProblem() {
        return problem;
    }

    public void setProblem(List<List<Boolean>> problem) {
        this.problem = problem;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
