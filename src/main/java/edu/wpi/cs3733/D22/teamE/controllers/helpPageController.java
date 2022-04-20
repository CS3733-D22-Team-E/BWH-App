package edu.wpi.cs3733.D22.teamE.controllers;

import static edu.wpi.cs3733.D22.teamE.pageControlFacade.isLightMode;

import edu.wpi.cs3733.D22.teamE.themeControl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/** Oop its gone */
public class helpPageController extends containsSideMenu {

  @FXML Button serviceHelp;
  @FXML Button mapHelp;

  @FXML Button btnMode;
  @FXML private ImageView imgMode;
  @FXML VBox parent;

  private static String databaseMode = "EMBEDDED_INSTANCE";

  public void changeMode(ActionEvent event) throws FileNotFoundException {
    if (isLightMode) {
      themeControl.setDarkMode(parent);
      javafx.scene.image.Image newImg =
          new javafx.scene.image.Image(
              new FileInputStream(
                  "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/ic-light.png"));
      imgMode.setImage(newImg);
      isLightMode = false;
    } else {
      themeControl.setLightMode(parent);
      javafx.scene.image.Image newImg =
          new javafx.scene.image.Image(
              new FileInputStream(
                  "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/ic-dark.png"));

      imgMode.setImage(newImg);
      isLightMode = true;
    }
  }

  public void mapHelp() {}

  public void serviceHelp() {}
}
