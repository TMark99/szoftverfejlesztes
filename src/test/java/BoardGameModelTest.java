import boardgame.model.*;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

public class BoardGameModelTest {

    private Piece[] pieces = {(new Piece(PieceType.RED, new Position(1, 1))),
                                (new Piece(PieceType.RED, new Position(2, 2))),
                                (new Piece(PieceType.BLUE, new Position(3, 2)))};

    BoardGameModel model = new BoardGameModel();

    @Test
    void testlength()
    {
        assertEquals(pieces.length,3);
        assertNotEquals(pieces.length,6);
    }

    @Test
    void testgetPieceCount(){
        assertEquals(model.getPieceCount(),16);
    }

    @Test
    void testgetPieceType()
    {
        assertEquals(model.getPieceType(1),PieceType.RED);
        assertEquals(model.getPieceType(9),PieceType.BLUE);
        assertEquals(model.getPieceType(15),PieceType.BLACK);
        assertNotEquals(model.getPieceType(2),PieceType.BLUE);
        assertNotEquals(model.getPieceType(4),PieceType.BLACK);
        assertNotEquals(model.getPieceType(10),PieceType.RED);
        assertNotEquals(model.getPieceType(12),PieceType.BLACK);
        assertNotEquals(model.getPieceType(15),PieceType.RED);
        assertNotEquals(model.getPieceType(14),PieceType.BLUE);
    }

    @Test
    void testgetPiecePosition(){
        assertEquals(model.getPiecePosition(2), new Position(0,2));
        assertEquals(model.getPiecePosition(10), new Position(5,3));
        assertEquals(model.getPiecePosition(14), new Position(3,2));
        assertNotEquals(model.getPiecePosition(2), new Position(2,2));
        assertNotEquals(model.getPiecePosition(10), new Position(3,2));
        assertNotEquals(model.getPiecePosition(14), new Position(0,2));
    }

    @Test
    void testisvalidmove(){
        assertThrows(IllegalArgumentException.class, () -> model.isValidMove(-1,RedDirection.DOWN));
        assertThrows(IllegalArgumentException.class, () -> model.isValidMove(20,RedDirection.DOWN));

        assertEquals(model.isValidMove(1,RedDirection.DOWN_RIGHT),true);
        assertEquals(model.isValidMove(1,RedDirection.DOWN),true);
        assertEquals(model.isValidMove(1,RedDirection.DOWN_LEFT),true);
        assertEquals(model.isValidMove(0,RedDirection.DOWN_LEFT),false);
        assertEquals(model.isValidMove(6,RedDirection.DOWN_RIGHT),false);

        model.move(2,RedDirection.DOWN);

        assertEquals(model.isValidMove(1,RedDirection.DOWN_RIGHT),false);
        assertEquals(model.isValidMove(3,RedDirection.DOWN_LEFT),false);

        model.move(3,RedDirection.DOWN_RIGHT);

        assertEquals(model.isValidMove(4,RedDirection.DOWN),false);

        for (int i =0; i<4; i++)
            model.move(1,RedDirection.DOWN);

        assertEquals(model.isValidMove(1,RedDirection.DOWN),false);
        assertEquals(model.isValidMove(1,RedDirection.DOWN_RIGHT),true);
        assertEquals(model.isValidMove(1,RedDirection.DOWN_LEFT),true);
    }

    @Test
    void testisValidMoveBLue(){
        assertThrows(IllegalArgumentException.class, () -> model.isValidMoveBlue(-1,BlueDirection.UP));
        assertThrows(IllegalArgumentException.class, () -> model.isValidMoveBlue(20,BlueDirection.UP));

        assertEquals(model.isValidMoveBlue(8,BlueDirection.UP),true);
        assertEquals(model.isValidMoveBlue(8,BlueDirection.UP_RIGHT),true);
        assertEquals(model.isValidMoveBlue(8,BlueDirection.UP_LEFT),true);
        assertEquals(model.isValidMoveBlue(7,BlueDirection.UP_LEFT),false);
        assertEquals(model.isValidMoveBlue(13,BlueDirection.UP_RIGHT),false);

        model.move(8,BlueDirection.UP);

        assertEquals(model.isValidMoveBlue(7,BlueDirection.UP_RIGHT),false);
        assertEquals(model.isValidMoveBlue(9,BlueDirection.UP_LEFT),false);

        model.move(9,BlueDirection.UP_RIGHT);

        assertEquals(model.isValidMoveBlue(10,BlueDirection.UP),false);

        for (int i =0; i<3; i++)
            model.move(8,BlueDirection.UP);

        assertEquals(model.isValidMoveBlue(10,BlueDirection.UP),false);
        assertEquals(model.isValidMoveBlue(10,BlueDirection.UP_RIGHT),true);
        assertEquals(model.isValidMoveBlue(10,BlueDirection.UP_LEFT),true);
    }

