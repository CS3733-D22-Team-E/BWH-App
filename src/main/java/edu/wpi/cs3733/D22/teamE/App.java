package edu.wpi.cs3733.D22.teamE;

import edu.wpi.cs3733.D22.teamE.controllers.*;
import edu.wpi.cs3733.D22.teamE.database.DBConnect;
import edu.wpi.cs3733.D22.teamE.database.DBCreation;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import java.io.IOException;
import java.sql.SQLException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application implements SharedScene {
  DAOSystem db;
  DBConnect connection;
  Stage s;
  Parent root;

  @Override
  public void init() throws InterruptedException {
    log.info("Starting Up");
    DBCreation.createTables();
    connection = DBConnect.getClientInstance();
    try {
      db = new DAOSystem();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("view/loadingSplash.fxml"));
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    AppPreloader p = new AppPreloader();
    p.start(primaryStage);
    Timeline timeline =
        new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(p.spinner.progressProperty(), 0)),
            new KeyFrame(
                Duration.seconds(10),
                e -> p.fadeInTo(root),
                new KeyValue(p.spinner.progressProperty(), 1)));
    timeline.setCycleCount(1);
    timeline.play();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }

  @Override
  public Parent getParentNode() {
    return root;
  }
}
