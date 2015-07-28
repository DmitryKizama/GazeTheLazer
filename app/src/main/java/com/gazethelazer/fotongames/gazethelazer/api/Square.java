package com.gazethelazer.fotongames.gazethelazer.api;

public class Square {

    public boolean right_side = false;

    public boolean bottom_side = false;

    public Square(boolean rightSide, boolean bottomSide) {
        right_side = rightSide;
        bottom_side = bottomSide;
    }

    public Square()
    {
        this(false, false);
    }

}
