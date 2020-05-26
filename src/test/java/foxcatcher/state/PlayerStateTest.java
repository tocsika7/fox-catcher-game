package foxcatcher.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerStateTest {

    PlayerState playerState = new PlayerState();

    @Test
    void testSetSelectedCharacter(){
        playerState.setFoxSelected(false);
        playerState.setSelectedCharacter(1,true);
        assertTrue(playerState.isFoxSelected());

        playerState.setSelectedCharacter(1,false);
        assertFalse(playerState.isFoxSelected());

        playerState.setDogSelected(false);
        playerState.setSelectedCharacter(2,true);
        assertTrue(playerState.isDogSelected());

        playerState.setSelectedCharacter(2,false);
        assertFalse(playerState.isDogSelected());
    }
}
