package edu.wpi.energetic_easter_bunnies;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUpWarning {

  public static void createWarning(String message) {
    Stage popupwindow = new Stage();

    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.setTitle("Warning");

    Label label1 = new Label(message);

    Button button1 = new Button("Close Window");

    button1.setOnAction(e -> popupwindow.close());

    VBox layout = new VBox(10);

    layout.getChildren().addAll(label1, button1);

    layout.setAlignment(Pos.CENTER);

    Scene scene1 = new Scene(layout, 300, 250);

    popupwindow.setScene(scene1);

    popupwindow.showAndWait();
  }
}
