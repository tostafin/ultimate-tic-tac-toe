package agh.ics.oop.ultimatetictactoe.gui;

import agh.ics.oop.ultimatetictactoe.BigField;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class Board extends Application {
    private String winner = "";
    private final int boardSize = 9;
    private final int smallFieldSize = 3;
    private final Button[][] buttons = new Button[this.boardSize][this.boardSize];
    private final VBox playersMoves = new VBox();

    @Override
    public void start(Stage primaryStage) {
        try {
            BigField board = new BigField();
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            for (int i = 0; i < this.boardSize; i++) {
                for (int j = 0; j < this.boardSize; j++) {
                    Button button = new Button();
                    button.setMinWidth(50.0);
                    button.setPrefWidth(50.0);
                    button.setMaxWidth(50.0);
                    button.setMinHeight(50.0);
                    button.setPrefHeight(50.0);
                    button.setMaxHeight(50.0);
                    int finalI = i;
                    int finalJ = j;

                    if (i % (this.boardSize / this.smallFieldSize) == (this.smallFieldSize - 1) &&
                            j % (this.boardSize / this.smallFieldSize) == (this.smallFieldSize - 1)) {
                        button.setStyle("-fx-border-color: black; -fx-border-width: 0 5 5 0");
                    }
                    else if (i % (this.boardSize / this.smallFieldSize) == (this.smallFieldSize - 1)) {
                        button.setStyle("-fx-border-color: black; -fx-border-width: 0 5 0 0");
                    }
                    else if (j % (this.boardSize / this.smallFieldSize) == (this.smallFieldSize - 1)) {
                        button.setStyle("-fx-border-color: black; -fx-border-width: 0 0 5 0");
                    }

                    button.setOnAction(event -> {
//                        board.placeMark(finalI / this.smallFieldSize, finalJ / this.smallFieldSize,
//                                finalI % this.smallFieldSize, finalJ % this.smallFieldSize);
                        String currWinner = board.placeMark(finalI / this.smallFieldSize,
                                finalJ / this.smallFieldSize, finalI % this.smallFieldSize,
                                finalJ % this.smallFieldSize);
                        this.playersMoves.getChildren().add(
                                new Text(board.getCurrMark() + " in big field (" + finalI / this.smallFieldSize +
                                        ", " + finalJ / this.smallFieldSize + "), in small field (" +
                                        finalI % this.smallFieldSize + ", " + finalJ % this.smallFieldSize + ")."));
                        if (Objects.equals(currWinner, "X") || Objects.equals(currWinner, "O") ||
                                Objects.equals(currWinner, "D")) {
                            this.disableSmallField(currWinner, finalI, finalJ, gridPane);
                        }
                        else if (currWinner != null) {
                            this.disableSmallField(board.getWinner(), finalI, finalJ, gridPane);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game over");
                            alert.setHeaderText("The game is over!");
                            alert.setContentText(currWinner);
                            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                            alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                            alert.showAndWait();
                        }
                        buttons[finalI][finalJ].setText(board.getCurrMark());
                        buttons[finalI][finalJ].setDisable(true);
                    });
                    buttons[i][j] = button;
                    gridPane.add(button, i, j, 1, 1);
                }
            }
            Text movesText = new Text("Players' moves:");
            this.playersMoves.getChildren().add(movesText);
            HBox hBox = new HBox(gridPane, this.playersMoves);
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(10.0);
            hBox.setFillHeight(false);
            Scene scene = new Scene(hBox, 1000.0, 1000.0);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Ultimate Tic Tac Toe");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disableSmallField(String currWinner, int finalI, int finalJ, GridPane gridPane) {
        Text winnerText = new Text(currWinner);
        winnerText.setFont(new Font(125.0));
        System.out.println((finalI - (finalI % this.smallFieldSize)));
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

    public static void main(String[] args) {
        Application.launch(Board.class, args);
    }
}
