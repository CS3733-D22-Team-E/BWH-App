package edu.wpi.energetic_easter_bunnies.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class labRequestController extends serviceRequestPageController {

  @FXML ComboBox<String> labRequestType;
  @FXML Button uploadFileButton;

  public labRequestController() {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {}
}
