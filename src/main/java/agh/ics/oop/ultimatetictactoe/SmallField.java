package agh.ics.oop.ultimatetictactoe;

import java.util.Objects;

public class SmallField {
    private final String[][] board;
    private String winner = "";
    private final int boardSize = 3;
    private int marksPlaced = 0;

    public SmallField() {
        this.board = new String[3][3];
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                this.board[i][j] = "";
            }
        }
    }

    public String getWinner() {
        return this.winner;
    }

    public void placeMark(int i, int j, String mark) {
        this.board[i][j] = mark;
        this.marksPlaced++;
        this.winner = this.checkForWin(i, j, mark);
    }

    public String checkForWin(int i, int j, String mark) {
        // check rows
        for (int k = 0; k < this.boardSize; k++) {
            if (!Objects.equals(this.board[i][k], mark)) break;
            if (k == this.boardSize - 1) return mark;
        }

        // check columns
        for (int k = 0; k < this.boardSize; k++) {
            if (!Objects.equals(this.board[k][j], mark)) break;
            if (k == this.boardSize - 1) return mark;
        }

        // check upper-left lower-right diagonal
        if (i == j) {
            for (int k = 0; k < this.boardSize; k++) {
                if (!Objects.equals(this.board[k][k], mark)) break;
                if (k == this.boardSize - 1) return mark;
            }
        }

        // check upper-right lower-left diagonal
        if (i + j == this.boardSize - 1) {
            for (int k = 0; k < this.boardSize; k++) {
                if (!Objects.equals(this.board[k][this.boardSize - 1 - k], mark)) break;
                if (k == this.boardSize - 1) return mark;
            }
        }

        if (this.marksPlaced == Math.pow(this.boardSize, 2) - 1) return "D";

        return "";
    }
}
