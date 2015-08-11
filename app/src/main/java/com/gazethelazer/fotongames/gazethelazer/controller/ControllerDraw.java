package com.gazethelazer.fotongames.gazethelazer.controller;

import android.graphics.Color;

import com.gazethelazer.fotongames.gazethelazer.api.MatrixAPI;
import com.gazethelazer.fotongames.gazethelazer.api.Square;

public class ControllerDraw {

    // FIXME: это пиздец
    int[][][][] mRenderedArray;

    int mSquareSize;
    int mWidthInSquares;
    int mHeightInSquares;

    public ControllerDraw(int square_size)
    {
        mSquareSize = square_size;
    }

    public ControllerDraw()
    {
    }

    public void render(MatrixAPI matrix)
    {
        Square[][] field = matrix.generateMatrix();

        mWidthInSquares = field[0].length;
        mHeightInSquares = field.length;

        mRenderedArray = new int[mHeightInSquares][mWidthInSquares][2][5];

        for (int i = 0; i <  mHeightInSquares; i++)
        {
            for (int j = 0; j < mWidthInSquares; j++)
            {
                int color_right, color_down;

                // fuck this shit
                if (field[i][j] == null)
                {
                    if (i == 0)
                    {
                        color_right = Color.GRAY;
                    }
                    else if (field[i-1][j] != null)
                    {
                        color_right = Color.BLACK;
                    }
                    else
                    {
                        color_right = Color.GRAY;
                    }

                    if (j == 0)
                    {
                        color_down = Color.GRAY;
                    }
                    else if (field[i][j-1] != null)
                    {
                        color_down = Color.BLACK;
                    }
                    else
                    {
                        color_down = Color.GRAY;
                    }
                }
                // even more of this shit
                else
                {
                    if (i == 0)
                    {
                        color_right = Color.BLACK;
                    }
                    else if (field[i-1][j] != null)
                    {
                        color_right = Color.BLACK;
                    }
                    else
                    {
                        if (field[i][j].right_side)
                        {
                            color_right = Color.RED;
                        }
                        else
                        {
                            color_right = Color.GRAY;
                        }
                    }

                    // please kill me
                    if (j == 0)
                    {
                        color_down = Color.BLACK;
                    }
                    else if (field[i][j-1] != null)
                    {
                        color_down = Color.BLACK;
                    }
                    else
                    {
                        if (field[i][j].bottom_side)
                        {
                            color_down = Color.RED;
                        }
                        else
                        {
                            color_down = Color.GRAY;
                        }
                    }
                    // fuck my life
                }

                int square_size = getSquareSize();

                int[] line_right = {j*square_size, i*square_size,
                        j*square_size + square_size, i*square_size, color_right};

                int[] line_down = {j*square_size, i*square_size,
                        j*square_size, i*square_size + square_size, color_down};

                mRenderedArray[i][j][0] = line_right;
                mRenderedArray[i][j][1] = line_down;
            }
        }

    }

    public int getSquareCoordsX(float x)
    {
        return Math.round(x/getSquareSize());
    }

    public int getSquareCoordsY(float y)
    {
        return Math.round(y/getSquareSize());
    }

    public int[][][][] getRenderedArray()
    {
        return mRenderedArray;
    }

    public int getSquareSize()
    {
        return mSquareSize;
    }

    public void setSquareSize(int new_size)
    {
        mSquareSize = new_size;
    }

    public int getWidth()
    {
        return mWidthInSquares*getSquareSize();
    }

    public int getHeight()
    {
        return mHeightInSquares*getSquareSize();
    }

    public int getmWidthInSquares()
    {
        return mWidthInSquares;
    }

    public int getHeightInSquares()
    {
        return mHeightInSquares;
    }
}
