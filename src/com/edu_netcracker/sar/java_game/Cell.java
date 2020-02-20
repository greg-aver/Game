package com.edu_netcracker.sar.java_game;

public class Cell {

    private final int X;
    private final int Y;

    public Cell(int X, int Y) {
        super();
        this.X = X;
        this.Y = Y;
    }
    public int getX(){
        return X;
    }
    
    public int getY(){
        return Y;
    }
    
    @Override
    public String toString(){
        return X + ":" + Y;
    }
}
