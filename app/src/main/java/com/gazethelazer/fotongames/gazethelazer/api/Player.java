package com.gazethelazer.fotongames.gazethelazer.api;

public class Player
{
    int mScore = 0;

    public int getScore()
    {
        return mScore;
    }

    public void setScore(int score)
    {
        mScore = score;
    }

    public void incrementScore()
    {
        mScore++;
    }
}
