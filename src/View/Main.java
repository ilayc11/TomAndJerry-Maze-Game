package View;


import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class Main extends Application {
    private static final int COUNT_LIMIT = 120000;
    public static final Logger logger = LogManager.getLogger(Main.class);


    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyView.fxml"));
        Parent root = loader.load();
        MyViewController myView = loader.getController();
        primaryStage.setTitle("Tom's Maze");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setOnCloseRequest(event -> myView.endGame());
        primaryStage.getIcons().add(new Image("Images/cheese.png"));
        primaryStage.show();

    }

    @Override
    public void init() throws Exception {

        // Perform some heavy lifting (i.e. database start, check for application updates, etc. )
        for (int i = 1; i <= COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            notifyPreloader(new Preloader.ProgressNotification(progress));
        }
    }


    public static void main(String[] args) {
        //logger.debug("Log4j configuration file path: " + Main.class.getClassLoader().getResource("log4j2.xml").getPath());
        System.setProperty("log4j.configurationFile", "log4j2.xml");
       // System.setProperty("log4j2.debug", "true");
        String pathName = "/log4j2.xml";
        Configurator.initialize(null, pathName);
        //logger.error("An error occurred: {}", "bla bla");

        System.setProperty("javafx.preloader", MyPreloader.class.getCanonicalName());
        //launch(args);
        Application.launch(Main.class,args);
    }
}