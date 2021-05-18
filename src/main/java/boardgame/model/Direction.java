package boardgame.model;

/**
 * Egy intefész, amit az irány osztályok terjesztenek ki
 */
public interface Direction {

    int getRowChange();
    int getColChange();

}
