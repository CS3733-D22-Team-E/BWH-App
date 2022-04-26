package edu.wpi.cs3733.D22.teamE;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.cs3733.D22.teamE.controllers.statusPageController;
import edu.wpi.cs3733.D22.teamE.entity.RequestInterface;
import edu.wpi.cs3733.D22.teamE.entity.requestPageFactory;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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

  public static void createReq(
      RequestInterface request, Window owner, boolean editable, Object controller) {
    JFXAlert reqPage = new JFXAlert(owner);
    reqPage.initModality(Modality.APPLICATION_MODAL);
    reqPage.setOverlayClose(false);
    JFXDialogLayout layout = new JFXDialogLayout();
    layout.setHeading(new Label(request.toString()));
    try {
      layout.setMaxHeight((2 * owner.getHeight()) / 3.0);
      JFXButton updateButton = new JFXButton("Update");
      JFXButton closeButton = new JFXButton("Close");
      closeButton.setStyle("-fx-background-color: lightgrey");
      updateButton.setStyle("-fx-background-color: lightgrey");
      closeButton.setOnAction(
          event -> {
            reqPage.hideWithAnimation();
            if (controller instanceof statusPageController)
              ((statusPageController) controller).genTable();
          });
      if (editable) {
        layout.setBody(requestPageFactory.getAsPage(request, updateButton, reqPage));
      } else layout.setBody(requestPageFactory.getAsPage(request, null, reqPage));
      HBox b = new HBox();
      b.setSpacing(10);
      b.getChildren().add(updateButton);
      b.getChildren().add(closeButton);
      layout.setActions(b);
      reqPage.setContent(layout);
      reqPage.show();
    } catch (InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }

    /* JFXPopup popup = new JFXPopup(new Label(message));
    popup.show(owner, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);*/
  }

  public static void aboutPopUp(String bio, Window owner, String employee) {
    JFXAlert alert = new JFXAlert(owner);
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.setOverlayClose(false);
    JFXDialogLayout layout = new JFXDialogLayout();
    layout.setHeading(new Label("About " + employee));
    layout.setBody(new Label(bio));
    JFXButton closeButton = new JFXButton("Exit");
    closeButton.setOnAction(event -> alert.hideWithAnimation());
    layout.setActions(closeButton);
    alert.setContent(layout);
    alert.showAndWait();
  }
}
