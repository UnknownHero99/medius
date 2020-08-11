package com.medius.student.controller;


import java.util.List;

public class Solver {

    public static List<List<Boolean>> toggle(List<List<Boolean>> matrix, int x, int y){
        if(matrix.size() >= y && y >= 0){

            List<Boolean> row = matrix.get(y);
            for(int i = x-1;i<=x+1;i++){                                                                // 0   1   0   1
                if(i < row.size() && i>= 0) row.set(i, !row.get(i)); //in inside matrix toggle x axis   // 0   T  T(X) T  T= Toggled
            }                                                                                           // 0   1   1   1
            matrix.set(y, row);

            if(y-1 < matrix.size() && y-1 >= 0) {      //0 1 T 1
                row = matrix.get(y - 1);               //0 1 X 1  T= Toggled
                row.set(x, !row.get(x));                //0 1 1 1
                matrix.set(y-1, row);
            }

            if(y+1 < matrix.size() && y+1 >= 0) {      //0 1 0 1
                row = matrix.get(y + 1);               //0 1 X 1  T= Toggled
                row.set(x, !row.get(x));                //0 1 T 1
                matrix.set(y+1, row);
            }

        }
        return matrix;
    }

    public static boolean rowTurnedOff(List<Boolean> row){
        for(int i=0; i <= row.size()-1; i++) if(row.get(i) == true)return false;
        return true;
    }

    public static boolean solve(List<List<Boolean>> matrix){

        for(int y = 0; y <= matrix.size()-2; y++){
            List<Boolean> row = matrix.get(y);
            for(int x = 0; x <= row.size()-1; x++){
                if(row.get(x) == true && y < matrix.size()) matrix = toggle(matrix, x, y+1);
            }
        }

        if(!rowTurnedOff(matrix.get(matrix.size()-1))) {
            System.out.println(matrix);
            return false;
        }
        return true;
    }
}
