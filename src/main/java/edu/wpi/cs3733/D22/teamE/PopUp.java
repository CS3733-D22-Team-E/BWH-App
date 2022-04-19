package edu.wpi.cs3733.D22.teamE;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.cs3733.D22.teamE.entity.requestPage;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Window;

public class PopUp {

  public static void createWarning(String message, Window owner) {
    JFXAlert alert = new JFXAlert(owner);
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.setOverlayClose(false);
    JFXDialogLayout layout = new JFXDialogLayout();
    layout.setHeading(new Label("Alert"));
    layout.setBody(new Label(message));
    JFXButton closeButton = new JFXButton("Understood");
    closeButton.setOnAction(event -> alert.hideWithAnimation());
    layout.setActions(closeButton);
    alert.setContent(layout);
    alert.showAndWait();

    /* JFXPopup popup = new JFXPopup(new Label(message));
    popup.show(owner, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);*/
  }

  public static void createReq(requestPage request, Window owner, boolean editable) {
    JFXAlert alert = new JFXAlert(owner);
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.setOverlayClose(false);
    JFXDialogLayout layout = new JFXDialogLayout();
    layout.setHeading(new Label(request.toString()));
    try {
      layout.setMaxHeight(owner.getHeight() / 2);
      layout.setBody(request.getAsPage(editable));
      JFXButton closeButton = new JFXButton("Finished");
      closeButton.setStyle("-fx-background-color: grey");
      closeButton.setOnAction(event -> alert.hideWithAnimation());
      layout.setActions(closeButton);
      alert.setContent(layout);
      alert.showAndWait();
    } catch (InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }

    /* JFXPopup popup = new JFXPopup(new Label(message));
    popup.show(owner, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);*/
  }
}
