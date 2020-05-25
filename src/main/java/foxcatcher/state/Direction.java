package foxcatcher.state;

/**
 *  Enum representing the possible directions of a player's move.
 */
public enum  Direction {
    UPLEFT(-1,-1),
    UPRIGHT(-1,1),
    DOWNLEFT(1,-1),
    DOWNRIGHT(1,1);

    private int dx;
    private int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns the x coordinate of the of the given direction.
     * @return the x coordinate of the of the given direction.
     */
    public int getDx(){
        return dx;
    }

    /**
     *  Returns the y coordinate of the given direction.
     * @return the y coordinate of the given direction.
     */
    public int getDy(){
        return dy;
    }

}


