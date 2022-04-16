package edu.wpi.cs3733.D22.teamE;

import com.sun.javafx.application.LauncherImpl;
import edu.wpi.cs3733.D22.teamE.controllers.*;
import edu.wpi.cs3733.D22.teamE.database.DBConnect;
import edu.wpi.cs3733.D22.teamE.database.DBCreation;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application implements SharedScene {
  DAOSystem db;
  DBConnect connection;
  Stage s;
  Parent root;

  public int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

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
    AppPreloader p = new AppPreloader();
    for (int i = 0; i < 100; i++) {
      Thread.sleep(getRandomNumber(10, 100));
      LauncherImpl.notifyPreloader(p, new Preloader.ProgressNotification(i / 100.0));
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {}

  @Override
  public void stop() {
    log.info("Shutting Down");
  }

  @Override
  public Parent getParentNode() {
    return root;
  }
}
