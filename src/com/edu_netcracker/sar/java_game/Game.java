package com.edu_netcracker.sar.java_game;

import java.awt.*;

import javax.swing.*;

import com.edu_netcracker.sar.java_game.implementation.*;

public class Game {

    private final JLabel cells[][];
    private final GameField gameField;
    private final HumanMove humanMove;
    private final ComputerMove computerMove;
    private final WinnerChecker winnerChecker;
    private boolean isHumanFirstMove;

    public Game() throws HeadlessException {
       // super("Game");

        gameField = (GameField) new RealizationGameField();
        humanMove = new RealizationHumonMove();
        computerMove = new RealizationComputerMove();
        winnerChecker = new RealizationWinnerChecker();

        initGameComponents();
        cells = new JLabel[gameField.getSize()][gameField.getSize()];
        isHumanFirstMove = true;
        createGameField();
    }

    protected void initGameComponents() {
        humanMove.setGameField(gameField);
        computerMove.setGameField(gameField);
        winnerChecker.setGameField(gameField);
    }

    protected void drawCellValue(Cell cell) {
        CellValue cellValue = gameField.getValue(cell.getX(), cell.getY());
        cells[cell.getX()][cell.getY()].setText(cellValue.getValue());
        if (cellValue == CellValue.COMPUTER) {
            cells[cell.getX()][cell.getY()].setForeground(Color.RED);
        } else {
            //human
            cells[cell.getX()][cell.getY()].setForeground(Color.BLUE);
        }

    }

    protected void markWinnerCells(DataSet<Cell> winnerCells) {
        for (int i = 0; i < winnerCells.size(); i++) {
            Cell cell = winnerCells.get(i);
            cells[cell.getX()][cell.getY()].setForeground(Color.CYAN);
            cells[cell.getX()][cell.getY()].setFont(new Font(Font.SERIF, Font.BOLD, 35));
        }
    }

    protected void createGameField() {
        setLayout(new GridLayout(gameField.getSize(), gameField.getSize()));
        for (int i = 0; i < gameField.getSize(); i++) {
            for (int j = 0; j < gameField.getSize(); j++) {
                final int row = i;
                final int col = j;
                cells[i][j] = new JLabel();
                cells[i][j].setPreferredSize(new Dimension(45, 45));
                cells[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                cells[i][j].setVerticalAlignment(SwingConstants.CENTER);
                cells[i][j].setFont(new Font(Font.SERIF, Font.PLAIN, 35));
                cells[i][j].setForeground(Color.BLACK);
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                add(cells[i][j]);
                cells[i][j].addMouseListener(new MouseAdapter() {

                    public void mouseClicked(MouseEvent e) {
                        handleHumanMove(row, col);
                    }
                }
            }
        }
    }

    protected void startNewGame() {
        isHumanFirstMove = !isHumanFirstMove;
        gameField.reInit();
        for (int i = 0; i < gameField.getSize(); i++) {
            for (int j = 0; j < gameField.getSize(); j++) {
                cells[i][j].setText(gameField.getValue(i, j).getValue());
                cells[i][j].setFont(new Font(Font.SERIF, Font.PLAIN, 35));
                cells[i][j].setForeground(Color.BLACK);
            }
        }
        if (!isHumanFirstMove) {
            Cell compCell = computerMove.makeFirstMove();
            drawCellValue(compCell);
        }
    }

    protected void stopGame() {
        for (int i = 0; i < gameField.getSize(); i++) {
            for (int j = 0; j < gameField.getSize(); j++) {
                cells[i][j].removeMouseListener(cells[i][j].getMouseListeners()[0]);
            }
        }
    }

    protected void handleGameOver(String message) {
        if (JOptionPane.showConfirmDialog(this, message) == JOptionPane.YES_OPTION) {
            startNewGame();
        } else {
            stopGame();
        }
    }

    protected void handleHumanMove(int row, int col) {
        if (gameField.isCellFree(row, col)) {
            Cell humanCell = humanMove.makeMove(row, col);
            drawCellValue(humanCell);
            WinnerResult winnerResult = winnerChecker.isWinnerFound(CellValue.HUMAN);
            if (winnerResult.winnerExists()) {
                markWinnerCells(winnerResult.getWinnerCells());
                handleGameOver("Game over: User win!\nNew game?");
                return;
            }
            if (!gameField.emptyCellExists()) {
                handleGameOver("Game over: Draw!\nNew game?");
                return;
            }
            Cell compCell = computerMove.makeMove();
            drawCellValue(compCell);
            winnerResult = winnerChecker.isWinnerFound(CellValue.COMPUTER);
            if (winnerResult.winnerExists()) {
                markWinnerCells(winnerResult.getWinnerCells());
                handleGameOver("Game over: Computer wins!\nNew game?");
                return;
            }
            if (!gameField.emptyCellExists()) {
                handleGameOver("Game over: Draw!\nNew game?");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Cell is not free! Click on free cell!");
        }
    }

    public static void main(String[] args) {
        Game w = new Game();
        w.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        w.setResizable(false);
        w.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        w.setLocation(dim.width / 2 - w.getSize().width / 2, dim.height / 2 - w.getSize().height / 2);
        w.setVisible(true);
    }

}
