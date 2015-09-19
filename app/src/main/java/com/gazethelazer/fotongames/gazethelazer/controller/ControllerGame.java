package com.gazethelazer.fotongames.gazethelazer.controller;

import android.util.Log;

import com.gazethelazer.fotongames.gazethelazer.api.MatrixAPI;
import com.gazethelazer.fotongames.gazethelazer.api.Player;
import com.gazethelazer.fotongames.gazethelazer.api.Square;

import java.util.ArrayList;

public class ControllerGame {

    MatrixAPI model;
    private Square[][] field;
    int height;
    int width;
    int totalScore = 0;
    boolean nextTurnIsOver;

    int myAxisX;
    int myAxisY;

    ArrayList<Player> mPlayers = new ArrayList<>();
    Player mCurrentPlayer;

    Square[][] mLastTurnField;
    ControllerDraw mControllerDraw;

    public ControllerGame(MatrixAPI matrix) {
        model = matrix;
        field = matrix.getMatrix();

        width = field[0].length;
        height = field.length;
    }

    public void addPlayer()
    {
        Player p = new Player(mPlayers.size());
        if (mPlayers.size() == 0)
            mCurrentPlayer = p;

        mPlayers.add(p);
    }

    public Player getCurrentPlayer()
    {
        return mCurrentPlayer;
    }

    public void setCurrentPlayer(Player p)
    {
        mCurrentPlayer = p;
    }

    public Player getPlayer(int n)
    {
        return mPlayers.get(n);
    }

    public int getPlayersNumber()
    {
        return mPlayers.size();
    }

    public void moveToNextPlayer()
    {
        if (getCurrentPlayer().getNumber() == getPlayersNumber()-1)
            setCurrentPlayer(getPlayer(0));
        else
            setCurrentPlayer(getPlayer(getCurrentPlayer().getNumber()+1));
    }

    int calculateScore(Square[][] latestField, Square[][] lastTurnField) {
        int score = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boolean latestSquareBordered, lastTurnSquareBordered;

                latestSquareBordered = checkSquareBorder(latestField, i, j);
                lastTurnSquareBordered = checkSquareBorder(lastTurnField, i, j);

                if (!lastTurnSquareBordered && latestSquareBordered)
                    score++;
            }
        }

        return score;
    }

    boolean checkSquareBorder(Square[][] matrix, int i, int j) {
        boolean left = false, top = false, right = false, bottom = false;

        if (matrix[i][j] != null) {
            if (j == 0 || matrix[i][j - 1] == null || matrix[i][j].bottom_side) {
                left = true;
            }
            if (i == 0 || matrix[i - 1][j] == null || matrix[i][j].right_side) {
                top = true;
            }
            if (j == width - 1 || matrix[i][j + 1] == null || matrix[i][j + 1].bottom_side) {
                right = true;
            }
            if (i == height - 1 || matrix[i + 1][j] == null || matrix[i + 1][j].right_side) {
                bottom = true;
            }
        }

        return left && top && right && bottom;
    }

    public void setControllerDraw(ControllerDraw controllerDraw) {
        mControllerDraw = controllerDraw;
    }

    public int[] checkForAwailableEdges(int x, int y) {
        int[] turn = new int[4];
        for (int i = 0; i < 4; i++) {
            turn[i] = 0;
        }

        if (x > 0 && y > 0 && x < width && y < height) {
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
        int iX = x;
        int iY = y;
        int i = 0;
        myAxisX = axisX;
        myAxisY = axisY;
        nextTurnIsOver = false;

        mLastTurnField = cloneField(field);

        while (!nextTurnIsOver) {
            i++;
            if (myAxisX != 0) {
                Log.d("ControllerGame", "axisX = " + myAxisX);
                iX = whileX(iX, iY, myAxisX);
            }
            if (myAxisY != 0) {
                Log.d("ControllerGame", "axisY = " + myAxisY);
                iY = whileY(iX, iY, myAxisY);
            }
            if (i == 40) {
                nextTurnIsOver = true;
            }
        }

        int curscore = calculateScore(field, mLastTurnField);
        totalScore += curscore;
        getCurrentPlayer().addScore(curscore);

        if (totalScore == model.getNotEmptySquares())
        {
            Log.i("lazer", "Game over");
        }
        else
        {
            moveToNextPlayer();
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
        if (field[y][x] == null || field[y][x - 1] == null)
            i = 1;
        while (field[y - i * sign][x] != null && field[y - i * sign][x - 1] != null) {
            Log.d("ControllerGame", "enter in while Y");
            field[y - i * sign][x].bottom_side = true;
            i++;
        }
        if (field[y - i * sign][x] == null && field[y - i * sign][x - 1] == null)
            nextTurnIsOver = true;

        if (field[y - i * sign][x] != null && field[y - i * sign][x - 1] == null) {
            myAxisX = 1;
            if (myAxisY == 1) {
                myAxisY = 0;
                return (y - i * sign + 1 * sign);
            }
            else {
                myAxisY = 0;
                return (y - i * sign);
            }
        }

        if (field[y - i * sign][x] == null && field[y - i * sign][x - 1] != null) {
            myAxisX = -1;
            if (myAxisY == 1) {
                myAxisY = 0;
                return (y - i * sign + 1 * sign);
            }
            else {
                myAxisY = 0;
                return (y - i * sign);
            }
        }

        return (y - i * sign + 1 * sign);
    }

    private int whileX(int x, int y, int sign) {
        int i = 0;
        if (field[y][x] == null || field[y - 1][x] == null)
            i = 1;
        while (field[y][x + i * sign] != null && field[y - 1][x + i * sign] != null) {
            Log.d("ControllerGame", "enter in while X");
            field[y][x + i * sign].right_side = true;
            i++;

        }
        if (field[y][x + i * sign] == null && field[y - 1][x + i * sign] == null)
            nextTurnIsOver = true;

        if (field[y][x + i * sign] != null && field[y - 1][x + i * sign] == null) {
            myAxisY = -1;
            if (myAxisX == -1) {
                myAxisX = 0;
                return (x + i * sign - 1 * sign);
            }
            else {
                myAxisX = 0;
                return (x + i * sign);
            }
        }
        if (field[y][x + i * sign] == null && field[y - 1][x + i * sign] != null) {
            myAxisY = 1;
            if (myAxisX == -1) {
                myAxisX = 0;
                return (x + i * sign - 1 * sign);
            }
            else {
                myAxisX = 0;
                return (x + i * sign);
            }
        }

        return (x + i * sign - 1 * sign);
    }

    public Square[][] getField() {
        return field;
    }

    public Square[][] cloneField(Square[][] input)
    {
        int height = input.length;
        int width = input[0].length;

        Square[][] output = new Square[height][width];

        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (input[i][j] != null)
                    output[i][j] = new Square(input[i][j].right_side, input[i][j].bottom_side);
            }
        }

        return output;
    }
}
