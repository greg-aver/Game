
package com.edu_netcracker.sar.java_game;


public interface WinnerChecker {
   
     void setGameField (GameField gameField); // Установка игрового поля
     
     WinnerResult isWinnerFound (CellValue cellValue);
    
}
