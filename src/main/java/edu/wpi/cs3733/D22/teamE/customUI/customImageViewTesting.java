package edu.wpi.cs3733.D22.teamE.customUI;

import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.EntityInterface;
import edu.wpi.cs3733.D22.teamE.entity.Equipment;
import edu.wpi.cs3733.D22.teamE.entity.Location;
import edu.wpi.cs3733.D22.teamE.entity.RequestInterface;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class customImageViewTesting extends ImageView {

  private Location l;

  final Popup popup = new Popup();
  private int numEntities = 0;
  private int numReqs = 0;
  private int numEquip = 0;

  public customImageViewTesting(Image i, Location l) {
    super(i);
    this.l = l;

    List<EntityInterface> arr = new ArrayList<>();
    arr.addAll(DAOSystemSingleton.INSTANCE.getSystem().getAllMedEquip());
    arr.addAll(DAOSystemSingleton.INSTANCE.getSystem().getAllServiceRequests());

    for (EntityInterface e : arr) {
      if (e.getLocation().equals(l)) {
        numEntities++;
        if (e instanceof RequestInterface) numReqs++;
        if (e instanceof Equipment) numEquip++;
      }
    }

    EventHandler<MouseEvent> hoverListener =
        event -> {
          String content = String.format("{ %.2f : %.2f }", this.getX(), this.getY());
          if (content == null) return;
          if (content.isBlank() || content.isEmpty()) return;
          Label popupContent1 = new Label(content);
          popupContent1.setStyle(
              "-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 5px; -fx-text-fill: black;");

          Label popupContent2 = new Label(l.getShortName());
          popupContent2.setStyle(
              "-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 5px; -fx-text-fill: black;");

          Label popupContent3 =
              new Label(
                  String.format(
                      "Number Of Entities On This Node : %d\n"
                          + ""
                          + "Number Of Requests On This Node : %d\n"
                          + ""
                          + "Number of Equipment On This Node : %d\n",
                      numEntities, numReqs, numEquip));
          popupContent3.setStyle(
              "-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 5px; -fx-text-fill: black;");
          VBox popupContent = new VBox();
          popupContent.getChildren().add(popupContent1);
          popupContent.getChildren().add(popupContent2);
          popupContent.getChildren().add(popupContent3);
          popup.getContent().clear();
          popup.getContent().addAll(popupContent);

          if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
            popup.hide();
          } else if (event.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
            popup.show(this, event.getScreenX() + 10, event.getScreenY());
          }
        };

    this.setOnMouseEntered(hoverListener);

    this.setOnMouseExited(hoverListener);
  }

  public Location getL() {
    return l;
  }

  public void setL(Location l) {
    this.l = l;
  }
}
