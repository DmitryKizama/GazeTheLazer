package com.gazethelazer.fotongames.gazethelazer.controller;

import com.gazethelazer.fotongames.gazethelazer.api.MatrixAPI;
import com.gazethelazer.fotongames.gazethelazer.api.Player;
import com.gazethelazer.fotongames.gazethelazer.api.Square;

import java.util.ArrayList;

public class ControllerGame {

    MatrixAPI model;
    private Square[][] field;
    int height;
    int widht;

    ArrayList<Player> mPlayers = new ArrayList<Player>();
    Player mCurrentPlayer;
    Square[][] mLastTurnField;
    ControllerDraw mControllerDraw;

    public ControllerGame(MatrixAPI matrix) {
        model = matrix;
        field = matrix.getMatrix();
    }

    int calculateScore(Square[][] latestField, Square[][] lastTurnField)
    {
        int score = 0;

        for (int i = 0; i < widht; i++)
        {
            for (int j = 0; j < height; j++)
            {
                boolean latestSquareBordered, lastTurnSquareBordered;

                latestSquareBordered = checkSquareBorder(latestField, i, j);
                lastTurnSquareBordered = checkSquareBorder(lastTurnField, i, j);

                if (!lastTurnSquareBordered && latestSquareBordered)
                    score++;
            }
        }

        return score;
    }

    boolean checkSquareBorder(Square[][] matrix, int x, int y)
    {
        boolean left = false, top = false, right = false, bottom = false;

        if (x==0 || matrix[x-1][y]==null || matrix[x][y].bottom_side)
        {
            left = true;
        }
        if (y==0 || matrix[x][y-1]==null || matrix[x][y].right_side)
        {
            top = true;
        }
        if (x==widht || matrix[x+1][y]==null || matrix[x+1][y].bottom_side)
        {
            right = true;
        }
        if (y==height || matrix[x][y+1]==null || matrix[x][y+1].right_side)
        {
            bottom = true;
        }

        return left && top && right && bottom;
    }

    public void setControllerDraw(ControllerDraw controllerDraw)
    {
        mControllerDraw = controllerDraw;
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

    public void turn(int x, int y, int axisX, int axisY) {
        int iX;
        int iY;
        if (axisX != 0) {
            iX = whileX(x, y, axisX);
            if (field[iX][y] == null) {
                whileY(iX, y, 1);
            }
            if (field[iX][y - 1] == null) {
                whileY(iX, y, -1);
            }
        }
        if (axisY != 0) {
            iY = whileY(x, y, axisY);
            if (field[iY][y] == null) {
                whileY(iY, y, 1);
            }
            if (field[iY][y - 1] == null) {
                whileY(iY, y, -1);
            }
        }

        mControllerDraw.render(model);
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

    private int whileY(int x, int y, int sign) {
        int i = 0;
        if (field[x][y] == null)
            i = 1;
        while (field[x][y + i * sign] != null && field[x - 1][y + i * sign] != null) {
            field[x][y + i * sign].bottom_side = true;
            i++;
        }
        return (y + i * sign);
    }

    private int whileX(int x, int y, int sign) {
        int i = 0;
        if (field[x][y] == null)
            i = 1;
        while (field[x + i * sign][y] != null && field[x + i * sign][y - 1] != null) {
            field[x + i * sign][y].right_side = true;
            i++;
        }
        return (x + i * sign);
    }

    public Square[][] getField() {
        return field;
    }

}
