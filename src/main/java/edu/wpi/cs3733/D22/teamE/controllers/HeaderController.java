package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.SVGPath;

public class HeaderController {

  @FXML JFXToggleNode homeButton;
  @FXML JFXToggleNode aboutUsButton;
  @FXML JFXToggleNode helpButton;
  @FXML Button profileButton;
  @FXML SVGPath bwhLogo;
  // ToggleGroup headerPageButtons;
  JFXToggleNode selectedPageButton;

  @FXML public JFXHamburger burger;
  // Node box;
  Node box;
  JFXDrawer drawer;
  BorderPane root;

  public HeaderController(JFXDrawer drawer, BorderPane root) {
    this.drawer = drawer;
    this.root = root;
  }

  public HeaderController() {}

  public void initialize(URL location, ResourceBundle resources) {

    /*
    headerPageButtons = new ToggleGroup();
    homeButton.setToggleGroup(headerPageButtons);
    aboutUsButton.setToggleGroup(headerPageButtons);
    helpButton.setToggleGroup(headerPageButtons);
    homeButton.setSelected(true);
    selectedPageButton = homeButton;
     */

    /*
    assert (burger != null);
    assert (drawer != null);
    HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(burger);
    box = pageControl.getPageRoot("sidePanel.fxml");
    System.out.println(box);
    drawer.setSidePane(box);
    drawer.setOnDrawerClosed(
        new EventHandler<JFXDrawerEvent>() {
          @Override
          public void handle(JFXDrawerEvent event) {
            box.setDisable(true);
            drawer.setDisable(true);
            transition.setRate(-1);
            transition.play();
          }
        });
    box.setDisable(true);
    drawer.setDisable(true);

    transition.setRate(-1);
    burger.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        (e) -> {
          transition.setRate(transition.getRate() * -1);
          transition.play();
          if (drawer.isOpened()) {
            drawer.close();
            box.setDisable(true);
            drawer.setDisable(true);
          } else {
            drawer.open();
            box.setDisable(false);
            drawer.setVisible(true);
            drawer.setDisable(false);
          }
        });
     */

  }

  @FXML
  public void homeButtonSelected(MouseEvent e) {
    homeButton.getStyleClass().clear();
    homeButton.getStyleClass().add("headerSelectedButton");
  }

  @FXML
  public void homeButtonUnselected(MouseEvent e) {
    homeButton.getStyleClass().clear();
    homeButton.getStyleClass().add("headerButton");
  }

  @FXML
  public void homeButton(ActionEvent event) {
    homeButton.getStyleClass().clear();
    homeButton.getStyleClass().add("headerSelectedButton");
    root.setCenter(pageControl.getPageRoot("homePage.fxml"));
  }

  @FXML
  public void aboutUsButton(ActionEvent event) {
    root.setCenter(pageControl.getPageRoot("aboutPage.fxml"));
  }

  @FXML
  public void helpButton(ActionEvent event) {
    root.setCenter(pageControl.getPageRoot("helpPage.fxml"));
  }

  @FXML
  public void profileButton(ActionEvent event) {
    root.setCenter(pageControl.getPageRoot("profilePage.fxml"));
  }

  @FXML
  public void openD(javafx.scene.input.MouseEvent mouseEvent) {
    drawer.toggle();
  }
}
