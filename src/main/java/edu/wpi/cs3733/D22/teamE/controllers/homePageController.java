package edu.wpi.cs3733.D22.teamE.controllers;

import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
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
  @FXML StackPane dashboardRect;

  private Account currentAccount;

  public void initialize(URL url, ResourceBundle resourceBundle) {

    dashboardPane.setOnMouseClicked(
        new EventHandler<javafx.scene.input.MouseEvent>() {
          @Override
          public void handle(javafx.scene.input.MouseEvent mouseEvent) {
            // DashboardPage.fxml
            pageControl.loadCenter("DashboardPage.fxml", (Stage) mainPane.getScene().getWindow());
          }
        });

    dashboardPane
        .scaleYProperty()
        .addListener(
            new ChangeListener<Number>() {
              @Override
              public void changed(
                  ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                double deltaY = t1.doubleValue();
                for (Node n : dashboardPane.getChildren()) {
                  n.setScaleY(deltaY);
                }
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

    currentAccount = AccountsManager.getInstance().getAccount();
    if (currentAccount.getAuthorityLevel() < 2) {
      employeeDatabasePane.setVisible(false);
      employeeDatabasePane.setOnMouseClicked(
          new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {

              // pageControl.loadCenter("employeePage.fxml", (Stage)
              // mainPane.getScene().getWindow());
            }
          });
    }
  }
}
