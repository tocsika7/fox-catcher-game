package foxcatcher.state;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class GameState {

    public GameState(){

    }

    public GameState(int[][] Board){
        this.Board = Board;
    }
    /**
     *  Value that stores the x position of the table's currently selected field.
     */
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    private int currentX;

    /**
     *  Value that stores the y position of the table's currently selected field.
     */
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    private int currentY;

    /**
     *  A String value that stores which character the current player is playing with.
     */
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    private String currentCharacter="fox";

    /**
     * Value that stores the winners name.
     */
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    private String winnerName;

    /**
     * Value that stores how much steps the winner took.
     */
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    private int winnerSteps;

    /**
     *  Value that stores the character the winner played with.
     */
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    private String winnerCharacter;

    /**
     *  Value that stores if the currently selected character is the dog.
     */
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    private boolean dogSelected = false;

    /**
     *  Value that stores if the currently selected character is the fox.
     */
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    private boolean foxSelected = false;

    /**
     *  A 2D Array which represents the game's board.
     *  The fox character is represented as 1, the dogs as 2 and the empty spaces as zeros.
     */
    private static int[][] Board = {
            {0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,2,0,2,0,2,0,2},
    };


    /**
     *  A method that sets either one of the selected characters.
     * @param player Which players character should be set.
     * @param state  What the characters state should be.
     */
    public void setSelectedCharacter(int player, boolean state){
        if(player==1)
            foxSelected=state;
        if(player==2)
            dogSelected=state;
    }

    /**
     *  A method that sets both the current x and y coordinates of the player.
     * @param x The x coordinate of the player.
     * @param y The y coordinate of the player.
     */
    public void setCurrentXandY(int x, int y){
        this.currentX = x;
        this.currentY = y;
    }

    /**
     * A method that returns which player is on the board's given position.
     * @param x The board's x coordinate.
     * @param y The board's y coordinate.
     * @return which player is on the board's given coordinates.
     */
    public int getTablePosition(int x,int y){
        return Board[x][y];
    }

    /**
     * A method that sets the player on the board's given position.
     * @param x The board's x coordinate.
     * @param y The board's y coordinate
     * @param playerType Which player should be set to the board's position.
     */
    public void setPosition(int x, int y, int playerType){
        Board[x][y]=playerType;
    }

    /**
     * A method that sets the board's given position to 0.
     * @param x The board's x coordinate.
     * @param y The board's y coordinate.
     */
    public void nullPosition(int x,int y){
        setPosition(x,y,0);
    }

    /**
     * A method that returns whether the player's next move is going to be valid.
     * @param x The current x position of the player.
     * @param y The current y position of the player
     * @param direction The {@code Direction} in which the player wants to move.
     * @throws ArrayIndexOutOfBoundsException when the move would move out of the board.
     * @return Whether the move is valid or not.
     */
    public boolean isValidMove(int x, int y, Direction direction){
        try {
            if (getTablePosition(x+direction.getDx(),y+direction.getDy())==0){
                return true;
            }
            else {
                return false;
            }
        }catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
    }

    /**
     *  A method that returns whether the fox has won the game or not.
     *  The fox wins the game when from his current position in the board there are only zeros.
     * @param x The fox's x coordinate on the board.
     * @param y The fox's y coordinate on the board.
     * @return Whether the fox has won or not.
     */
    public boolean foxWin(int x,int y){
        for(int i=x; i<Board.length; i++){
            for(int j=y; j< Board[i].length; j++){
                if(getTablePosition(i,j)==2){
                    return false;
                }
            }
        }
        log.info("The fox has won the game.");
        return true;
    }

    /**
     * A method that returns whether the dogs has won the game or not.
     * The dogs win the game if the fox has no valid moves.
     * @param x The fox's current x coordinate on the board.
     * @param y The fox's current y coordinate on the board.
     * @return Whether the dogs has won the game or not.
     */
    public boolean dogWin(int x, int y){
        if((!isValidMove(x, y, Direction.UPRIGHT)) &&
                (!isValidMove(x, y, Direction.UPLEFT))&&
                (!isValidMove(x, y, Direction.DOWNLEFT))&&
                (!isValidMove(x, y, Direction.DOWNRIGHT))){
            log.info("Th dog has won");
            return true;
        }
        return false;
    }
}
