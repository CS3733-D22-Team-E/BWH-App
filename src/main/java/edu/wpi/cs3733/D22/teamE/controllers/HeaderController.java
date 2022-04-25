package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class HeaderController {

  @FXML JFXToggleNode homeButton;
  @FXML JFXToggleNode aboutUsButton;
  @FXML JFXToggleNode helpButton;
  @FXML Button profileButton;
  @FXML SVGPath bwhLogo;
  ToggleGroup headerPageButtons;
  JFXToggleNode selectedPageButton;

  @FXML public JFXHamburger burger;
  // Node box;
  Node box;
  JFXDrawer drawer;

  public HeaderController(JFXDrawer drawer) {
    this.drawer = drawer;
  }

  public HeaderController() {}

  public void initialize(URL location, ResourceBundle resources) {

    headerPageButtons = new ToggleGroup();
    homeButton.setToggleGroup(headerPageButtons);
    aboutUsButton.setToggleGroup(headerPageButtons);
    helpButton.setToggleGroup(headerPageButtons);
    homeButton.setSelected(true);
    selectedPageButton = homeButton;

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
  public void homeButton(ActionEvent event) {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("defaultPage.fxml", thisStage);
  }

  @FXML
  public void aboutUsButton(ActionEvent event) {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("aboutPage.fxml", thisStage);
  }

  @FXML
  public void helpButton(ActionEvent event) {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("helpPage.fxml", thisStage);
  }

  @FXML
  public void profileButton(ActionEvent event) {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("helpPage.fxml", thisStage);
  }

  @FXML
  public void openD(javafx.scene.input.MouseEvent mouseEvent) {
    drawer.toggle();
  }
}
