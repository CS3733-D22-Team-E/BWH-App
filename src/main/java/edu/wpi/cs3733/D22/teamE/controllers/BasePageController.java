package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.events.JFXDrawerEvent;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

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
    drawer.setMinWidth(150);
    drawer.setDisable(false);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    rootBorderPane.setCenter(pageControl.getPageRoot("homePage.fxml"));
    sideMenuController sideMenu = new sideMenuController(rootBorderPane);
    Parent sideMenuView = (Parent) pageControl.getPageRoot("sidePanel.fxml", sideMenu);
    HeaderController headerController = new HeaderController(drawer, rootBorderPane);
    Parent headerView = (Parent) pageControl.getPageRoot("Header.fxml", headerController);
    rootBorderPane.setTop(headerView);
    drawer.setSidePane(sideMenuView);
    drawer.close();
    drawer.setDisable(true);
    drawer.setMinWidth(0);
  }
}
