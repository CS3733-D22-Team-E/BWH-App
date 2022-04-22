package edu.wpi.cs3733.D22.teamE;

import edu.wpi.cs3733.D22.teamE.controllers.*;
import edu.wpi.cs3733.D22.teamE.database.DBConnect;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
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
  Connection connection;
  Stage s;
  Parent root;

  @Override
  public void init() throws InterruptedException {
    log.info("Starting Up");
    connection = DBConnect.getClientInstance().getConnection();
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
  public void start(Stage stage) throws Exception {
    AppPreloader p = new AppPreloader();
    stage.setResizable(false);
    stage.setScene(p.createPreloaderScene(root));
    stage.show();
    Timeline timeline = new Timeline();
    KeyFrame start = new KeyFrame(Duration.ZERO);
    KeyFrame end = new KeyFrame(Duration.seconds(5), e -> p.fadeInTo(timeline));
    timeline.getKeyFrames().addAll(start, end);
    timeline.setCycleCount(Animation.INDEFINITE);
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
