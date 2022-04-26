package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.events.JFXDrawerEvent;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BasePageController implements Initializable {

  @FXML BorderPane rootBorderPane;
  @FXML JFXDrawer drawer;

  @FXML
  public void closeDrawer(JFXDrawerEvent jfxDrawerEvent) {
    drawer.setMinWidth(0);
    drawer.setDisable(true);
  }

  @FXML
  public void openDrawer(JFXDrawerEvent jfxDrawerEvent) {
    drawer.setMinWidth(100);
    drawer.setDisable(false);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    rootBorderPane.setCenter(pageControl.getPageRoot("homePage.fxml"));
    pageControl.loadTop("Header.fxml", (Stage) rootBorderPane.getScene().getWindow());
    drawer.setSidePane(pageControl.getPageRoot("sidePanel.fxml"));
    drawer.close();
    drawer.setDisable(true);
    drawer.setMinWidth(0);
  }
}
