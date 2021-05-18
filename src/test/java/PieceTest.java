import boardgame.model.Piece;
import boardgame.model.PieceType;
import boardgame.model.Position;
import boardgame.model.RedDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PieceTest {

    private Piece testPiece = new Piece(PieceType.RED, new Position(-1,-1));

    @Test
    void testgetType()
    {
        assertEquals(testPiece.getType(),PieceType.RED);
    }

    @Test
    void testgetPosition(){
        assertEquals(testPiece.getPosition(),new Position(-1,-1));
    }

    @Test
    void testisAlive()
    {
        assertEquals(testPiece.getIsAlive(),true);
    }

    @Test
    void testkill()
    {
        testPiece.kill(testPiece);
        assertEquals(testPiece.getIsAlive(),false);
    }

    @Test
    void testmoveTO()
    {
        testPiece.moveTo(RedDirection.DOWN);
        assertEquals(testPiece.getPosition(), new Position(0,-1));
    }
}
