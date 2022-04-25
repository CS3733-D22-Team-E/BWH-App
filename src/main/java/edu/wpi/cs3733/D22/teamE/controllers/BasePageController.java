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

  @FXML BorderPane root;
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
    root.setCenter(pageControl.getPageRoot("defaultPage.fxml"));
    sideMenuController sideMenu = new sideMenuController();
    Parent sideMenuView = (Parent) pageControl.getPageRoot("sidePanel.fxml", sideMenu);
    HeaderController headerController = new HeaderController();
    Parent headerView = (Parent) pageControl.getPageRoot("Header.fxml", headerController);
    root.setTop(headerView);
    drawer.setSidePane(sideMenuView);
    drawer.close();
    drawer.setDisable(true);
    drawer.setMinWidth(0);
  }
}
