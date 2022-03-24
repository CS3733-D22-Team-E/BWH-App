package edu.wpi.TeamE;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {
  @FXML Button sanitationButton;
  @FXML MenuBar menuBar;

  FXMLLoader loader = new FXMLLoader();
  Parent root;

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @FXML
  private void sanitationButton(ActionEvent event) throws IOException {
    Node node = (Node) event.getSource();
    Stage thisStage = (Stage) node.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/sanitationPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @FXML
  private void homeButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/defaultPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    loader.setLocation(getClass().getResource("view/defaultPage.fxml"));
    root = loader.load();

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
