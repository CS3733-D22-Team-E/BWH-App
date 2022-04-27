package edu.wpi.cs3733.D22.teamE.controllers;

import edu.wpi.cs3733.D22.teamE.pageControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class landingPageController {
  @FXML HBox mainPane;

  @FXML
  public void getStarted(ActionEvent event) {
    {
      Stage thisStage = (Stage) mainPane.getScene().getWindow();
      pageControl.loadPage("loginPage.fxml", thisStage);
    }
  }
}
