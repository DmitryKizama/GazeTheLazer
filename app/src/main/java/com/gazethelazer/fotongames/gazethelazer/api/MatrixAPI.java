package com.gazethelazer.fotongames.gazethelazer.api;

import java.util.Random;

public class MatrixAPI {

    private int[][] matrix;
    private int widthsM;
    private int heightsM;
    private Random random = new Random();

    public MatrixAPI(int widths, int heights) {
        matrix = new int[widths][];
        this.heightsM = heights;
        this.widthsM = widths;
        for (int i = 0; i < widths; i++) {
            matrix[i] = new int[heights];
        }

        for (int i = 0; i < widths; i++) {
            for (int j = 0; j < heights; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public int[][] getMatrix(int width, int height) {
        MatrixAPI m = new MatrixAPI(width, height);

        setRandomMaap();

        return matrix;
    }

    private void setRandomMaap() {
        for (int i = 1; i < widthsM; i++) {
            for (int j = 1; j < heightsM; j++) {
                matrix[Math.round(widthsM / 6)][Math.round(heightsM / 6)] = 1;
                matrix[Math.round(widthsM / 6)][Math.round(heightsM / 6) + 1] = 1;
                matrix[Math.round(widthsM / 6) + 1][Math.round(heightsM / 6) + 1] = 1;
                if (random.nextBoolean()) {
                    if (matrix[i - 1][j] == 1)
                        matrix[i][j] = 1;

                    if (matrix[i][j - 1] == 1)
                        matrix[i][j] = 1;

                    if (matrix[i][j + 1] == 1)
                        matrix[i][j] = 1;

                    if (matrix[i + 1][j] == 1)
                        matrix[i][j] = 1;

                }
            }
        }
    }

}
