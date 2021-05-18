package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Osztály a bábuk számára.
 */
public class Piece {

    private PieceType type;
    private ObjectProperty<Position> position = new SimpleObjectProperty<Position>();
    private boolean IsAlive = true;

    /**
     * Konstruktor a bábuk számára.
     * @param type a bábu típusa (színe)
     * @param position a bábu kezdő poziciója a táblán
     */
    public Piece(PieceType type, Position position) {
        this.type = type;
        this.position.set(position);
    }

    /**
     * Vissza adja a bábú típusát.
     * @return a bábu típusa
     */
    public PieceType getType() {
        return type;
    }

    /**
     * A kiütött bábu élet státuszát változtatja meg.
     * @param piece a bábu amit kiütnek
     */
    public void kill(Piece piece){
        this.IsAlive = false;
    }

    /**
     * Visszadja a bábu élet állapotát.
     * @return a bábu élet állapota
     */
    public boolean getIsAlive(){
        return IsAlive;
    }

    /**
     * Visszadja a bábu jelenlegi pozicióját.
     * @return a bábu poziciója
     */
    public Position getPosition() {
        return position.get();
    }

    /**
     * Áthelyezi a bábut a megadott irányba
     * @param direction az irány amerre elmozdul a bábu
     */
    public void moveTo(Direction direction) {
        Position newPosition = position.get().moveTo(direction);
        position.set(newPosition);
    }

    /**
     * Visszadja a bábu sor és oszlop számát
     * @return a sor és oszlop számot
     */
    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    /**
     * A bábu sor és oszlop számát szöveg ként adja vissza
     * @return a sor és oszlop szám szövegként
     */
    public String toString() {
        return type.toString() + position.get().toString();
    }
}
