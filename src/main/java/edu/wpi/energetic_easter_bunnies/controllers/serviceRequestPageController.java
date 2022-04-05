package edu.wpi.energetic_easter_bunnies.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import edu.wpi.energetic_easter_bunnies.Main;
import edu.wpi.energetic_easter_bunnies.entity.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public abstract class serviceRequestPageController extends sideMenuController {

  @FXML TextField notes;
  @FXML Button submitButton;
  @FXML Button resetButton;
  @FXML TextField requestStatus;
  @FXML TextField staffAssignee;
  @FXML JFXHamburger burger;
  @FXML JFXDrawer drawer;
  VBox box;

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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    assert (burger != null);
    assert (drawer != null);
    HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(burger);
    FXMLLoader l = new FXMLLoader();
    l.setLocation(Main.class.getResource("view/sidePanel.fxml"));
    try {
      box = l.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    drawer.setSidePane(box);

    transition.setRate(-1);
    burger.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        (e) -> {
          transition.setRate(transition.getRate() * -1);
          transition.play();
          if (drawer.isOpened()) {
            drawer.close();
            box.setDisable(true);
          } else {
            drawer.open();
            box.setDisable(false);
          }
        });
  }
}
