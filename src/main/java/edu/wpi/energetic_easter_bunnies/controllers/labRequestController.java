package edu.wpi.energetic_easter_bunnies.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class labRequestController extends serviceRequestPageController {

  @FXML ComboBox<String> labRequestType;
  @FXML Button uploadFileButton;

  public labRequestController() {}

  @Override
  public void submitButton(ActionEvent event) {}
}
