package com.medius.student.model;

import javax.persistence.*;

@Entity
@Table(name = "solution_steps")
public class SolutionStep {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private int toggleCoordinateX;
    private int toggleCoordinateY;
    int step;

    public SolutionStep(int toggleCoordinateX, int toggleCoordinateY, int step) {
        this.toggleCoordinateX = toggleCoordinateX;
        this.toggleCoordinateY = toggleCoordinateY;
        this.step = step;
    }

    public SolutionStep() {

    }

    public int getToggleCoordinateX() {
        return toggleCoordinateX;
    }

    public void setToggleCoordinateX(int toggleCoordinateX) {
        this.toggleCoordinateX = toggleCoordinateX;
    }

    public int getToggleCoordinateY() {
        return toggleCoordinateY;
    }

    public void setToggleCoordinateY(int toggleCoordinateY) {
        this.toggleCoordinateY = toggleCoordinateY;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
