package com.gazethelazer.fotongames.gazethelazer.api;

import android.util.Log;

import com.gazethelazer.fotongames.gazethelazer.static_and_final_variables.Final;

import java.util.Random;

public class MatrixAPI {

    private Square[][] matrix;
    private int mWidth;
    private int mHeight;
    private Random random = new Random();

    int mNotEmptySquares = 0;

    public MatrixAPI(int heights, int widths) {
        matrix = new Square[heights][widths];
        this.mHeight = heights;
        this.mWidth = widths;
        setRandomMap();

        for (int i = 0; i < mHeight; i++) {
            for (int j = 0; j < mWidth; j++) {
                if (matrix[i][j] != null)
                    mNotEmptySquares++;
            }
        }
    }

    public Square[][] getMatrix() {
        return matrix;
    }

    private void setRandomMap() {
        Log.d("Matrix", "Enter in setRandomMap");
        int centerW = Math.round(mWidth / 2);
        int centerH = Math.round(mHeight / 2);
        int numberOfRepeat = 1;
        matrix[centerH][centerW] = new Square();
        for (int i = 0; i < Final.MIN_SIZE_PLAYGROUND; i++) {
            for (int j = 0; j < Final.MIN_SIZE_PLAYGROUND; j++) {
                matrix[centerH - i][centerW - j] = new Square();
                matrix[centerH - i][centerW + j] = new Square();
                matrix[centerH - j][centerW - i] = new Square();
                matrix[centerH + j][centerW - i] = new Square();
                matrix[centerH + j][centerW + i] = new Square();
            }
        }

        if (mWidth > 55 && mHeight > 55)
            numberOfRepeat = 2;
        if (mWidth > 110 && mHeight > 110)
            numberOfRepeat = 4;
        if (mWidth > 165 && mHeight > 165)
            numberOfRepeat = 6;
        if (mWidth > 220 && mHeight > 220)
            numberOfRepeat = 8;

        for (int k = 0; k < numberOfRepeat; k++) {

            for (int i = 1; i < mHeight - 1; i++) {
                for (int j = 1; j < mWidth - 1; j++) {
                    if (random.nextBoolean()) {
                        if (matrix[i][j - 1] != null)
                            matrix[i][j] = new Square();
                        if (matrix[i - 1][j] != null)
                            matrix[i][j] = new Square();
                        if (matrix[i + 1][j] != null)
                            matrix[i][j] = new Square();
                        if (matrix[i][j + 1] != null)
                            matrix[i][j] = new Square();
                    }
                }
            }
            for (int i = mHeight - 2; i > 1; i--) {
                for (int j = mWidth - 2; j > 1; j--) {
                    if (random.nextBoolean()) {
                        if (matrix[i][j - 1] != null)
                            matrix[i][j] = new Square();
                        if (matrix[i - 1][j] != null)
                            matrix[i][j] = new Square();
                        if (matrix[i + 1][j] != null)
                            matrix[i][j] = new Square();
                        if (matrix[i][j + 1] != null)
                            matrix[i][j] = new Square();
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

    public int getNotEmptySquares()
    {
        return mNotEmptySquares;
    }

}
