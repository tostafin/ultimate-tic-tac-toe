package agh.ics.oop.ultimatetictactoe.gui;

import agh.ics.oop.ultimatetictactoe.BigField;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class Board extends Application {
    private String winner = "";
    private final int boardSize = 9;
    private final int smallFieldSize = 3;

    @Override
    public void start(Stage primaryStage) {
        try {
            BigField board = new BigField();
            Button[][] buttons = new Button[this.boardSize][this.boardSize];
            GridPane gridPane = new GridPane();
            for (int i = 0; i < this.boardSize; i++) {
                for (int j = 0; j < this.boardSize; j++) {
                    Button button = new Button();
                    button.setMinWidth(50.0);
                    button.setPrefWidth(50.0);
                    button.setMaxWidth(50.0);
                    button.setMinHeight(50.0);
                    button.setPrefHeight(50.0);
                    button.setMaxHeight(30.0);
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
                        board.placeMark(finalI / this.smallFieldSize, finalJ / this.smallFieldSize,
                                finalI % this.smallFieldSize, finalJ % this.smallFieldSize);
                        String currWinner = board.getWinner();
                        if (!Objects.equals(this.winner, "")) this.winner = currWinner;
                        buttons[finalI][finalJ].setText(board.getCurrMark());
                        buttons[finalI][finalJ].setDisable(true);
                    });
                    buttons[i][j] = button;
                    gridPane.add(button, i, j, 1, 1);
                }
            }
            Label playersMoves = new Label("List handler");
            HBox hBox = new HBox(gridPane, playersMoves);
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(50);
            Label label = new Label(this.winner);
            VBox vBox = new VBox(hBox, label);
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(50);

            Scene scene = new Scene(vBox, 1000, 1000);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Ultimate Tic Tac Toe");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Application.launch(Board.class, args);
    }
}
