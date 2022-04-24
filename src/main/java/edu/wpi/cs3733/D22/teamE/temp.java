package edu.wpi.cs3733.D22.teamE;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.events.JFXDrawerEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class temp implements Initializable {
  @FXML JFXDrawer drawer;

  @FXML BorderPane pane;

  @FXML HBox root;

  @FXML JFXButton add;

  @FXML
  public void resizeClose(JFXDrawerEvent jfxDrawerEvent) {
    drawer.setMinWidth(0);
    drawer.setDisable(true);
  }

  @FXML
  public void resizeOpen(JFXDrawerEvent jfxDrawerEvent) {
    drawer.setMinWidth(100);
    drawer.setDisable(false);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    pane.setCenter(pageControl.getPageRoot("loadingSplash.fxml"));
    tempSide s = new tempSide(pane);
    Parent side = (Parent) pageControl.getPageRoot("Tside.fxml", s);
    h he = new h(drawer);
    Parent top = (Parent) pageControl.getPageRoot("h.fxml", he);
    pane.setTop(top);
    drawer.setSidePane(side);
    drawer.close();
    drawer.setDisable(true);
    drawer.setMinWidth(0);
  }
}
