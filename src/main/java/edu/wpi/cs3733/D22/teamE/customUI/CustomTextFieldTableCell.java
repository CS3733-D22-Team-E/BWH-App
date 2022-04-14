package edu.wpi.cs3733.D22.teamE.customUI;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

public class CustomTextFieldTableCell<S, T> extends TextFieldTableCell<S, T> {

  public static <S> Callback<TableColumn<S, String>, TableCell<S, String>> forTableColumn() {
    return forTableColumn(new DefaultStringConverter());
  }

  public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(
      final StringConverter<T> converter) {
    return list -> {
      final TextFieldTableCell<S, T> result = new TextFieldTableCell<S, T>(converter);
      final Popup popup = new Popup();
      popup.setAutoHide(true);

      final EventHandler<MouseEvent> hoverListener =
          event -> {
            String content = result.getText();
            if (content == null) return;
            if (content.isBlank() || content.isEmpty()) return;
            final Label popupContent = new Label(content);
            popupContent.setStyle(
                "-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 5px; -fx-text-fill: black;");

            popup.getContent().clear();
            popup.getContent().addAll(popupContent);

            if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
              popup.hide();
            } else if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
              popup.show(result, event.getScreenX() + 10, event.getScreenY());
            }
          };

      result.setOnMouseEntered(hoverListener);
      result.setOnMouseExited(hoverListener);
      return result;
    };
  }
}
