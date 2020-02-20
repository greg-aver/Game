package com.edu_netcracker.sar.java_game.implementation;

import com.edu_netcracker.sar.java_game.*;
import java.util.Objects;
import java.util.Random;


public class RealizationComputerMove implements ComputerMove {

    private GameField gameField;
    private int winCount = Constants.WIN_COUNT;

    @Override
    public void setGameField(GameField gameField) {
        Objects.requireNonNull(gameField, "Game table can't be null");
        if (gameField.getSize() < winCount) {
            throw new IllegalArgumentException("Size of gameField is small: size=" + gameField.getSize() + ". Required >= " + winCount);
        }
        this.gameField = gameField;
    }

    @Override
    public Cell makeMove() {
        CellValue[] figures = {CellValue.COMPUTER, CellValue.HUMAN};
        for (int i = winCount - 1; i > 0; i--) {
            for (CellValue cellValue : figures) {
                Cell cell = tryMakeMove(cellValue, i);
                if (cell != null) {
                    return cell;
                }
            }
        }
        return makeRandomMove();
    }

    @Override
    public Cell makeFirstMove() {
        Cell cell = new Cell(gameField.getSize() / 2, gameField.getSize() / 2);
        gameField.setValue(cell.getX(), cell.getY(), CellValue.COMPUTER);
        return cell;
    }

    protected Cell makeRandomMove() throws Exception {
        DataSet<Cell> emptyCells = getAllEmptyCells();
        if (emptyCells.size() > 0) {
            Cell randomCell = emptyCells.get(new Random().nextInt(emptyCells.size()));
            gameField.setValue(randomCell.getX(), randomCell.getY(), CellValue.COMPUTER);
            return randomCell;
        } else {
            throw new Exception("All cells are filled! Have you checked draw state before call of computer turn?");
        }
    }

    protected DataSet<Cell> getAllEmptyCells() {
        DataSet<Cell> emptyCells = new DynaArray<>();
        for (int i = 0; i < gameField.getSize(); i++) {
            for (int j = 0; j < gameField.getSize(); j++) {
                if (gameField.isCellFree(i, j)) {
                    emptyCells.add(new Cell(i, j));
                }
            }
        }
        return emptyCells;
    }

    protected Cell tryMakeMove(CellValue cellValue, int notBlankCount) {
        Cell cell = tryMakeMoveByRow(cellValue, notBlankCount);
        if (cell != null) {
            return cell;
        }
        cell = tryMakeMoveByCol(cellValue, notBlankCount);
        if (cell != null) {
            return cell;
        }
        cell = tryMakeMoveByMainDiagonal(cellValue, notBlankCount);
        if (cell != null) {
            return cell;
        }
        cell = tryMakeMoveByNotMainDiagonal(cellValue, notBlankCount);
        if (cell != null) {
            return cell;
        }
        return null;
    }

    protected Cell tryMakeMoveByRow(CellValue cellValue, int notBlankCount) {
        for (int i = 0; i < gameField.getSize(); i++) {
            for (int j = 0; j < gameField.getSize() - winCount - 1; j++) {
                boolean hasEmptyCells = false;
                int count = 0;
                DataSet<Cell> inspectedCells = new DynaArray<>();
                for (int k = 0; k < winCount; k++) {
                    inspectedCells.add(new Cell(i, j + k));
                    if (gameField.getValue(i, j + k) == cellValue) {
                        count++;
                    } else if (gameField.getValue(i, j + k) == CellValue.EMPTY) {
                        hasEmptyCells = true;
                    } else {
                        hasEmptyCells = false;
                        break;
                    }
                }
                if (count == notBlankCount && hasEmptyCells) {
                    return makeMoveToOneCellFromDataSet(inspectedCells);
                }
            }
        }
        return null;
    }

