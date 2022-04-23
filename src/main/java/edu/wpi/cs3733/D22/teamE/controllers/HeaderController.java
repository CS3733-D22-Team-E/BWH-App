package edu.wpi.cs3733.D22.teamE.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXToggleNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.SVGPath;

public class HeaderController extends containsSideMenu {

  @FXML JFXToggleNode homeButton;
  @FXML JFXToggleNode aboutUsButton;
  @FXML JFXToggleNode helpButton;
  @FXML Button profileButton;
  @FXML SVGPath bwhLogo;
  ToggleGroup headerPageButtons;
  JFXToggleNode selectedPageButton;

  public HeaderController() {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);

    headerPageButtons = new ToggleGroup();
    homeButton.setToggleGroup(headerPageButtons);
    aboutUsButton.setToggleGroup(headerPageButtons);
    helpButton.setToggleGroup(headerPageButtons);
    homeButton.setSelected(true);
    selectedPageButton = homeButton;



  }



  @FXML
  public void homeButton(ActionEvent event) {}

  @FXML
  public void aboutUsButton(ActionEvent event) {}

  @FXML
  public void helpButton(ActionEvent event) {}

  @FXML
  public void profileButton(ActionEvent event) {}
}
