package com.gazethelazer.fotongames.gazethelazer.api;

import android.util.Log;

import com.gazethelazer.fotongames.gazethelazer.static_and_final_variables.Final;

import java.util.Random;

public class MatrixAPI {

    private Square[][] matrix;
    private int widthsM;
    private int heightsM;
    private Random random = new Random();

    public MatrixAPI(int widths, int heights) {
        matrix = new Square[heights][widths];
        this.heightsM = heights;
        this.widthsM = widths;
    }

    public Square[][] generateMatrix() {
        setRandomMap();
        return matrix;
    }

    public Square[][] getMatrix() {
        return matrix;
    }

    private void setRandomMap() {
        Log.d("Matrix", "Enter in setRandomMap");
        int centerW = Math.round(widthsM / 2);
        int centerH = Math.round(heightsM / 2);
        int numberOfRepeat = 1;
        Square square = new Square();
        matrix[centerW][centerH] = square;
        for (int i = 0; i < Final.MIN_SIZE_PLAYGROUND; i++) {
            for (int j = 0; j < Final.MIN_SIZE_PLAYGROUND; j++) {
                matrix[centerW - i][centerH - j] = square;
                matrix[centerW - i][centerH + j] = square;
                matrix[centerW - j][centerH - i] = square;
                matrix[centerW + j][centerH - i] = square;
                matrix[centerW + j][centerH + i] = square;
            }
        }

        if (widthsM > 55 && heightsM > 55)
            numberOfRepeat = 2;
        if (widthsM > 110 && heightsM > 110)
            numberOfRepeat = 4;
        if (widthsM > 165 && heightsM > 165)
            numberOfRepeat = 6;
        if (widthsM > 220 && heightsM > 220)
            numberOfRepeat = 8;

        for (int k = 0; k < numberOfRepeat; k++) {

            for (int i = 1; i < widthsM - 1; i++) {
                for (int j = 1; j < heightsM - 1; j++) {
                    if (random.nextBoolean()) {
                        if (matrix[i][j - 1] != null)
                            matrix[i][j] = square;
                        if (matrix[i - 1][j] != null)
                            matrix[i][j] = square;
                        if (matrix[i + 1][j] != null)
                            matrix[i][j] = square;
                        if (matrix[i][j + 1] != null)
                            matrix[i][j] = square;
                    }
                }
            }
            for (int i = widthsM - 2; i > 1; i--) {
                for (int j = heightsM - 2; j > 1; j--) {
                    if (random.nextBoolean()) {
                        if (matrix[i][j - 1] != null)
                            matrix[i][j] = square;
                        if (matrix[i - 1][j] != null)
                            matrix[i][j] = square;
                        if (matrix[i + 1][j] != null)
                            matrix[i][j] = square;
                        if (matrix[i][j + 1] != null)
                            matrix[i][j] = square;
                    }
                }
            }
        }

//        for (int i = 0; i < matrix.length; i++) {
//            Square[] newmatrix = new Square[matrix.length];
//            for (int j = 0; j < matrix.length; j++) {
//                newmatrix[j] = matrix[i][j];
//            }
//            Log.d("Matrix", "" + Arrays.toString(newmatrix));
//        }


    }

}
