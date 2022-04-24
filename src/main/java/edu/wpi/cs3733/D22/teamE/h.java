package edu.wpi.cs3733.D22.teamE;

import com.jfoenix.controls.JFXDrawer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class h {
  private JFXDrawer drawer;

  public h(JFXDrawer d) {
    drawer = d;
  }

  @FXML
  public void openD(ActionEvent e) {
    drawer.toggle();
  }
}
