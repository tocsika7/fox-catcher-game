package foxcatcher.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class PlayerStateTest {

    PlayerState playerState;

    @Test
    void testToString(){
        assertEquals("15",playerState.toString(15));
    }
}
