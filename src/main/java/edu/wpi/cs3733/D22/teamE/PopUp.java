package edu.wpi.cs3733.D22.teamE;

import com.jfoenix.controls.JFXPopup;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class PopUp {

  public static void createWarning(String message, Node owner) {

    JFXPopup popup = new JFXPopup(new Label(message));
    popup.show(owner, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
  }
}
