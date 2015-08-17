package com.gazethelazer.fotongames.gazethelazer.controller;

import com.gazethelazer.fotongames.gazethelazer.api.MatrixAPI;
import com.gazethelazer.fotongames.gazethelazer.api.Square;

public class ControllerGame {

    private Square[][] field;
    int height;
    int widht;

    public ControllerGame(MatrixAPI matrix) {
        field = matrix.getMatrix();
    }

    public int[] checkForAwailableEdges(int x, int y) {
        int[] turn = new int[4];
        for (int i = 0; i < 4; i++) {
            turn[i] = 0;
        }

        widht = field[0].length;
        height = field.length;

        if (x > 0 && y > 0 && x < widht && y < height) {
            if (field[y][x] != null) {
                if (field[y - 1][x] != null) {
                    if (field[y][x - 1] != null) {
                        if (field[y - 1][x - 1] == null) {
                            turn[2] = 1;
                            turn[1] = 1;
                        }
                    } else {
                        turn[1] = 1;
                        if (field[y - 1][x - 1] != null)
                            turn[0] = 1;
                    }
                } else {
                    if (field[y][x - 1] != null) {
                        turn[2] = 1;
                        if (field[y - 1][x - 1] != null)
                            turn[3] = 1;
                    }
                }
            } else {
                if (field[y - 1][x - 1] != null) {
                    if (field[y - 1][x] != null)
                        turn[0] = 1;

                    if (field[y][x - 1] != null)
                        turn[3] = 1;
                }
            }
        }

        return turn;
    }

    public void turn(int x, int y, int axisX, int asixY) {
        widht = field[0].length;
        height = field.length;
    }

    public int[] getEdgeSingularMove(int[] moves) {
        if (moves[0] == 1)
            return new int[]{0, 1};
        if (moves[1] == 1)
            return new int[]{1, 0};
        if (moves[2] == 1)
            return new int[]{0, -1};
        else
            return new int[]{-1, 0};
    }

}
