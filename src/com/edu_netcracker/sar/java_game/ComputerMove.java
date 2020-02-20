package com.edu_netcracker.sar.java_game;

public interface ComputerMove {

    void setGameField(GameField gameField); //Установка игровой таблицы 

    Cell makeMove(); // Выполняет ход и возвращает объект ячейки туда куда он поставил ход

    Cell makeFirstMove(); // Самый первый ход компьютера
}
