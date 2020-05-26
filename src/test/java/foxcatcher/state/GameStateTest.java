package foxcatcher.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GameStateTest {



    GameState gameState = new GameState();

    @Test
    void testSetCurrentXandY(){
        gameState = new GameState();
        gameState.setCurrentXandY(3,4);
        assertEquals(3,gameState.getCurrentX());
        assertEquals(4,gameState.getCurrentY());

        gameState.setCurrentXandY(5,1);
        assertEquals(5,gameState.getCurrentX());
        assertEquals(1,gameState.getCurrentY());

        gameState.setCurrentXandY(0,0);
        assertEquals(0,gameState.getCurrentX());
        assertEquals(0,gameState.getCurrentY());

        gameState.setCurrentXandY(7,7);
        assertEquals(7,gameState.getCurrentX());
        assertEquals(7,gameState.getCurrentY());

        gameState.setCurrentXandY(3,6);
        assertEquals(3,gameState.getCurrentX());
        assertEquals(6,gameState.getCurrentY());
    }

    @Test
    void testGetTablePosition(){
        gameState = new GameState();
        gameState.setPosition(7,7,2);
        gameState.setPosition(1,3,1);
        gameState.setPosition(5,4,2);
        assertEquals(2,gameState.getTablePosition(7,7));
        assertEquals(1,gameState.getTablePosition(1,3));
        assertEquals(2,gameState.getTablePosition(5,4));
        assertEquals(0,gameState.getTablePosition(0,0));
        assertEquals(0,gameState.getTablePosition(6,4));
    }

    @Test
    void testNullPosition(){
        gameState = new GameState();
        gameState.setPosition(5,3,2);
        gameState.nullPosition(5,3);
        assertEquals(0,gameState.getTablePosition(5,3));

        gameState.setPosition(0,0,1);
        gameState.nullPosition(0,0);
        assertEquals(0,gameState.getTablePosition(0,0));

        gameState.setPosition(6,4,2);
        gameState.nullPosition(6,4);
        assertEquals(0,gameState.getTablePosition(6,4));

        gameState.setPosition(0,3,1);
        gameState.nullPosition(0,3);
        assertEquals(0,gameState.getTablePosition(0,3));

        gameState.setPosition(7,1,2);
        gameState.nullPosition(7,1);
        assertEquals(0,gameState.getTablePosition(7,1));
    }

    @Test
    void testIsValidMove(){
        int[][] Test1 = {
                {0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,2,0,2,0,2,0,2},
        };
        gameState = new GameState(Test1);
        assertTrue(gameState.isValidMove(0,3,Direction.DOWNLEFT));

        int[][] Test2 = {
                {0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,2,0,2,0,2,0,2},
        };
        gameState = new GameState(Test2);
        assertFalse(gameState.isValidMove(1,0,Direction.DOWNLEFT));

        int[][] Test3 = {
                {0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,2,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,2,0,0,0,2,0,2},
        };
        gameState = new GameState(Test3);
        assertTrue(gameState.isValidMove(4,5,Direction.UPRIGHT));

        int[][] Test4 = {
                {0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,2,0,2,0,2,0,2},
        };
        gameState = new GameState(Test4);
        assertFalse(gameState.isValidMove(0,3,Direction.UPLEFT));

        int[][] Test5 = {
                {0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,2,0,2,0,2,0,2},
        };
        gameState = new GameState(Test5);
        assertFalse(gameState.isValidMove(7,1,Direction.DOWNRIGHT));
    }

    @Test
    void testWin(){
        int[][] Test1 = {
                {0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,2,0,2,0,2,0,2},
        };
        gameState = new GameState(Test1);
        assertFalse(gameState.foxWin(0,3));
        assertFalse(gameState.dogWin(0,3));

        int[][] Test2 = {
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,2,0,2,0,0},
                {0,2,0,0,0,0,0,0},
                {0,0,0,0,0,2,0,0},
                {0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
        };
        gameState = new GameState(Test2);
        assertTrue(gameState.foxWin(5,2));
        assertFalse(gameState.dogWin(5,2));

        int[][] Test3 = {
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,2,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0},
                {0,2,0,0,0,0,0,0},
                {0,0,0,0,0,2,0,2},
        };
        gameState = new GameState(Test3);
        assertFalse(gameState.foxWin(5,0));
        assertTrue(gameState.dogWin(5,0));

        int[][] Test4 = {
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,2,0,2,0,0},
                {0,0,0,0,1,0,0,0},
                {0,0,0,2,0,2,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
        };
        gameState = new GameState(Test4);
        assertFalse(gameState.foxWin(3,4));
        assertTrue(gameState.dogWin(3,4));

        int[][] Test5 = {
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,2,0,0,0,0,0,0},
                {0,0,0,0,2,0,0,0},
                {0,0,0,2,0,2,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,1,0,0,0,0},
                {0,0,0,0,0,0,0,0},
        };
        gameState = new GameState(Test5);
        assertTrue(gameState.foxWin(6,3));
        assertFalse(gameState.dogWin(6,3));
    }


}
