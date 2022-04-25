package edu.wpi.cs3733.D22.teamE.customUI;

import edu.wpi.cs3733.D22.teamE.entity.Location;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class customImageView extends ImageView {

  private Location l;

  public customImageView(Image i, Location l) {
    super(i);
    this.l = l;

    final Popup popup = new Popup();
    popup.setAutoHide(true);

    final EventHandler<MouseEvent> hoverListener =
        event -> {
          String content = "{" + this.getX() + " : " + this.getY() + "}";
          if (content == null) return;
          if (content.isBlank() || content.isEmpty()) return;
          Label popupContent1 = new Label(content);
          popupContent1.setStyle(
              "-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 5px; -fx-text-fill: black;");

          Label popupContent2 = new Label(this.l.getShortName());
          popupContent2.setStyle(
              "-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 5px; -fx-text-fill: black;");

          VBox popupContent = new VBox();
          popupContent.getChildren().add(popupContent1);
          popupContent.getChildren().add(popupContent2);
          popup.getContent().clear();
          popup.getContent().addAll(popupContent);

          if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
            popup.hide();
          } else if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            popup.show(this, event.getScreenX() + 10, event.getScreenY());
          }
        };

    this.setOnMouseEntered(hoverListener);
    this.setOnMouseExited(hoverListener);
  }
}
