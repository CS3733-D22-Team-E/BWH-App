package edu.wpi.cs3733.D22.teamE.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ResourceBundle;

public class HeaderController extends containsSideMenu {

    @FXML Button homeButton;
    @FXML Button aboutUsButton;
    @FXML Button helpButton;
    @FXML Button profileButton;
    @FXML SVGPath bwhLogo;

    public HeaderController() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

    }

    @FXML
    private void homeButton(ActionEvent event) {

    }

    @FXML
    private void profileButton(ActionEvent event) {

    }
}
