package foxcatcher.javafx.controller;

import foxcatcher.results.GameResult;
import foxcatcher.results.GameResultDao;
import foxcatcher.state.Direction;
import foxcatcher.state.GameState;
import foxcatcher.state.PlayerState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
public class GameController {

    GameState gameState = new GameState();

    PlayerState playerState = new PlayerState();

    @Inject
    private GameResultDao gameResultDao;

    @Inject
    FXMLLoader fxmlLoader;

    @FXML
    Label stopWatchLabel;

    @FXML
    Label messageLabel;

    @FXML
    Label p1StepsLabel;

    @FXML
    Label p2StepsLabel;

    @FXML
    GridPane gameGrid;

    @FXML
    Button resetButton;

    @FXML
    Button highscoreButton;

    private String player1Name, player2Name;
    private Instant startTime;
    private List<Image> boardImages;
    private Timeline stopWatchTimeline;

    public void setPlayer1Name(String player1Name){ this.player1Name = player1Name; }

    public void setPlayer2Name(String player2Name){ this.player2Name = player2Name; }

    @FXML
    public void initialize() {
        boardImages = List.of(
                new Image(getClass().getResource("/images/empty.png").toExternalForm()),
                new Image(getClass().getResource("/images/fox.png").toExternalForm()),
                new Image(getClass().getResource("/images/dog.png").toExternalForm())

        );
        setStartingState();
        resetGame();
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
        if(gameState.isValidMove(gameState.getCurrentX(),gameState.getCurrentY(),direction)){
            gameState.nullPosition(gameState.getCurrentX(),gameState.getCurrentY());
            ImageView imageView = (ImageView) gameGrid.getChildren().get(gameState.getCurrentX() * 8 + gameState.getCurrentY());
            imageView.setImage(boardImages.get(0));
            gameState.setCurrentXandY(gameState.getCurrentX() + direction.getDx(),gameState.getCurrentY()+direction.getDy());
            gameState.setPosition(gameState.getCurrentX(),gameState.getCurrentY(),image);
            ImageView newFox = (ImageView) gameGrid.getChildren().get(gameState.getCurrentX() * 8 + gameState.getCurrentY());
            newFox.setImage(boardImages.get(image));
            playerState.setSelectedCharacter(image,false);
            log.info("Player{} moved to the {} direction.",image,direction);
        }
    }

    @FXML
    private void foxMove(KeyEvent keyEvent){
            switch (keyEvent.getCode()) {
                case A:
                     Move(Direction.DOWNLEFT,1,playerState.isFoxSelected());
                     winHandler();
                     playerStepIncrease(1);
                     break;
                case D:
                    Move(Direction.DOWNRIGHT,1,playerState.isFoxSelected());
                    winHandler();
                    playerStepIncrease(1);
                    break;
                case Q:
                    Move(Direction.UPLEFT,1,playerState.isFoxSelected());
                    winHandler();
                    playerStepIncrease(1);
                    break;
                case E:
                    Move(Direction.UPRIGHT,1,playerState.isFoxSelected());
                    winHandler();
                    playerStepIncrease(1);
                    break;
            }
    }

    public void playerStepIncrease(int player){
        if(player==1){
            playerState.setP1Steps(playerState.getP1Steps()+1);
            p1StepsLabel.setText(String.valueOf(playerState.getP1Steps()));
        }
        else if(player==2){
            playerState.setP2Steps(playerState.getP2Steps()+1);
            p2StepsLabel.setText(String.valueOf(playerState.getP2Steps()));
        }
    }

    public void resetSteps(){
        playerState.setP1Steps(0);
        playerState.setP2Steps(0);
        p1StepsLabel.setText("0");
        p2StepsLabel.setText("0");
        log.info("The steps has been reset");
    }

    @FXML
    private void dogMove(KeyEvent keyEvent){
        switch (keyEvent.getCode()){
            case Q:
                Move(Direction.UPLEFT,2,playerState.isDogSelected());
                playerStepIncrease(2);
                break;
            case E:
                Move(Direction.UPRIGHT,2,playerState.isDogSelected());
                playerStepIncrease(2);
                break;
        }
    }



    @FXML
    private void moveHandler(KeyEvent keyEvent) {
        if(playerState.isFoxSelected()){
            foxMove(keyEvent);
        }
        else if(playerState.isDogSelected()) {
            dogMove(keyEvent);
        }
    }



    @FXML
    private void handleClickOnPostion(MouseEvent mouseEvent){
        int row = GridPane.getRowIndex((Node) mouseEvent.getSource());
        int col = GridPane.getColumnIndex((Node) mouseEvent.getSource());
        log.info("Position {} {} is selected.",row,col);
        if((gameState.getTablePosition(row,col)==1)&&gameState.getCurrentCharacter()=="fox"){
            playerState.setFoxSelected(true);
            playerState.setDogSelected(false);
            gameState.setCurrentX(row);
            gameState.setCurrentY(col);
            gameState.setCurrentCharacter("dog");
        }
        else if((gameState.getTablePosition(row,col)==2)&&gameState.getCurrentCharacter()=="dog"){
            playerState.setDogSelected(true);
            playerState.setFoxSelected(false);
            gameState.setCurrentX(row);
            gameState.setCurrentY(col);
            gameState.setCurrentCharacter("fox");
        }
    }

    @FXML
    public void handleHighScoreButton(ActionEvent actionEvent) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/highscore.fxml"));
        Parent root2 = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root2));
        stage.show();

    }

    public void winHandler(){
        if(gameState.foxWin(gameState.getCurrentX(),gameState.getCurrentY())){
            gameState.setWinnerName(player1Name);
            gameState.setWinnerSteps(playerState.getP1Steps());
            gameState.setWinnerCharacter("fox");
            gameResultDao.persist(createGameResult());
            messageLabel.setText("Congratulations "+player1Name+"!");
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    gameState.nullPosition(i,j);
                }
            }
            stopWatchTimeline.stop();
        }
        else if(gameState.dogWin(gameState.getCurrentX(),gameState.getCurrentY())){
            gameState.setWinnerName(player2Name);
            gameState.setWinnerSteps(playerState.getP2Steps());
            gameState.setWinnerCharacter("dog");
            gameResultDao.persist(createGameResult());
            messageLabel.setText("Congratulations "+player2Name+"!");
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    gameState.nullPosition(i,j);
                }
            }
            stopWatchTimeline.stop();
        }
    }


   private GameResult createGameResult(){
        GameResult result = GameResult.builder()
                .player(gameState.getWinnerName())
                .steps(gameState.getWinnerSteps())
                .animal(gameState.getWinnerCharacter())
                .duration(Duration.between(startTime,Instant.now()))
                .build();
        return result;
   }


    private void resetGame() {

        startTime=Instant.now();
      createStopWatch();
      Platform.runLater(()-> messageLabel.setText("Good Luck "+ player1Name + " and " + player2Name));

      resetSteps();

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

