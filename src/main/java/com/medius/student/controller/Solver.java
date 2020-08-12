package com.medius.student.controller;


import com.medius.student.model.Problem;
import com.medius.student.model.Solution;
import com.medius.student.model.SolutionStep;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solver {

    public static List<List<Boolean>> toggle(List<List<Boolean>> matrix, int x, int y){
        if(matrix.size() >= y && y >= 0){

            List<Boolean> row = matrix.get(y);
            for(int i = x-1;i<=x+1;i++){                                                                  // 0   1   0   1
                if(i < row.size() && i>= 0) row.set(i, !row.get(i)); //in inside matrix toggle x axis     // 0   T  T(X) T  T= Toggled
            }                                                                                             // 0   1   1   1
            matrix.set(y, row);

            if(y-1 < matrix.size() && y-1 >= 0) {      //0 1 T 1
                row = matrix.get(y - 1);               //0 1 X 1  T= Toggled
                row.set(x, !row.get(x));               //0 1 1 1
                matrix.set(y-1, row);
            }

            if(y+1 < matrix.size() && y+1 >= 0) {      //0 1 0 1
                row = matrix.get(y + 1);               //0 1 X 1  T= Toggled
                row.set(x, !row.get(x));               //0 1 T 1
                matrix.set(y+1, row);
            }

        }
        return matrix;
    }

    public static boolean solutionValid(Solution solution){
        List<List<Boolean>> matrix = new ArrayList<>(solution.getProblem().getProblem().stream().map(x -> new ArrayList<>(x)).collect(Collectors.toList()));
        System.out.println(matrix);
        for (SolutionStep step: solution.getSolutionSteps()) {
            matrix = toggle(matrix,step.getToggleCoordinateX(), step.getToggleCoordinateY());
            System.out.println(matrix);
        }
        if(matrixSolved(matrix)) return true;
        return false;
    }


    public static boolean matrixSolved(List<List<Boolean>> matrix){
        for (List<Boolean> row : matrix) {
            for (Boolean value : row) {
                if(value)return false;
            }
        }
        return true;
    }

    static int KthBit(int n, int k)
    {
        return (n >> k) & 1;
    }

    public static boolean rowSolved(List<Boolean> row){
        for(int i=0; i <= row.size()-1; i++) if(row.get(i) == true)return false;
        return true;
    }

    public static List<List<Boolean>> chaseLights(List<List<Boolean>> matrix){
        List<SolutionStep> solutionSteps = new ArrayList<>();
        int count = 0;
        for(int y = 0; y < matrix.size()-1; y++) {
            List<Boolean> row = matrix.get(y);
            for (int x = 0; x < row.size(); x++) {
                if (row.get(x) == true && y+1 < matrix.size()) {
                    matrix = toggle(matrix, x, y + 1);
                }
            }
        }
        return matrix;
    }

    public static boolean solve(Problem problem){
        long start = System.currentTimeMillis();
        long stop;
        System.out.println("Started solving problem");

        List<List<Boolean>> matrix = problem.getProblemClone(problem.getProblem());
        matrix = chaseLights(matrix);
        List<List<Boolean>> clone = problem.getProblemClone(matrix);
        if(matrixSolved(matrix)) return true;
        List<List<Boolean>> matrixbkp = new ArrayList<>(matrix.stream().map(x -> new ArrayList<>(x)).collect(Collectors.toList()));

        for(int i = 0; i<(int)Math.pow(2,matrix.get(0).size()); i++){
            List<Boolean> row = matrix.get(0);
            for(int c =0; c < row.size(); c++){
                if(KthBit(i, c) == 1)toggle(matrix, c, 0);
            }
            matrix = chaseLights(matrix);
            if(matrixSolved(matrix)){
                stop = System.currentTimeMillis();
                System.out.println("Stopped solving problem");
                System.out.println("Time elapsed: " + (stop-start/1000));
                return true;
            }
            matrix = problem.getProblemClone(clone);
        }
        stop = System.currentTimeMillis();
        System.out.println("Stopped solving problem");
        System.out.println("Time elapsed: " + (stop-start/1000));
        return false;
    }
}
