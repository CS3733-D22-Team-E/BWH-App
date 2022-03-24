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
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  @FXML Button medicalEquipmentButton;
  @FXML Button backButton;
  @FXML Button submitRequestButton;

  FXMLLoader loader = new FXMLLoader();
  Parent root;

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    loader.setLocation(getClass().getResource("view/defaultPage.fxml"));
    root = loader.load();

    Scene scene = new Scene(root);

    primaryStage.setTitle("Application");
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  @FXML
  private void backButton(ActionEvent event) throws IOException {
    Node node = (Node) event.getSource();
    Stage thisStage = (Stage) node.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/defaultPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @FXML
  private void medicalEquipmentButton(ActionEvent event) throws IOException {
    Node node = (Node) event.getSource();
    Stage thisStage = (Stage) node.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/medicalEquipmentPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
