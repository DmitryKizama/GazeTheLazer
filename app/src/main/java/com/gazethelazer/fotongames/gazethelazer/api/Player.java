package com.gazethelazer.fotongames.gazethelazer.api;

public class Player
{
    int mScore = 0;
    int mNumber = 0;

    public Player(int n)
    {
        mNumber = n;
    }

    public int getScore()
    {
        return mScore;
    }

    public void setScore(int score)
    {
        mScore = score;
    }

    public void addScore(int score)
    {
        mScore += score;
    }

    public void incrementScore()
    {
        mScore++;
    }

    public void setNumber(int n)
    {
        mNumber = n;
    }

    public int getNumber()
    {
        return mNumber;
    }
}
