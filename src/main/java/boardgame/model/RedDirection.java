package boardgame.model;

/**
 * A piros bábuk lehetséges lépését tartlamza
 * **/

public enum RedDirection implements Direction {

    DOWN_RIGHT(1, 1),
    DOWN(1, 0),
    DOWN_LEFT(1, -1);

    private int rowChange;
    private int colChange;

    private RedDirection(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * A lépés irányát követően megmondja, hogy milyen értékkel fog változni a sor számm
     * @return a lépés során történő sor változást értékét
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     * A lépés irányát követően megmondja, hogy milyen értékkel fog változni az  oszlop számm
     * @return a lépés során történő oszlopváltozás értékét
     */
    public int getColChange() {
        return colChange;
    }

    /**
     * Vissza adja a sor és oszlop változás által meghatároztt lépés irányt
     * @param rowChange a sor változás értéke
     * @param colChange az oszlop változás értéke
     * @return az irány ha a bábu léphet abba az irányba
     */
    public static RedDirection of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }
}
