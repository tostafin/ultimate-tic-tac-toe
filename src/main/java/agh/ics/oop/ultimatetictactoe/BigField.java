package agh.ics.oop.ultimatetictactoe;

import java.util.Objects;

public class BigField {
    private final SmallField[][] board;
    private String winner = "";
    private final int boardSize = 3;
    private String currMark = "X";

    public BigField() {
        this.board = new SmallField[this.boardSize][this.boardSize];
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) this.board[i][j] = new SmallField();
        }
    }

    public String getWinner() {
        return this.winner;
    }

    public String getCurrMark() {
        return this.currMark;
    }

    public void placeMark(int fieldRow, int fieldCol, int i, int j) {
        if (Objects.equals(this.currMark, "O")) this.currMark = "X";
        else this.currMark = "O";
        this.board[fieldRow][fieldCol].placeMark(i, j, this.currMark);
        if (!Objects.equals(this.board[fieldRow][fieldCol].getWinner(), null)) {
            if (!Objects.equals(this.checkForWin(fieldRow, fieldCol), null)) this.winner = this.currMark;
        }
    }

    public String checkForWin(int i, int j) {
        // check rows
        for (int k = 0; k < this.boardSize; k++) {
            if (!Objects.equals(this.board[i][k].getWinner(), this.currMark)) break;
            if (k == this.boardSize - 1) return this.currMark;
        }

        // check columns
        for (int k = 0; k < this.boardSize; k++) {
            if (!Objects.equals(this.board[k][j].getWinner(), this.currMark)) break;
            if (k == this.boardSize - 1) return this.currMark;
        }

        // check upper-left lower-right diagonal
        if (i == j) {
            for (int k = 0; k < this.boardSize; k++) {
                if (!Objects.equals(this.board[k][k].getWinner(), this.currMark)) break;
                if (k == this.boardSize - 1) return this.currMark;
            }
        }

        // check upper-right lower-left diagonal
        if (i + j == this.boardSize - 1) {
            for (int k = 0; k < this.boardSize; k++) {
                if (!Objects.equals(this.board[k][this.boardSize - 1 - k].getWinner(), this.currMark)) break;
                if (k == this.boardSize - 1) return this.currMark;
            }
        }
        return null;
    }
}
