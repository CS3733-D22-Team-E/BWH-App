package edu.wpi.cs3733.D22.teamE.customUI;

import edu.wpi.cs3733.D22.teamE.controllers.mapPageController;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;

public class NodeImageView<T> extends ImageView {

  protected mapPageController controller;

  private boolean isClicked = false;
  private boolean isHighlighted = false;

  protected T node;

  protected double widthOffset, heightOffset, modifier, startX, startY;

  final Popup popup = new Popup();

  protected boolean drag = false;

  protected String name = "";

  protected final EventHandler<MouseEvent> hoverListener =
      event -> {
        String content = String.format("{ %.2f : %.2f }", this.getX(), this.getY());
        if (content == null) return;
        if (content.isBlank() || content.isEmpty()) return;
        Label popupContent1 = new Label(content);
        popupContent1.setStyle(
            "-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 5px; -fx-text-fill: black;");

        Label popupContent2 = new Label(name);
        popupContent2.setStyle(
            "-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 5px; -fx-text-fill: black;");

        VBox popupContent = new VBox();
        popupContent.getChildren().add(popupContent1);
        popupContent.getChildren().add(popupContent2);
        popup.getContent().clear();
        popup.getContent().addAll(popupContent);

        if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
          popup.hide();
        } else if (event.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
          popup.show(this, event.getScreenX() + 10, event.getScreenY());
        }
      };

  public boolean isDrag() {
    return drag;
  }

  public void setDrag(boolean drag) {
    this.drag = drag;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  Blend blush;

  public NodeImageView(Image i, T node) {
    super(i);

    ColorAdjust monochrome = new ColorAdjust();
    monochrome.setSaturation(-1.0);

    blush =
        new Blend(
            BlendMode.MULTIPLY,
            monochrome,
            new ColorInput(
                0, 0, this.getImage().getWidth(), this.getImage().getHeight(), Color.RED));

    name = node.toString();
    this.addEventFilter(
        MouseEvent.DRAG_DETECTED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            drag = true;
          }
        });
    this.addEventFilter(
        MouseEvent.MOUSE_PRESSED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            drag = false;
          }
        });

    this.node = node;
    popup.setAutoHide(true);

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
          this.setClicked(!isClicked());
        });

    this.setOnMouseDragged(
        event -> {
          if (event.getButton().equals(MouseButton.PRIMARY) && drag) {
            this.setCursor(Cursor.CLOSED_HAND);
            this.setX(event.getX() - widthOffset);
            this.setY(event.getY() - heightOffset);
            popup.hide();
          }
        });
  }

  public double getWidthOffset() {
    return widthOffset;
  }

  public void setWidthOffset(double widthOffset) {
    this.widthOffset = widthOffset;
  }

  public double getHeightOffset() {
    return heightOffset;
  }

  public void setHeightOffset(double heightOffset) {
    this.heightOffset = heightOffset;
  }

  public double getModifier() {
    return modifier;
  }

  public void setModifier(double modifier) {
    this.modifier = modifier;
  }

  public double getStartX() {
    return startX;
  }

  public double getStartY() {
    return startY;
  }

  public void highlightNode() {
    this.setCursor(Cursor.OPEN_HAND);
    this.setScaleX(this.getScaleX() + 0.4);
    this.setScaleY(this.getScaleY() + 0.4);
    this.setStyle("-fx-border-color: black; -fx-border-style: solid;-fx-border-width: 5;");
  }

  public void unhighlightNode() {
    this.setCursor(Cursor.DEFAULT);
    this.setScaleX(this.getScaleX() - 0.4);
    this.setScaleY(this.getScaleY() - 0.4);
    // this.setStyle("");
  }

  public void attach(mapPageController c) {
    controller = c;
  }

  public void notifyController() {
    controller.updateNode(this);
  }

  public boolean isClicked() {
    return isClicked;
  }

  public void setClicked(boolean clicked) {
    if (isClicked) this.setCursor(Cursor.CLOSED_HAND);

    isClicked = clicked;
    notifyController();
  }

  public boolean isHighlighted() {
    return isHighlighted;
  }

  public void setHighlighted(boolean highlighted) {
    isHighlighted = highlighted;
    if (isHighlighted) {
      highlightNode();
    } else {
      unhighlightNode();
    }
  }

  public T getNode() {
    return node;
  }

  public void setNode(T node) {
    this.node = node;
  }

  public void setStartX(double x) {
    this.startX = x;
  }

  public void setStartY(double y) {
    this.startY = y;
  }

  public void deleteFromDB() {
    throw new RuntimeException(
        String.format("Cannot Delete Node Of This Type : %s", node.toString()));
  }
}
