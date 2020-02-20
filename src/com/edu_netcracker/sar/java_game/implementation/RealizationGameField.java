package com.edu_netcracker.sar.java_game.implementation;

import com.edu_netcracker.sar.java_game.*;

public class RealizationGameField  implements GameField{

    private final CellValue[][] gameField;

    public RealizationGameField() {
        gameField = new CellValue[Constants.SIZE][Constants.SIZE];
        reInit();
    }

    public int getSize() {
        return Constants.SIZE;
    }

    public void reInit() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                setValue(i, j, CellValue.EMPTY);
            }
        }

    }

    
    public CellValue getValue(int x, int y) {
        if (x >= 0 && x < getSize() && y >= 0 && y < getSize()) {
            return gameField[x][y];
        } else {
            throw new IndexOutOfBoundsException("Ошибка координат. Х = " + x + "Y = " + y + "Размер поля" + getSize());
        }

    }
    
    
    public void setValue(int x, int y, CellValue cellValue) {
        if (x >= 0 && x < getSize() && y >= 0 && y < getSize()) {
            gameField[x][y] = cellValue;
        } else {
            throw new IndexOutOfBoundsException("Ошибка координат. Х = " + x + "Y = " + y + "Размер поля" + getSize());
        } 
    }

    public boolean isCellFree(int x, int y) {
        return getValue(x, y) == CellValue.EMPTY;
    }
 //Свободна ли хоть 1 ячейка   
 public boolean emptyCellExists() {
     for (int i = 0; i < getSize(); i++) {
         for (int j = 0; j < getSize(); j++) {
             if (getValue(j, j) == CellValue.EMPTY) return true;
             
         }
     }
     return false;
 }

   
}
