package edu.wpi.energetic_easter_bunnies.controllers;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

public class aboutPageController extends containsSideMenu {

  @FXML Hyperlink wilsonWong;

  @FXML
  public void wilsonWongLink(ActionEvent event) throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("http://wilsonwong.org/"));
  }
}