    protected Cell tryMakeMoveByCol(CellValue cellValue, int notBlankCount) {
        for (int i = 0; i < gameField.getSize(); i++) {
            for (int j = 0; j < gameField.getSize() - winCount - 1; j++) {
                boolean hasEmptyCells = false;
                int count = 0;
                DataSet<Cell> inspectedCells = new DynaArray<>();
                for (int k = 0; k < winCount; k++) {
                    inspectedCells.add(new Cell(j + k, i));
                    if (gameField.getValue(j + k, i) == cellValue) {
                        count++;
                    } else if (gameField.getValue(j + k, i) == CellValue.EMPTY) {
                        hasEmptyCells = true;
                    } else {
                        hasEmptyCells = false;
                        break;
                    }
                }
                if (count == notBlankCount && hasEmptyCells) {
                    return makeMoveToOneCellFromDataSet(inspectedCells);
                }
            }
        }
        return null;
    }

    protected Cell tryMakeMoveByMainDiagonal(CellValue cellValue, int notBlankCount) {
        for (int i = 0; i < gameField.getSize() - winCount - 1; i++) {
            for (int j = 0; j < gameField.getSize() - winCount - 1; j++) {
                boolean hasEmptyCells = false;
                int count = 0;
                DataSet<Cell> inspectedCells = new DynaArray<>();
                for (int k = 0; k < winCount; k++) {
                    inspectedCells.add(new Cell(i + k, j + k));
                    if (gameField.getValue(i + k, j + k) == cellValue) {
                        count++;
                    } else if (gameField.getValue(i + k, j + k) == CellValue.EMPTY) {
                        hasEmptyCells = true;
                    } else {
                        hasEmptyCells = false;
                        break;
                    }
                }
                if (count == notBlankCount && hasEmptyCells) {
                    return makeMoveToOneCellFromDataSet(inspectedCells);
                }
            }
        }
        return null;
    }

    protected Cell tryMakeMoveByNotMainDiagonal(CellValue cellValue, int notBlankCount) {
        for (int i = 0; i < gameField.getSize() - winCount - 1; i++) {
            for (int j = winCount - 1; j < gameField.getSize(); j++) {
                boolean hasEmptyCells = false;
                int count = 0;
                DataSet<Cell> inspectedCells = new DynaArray<>();
                for (int k = 0; k < winCount; k++) {
                    inspectedCells.add(new Cell(i + k, j - k));
                    if (gameField.getValue(i + k, j - k) == cellValue) {
                        count++;
                    } else if (gameField.getValue(i + k, j - k) == CellValue.EMPTY) {
                        hasEmptyCells = true;
                    } else {
                        hasEmptyCells = false;
                        break;
                    }
                }
                if (count == notBlankCount && hasEmptyCells) {
                    return makeMoveToOneCellFromDataSet(inspectedCells);
                }
            }
        }
        return null;
    }

    protected Cell makeMoveToOneCellFromDataSet(DataSet<Cell> inspectedCells) {
        Cell cell = findEmptyCellForComputerMove(inspectedCells);
        gameField.setValue(cell.getX(), cell.getY(), CellValue.COMPUTER);
        return cell;
    }

    protected Cell findEmptyCellForComputerMove(DataSet<Cell> cells) throws Exception{
        for (int i = 0; i < cells.size(); i++) {
            Cell currentCell = cells.get(i);
            if (gameField.getValue(currentCell.getX(), currentCell.getY()) != CellValue.EMPTY) {
                if (i == 0) {
                    if (isCellEmpty(cells.get(i + 1))) {
                        return cells.get(i + 1);
                    }
                } else if (i == cells.size() - 1) {
                    if (isCellEmpty(cells.get(i - 1))) {
                        return cells.get(i - 1);
                    }
                } else {
                    boolean searchDirectionAsc = new Random().nextBoolean();
                    int first = searchDirectionAsc ? i + 1 : i - 1;
                    int second = searchDirectionAsc ? i - 1 : i + 1;
                    if (isCellEmpty(cells.get(first))) {
                        return cells.get(first);
                    } else if (isCellEmpty(cells.get(second))) {
                        return cells.get(second);
                    }
                }
            }
        }
        throw new Exception("All cells are filled: " + cells);
    }

    protected boolean isCellEmpty(Cell cell) {
        return gameField.getValue(cell.getX(), cell.getY()) == CellValue.EMPTY;
    }
}
