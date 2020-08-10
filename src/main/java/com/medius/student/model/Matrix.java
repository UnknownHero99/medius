package com.medius.student.model;

import java.io.Serializable;
import java.util.List;

public class Matrix implements Serializable {
    private List<List<Boolean>> matrix;

    public Matrix(List<List<Boolean>> matrix) {
        this.matrix = matrix;
    }

    public List<List<Boolean>> getMatrix() {
        return matrix;
    }

    public void setMatrix(List<List<Boolean>> matrix) {
        this.matrix = matrix;
    }
}
