package boardgame.model;

/**
 * Egy rekord, ami kiszámolja az új mező koordinátáját
 */
public record Position(int row, int col) {

    /**
     * Kiszámolja a lépést követő új mező koordinátáját
     * @param direction az elmozdulás iránya
     * @return az új mező poziciója
     */
    public Position moveTo(Direction direction) {
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }
}