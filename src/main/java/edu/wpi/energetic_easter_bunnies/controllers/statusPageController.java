package edu.wpi.energetic_easter_bunnies.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import edu.wpi.energetic_easter_bunnies.Main;
import edu.wpi.energetic_easter_bunnies.entity.serviceRequest;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class statusPageController implements Initializable {
  @FXML VBox root;
  @FXML JFXHamburger burger;
  @FXML JFXDrawer drawer;
  @FXML TableView<serviceRequest> requestTable;
  @FXML TableColumn<serviceRequest, Integer> idColumn;
  @FXML TableColumn<serviceRequest, String> typeColumn;
  @FXML TableColumn<serviceRequest, String> statusColumn;
  @FXML TableColumn<serviceRequest, String> assignedColumn;
  @FXML TableColumn<serviceRequest, String> dateColumn;
  VBox box;

  public statusPageController() {}

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
}
