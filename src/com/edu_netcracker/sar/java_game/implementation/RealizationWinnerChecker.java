package com.edu_netcracker.sar.java_game.implementation;

import com.edu_netcracker.sar.java_game.*;
import java.util.Objects;

public class RealizationWinnerChecker implements WinnerChecker {

    private GameField gameField;
    private int winCount = Constants.WIN_COUNT;

    @Override
    public void setGameField(GameField gameField) {
        if (gameField == null) {
            throw new NullPointerException("Игровое поле пустое");
        }

        if (gameField.getSize() < winCount) {
            throw new IllegalArgumentException("Размер поля слишком маленький. Он должен быть не меньше" + winCount);
        }
        this.gameField = gameField;
    }

    @Override
    public WinnerResult isWinnerFound(CellValue cellValue) {
        Objects.requireNonNull(cellValue, "Игровое поле null");
        DataSet<Cell> result = isWinnerByRow(cellValue);

        if (result != null) {
            return new RealizationWinnerResult(result);
        }

        result = isWinnerByCol(cellValue);
        if (result != null) {
            return new RealizationWinnerResult(result);
        }

        result = isWinnerByMainDiagonal(cellValue);
        if (result != null) {
            return new RealizationWinnerResult(result);
        }
        result = isWinnerByNotMainDiagonal(cellValue);
        if (result != null) {
            return new RealizationWinnerResult(result);
        }

        return new RealizationWinnerResult(null);

    }

    protected DataSet<Cell> isWinnerByRow(CellValue cellValue) {
        for (int i = 0; i < gameField.getSize(); i++) {
            DataSet<Cell> cells = new DynaArray<>();
            for (int j = 0; j < gameField.getSize(); j++){
            if (gameField.getValue(i, j) == cellValue) {
                    cells.add(new Cell(i, j));
                    if (cells.size() == winCount) {
                        return cells;
                    }
                } else {
                    cells.clear();
                    if (j < (gameField.getSize() - winCount)) break;
                    }
            }
        }
        return null;
    }
    
    
    
   protected DataSet<Cell> isWinnerByCol(CellValue cellValue) {
        for (int i = 0; i < gameField.getSize(); i++) {
            DataSet<Cell> cells = new DynaArray<>();
            for (int j = 0; j < gameField.getSize(); j++){
            if (gameField.getValue(j, i) == cellValue) {
                    cells.add(new Cell(j, i));
                    if (cells.size() == winCount) {
                        return cells;
                    }
                } else {
                    cells.clear();
                    if (j < (gameField.getSize() - winCount)) break;
                    }
            }
        }
        return null;
    }
     
    
   protected DataSet <Cell> isWinnerByMainDiagonal (CellValue cellValue) {
       for (int i=0; i < gameField.getSize() - winCount - 1; i++){
           for (int j = 0; j < gameField.getSize() - winCount - 1; j++) {
               DataSet<Cell> cells = new DynaArray<>();
               for (int k = 0; k < winCount; k++) {
                   if (gameField.getValue(i+k, j+k) == cellValue) {
                       cells.add(new Cell(i+k, j+k));
                       if (cells.size() == winCount) return cells;
                   } else break;
                   
               }
               
           }
       }
       
       
    return null;
       
   }

   
   
      protected DataSet <Cell> isWinnerByNotMainDiagonal (CellValue cellValue) {
       for (int i=0; i < gameField.getSize() - winCount - 1; i++){
           for (int j = winCount - 1; j < gameField.getSize() - winCount - 1; j++) {
               DataSet<Cell> cells = new DynaArray<>();
               for (int k = 0; k < winCount; k++) {
                   if (gameField.getValue(i+k, j-k) == cellValue) {
                       cells.add(new Cell(i+k, j-k));
                       if (cells.size() == winCount) return cells;
                   } else break;
                   
               }
               
           }
       }
       
    return null;
       
   }
   
private static class RealizationWinnerResult implements WinnerResult {
    private final DataSet<Cell> winnerCells;
    
    RealizationWinnerResult(DataSet<Cell> winnerCells) {
        this.winnerCells = winnerCells != null ? DataUtils.newImmutableDataSet(winnerCells) : DataUtils.newImmutableDataSet(new Cell[0]);
           }
    
    public DataSet <Cell> getWinnerCells() {
        return winnerCells;
    }
    
    public boolean winnerExists () {
        return winnerCells.size() > 0;
    }
}


}
      


