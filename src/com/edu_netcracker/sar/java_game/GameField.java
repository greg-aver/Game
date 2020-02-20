package com.edu_netcracker.sar.java_game;

public interface GameField {

    CellValue getValue(int x, int y); // Находит значение ячеки поля по координатам
    
    void setValue(int x, int y, CellValue cellValue); // Устанавливает значение ячейки
    
    int getSize();
    
    void reInit(); 
    
    boolean isCellFree (int x, int y); // True - ячейка свободна
    boolean emptyCellExists(); // Проверка на наличие свободных ячеек
}
