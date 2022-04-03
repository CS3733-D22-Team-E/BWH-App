package edu.wpi.energetic_easter_bunnies.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import edu.wpi.energetic_easter_bunnies.Main;
import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.entity.sanitationRequest;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class sanitationServiceController extends serviceRequestPageController
    implements Initializable {

  @FXML JFXHamburger burger;
  @FXML JFXDrawer drawer;
  @FXML TextField locationField;
  @FXML ToggleGroup biohazardGroup;
  @FXML ToggleGroup urgencyGroup;
  @FXML ToggleGroup sizeGroup;
  VBox box;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
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

  public sanitationServiceController() {}

  @FXML
  public void submitButton(ActionEvent event) {
    sanitationRequest request = new sanitationRequest();
    try {
      RadioButton selectBiohazard = (RadioButton) biohazardGroup.getSelectedToggle();
      switch (selectBiohazard.getText()) {
        case "Yes":
          request.setBiohazardOnSite(sanitationRequest.Biohazard.Yes);
          break;
        case "No":
          request.setBiohazardOnSite(sanitationRequest.Biohazard.No);
          break;
        case "Unsure":
          request.setBiohazardOnSite(sanitationRequest.Biohazard.Unsure);
          break;
      }

      RadioButton selectSize = (RadioButton) sizeGroup.getSelectedToggle();
      switch (selectSize.getText()) {
        case "Light":
          request.setSizeOfCleaning(sanitationRequest.Size.Light);
          break;
        case "Medium":
          request.setSizeOfCleaning(sanitationRequest.Size.Medium);
          break;
        case "Heavy":
          request.setSizeOfCleaning(sanitationRequest.Size.Heavy);
          break;
      }

      RadioButton selectUrgency = (RadioButton) urgencyGroup.getSelectedToggle();
      switch (selectUrgency.getText()) {
        case "Critical":
          request.setUrgent(true);
          break;
        case "NotCritical":
          request.setUrgent(false);
          break;
      }

      if (locationField.getText().isEmpty() || locationField.getText().isBlank())
        throw new NullPointerException();
    } catch (NullPointerException error) {
      System.out.println(error.getMessage());
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }
}
