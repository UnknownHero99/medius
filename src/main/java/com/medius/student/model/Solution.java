package com.medius.student.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "solutions")
public class Solution {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    private Problem problem;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    private Player player;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SolutionStep> solutionSteps = new ArrayList<>();

    public Solution(Problem problem, Player player, List<SolutionStep> solutionSteps) {
        this.problem = problem;
        this.player = player;
        this.solutionSteps = solutionSteps;
    }

    public Solution() {

    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<SolutionStep> getSolutionSteps() {
        return solutionSteps;
    }

    public void setSolutionSteps(List<SolutionStep> solutionSteps) {
        this.solutionSteps = solutionSteps;
    }
}
