package edu.wpi.cs3733.D22.teamE.customUI;

import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.*;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class EntityView extends NodeImageView<EntityInterface> {

  protected ImageView myLoc;
  private int numEntities = 0;
  private int numReqs = 0;
  private int numEquip = 0;

  final Popup otherpopup = new Popup();

  public EntityView(Image i, EntityInterface node, ImageView myLoc) {
    super(i, node);

    List<EntityInterface> arr = new ArrayList<>();
    arr.addAll(DAOSystemSingleton.INSTANCE.getSystem().getAllMedEquip());
    arr.addAll(DAOSystemSingleton.INSTANCE.getSystem().getAllServiceRequests());

    for (EntityInterface e : arr) {
      if (e.getLocation().equals(node.getLocation())) {
        numEntities++;
        if (e instanceof RequestInterface) numReqs++;
        if (e instanceof Equipment) numEquip++;
      }
    }

    myLoc.setScaleX(this.getScaleX() * 1.2);
    myLoc.setScaleY(this.getScaleY() * 1.2);

    this.scaleXProperty()
        .addListener((observableValue, number, t1) -> myLoc.setScaleX(t1.doubleValue() * 1.2));
    this.scaleYProperty()
        .addListener((observableValue, number, t1) -> myLoc.setScaleY(t1.doubleValue() * 1.2));

    this.myLoc = myLoc;

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
                      + this.node.getLocation().getLongName()
                      + String.format(
                          "\nNumber Of Entities On This Node : %d\n"
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
              boolean edit =
                  AccountsManager.getInstance().getAccount().getAuthorityLevel()
                      >= Account.adminPerm;
              if (numEntities == 1) {
                PopUp.createReq(
                    node, this.getScene().getWindow(), node instanceof RequestInterface, null);
              } else if (numEntities > 1) {
                PopUp.createLocList(node.getLocation(), this.getScene().getWindow(), null);
              }
              popup.hide();
            }
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
              int newX = (int) (node.getXCoord() - deltaX);
              int newY = (int) (node.getYCoord() - deltaY);
              newX = (int) (event.getX() / getModifier());
              newY = (int) (event.getY() / getModifier());
              Location l =
                  DAOSystemSingleton.INSTANCE
                      .getSystem()
                      .getClosestLocation(newX, newY, node.getFloorID());
              System.out.println(newX + " : " + newY);
              System.out.println(l.getXCoord() + " : " + l.getYCoord() + " : " + l.getShortName());
              node.setLocation(l);
              // System.out.println(node.getLocation());
              DAOSystemSingleton.INSTANCE.getSystem().updateEntity(node);
              this.setClicked(false);
              controller.fetchDB();
              drag = false;
            }
          } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
          }
        });
  }
}
