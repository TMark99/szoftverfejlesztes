import boardgame.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardGameModelTest {

    private Piece[] pieces = {(new Piece(PieceType.RED, new Position(1, 1))),
                                (new Piece(PieceType.RED, new Position(2, 2))),
                                (new Piece(PieceType.BLUE, new Position(3, 2)))};
    @Test
    void testlength()
    {
        assertEquals(pieces.length,3);
    }

}
