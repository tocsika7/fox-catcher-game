package foxcatcher.javafx.controller;

import foxcatcher.state.Direction;
import foxcatcher.state.GameState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;

import javax.inject.Inject;
import java.awt.event.KeyListener;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class GameController {

    GameState gameState = new GameState();


    @Inject
    FXMLLoader fxmlLoader;

    @FXML
    Label stopWatchLabel;

    @FXML
    Label messageLabel;

    @FXML
    GridPane gameGrid;

    private String player1Name, player2Name;
    private Instant startTime;
    private List<Image> boardImages;
    private Timeline stopWatchTimeline;

    private int currentX;
    private int currentY;

    public final int[][] Original = gameState.getOriginal();

    private boolean dogSelected=false;
    private boolean foxSelected=false;


    public int getCurrentX(){
        return currentX;
    }

    public int getCurrentY(){
        return currentY;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public void setCurrentPosition(int currentX, int currentY){
        this.currentX = currentX;
        this.currentY = currentY;
    }

    public void setPlayerState(int player,boolean state){
        if(player==1)
            foxSelected=state;
        if(player==2)
            dogSelected=state;
    }


    @FXML
    public void initialize() {
        boardImages = List.of(
                new Image(getClass().getResource("/images/empty.png").toExternalForm()),
                new Image(getClass().getResource("/images/fox.png").toExternalForm()),
                new Image(getClass().getResource("/images/dog.png").toExternalForm())

        );
        setStartingState();;
    }

    private void setStartingState() {
        ImageView fox = (ImageView) gameGrid.getChildren().get(0 + 2);
        fox.setImage(boardImages.get(1));

        for(int i = 57; i< 64; i+=2) {
            ImageView dog = (ImageView) gameGrid.getChildren().get(i);
            dog.setImage(boardImages.get(2));
        }
    }

    @FXML
    private void Move(Direction direction,int image,boolean player){
        if(gameState.isValidMove(currentX,currentY,direction)==true){
            gameState.nullPosition(currentX,currentY);
            ImageView imageView = (ImageView) gameGrid.getChildren().get(currentX * 8 + currentY);
            imageView.setImage(boardImages.get(0));
            setCurrentPosition(currentX + direction.getDx(),currentY+direction.getDy());
            gameState.setPosition(currentX,currentY,image);
            ImageView newFox = (ImageView) gameGrid.getChildren().get(currentX * 8 + currentY);
            newFox.setImage(boardImages.get(image));
            setPlayerState(image,false);
        }
    }

    @FXML
    private void foxMove(KeyEvent keyEvent){
            switch (keyEvent.getCode()) {
                case A:
                     Move(Direction.DOWNLEFT,1,foxSelected);
                     gameState.printState();
                     gameState.foxWin(getCurrentX(),getCurrentY());
                     gameState.dogWin(getCurrentX(),getCurrentY());
                     break;
                case D:
                    Move(Direction.DOWNRIGHT,1,foxSelected);
                    gameState.printState();
                    gameState.foxWin(getCurrentX(),getCurrentY());
                    gameState.dogWin(getCurrentX(),getCurrentY());
                    break;
                case Q:
                    Move(Direction.UPLEFT,1,foxSelected);
                    gameState.printState();
                    gameState.foxWin(getCurrentX(),getCurrentY());
                    gameState.dogWin(getCurrentX(),getCurrentY());
                    break;
                case E:
                    Move(Direction.UPRIGHT,1,foxSelected);
                    gameState.printState();
                    gameState.foxWin(getCurrentX(),getCurrentY());
                    gameState.dogWin(getCurrentX(),getCurrentY());
                    break;
            }
    }

    @FXML
    private void dogMove(KeyEvent keyEvent){
        switch (keyEvent.getCode()){
            case Q:
                Move(Direction.UPLEFT,2,dogSelected);
                gameState.printState();
                break;
            case E:
                Move(Direction.UPRIGHT,2,dogSelected);
                gameState.printState();
                break;
        }
    }



    @FXML
    private void moveHandler(KeyEvent keyEvent) {
        if(foxSelected==true){
            System.out.println("Fox has moved");
            foxMove(keyEvent);
        }
        else if(dogSelected==true) {
            System.out.println("Dog has moved");
            dogMove(keyEvent);
        }
    }

    @FXML
    private void handleClickOnPostion(MouseEvent mouseEvent){
        int row = GridPane.getRowIndex((Node) mouseEvent.getSource());
        int col = GridPane.getColumnIndex((Node) mouseEvent.getSource());
        System.out.println("Position: "+row+" "+col+" is pressed"); //TODO Change to log later
        if(gameState.getTablePosition(row,col)==1) {
            foxSelected = true;
            dogSelected=false;
            currentX = row;
            currentY = col;
        }
        else if(gameState.getTablePosition(row,col)==2) {
            dogSelected = true;
            foxSelected=false;
            currentX = row;
            currentY = col;
        }
    }



    private void resetGame() {

        startTime=Instant.now();
      createStopWatch();
      Platform.runLater(()-> messageLabel.setText("Good Luck "+ player1Name + " and " + player2Name));

      for(int i=0; i<8; i++) {
          for (int j = 0 ;j < 8; j++) {
              ImageView imageView = (ImageView) gameGrid.getChildren().get(i * 8 + j);
              gameState.nullPosition(i,j);
              imageView.setImage(boardImages.get(0));
          }
      }

      setStartingState();
      gameState.setPosition(0,2,1);
      for(int i=1; i<8; i+=2){
          gameState.setPosition(7,i,2);
      }
    }

    public void handleResetButton(ActionEvent actionEvent) {
        resetGame();
    }

    private void createStopWatch() {
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopWatchLabel.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();
    }
}
