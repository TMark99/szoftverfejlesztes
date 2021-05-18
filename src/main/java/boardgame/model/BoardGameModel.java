package boardgame.model;

import javafx.beans.property.ObjectProperty;

import java.util.*;

/**
 * A játék szimulálása
 */
public class BoardGameModel {

    public Piece[] pieces;

    /**
     * Inicializálja a bábukat a táblán
     */
    public BoardGameModel() {
        this(new Piece(PieceType.RED, new Position(0, 0)),
                new Piece(PieceType.RED, new Position(0, 1)),
                new Piece(PieceType.RED, new Position(0, 2)),
                new Piece(PieceType.RED, new Position(0, 3)),
                new Piece(PieceType.RED, new Position(0, 4)),
                new Piece(PieceType.RED, new Position(0, 5)),
                new Piece(PieceType.RED, new Position(0, 6)),
                new Piece(PieceType.BLUE, new Position(5, 0)),
                new Piece(PieceType.BLUE, new Position(5, 1)),
                new Piece(PieceType.BLUE, new Position(5, 2)),
                new Piece(PieceType.BLUE, new Position(5, 3)),
                new Piece(PieceType.BLUE, new Position(5, 4)),
                new Piece(PieceType.BLUE, new Position(5, 5)),
                new Piece(PieceType.BLUE, new Position(5, 6)),
                new Piece(PieceType.BLACK,new Position(3,2)),
                new Piece(PieceType.BLACK,new Position(2,4)));
    }

    /**
     * Bemásolja a paraméterül kapott vektorba a játék bábukat
     * @param pieces összes bábut tartalmazó vektor
     */
    public BoardGameModel(Piece... pieces) {
        checkPieces(pieces);
        this.pieces = pieces.clone();
    }

    private void checkPieces(Piece[] pieces) {
        var seen = new HashSet<Position>();
        for (var piece : pieces) {
            if (!isOnBoard(piece.getPosition()) || seen.contains(piece.getPosition())) {
                throw new IllegalArgumentException();
            }
            if(piece.getIsAlive())
            seen.add(piece.getPosition());
        }
    }

    /**
     * A bábukat tartalmazó vektor sokaságát számolja ki
     * @return a bábuk száma
     */
    public int getPieceCount() {
        return pieces.length;
    }

    /**
     * Egy adott bábu típusát (színét) adja vissza
     * @param pieceNumber a bábu indexe a listában
     * @return a bábu típusa
     */
    public PieceType getPieceType(int pieceNumber) {
        return pieces[pieceNumber].getType();
    }

    /**
     * Egy adott bábu pozicióját adja vissza
     * @param pieceNumber a bábu indexe a listában
     * @return a bábú poziciója
     */
    public Position getPiecePosition(int pieceNumber) {
        return pieces[pieceNumber].getPosition();
    }

    /**
     * A mező sor és oszlop számát adja vissza, ahol a bábu van
     * @param pieceNumber a bábu indexe a listában
     * @return a mező sor és oszlop száma szövegként
     */
    public ObjectProperty<Position> positionProperty(int pieceNumber) {
        return pieces[pieceNumber].positionProperty();
    }

    /**
     * Megvizsgálja hogy a piros bábu lehetséges lépési közül melyeket lépheti meg a játék állása alapján
     * @param pieceNumber a bábu indexe a vektorban
     * @param direction az irány amelyre ellenörizük a feltételeket
     * @return igazat ha léphet, egyébkny hamisat
     */
    public boolean isValidMove(int pieceNumber, RedDirection direction) {
        if (pieceNumber < 0 || pieceNumber >= pieces.length) {
            throw new IllegalArgumentException();
        }
        Position newPosition = pieces[pieceNumber].getPosition().moveTo(direction);
        PieceType selectedpiece = pieces[pieceNumber].getType();
        if (! isOnBoard(newPosition)) {
            return false;
        }
        for (var piece : pieces) {
            if(!piece.getIsAlive() && direction.equals(RedDirection.DOWN))
            {
                return true;
            }
            else if(!piece.getIsAlive() && !direction.equals(RedDirection.DOWN)){
                return false;
            }
            else if (piece.getPosition().equals(newPosition) && selectedpiece.equals(piece.getType())) {
                return false;
            }
            else if(piece.getType().equals(PieceType.BLACK) && piece.getPosition().equals(newPosition)){
                return false;
            }
            else if((piece.getPosition().equals(newPosition)) && direction.equals(RedDirection.DOWN)){
                return false;
            }
            else if(piece.getPosition().equals(newPosition) && direction.equals(RedDirection.DOWN_LEFT)){
                return true;
            }
            else if(piece.getPosition().equals(newPosition) && direction.equals(RedDirection.DOWN_RIGHT)){
                return  true;
            }
        }
        return true;
    }

