package edu.wpi.cs3733.D22.teamE;

import edu.wpi.cs3733.D22.teamE.controllers.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("view/loginPage.fxml"));
    Parent root = loader.load();

    primaryStage.setTitle("Application");
    primaryStage.setScene(new Scene(root));
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
