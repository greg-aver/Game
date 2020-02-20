package com.edu_netcracker.sar.java_game;

public enum CellValue {
    EMPTY(' '),
    HUMAN('X'),
    COMPUTER('0');
    
    private char value;
    
    private CellValue(char value){
        this.value = value;
    } 
    
    public String getValue(){
        return String.valueOf(value);
    }
}