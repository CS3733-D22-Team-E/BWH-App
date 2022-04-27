package edu.wpi.cs3733.D22.teamE.customUI;

import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class LocationView extends NodeImageView<Location> {
  private int numEntities = 0;
  private int numReqs = 0;
  private int numEquip = 0;

  public LocationView(Image i, Location node) {
    super(i, node);

    List<EntityInterface> arr = new ArrayList<>();
    arr.addAll(DAOSystemSingleton.INSTANCE.getSystem().getAllMedEquip());
    arr.addAll(DAOSystemSingleton.INSTANCE.getSystem().getAllServiceRequests());

    for (EntityInterface e : arr) {
      if (e.getLocation().equals(this.node)) {
        numEntities++;
        if (e instanceof RequestInterface) numReqs++;
        if (e instanceof Equipment) numEquip++;
      }
    }

    this.setName(node.getLongName());

    hoverListener =
        event -> {
          String content = String.format("{ %.2f : %.2f }", this.getX(), this.getY());
          if (content == null) return;
          if (content.isBlank() || content.isEmpty()) return;
          Label popupContent1 =
              new Label(
                  content
                      + "\n"
                      + name
                      + "\n"
                      + String.format(
                          "Number Of Entities On This Node : %d\n"
                              + ""
                              + "Number Of Requests On This Node : %d\n"
                              + ""
                              + "Number of Equipment On This Node : %d\n",
                          numEntities, numReqs, numEquip));
          popupContent1.setStyle(
              "-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 5px; -fx-text-fill: black;");
          VBox popupContent = new VBox();
          popupContent.getChildren().add(popupContent1);
          popup.getContent().clear();
          popup.getContent().addAll(popupContent);

          if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
            popup.hide();
          } else if (event.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
            popup.show(this, event.getScreenX() + 10, event.getScreenY());
          }
        };

    this.setOnMouseEntered(
        event -> {
          hoverListener.handle(event);
          if (!isClicked()) setHighlighted(true);
        });

    this.setOnMouseExited(
        event -> {
          hoverListener.handle(event);
          if (!isClicked()) setHighlighted(false);
        });

    this.setOnMouseClicked(
        event -> {
          if (!drag && event.getButton().equals(MouseButton.SECONDARY)) {
            if (event.isShiftDown()) {
              if (numEntities >= 1) {
                PopUp.createLocList(node, this.getScene().getWindow(), this);
                popup.hide();
              }
            } else this.setClicked(!isClicked());
          }
        });

    this.setOnMouseReleased(
        event -> {
          try {
            if (drag && event.getButton().equals(MouseButton.SECONDARY)) {
              double deltaX =
                  ((this.getStartX() - event.getX()) / getModifier()) + getWidthOffset();
              double deltaY =
                  ((this.getStartY() - event.getY()) / getModifier()) + getHeightOffset();
              DAOSystemSingleton.INSTANCE
                  .getSystem()
                  .updateCoord(
                      node, (int) (node.getXCoord() - deltaX), (int) (node.getYCoord() - deltaY));
              this.setClicked(false);
              controller.fetchDB();
              drag = false;
            }
          } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
          }
        });
  }

  @Override
  public void deleteFromDB() {
    DAOSystemSingleton.INSTANCE.getSystem().deleteLocation(node);
  }
}
