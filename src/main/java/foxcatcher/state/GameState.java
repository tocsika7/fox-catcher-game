package foxcatcher.state;

import java.util.Arrays;

public class GameState {

    public GameState(){
        this(T);
    }

    public GameState(int[][] a){
    }



    private static int[][] T = {
            {0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,2,0,2,0,2,0,2},
    };

    private final static int[][] Original = {
            {0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,2,0,2,0,2,0,2},
    };


    public int[][] getOriginal(){
        return Original;
    }

    public void setT(int[][] T){
        T = this.T;
    }


    public int getTablePosition(int x,int y){
        return T[x][y];
    }

    public void setPosition(int x, int y, int playerType){
        T[x][y]=playerType;
    }

    public void nullPosition(int x,int y){
        setPosition(x,y,0);
    }

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

    public void printState(){
        System.out.println(Arrays.deepToString(T).replace("], ", "]\n"));
    }

    public boolean foxWin(int x,int y){
        for(int i=x; i<T.length; i++){
            for(int j=y; j< T[i].length; j++){
                if(getTablePosition(i,j)==2){
                    return false;
                }
            }
        }
        System.out.println("The fox has won");
        return true;
    }

    public boolean dogWin(int x, int y){
        if((isValidMove(x,y,Direction.UPRIGHT)==false) &&
                (isValidMove(x,y,Direction.UPLEFT)==false)&&
                (isValidMove(x,y,Direction.DOWNLEFT)==false)&&
                (isValidMove(x,y,Direction.DOWNRIGHT)==false)){
            System.out.println("The dog has won");
            return true;
        }
        return false;
    }
}
