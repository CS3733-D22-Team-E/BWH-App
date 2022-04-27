package edu.wpi.cs3733.D22.teamE;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import edu.wpi.cs3733.D22.teamE.controllers.statusPageController;
import edu.wpi.cs3733.D22.teamE.customUI.ServiceRequestButtonListCell;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.EntityInterface;
import edu.wpi.cs3733.D22.teamE.entity.Location;
import edu.wpi.cs3733.D22.teamE.entity.requestPageFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
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
      EntityInterface request, Window owner, boolean editable, Object controller) {
    JFXAlert reqPage = new JFXAlert(owner);
    reqPage.initModality(Modality.APPLICATION_MODAL);
    reqPage.setOverlayClose(false);
    JFXDialogLayout layout = new JFXDialogLayout();
    layout.setHeading(new Label(request.toString()));
    try {
      layout.setMaxHeight((2 * owner.getHeight()) / 3.0);
      if (editable) {
        JFXButton updateButton = new JFXButton("Update");
        JFXButton closeButton = new JFXButton("Close");
        updateButton.setStyle("-fx-background-color: lightgrey");
        closeButton.setStyle("-fx-background-color: lightgrey");
        closeButton.setOnAction(
            event -> {
              reqPage.hideWithAnimation();
              if (controller instanceof statusPageController)
                ((statusPageController) controller).genTable();
            });
        Parent r = (Parent) requestPageFactory.getAsPage(request, updateButton, reqPage);
        layout.setBody(r);
        HBox b = new HBox();
        b.setSpacing(10);
        b.getChildren().add(updateButton);
        b.getChildren().add(closeButton);
        layout.setActions(b);
        reqPage.setContent(layout);
        reqPage.showAndWait();
      } else {
        JFXButton closeButton = new JFXButton("Close");
        closeButton.setStyle("-fx-background-color: lightgrey");
        closeButton.setOnAction(
            event -> {
              reqPage.hideWithAnimation();
              if (controller instanceof statusPageController)
                ((statusPageController) controller).genTable();
            });
        Parent r = (Parent) requestPageFactory.getAsPage(request, null, reqPage);
        layout.setBody(r);
        HBox b = new HBox();
        b.setSpacing(10);
        b.getChildren().add(closeButton);
        layout.setActions(b);
        reqPage.setContent(layout);
        reqPage.showAndWait();
      }
    } catch (InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  public static void createLocList(Location Loc, Window owner, Object controller) {
    JFXListView<EntityInterface> reqList = new JFXListView<>();
    List<EntityInterface> l = new ArrayList<>();
    l.addAll(DAOSystemSingleton.INSTANCE.getSystem().getAllServiceRequests());
    l.addAll(DAOSystemSingleton.INSTANCE.getSystem().getAllMedEquip());
    ObservableList<EntityInterface> data = FXCollections.observableArrayList();
    for (EntityInterface r : l) {
      if (Objects.equals(r.getRoomID(), Loc.getNodeID())) {
        data.add(r);
      }
    }
    reqList.setItems(data);
    reqList.setCellFactory(ServiceRequestButtonListCell.forListView(controller));
    JFXAlert reqPage = new JFXAlert(owner);
    reqPage.initModality(Modality.APPLICATION_MODAL);
    reqPage.setOverlayClose(false);
    JFXDialogLayout layout = new JFXDialogLayout();
    layout.setHeading(new Label(Loc.getLongName()));
    layout.setMaxHeight((2 * owner.getHeight()) / 3.0);
    JFXButton closeButton = new JFXButton("Close");
    closeButton.setStyle("-fx-background-color: lightgrey");
    closeButton.setOnAction(
        event -> {
          reqPage.hideWithAnimation();
        });
    layout.setBody(reqList);
    HBox b = new HBox();
    b.setSpacing(10);
    b.getChildren().add(closeButton);
    layout.setActions(b);
    reqPage.setContent(layout);
    reqPage.showAndWait();
  }
}
