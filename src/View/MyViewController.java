package View;

import Server.Configurations;
import ViewModel.MyViewModel;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;


public class MyViewController implements Observer, Initializable {

    public static final Logger logger = LogManager.getLogger(MyViewController.class);


    public BorderPane MainBorderPane;
    public Pane centerPane;
    public Pane container;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public MazeDisplayer mazeDisplayer;
    public MyViewModel viewModel;
    public ScrollPane scrollPane;
    public Menu Exit;
    public Menu Help;
    public Menu About;
    public Button solveBtn;
    public Audio audio = new Audio();
    public Properties properties;
    @FXML
    private MediaView DancingJerryWithBird;
    @FXML
    private MediaView StartingVideo;
    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        viewModel = new MyViewModel();
        viewModel.addObserver(this);
        // resize maze when user change window size.
        mazeDisplayer.heightProperty().bind(centerPane.heightProperty());
        mazeDisplayer.widthProperty().bind(centerPane.widthProperty());
        //initialize zoom in/out
        scrollPane = new ScrollPane(mazeDisplayer);
        scrollPane.setPannable(true);
        container.getChildren().add(scrollPane);
        addMouseScrolling(mazeDisplayer);
        //initialize music
        audio.playInLoop(0);

        // initialize video
        String _path1 = getClass().getResource("/Video/StartingVideo.MPEG").getPath();
        String _path2 = getClass().getResource("/Video/DancingJerryWithBird.MPEG").getPath();

        file = new File(_path1);
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        StartingVideo.setMediaPlayer(this.mediaPlayer);

        file = new File(_path2);
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        DancingJerryWithBird.setMediaPlayer(this.mediaPlayer);

        // initialize exit button
        setMenuButton();
        setBackground();
    }

    public void generateMaze() throws IOException {

        properties = new Properties();
        InputStream file = null;
        try {
            file = new FileInputStream("resources/config.properties");
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //stop the menu music and start playing game music:
        audio.stop(0);
        audio.playInLoop(1); // starts play game song
        mediaPlayer.dispose();


        int rows = Integer.parseInt(textField_mazeRows.getText());
        int cols = Integer.parseInt(textField_mazeColumns.getText());
        if(rows < 4 || cols < 4)
        {
            showAlertWarning("Minimum size of rows,columns is 4! choose bigger maze.","WARNING");
        }
        else if(rows > 40 || cols > 40){
            showAlertWarning("Maximum size of rows,columns is 100! choose smaller maze.","WARNING");
        }
        else{
            if(solveBtn.isDisable())
                solveBtn.setDisable(false);

            // write to the log :
            logger.info("******************************************************************************");
            logger.info("Some information about the maze before the user start to play :");
            logger.info("The algorithm the system choose to create the maze is --> " + properties.getProperty("mazeGeneratingAlgorithm"));
            logger.info("The algorithm the system choose to solve the maze itself is --> " + properties.getProperty("mazeSearchingAlgorithm"));


            viewModel.generateMaze(rows,cols);
        }
    }

    private void setBackground(){
        Image img = new Image("Images/House.jpg");
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        MainBorderPane.setBackground(bGround);
    }

    private void setMenuButton(){
        Label menuLabelExit = new Label("Exit");
        menuLabelExit.setStyle("-fx-text-fill: black;");
        Label menuLabelHelp = new Label("Help");
        menuLabelHelp.setStyle("-fx-text-fill: black;");
        Label menuLabelAbout = new Label("About");
        menuLabelAbout.setStyle("-fx-text-fill: black;");


        menuLabelAbout.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popUpPage("About");
            }
        });
        About.setGraphic(menuLabelAbout);

        menuLabelHelp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popUpPage("Help");
            }
        });
        Help.setGraphic(menuLabelHelp);

        menuLabelExit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                endGame();
            }
        });
        Exit.setGraphic(menuLabelExit);
    }

    public void addMouseScrolling(MazeDisplayer md) {
        md.setOnScroll((ScrollEvent event) -> {
            // Adjust the zoom factor as per your requirement
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0){
                zoomFactor = 2.0 - zoomFactor;
            }
            double scaleY = md.getScaleY() * zoomFactor;
            double scaleX = md.getScaleX() * zoomFactor;
            if(scaleY > 2) scaleY = 2;
            else if ( scaleY < 0.5) scaleY = 0.5;

            if(scaleX > 2) scaleX = 2;
            else if ( scaleX < 0.5) scaleX = 0.5;

            md.setScaleX(scaleX);
            md.setScaleY(scaleY);

        });
    }
