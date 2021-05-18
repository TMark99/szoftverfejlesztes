import boardgame.model.RedDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RedDirectionTest {

    @Test
    void testOf(){
        assertEquals(RedDirection.DOWN_LEFT,RedDirection.of(1,-1));
        assertEquals(RedDirection.DOWN, RedDirection.of(1,0));
        assertEquals(RedDirection.DOWN_RIGHT, RedDirection.of(1,1));
    }

    @Test
    void testOf_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> RedDirection.of(-1, 1));
    }
}
