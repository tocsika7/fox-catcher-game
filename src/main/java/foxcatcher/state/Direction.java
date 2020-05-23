package foxcatcher.state;

public enum  Direction {
    UPLEFT(-1,-1),
    UPRIGHT(-1,1),
    DOWNLEFT(1,-1),
    DOWNRIGHT(1,1);

    private int dx;
    private int dy;

    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx(){
        return dx;
    }

    public int getDy(){
        return dy;
    }

    public Direction opposite() {
        switch (this) {
            case UPLEFT: return DOWNRIGHT;
            case UPRIGHT: return DOWNLEFT;
            case DOWNLEFT: return UPRIGHT;
            case DOWNRIGHT:return UPLEFT;
        }
        throw new AssertionError();
    }
}


