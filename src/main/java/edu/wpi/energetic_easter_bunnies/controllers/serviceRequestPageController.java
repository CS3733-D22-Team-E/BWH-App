package edu.wpi.energetic_easter_bunnies.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import edu.wpi.energetic_easter_bunnies.entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public abstract class serviceRequestPageController extends containsSideMenu {

  @FXML TextField notes;
  @FXML Button submitButton;
  @FXML Button resetButton;
  @FXML TextField requestStatus;
  @FXML TextField staffAssignee;
  @FXML JFXHamburger burger;
  @FXML JFXDrawer drawer;

  serviceRequestPageController() {
    super();
  }

  public boolean sendToDB(serviceRequest request) {
    // todo : implement DB communication
    return true;
  }

  public void populateRequestTable() {
    // todo : get all service requests as list
    // todo : filter through to match MY type
    // todo : populate my table
  }

  @FXML
  public abstract void submitButton(ActionEvent event);
}
