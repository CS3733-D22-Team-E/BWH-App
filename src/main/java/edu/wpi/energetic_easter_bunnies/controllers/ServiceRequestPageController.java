package edu.wpi.energetic_easter_bunnies.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ServiceRequestPageController {

  FXMLLoader loader = new FXMLLoader();
  Parent root;

  @FXML MenuBar menuBar;
  @FXML TextField notes;
  @FXML Button submitButton;

  @FXML
  private void homeButton(ActionEvent event) throws IOException {
    Node node = (Node) event.getSource();
    Stage thisStage = (Stage) node.getScene().getWindow();

    loader.setLocation(getClass().getResource("../view/defaultPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }
}
