package com.edu_netcracker.sar.java_game.implementation;

import com.edu_netcracker.sar.java_game.*;

public class RealizationHumonMove implements HumanMove {

    private GameField gameField;

    //Установка игрового поля
    @Override
    public void setGameField(GameField gameField) {
        if (gameField == null) {
            throw new NullPointerException("Игровое поле пустое");
        }
        this.gameField = gameField;
    }

    // Если ходит человек, установка Х 
    @Override
    public Cell makeMove(int x, int y) {
        gameField.setValue(x, y, CellValue.HUMAN);
        return new Cell(x, y);
    }

}
