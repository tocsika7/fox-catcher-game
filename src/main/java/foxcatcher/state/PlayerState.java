package foxcatcher.state;

public class PlayerState {

    /**
     * A value that stores the steps of the fox.
     */
    private int p1Steps = 0;

    /**
     * A value that stores the steps of the dogs.
     */
    private int p2Steps = 0;

    public void setP1Steps(int p1Steps) {
        this.p1Steps = p1Steps;
    }

    public void setP2Steps(int p2Steps) {
        this.p2Steps = p2Steps;
    }

    public int getP1Steps() {
        return p1Steps;
    }

    public int getP2Steps() {
        return p2Steps;
    }

    /**
     * A method that makes a {@code String} from an {@code Integer}
     * @param a The int to convert.
     * @return The given {@code Integer} converted as a {@cde String}.
     */
    public String toString(int a) { return "" + a; }


}
