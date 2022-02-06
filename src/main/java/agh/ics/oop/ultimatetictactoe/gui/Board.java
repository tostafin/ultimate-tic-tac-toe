package agh.ics.oop.ultimatetictactoe.gui;

import agh.ics.oop.ultimatetictactoe.BigField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board extends Application {
    private final int boardSize = 9;
    private final int smallFieldSize = 3;
    private final Button[][] buttons = new Button[this.boardSize][this.boardSize];
    private final VBox playersMoves = new VBox();
    private String currMark = "X";
    List<int[]> buttonsToDisable = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        try {
            BigField board = new BigField();
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            for (int i = 0; i < this.boardSize; i++) {
                for (int j = 0; j < this.boardSize; j++) {
                    Button button = new Button();
                    button.getStyleClass().addAll("mark-button", "button-background-color");
                    int finalI = i;
                    int finalJ = j;
                    this.buttons[i][j] = button;
                    gridPane.add(button, i, j, 1, 1);
                    this.styleButtons(button, i, j);
                    button.setOnAction(event -> {
                        for (int[] t : this.buttonsToDisable) {
                            this.buttons[t[0]][t[1]].setDisable(false);
                        }
                        this.buttonsToDisable.clear();

                        for (int x = 0; x < this.boardSize; x++) {
                            for (int y = 0; y < this.boardSize; y++) {
                                buttons[x][y].getStyleClass().removeAll("button-available-positions-x");
                                buttons[x][y].getStyleClass().removeAll("button-available-positions-o");
                                buttons[x][y].getStyleClass().add("button-background-color");
                            }
                        }
//                        int xBigField = finalI / this.smallFieldSize;
//                        int yBigField = finalJ / this.smallFieldSize;
//                        int xSmallField = finalI % this.smallFieldSize;
//                        int ySmallField = finalJ % this.smallFieldSize;
                        String currWinner = board.placeMark(finalI / this.smallFieldSize, finalJ / this.smallFieldSize, finalI % this.smallFieldSize, finalJ % this.smallFieldSize,
                                this.currMark);

                        this.playersMoves.getChildren().add(
                                new Text(currMark + " in big field (" + finalJ / this.smallFieldSize + ", " + finalI / this.smallFieldSize +
                                        "), in small field (" +
                                        finalJ % this.smallFieldSize + ", " + finalI % this.smallFieldSize + ")."));

                        if (Objects.equals(currWinner, "X") || Objects.equals(currWinner, "O") ||
                                Objects.equals(currWinner, "D")) {
                            this.disableSmallField(currWinner, finalI, finalJ, gridPane);
                        } else if (currWinner != null) {
                            this.disableSmallField(board.getWinner(), finalI, finalJ, gridPane);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game over");
                            alert.setHeaderText("The game is over!");
                            alert.setContentText(currWinner);
                            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                            alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                            alert.showAndWait();
                            Platform.exit();
                            System.exit(0);
                        }

                        buttons[finalI][finalJ].setText(currMark);
                        if (Objects.equals(currMark, "X")) {
                            buttons[finalI][finalJ].getStyleClass().add("button-x");
                        } else buttons[finalI][finalJ].getStyleClass().add("button-o");
                        buttons[finalI][finalJ].setDisable(true);

                        if (Objects.equals(this.currMark, "O")) this.currMark = "X";
                        else this.currMark = "O";

                        if (Objects.equals(board.getWinnerInSmallField
                                (finalI % this.smallFieldSize, (finalJ % this.smallFieldSize)), "")) {
                            if (Objects.equals(this.currMark, "O")) {
                                this.checkAvailableMoves((finalI % this.smallFieldSize) * 3, (finalJ % this.smallFieldSize) * 3);
                                for (int x = (finalI % this.smallFieldSize) * 3; x < (finalI % this.smallFieldSize) * 3 + this.smallFieldSize; x++) {
                                    for (int y = (finalJ % this.smallFieldSize) * 3; y < (finalJ % this.smallFieldSize) * 3 + this.smallFieldSize; y++) {
                                        buttons[x][y].getStyleClass().removeAll("button-background-color");
                                        buttons[x][y].getStyleClass().add("button-available-positions-o");
                                    }
                                }
                            } else {
                                for (int x = (finalI % this.smallFieldSize) * 3; x < (finalI % this.smallFieldSize) * 3 + this.smallFieldSize; x++) {
                                    for (int y = (finalJ % this.smallFieldSize) * 3; y < (finalJ % this.smallFieldSize) * 3 + this.smallFieldSize; y++) {
                                        buttons[x][y].getStyleClass().removeAll("button-background-color");
                                        buttons[x][y].getStyleClass().add("button-available-positions-x");
                                    }
                                }
                            }

                        } else {
                            if (Objects.equals(this.currMark, "O")) {
                                for (int x = 0; x < this.boardSize; x++) {
                                    for (int y = 0; y < this.boardSize; y++) {
                                        buttons[x][y].getStyleClass().removeAll("button-background-color");
                                        buttons[x][y].getStyleClass().add("button-available-positions-o");
                                    }
                                }
                            } else {
                                for (int x = 0; x < this.boardSize; x++) {
                                    for (int y = 0; y < this.boardSize; y++) {
                                        buttons[x][y].getStyleClass().removeAll("button-background-color");
                                        buttons[x][y].getStyleClass().add("button-available-positions-x");
                                    }
                                }
                            }
                        }
                    });
                }
            }
            Text movesText = new Text("Players' moves:");
            this.playersMoves.getChildren().add(movesText);
            HBox hBox = new HBox(gridPane, this.playersMoves);
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(10.0);
            hBox.setFillHeight(false);
            Scene scene = new Scene(hBox, 1000.0, 1000.0);
            scene.getStylesheets().add("styles.css");
            primaryStage.setScene(scene);
            primaryStage.setTitle("Ultimate Tic Tac Toe");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void styleButtons(Button button, int i, int j) {
        button.setMinWidth(50.0);
        button.setPrefWidth(50.0);
        button.setMaxWidth(50.0);
        button.setMinHeight(50.0);
        button.setPrefHeight(50.0);
        button.setMaxHeight(50.0);

        if (i % (this.boardSize / this.smallFieldSize) == (this.smallFieldSize - 1) &&
                j % (this.boardSize / this.smallFieldSize) == (this.smallFieldSize - 1)) {
            button.getStyleClass().add("button-right-and-lower-border");
        } else if (i % (this.boardSize / this.smallFieldSize) == (this.smallFieldSize - 1)) {
            button.getStyleClass().add("button-right-border");
        } else if (j % (this.boardSize / this.smallFieldSize) == (this.smallFieldSize - 1)) {
            button.getStyleClass().add("button-lower-border");
        }
    }

    public void disableSmallField(String currWinner, int finalI, int finalJ, GridPane gridPane) {
        Text winnerText = new Text(currWinner);
        winnerText.setFont(new Font(125.0));
        if (Objects.equals(currWinner, "X")) winnerText.setFill(Color.BLUE);
        else winnerText.setFill(Color.RED);
        gridPane.add(winnerText, finalI - (finalI % this.smallFieldSize),
                finalJ - (finalJ % this.smallFieldSize), 3, 3);
        GridPane.setHalignment(winnerText, HPos.CENTER);
        this.disableButtons(finalI - (finalI % this.smallFieldSize), finalJ - (finalJ % this.smallFieldSize));
    }

    public void disableButtons(int x, int y) {
        for (int i = x; i < x + this.smallFieldSize; i++) {
            for (int j = y; j < y + this.smallFieldSize; j++) this.buttons[i][j].setDisable(true);
        }
    }

    public void checkAvailableMoves(int x, int y) {
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if (!(this.buttons[i][j].isDisable()) && !(i >= x && i < x + this.smallFieldSize && j >= y && j < y + this.smallFieldSize)) {
                    this.buttonsToDisable.add(new int[]{i, j});
                    this.buttons[i][j].setDisable(true);
                }
            }
        }
    }

    public static void main(String[] args) {
        Application.launch(Board.class, args);
    }
}
