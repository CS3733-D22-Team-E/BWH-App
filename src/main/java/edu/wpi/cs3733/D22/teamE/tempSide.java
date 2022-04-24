package edu.wpi.cs3733.D22.teamE;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class tempSide implements Initializable {
  @FXML JFXButton add;

  private BorderPane root;

  public tempSide(BorderPane r) {
    root = r;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    add.setOnAction(
        event -> {
          root.setCenter(pageControl.getPageRoot("other.fxml"));
        });
  }
}
