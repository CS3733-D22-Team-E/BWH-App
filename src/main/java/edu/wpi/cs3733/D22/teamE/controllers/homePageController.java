package edu.wpi.cs3733.D22.teamE.controllers;

import edu.wpi.cs3733.D22.teamE.pageControl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class homePageController implements Initializable {

  @FXML VBox mainPane;

  @FXML VBox dashboardPane;
  @FXML VBox myProfilePane;
  @FXML VBox submitRequestPane;
  @FXML VBox statusPane;
  @FXML VBox mapPane;
  @FXML VBox employeeDatabasePane;

  public void initialize(URL url, ResourceBundle resourceBundle) {

    dashboardPane.setOnMouseClicked(
        new EventHandler<javafx.scene.input.MouseEvent>() {
          @Override
          public void handle(javafx.scene.input.MouseEvent mouseEvent) {
            // DashboardPage.fxml
            pageControl.loadCenter("DashboardPage.fxml", (Stage) mainPane.getScene().getWindow());
          }
        });

    myProfilePane.setOnMouseClicked(
        new EventHandler<javafx.scene.input.MouseEvent>() {
          @Override
          public void handle(javafx.scene.input.MouseEvent mouseEvent) {

            pageControl.loadCenter("profilePage.fxml", (Stage) mainPane.getScene().getWindow());
          }
        });

    submitRequestPane.setOnMouseClicked(
        new EventHandler<javafx.scene.input.MouseEvent>() {
          @Override
          public void handle(javafx.scene.input.MouseEvent mouseEvent) {
            pageControl.loadCenter(
                "serviceRequestLanding.fxml", (Stage) mainPane.getScene().getWindow());
          }
        });

    statusPane.setOnMouseClicked(
        new EventHandler<javafx.scene.input.MouseEvent>() {
          @Override
          public void handle(javafx.scene.input.MouseEvent mouseEvent) {

            pageControl.loadCenter("statusPage.fxml", (Stage) mainPane.getScene().getWindow());
          }
        });
    mapPane.setOnMouseClicked(
        new EventHandler<javafx.scene.input.MouseEvent>() {
          @Override
          public void handle(javafx.scene.input.MouseEvent mouseEvent) {

            pageControl.loadCenter("map.fxml", (Stage) mainPane.getScene().getWindow());
          }
        });
    employeeDatabasePane.setOnMouseClicked(
        new EventHandler<javafx.scene.input.MouseEvent>() {
          @Override
          public void handle(javafx.scene.input.MouseEvent mouseEvent) {

            pageControl.loadCenter("employeePage.fxml", (Stage) mainPane.getScene().getWindow());
          }
        });
  }
}