    @Test
    void testgetValidMoves()
    {
        EnumSet<RedDirection> validMovesforPiece0 = EnumSet.noneOf(RedDirection.class);
        validMovesforPiece0.add(RedDirection.DOWN);
        validMovesforPiece0.add(RedDirection.DOWN_RIGHT);
        assertEquals(model.getValidMoves(0),validMovesforPiece0);

        EnumSet<RedDirection> validMovesforPiece6 = EnumSet.noneOf(RedDirection.class);
        validMovesforPiece6.add(RedDirection.DOWN);
        validMovesforPiece6.add(RedDirection.DOWN_LEFT);
        assertEquals(model.getValidMoves(6),validMovesforPiece6);

        EnumSet<RedDirection> validMovesforPiece5 = EnumSet.noneOf(RedDirection.class);
        validMovesforPiece5.add(RedDirection.DOWN);
        validMovesforPiece5.add(RedDirection.DOWN_LEFT);
        validMovesforPiece5.add(RedDirection.DOWN_RIGHT);
        assertEquals(model.getValidMoves(5),validMovesforPiece5);

        model.move(3,RedDirection.DOWN);

        EnumSet<RedDirection> validMovesforPiece4 = EnumSet.noneOf(RedDirection.class);
        validMovesforPiece4.add(RedDirection.DOWN);
        validMovesforPiece4.add(RedDirection.DOWN_RIGHT);
        assertEquals(model.getValidMoves(4) ,validMovesforPiece4);

        EnumSet<RedDirection> validMovesforPiece2 = EnumSet.noneOf(RedDirection.class);
        validMovesforPiece2.add(RedDirection.DOWN);
        validMovesforPiece2.add(RedDirection.DOWN_LEFT);
        assertEquals(model.getValidMoves(2),validMovesforPiece2);

        for (int i =0; i<4; i++)
            model.move(1,RedDirection.DOWN);

        EnumSet<RedDirection> validMovesforPiece1 = EnumSet.noneOf(RedDirection.class);
        validMovesforPiece1.add(RedDirection.DOWN_LEFT);
        validMovesforPiece1.add(RedDirection.DOWN_RIGHT);
        assertEquals(model.getValidMoves(1),validMovesforPiece1);
    }

    @Test
    void getValidMovesBlue(){
        EnumSet<BlueDirection> validMovesforPiece7 = EnumSet.noneOf(BlueDirection.class);
        validMovesforPiece7.add(BlueDirection.UP);
        validMovesforPiece7.add(BlueDirection.UP_RIGHT);
        assertEquals(model.getValidMovesBlue(7),validMovesforPiece7);

        EnumSet<BlueDirection> validMovesforPiece13 = EnumSet.noneOf(BlueDirection.class);
        validMovesforPiece13.add(BlueDirection.UP);
        validMovesforPiece13.add(BlueDirection.UP_LEFT);
        assertEquals(model.getValidMovesBlue(13),validMovesforPiece13);

        EnumSet<BlueDirection> validMovesforPiece12 = EnumSet.noneOf(BlueDirection.class);
        validMovesforPiece12.add(BlueDirection.UP);
        validMovesforPiece12.add(BlueDirection.UP_LEFT);
        validMovesforPiece12.add(BlueDirection.UP_RIGHT);
        assertEquals(model.getValidMovesBlue(12),validMovesforPiece12);

        model.move(10,BlueDirection.UP);

        EnumSet<BlueDirection> validMovesforPiece9 = EnumSet.noneOf(BlueDirection.class);
        validMovesforPiece9.add(BlueDirection.UP);
        validMovesforPiece9.add(BlueDirection.UP_LEFT);
        assertEquals(model.getValidMovesBlue(9),validMovesforPiece9);

        EnumSet<BlueDirection> validMovesforPiece11 = EnumSet.noneOf(BlueDirection.class);
        validMovesforPiece11.add(BlueDirection.UP);
        validMovesforPiece11.add(BlueDirection.UP_RIGHT);
        assertEquals(model.getValidMovesBlue(11),validMovesforPiece11);

        for (int i =0; i<4; i++)
            model.move(8,BlueDirection.UP);

        EnumSet<BlueDirection> validMovesforPiece8 = EnumSet.noneOf(BlueDirection.class);
        validMovesforPiece8.add(BlueDirection.UP_RIGHT);
        validMovesforPiece8.add(BlueDirection.UP_LEFT);
        assertEquals(model.getValidMovesBlue(8),validMovesforPiece8);
    }
}
