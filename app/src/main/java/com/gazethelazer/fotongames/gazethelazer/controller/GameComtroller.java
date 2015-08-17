package com.gazethelazer.fotongames.gazethelazer.controller;

import com.gazethelazer.fotongames.gazethelazer.api.MatrixAPI;
import com.gazethelazer.fotongames.gazethelazer.api.Square;

public class GameComtroller {

    private boolean onlyOneWay = false;
    private Square[][] field;
    int height;
    int widht;

    public GameComtroller(MatrixAPI matrix) {
        field = matrix.getMatrix();
    }

    public int[] checkForAvailableAdges(int x, int y) {
        int[] turn = new int[4];
        for (int i = 0; i < 4; i++) {
            turn[i] = 0;
        }

        widht = field[0].length;
        height = field.length;

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

        return turn;
    }

    public void turn(int x, int y, int axisX, int asixY) {
        widht = field[0].length;
        height = field.length;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < widht; j++) {

                if (field[y][x] != null) {
                    if (field[y - 1][x] != null)
                        field[y][x].right_side = true;
                    else
                        field[i + 1][j].bottom_side = true;
                }

                if (field[i + 1][j] == null) {
                    if (field[i + 1][j + 1] != null)
                        field[i + 1][j + 1].bottom_side = true;
                }
            }
        }
    }

    private void choosenWay() {

    }

}
