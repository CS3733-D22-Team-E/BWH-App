package edu.wpi.cs3733.D22.teamE.controllers;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class aboutPageController extends containsSideMenu {

  public aboutPageController() throws FileNotFoundException {}

  @FXML GridPane joeyPane;
  @FXML Hyperlink wilsonWong;

  public void setImages(Stage stage) throws FileNotFoundException {
    Tooltip tooltip = new Tooltip("About Joey");
    ImageView imV =
        new ImageView(
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/about.png")));
    // ImageView.setPickOnBounds(true);
    Tooltip.install(imV, tooltip);

    StackPane root = new StackPane(imV);

    Scene scene = new Scene(root);

    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void wilsonWongLink(ActionEvent event) throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("http://wilsonwong.org/"));
  }
}
