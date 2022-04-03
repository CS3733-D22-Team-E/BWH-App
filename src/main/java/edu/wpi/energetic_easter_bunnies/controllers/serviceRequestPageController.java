package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.entity.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

public abstract class serviceRequestPageController {

  FXMLLoader loader = new FXMLLoader();
  Parent root;

  @FXML TextField notes;
  @FXML Button submitButton;
  @FXML Button resetButton;
  @FXML MenuBar menuBar;
  @FXML TextField requestStatus;
  @FXML TextField staffAssignee;

  serviceRequestPageController() {}

  public boolean sendToDB(serviceRequest request) {
    // todo : implement DB communication
    return true;
  }

  public void populateRequestTable() {
    // todo : get all service requests as list
    // todo : filter through to match MY type
    // todo : populate my table
  }
}
