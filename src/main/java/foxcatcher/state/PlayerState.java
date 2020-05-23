package foxcatcher.state;

public class PlayerState {

    private int p1Steps=0;
    private int p2Steps=0;

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


    public String toString(int a){
        return ""+a;
    }
}
