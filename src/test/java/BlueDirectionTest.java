import boardgame.model.BlueDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BlueDirectionTest {

    @Test
    void testOf()
    {
        assertEquals(BlueDirection.UP,BlueDirection.of(-1,0));
        assertEquals(BlueDirection.UP_LEFT,BlueDirection.of(-1,-1));
        assertEquals(BlueDirection.UP_RIGHT,BlueDirection.of(-1,1));
    }

    @Test
    void testOf_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> BlueDirection.of(1, 1));
    }
}
