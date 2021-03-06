package agh.ics.oop.ultimatetictactoe;

import java.util.Objects;

public class BigField {
    private final SmallField[][] board;
    private String winner = "";
    private final int boardSize = 3;
    private int marksPlaced = 0;

    public String getWinnerInSmallField(int i, int j) {
        return this.board[i][j].getWinner();
    }

    public BigField() {
        this.board = new SmallField[this.boardSize][this.boardSize];
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) this.board[i][j] = new SmallField();
        }
    }

    public String getWinner() {
        return this.winner;
    }

    public String placeMark(int fieldRow, int fieldCol, int i, int j, String currMark) {
        this.board[fieldRow][fieldCol].placeMark(i, j, currMark);
        String smallFieldWinner = this.board[fieldRow][fieldCol].getWinner();
        if (Objects.equals(smallFieldWinner, currMark) || Objects.equals(smallFieldWinner, "D")) {
            this.marksPlaced++;
            String bigFieldWinner = this.checkForWin(fieldRow, fieldCol, currMark);
            this.winner = bigFieldWinner;
            if (Objects.equals(bigFieldWinner, currMark)) return "The winner is " + currMark + "!";
            else if (Objects.equals(bigFieldWinner, "D")) return "It's a draw!";
            return smallFieldWinner;
        }
        return null;
    }

    public String checkForWin(int i, int j, String currMark) {
        // check rows
        for (int k = 0; k < this.boardSize; k++) {
            if (!Objects.equals(this.board[i][k].getWinner(), currMark)) break;
            if (k == this.boardSize - 1) return currMark;
        }

        // check columns
        for (int k = 0; k < this.boardSize; k++) {
            if (!Objects.equals(this.board[k][j].getWinner(), currMark)) break;
            if (k == this.boardSize - 1) return currMark;
        }

        // check upper-left lower-right diagonal
        if (i == j) {
            for (int k = 0; k < this.boardSize; k++) {
                if (!Objects.equals(this.board[k][k].getWinner(), currMark)) break;
                if (k == this.boardSize - 1) return currMark;
            }
        }

        // check upper-right lower-left diagonal
        if (i + j == this.boardSize - 1) {
            for (int k = 0; k < this.boardSize; k++) {
                if (!Objects.equals(this.board[k][this.boardSize - 1 - k].getWinner(), currMark)) break;
                if (k == this.boardSize - 1) return currMark;
            }
        }

        if (this.marksPlaced == Math.pow(this.boardSize, 2) - 1) return "D";

        return "";
    }
}
