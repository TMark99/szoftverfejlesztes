package boardgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import boardgame.model.*;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import org.tinylog.Logger;

public class BoardGameController {

    private enum SelectionPhase {
        SELECT_FROM,
        SELECT_TO;

        public SelectionPhase alter() {
            return switch (this) {
                case SELECT_FROM -> SELECT_TO;
                case SELECT_TO -> SELECT_FROM;
            };
        }
    }

    private String playerName, oponentName;

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setOponentName(String opnentName){ this.oponentName = oponentName;}

    private SelectionPhase selectionPhase = SelectionPhase.SELECT_FROM;

    private List<Position> selectablePositions = new ArrayList<>();

    private Position selected;

    private BoardGameModel model = new BoardGameModel();

    private boolean gamestate = true;

    @FXML
    private GridPane board;

    @FXML
    private void initialize() {
        createBoard();
        createPieces();
        setSelectablePositions();
        showSelectablePositions();
    }

    private void createBoard() {
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
    }

    private StackPane createSquare(int i, int j) {
        var square = new StackPane();
        square.getStyleClass().add("square");
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    private void createPieces() {
        for (int i = 0; i < model.getPieceCount(); i++) {
            model.positionProperty(i).addListener(this::piecePositionChange);
            var piece = createPiece(Color.valueOf(model.getPieceType(i).name()));
            getSquare(model.getPiecePosition(i)).getChildren().add(piece);
        }
    }

    private Circle createPiece(Color color) {
        var piece = new Circle(50);
        piece.setFill(color);
        return piece;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        var position = new Position(row, col);
        Logger.info("Click on square {}", position);
        handleClickOnSquare(position);
    }

    private boolean checkGame(){
        int index = 0;
        if(gamestate)
        {
            for (var piece: model.pieces)
            {
                if(piece.getType().equals(PieceType.BLUE) && piece.getIsAlive())
                    for(var direction : model.getValidMovesBlue(index))
                    {
                        if(model.isValidMoveBlue(index,direction))
                            return true;
                    }
            }
        }
        else{
            for (var piece: model.pieces)
            {
                if(piece.getType().equals(PieceType.RED) && piece.getIsAlive())
                    for(var direction : model.getValidMoves(index))
                    {
                        if(model.isValidMove(index,direction))
                            return true;
                    }
            }
        }
        return false;
    }

    private void handleClickOnSquare(Position position) {
        switch (selectionPhase) {
            case SELECT_FROM -> {
                if (selectablePositions.contains(position)) {
                    selectPosition(position);
                    alterSelectionPhase();
                }
            }
            case SELECT_TO -> {
                if (selectablePositions.contains(position)) {
                    var pieceNumber = model.getPieceNumber(selected).getAsInt();
                    switch (model.getPieceType(pieceNumber)){
                        case RED -> {
                            var direction = RedDirection.of(position.row() - selected.row(), position.col() - selected.col());
                            Logger.info("Moving piece {} {}", pieceNumber, direction);
                            model.move(pieceNumber, direction);
                            deselectSelectedPosition();
                            alterSelectionPhase();
                        }
                        case BLUE -> {
                            var direction = BlueDirection.of(position.row() - selected.row(), position.col() - selected.col());
                            Logger.info("Moving piece {} {}", pieceNumber, direction);
                            model.move(pieceNumber, direction);
                            deselectSelectedPosition();
                            alterSelectionPhase();
                        }
                    }
                }
            }
        }
    }

    private void alterSelectionPhase() {
        selectionPhase = selectionPhase.alter();
        hideSelectablePositions();
        setSelectablePositions();
        showSelectablePositions();
    }

    private void selectPosition(Position position) {
        selected = position;
        showSelectedPosition();
    }

    private void showSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().add("selected");
    }

    private void deselectSelectedPosition() {
        hideSelectedPosition();
        selected = null;
    }

    private void hideSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().remove("selected");
    }

    private void setSelectablePositions() {
        selectablePositions.clear();
        switch (selectionPhase) {
            case SELECT_FROM -> {
                int index = 0;
                for (var piece : model.pieces){
                    if(piece.getIsAlive())
                        if(gamestate){
                            if(piece.getType().equals(PieceType.BLUE))
                                selectablePositions.add(model.getPiecePosition(index));
                        }
                        else if (piece.getType().equals(PieceType.RED))
                            selectablePositions.add(model.getPiecePosition(index));
                    index++;
                }
            }

            case SELECT_TO -> {
                var pieceNumber = model.getPieceNumber(selected).getAsInt();
                switch (model.getPieceType(pieceNumber)) {
                    case RED -> {
                        for (var direction : model.getValidMoves(pieceNumber)) {
                            selectablePositions.add(selected.moveTo(direction));
                        }
                    }
                    case BLUE -> {
                        for (var direction : model.getValidMovesBlue(pieceNumber)) {
                            selectablePositions.add(selected.moveTo(direction));
                        }
                    }
                }
            }
        }
    }

    private void showSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().add("selectable");
        }
    }

    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().remove("selectable");
        }
    }

    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

    private void piecePositionChange(ObservableValue<? extends Position> observable, Position oldPosition, Position newPosition) {
        Logger.info("Move: {} -> {}", oldPosition, newPosition);
        StackPane oldSquare = getSquare(oldPosition);
        StackPane newSquare = getSquare(newPosition);
        for(var piece : model.pieces)
        {
            if(piece.getPosition().equals(oldPosition) && piece.getIsAlive()) {
                piece.kill(piece);
            }
        }
        newSquare.getChildren().clear();
        newSquare.getChildren().addAll(oldSquare.getChildren());
        oldSquare.getChildren().clear();
        gamestate=!gamestate;
        if(checkGame())
            Logger.info("Gameover");
    }

}
