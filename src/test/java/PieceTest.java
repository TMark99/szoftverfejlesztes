import boardgame.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {

    private Piece redtestPiece = new Piece(PieceType.RED, new Position(-1,-1));
    private Piece bluetestPiece = new Piece(PieceType.BLUE, new Position(0,0));
    private Piece blacktestPiece = new Piece(PieceType.BLACK, new Position(1,1));

    @Test
    void testgetType_BLUE()
    {
        assertNotEquals(bluetestPiece.getType(),PieceType.RED);
        assertNotEquals(bluetestPiece.getType(),PieceType.BLACK);
        assertEquals(bluetestPiece.getType(),PieceType.BLUE);
    }

    @Test
    void testgetType_RED()
    {
        assertNotEquals(redtestPiece.getType(),PieceType.BLUE);
        assertNotEquals(redtestPiece.getType(),PieceType.BLACK);
        assertEquals(redtestPiece.getType(),PieceType.RED);
    }

    @Test
    void testgetType_BLACK()
    {
        assertNotEquals(blacktestPiece.getType(),PieceType.BLUE);
        assertNotEquals(blacktestPiece.getType(),PieceType.RED);
        assertEquals(blacktestPiece.getType(),PieceType.BLACK);
    }

    @Test
    void testgetPosition(){
        assertNotEquals(redtestPiece.getPosition(),bluetestPiece.getPosition());
        assertNotEquals(redtestPiece.getPosition(),blacktestPiece.getPosition());
        assertEquals(redtestPiece.getPosition(),new Position(-1,-1));
        assertEquals(bluetestPiece.getPosition(),new Position(0,0));
        assertEquals(blacktestPiece.getPosition(),new Position(1,1));
    }

    @Test
    void testisAlive()
    {
        assertEquals(redtestPiece.getIsAlive(),true);
        assertEquals(bluetestPiece.getIsAlive(),true);
        assertEquals(blacktestPiece.getIsAlive(),true);
        assertNotEquals(redtestPiece.getIsAlive(),false);
        assertNotEquals(bluetestPiece.getIsAlive(),false);
        assertNotEquals(blacktestPiece.getIsAlive(),false);
    }

    @Test
    void testkill()
    {
        redtestPiece.kill(redtestPiece);
        assertEquals(redtestPiece.getIsAlive(),false);
        assertNotEquals(redtestPiece.getIsAlive(),true);
        bluetestPiece.kill(bluetestPiece);
        assertEquals(bluetestPiece.getIsAlive(),false);
        assertNotEquals(bluetestPiece.getIsAlive(),true);
        blacktestPiece.kill(blacktestPiece);
        assertEquals(blacktestPiece.getIsAlive(),false);
        assertNotEquals(blacktestPiece.getIsAlive(),true);
    }

    @Test
    void testmoveTO()
    {
        redtestPiece.moveTo(RedDirection.DOWN);
        assertEquals(redtestPiece.getPosition(), new Position(0,-1));
        redtestPiece.moveTo(RedDirection.DOWN_LEFT);
        assertEquals(redtestPiece.getPosition(),new Position(1,-2));
        redtestPiece.moveTo(RedDirection.DOWN_RIGHT);
        assertEquals(redtestPiece.getPosition(), new Position(2,-1));
        bluetestPiece.moveTo(BlueDirection.UP);
        assertEquals(bluetestPiece.getPosition(), new Position(-1,0));
        bluetestPiece.moveTo(BlueDirection.UP_LEFT);
        assertEquals(bluetestPiece.getPosition(),new Position(-2,-1));
        bluetestPiece.moveTo(BlueDirection.UP_RIGHT);
        assertEquals(bluetestPiece.getPosition(),new Position(-3,0));
    }
}
