package edu.wpi.energetic_easter_bunnies.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.events.JFXDrawerEvent;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import edu.wpi.energetic_easter_bunnies.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * This class
 */
public class containsSideMenu implements Initializable {
  @FXML public JFXHamburger burger;
  @FXML JFXDrawer drawer;
  // Node box;
  StackPane box;
  // ScrollPane box;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    assert (burger != null);
    assert (drawer != null);
    // scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    // scrollPane.setFitToWidth(true);
    HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(burger);
    FXMLLoader l = new FXMLLoader();
    l.setLocation(Main.class.getResource("view/sidePanel.fxml"));
    box = null;
    try {
      box = l.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert (box != null);
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
  }
}