/*
    public void generateMaze() throws IOException {
        this.properties = new Properties();
        InputStream file = new FileInputStream("resources/config.properties");
        properties.load(file);

        audio.stop(0);
        audio.playInLoop(1); // starts play game song
        mediaPlayer.dispose();


        int rows = Integer.parseInt(textField_mazeRows.getText());
        int cols = Integer.parseInt(textField_mazeColumns.getText());
        if(rows < 4 || cols < 4)
        {
            showAlertWarning("Minimum size of rows,columns is 4! choose bigger maze.","WARNING");
        }
        else if(rows > 40 || cols > 40){
            showAlertWarning("Maximum size of rows,columns is 100! choose smaller maze.","WARNING");
        }
        else{
            if(solveBtn.isDisable())
                solveBtn.setDisable(false);
            logger.info("Maze :" + properties.getProperty("mazeGeneratingAlgorithm") + "Noder ");
            viewModel.generateMaze(rows,cols);
        }
    }


 */
    public void showAlertWarning(String msg,String Type){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.show();
    }

    public void solveMaze(ActionEvent actionEvent) {viewModel.solveMaze();}

    @Override
    public void update(Observable o, Object arg) {

        String option = (String) arg;

        switch (option) {
            case "load failed" -> showAlertWarning("There no maze saved in that name.","WARNING");
            case "loaded successfully", "Maze generated" -> {
                mazeDisplayer.clearMaze();
                mazeDisplayer.drawMaze(viewModel.getMaze());
            }
            case "Maze solved" -> mazeDisplayer.drawSolveMaze(viewModel.getSolutionPath());
            case "Changed Location and win" -> {
                mazeDisplayer.setPlayerPosition(viewModel.getRowLocation(), viewModel.getColLocation());
                mazeDisplayer.drawMaze(viewModel.getMaze());
                showWinningWindow();
                mazeDisplayer.clearMaze();
                mazeDisplayer.drawMaze(viewModel.getMaze());
            }
            case "Character Location Changed" -> {
                mazeDisplayer.setPlayerPosition(viewModel.getRowLocation(), viewModel.getColLocation());
                mazeDisplayer.drawMaze(viewModel.getMaze());
            }
            default -> {
            }
        }
    }

    public void setViewModel(MyViewModel viewModel){
        viewModel = viewModel;
        viewModel.addObserver(this);
    }

    public void playerMovement(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent);
        keyEvent.consume();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    public void saveMaze(String fileName) {
        viewModel.saveMaze(fileName);
    }

    public void loadMaze(String fileName){ viewModel.loadMaze(fileName);}

    public void popUpSaveName(ActionEvent actionEvent)  {
        if(viewModel.getMaze() == null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No maze to save, Please start a game first.");
            alert.show();
        }
        else {
            Stage stage = new Stage();
            Parent root = null;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveMaze.fxml"));
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            SaveLoadController inputController = loader.getController();
            stage.setScene(new Scene(root));
            stage.setTitle("Save Maze");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(mazeDisplayer.getScene().getWindow());
            stage.showAndWait();

            String fileName = inputController.getSavedName();
            if (fileName != null)
                saveMaze(fileName);

        }
    }

    public void popUpLoadName(ActionEvent actionEvent)  {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadMaze.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SaveLoadController inputController = loader.getController();
        stage.setScene(new Scene(root));
        stage.setTitle("Load Maze");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mazeDisplayer.getScene().getWindow());
        stage.showAndWait();

        String fileName = inputController.getLoadName();
        if(fileName != null)
            loadMaze(fileName);

    }

    public void popUpProperties(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Properties.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropController propController = loader.getController();
        propController.setMatrix();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("MainStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Maze Properties");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mazeDisplayer.getScene().getWindow());
        stage.showAndWait();

    }

    public void showWinningWindow(){
        audio.stop(1);
        audio.playInLoop(2);

        double pSteps = viewModel.getPlayerSteps();
        double oSteps = viewModel.getBestSteps();
        int gameP = 0;
        Boolean helpFlag = viewModel.getAskForHelp();
        if(!helpFlag)gameP = (int)Math.floor((oSteps/pSteps)*100);



        String rows = String.valueOf(viewModel.getRows());
        String cols = String.valueOf(viewModel.getCols());
        Stage stage = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource( "Win.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene winScene = new Scene(root);
        winScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("MainStyle.css")).toExternalForm());
        stage.setScene(winScene);
        stage.setTitle("MAZE SOLVED");
        stage.getIcons().add(new Image("Images/trophyIcon.jpg"));
        Win winController = loader.getController();
        winController.setWinWindow(rows,cols,String.valueOf(pSteps),String.valueOf(oSteps),String.valueOf(gameP),helpFlag,this);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mazeDisplayer.getScene().getWindow());

        stage.showAndWait();

        /*
        in case new game starts, stop the winning music
         */
        audio.stop(2);
        try {
            generateMaze();
        }catch (Exception e){}
    }

    public void popUpPage(String page) {
        Stage stage = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource(page +".fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("MainStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Maze " + page);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mazeDisplayer.getScene().getWindow());
        stage.showAndWait();
    }

    public void endGame() {
        viewModel.endGame();
        Stage stage = (Stage) MainBorderPane.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

}