    /**
     * Megvizsgálja hogy a kék bábu lehetséges lépési közül melyeket lépheti meg a játék állása alapján
     * @param pieceNumber a bábu indexe a vektorban
     * @param direction az irány amelyre ellenörizük a feltételeket
     * @return igazat ha léphet, egyébkny hamisat
     */
    public boolean isValidMoveBlue(int pieceNumber, BlueDirection direction) {
        if (pieceNumber < 0 || pieceNumber >= pieces.length) {
            throw new IllegalArgumentException();
        }
        Position newPosition = pieces[pieceNumber].getPosition().moveTo(direction);
        PieceType selectedpiece = pieces[pieceNumber].getType();
        if (! isOnBoard(newPosition)) {
            return false;
        }
        for (var piece : pieces) {
            if(!piece.getIsAlive() && direction.equals(BlueDirection.UP)){
                return true;
            }
            else if (piece.getPosition().equals(newPosition) && selectedpiece.equals(piece.getType())) {
                return false;
            }
            else if(piece.getType().equals(PieceType.BLACK) && piece.getPosition().equals(newPosition)){
                return false;
            }
            else if((piece.getPosition().equals(newPosition)) && direction.equals(BlueDirection.UP)){
                return false;
            }
            else if(piece.getPosition().equals(newPosition) && direction.equals(BlueDirection.UP_LEFT)){
                return true;
            }
            else if(piece.getPosition().equals(newPosition) && direction.equals(BlueDirection.UP_RIGHT)){
                return  true;
            }
        }
        return true;
    }

    /**
     * A paraméterül kapott piros bábu összes lehetséges lépése köszül visszadja a léphetőket.
     * @param pieceNumber piros bábu indexe
     * @return az összes lehetséges lépés
     */
    public Set<RedDirection> getValidMoves(int pieceNumber) {
        EnumSet<RedDirection> validMoves = EnumSet.noneOf(RedDirection.class);
        for (var direction : RedDirection.values()) {
            if (isValidMove(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    /**
     * A paraméterül kapott kék bábu összes lehetséges lépése köszül visszadja a léphetőket.
     * @param pieceNumber kék bábu index
     * @return az összes lehetséges lépés
     */
    public Set<BlueDirection> getValidMovesBlue(int pieceNumber) {
        EnumSet<BlueDirection> validMoves = EnumSet.noneOf(BlueDirection.class);
        for (var direction : BlueDirection.values()) {
            if (isValidMoveBlue(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    /**
     * Ellépteti a piros  bábut a megadott irányba
     * @param pieceNumber piros bábu indexe
     * @param direction a lépés iránya
     */
    public void move(int pieceNumber, RedDirection direction) {
        pieces[pieceNumber].moveTo(direction);
    }

    /**
     * Ellépteti a kék bábut a megadott irányba
     * @param pieceNumber kék bábu indexe
     * @param direction a lépés iránya
     */
    public void move(int pieceNumber, BlueDirection direction) {
        pieces[pieceNumber].moveTo(direction);
    }

    /**
     * Ellenörőzi hogy a paraméterként kapott pozició rajta van-e a táblán
     * @param position pozició
     * @return igaz ha rajta van a táblán, egyébként hamis
     */
    public static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < 6
                && 0 <= position.col() && position.col() < 7;
    }

    /**
     * Visszaadja a paraméterül kapott pozición tartozkodó bábu indexét
     * @param position pozició
     * @return a bábu indexe a vektorban
     */
    public OptionalInt getPieceNumber(Position position) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getPosition().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (var piece : pieces) {
            joiner.add(piece.toString());
        }
        return joiner.toString();
    }
}
