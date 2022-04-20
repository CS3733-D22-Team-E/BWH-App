package edu.wpi.cs3733.D22.teamE.customUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;

public class CustomJFXButtonListCell<T> extends JFXListCell<T> {
  JFXButton button = new JFXButton();
  HBox box = new HBox();
  DAOSystem db;
  Object controller;

  public CustomJFXButtonListCell(Object controller) {
    super();
    this.controller = controller;
    try {
      db = new DAOSystem();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    this.setPadding(new Insets(0, 0, 0, 0));
    button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    HBox.setHgrow(button, Priority.ALWAYS);
    box.getChildren().add(button);
  }

  @Override
  protected void updateItem(T item, boolean empty) {
    super.updateItem(item, empty);
    if (empty || item == null) {
      setText(null);
      setGraphic(null);
    } else {
      setText(null);
      button.setText(item.toString());
      setGraphic(button);
    }
  }

  public static <T> Callback<ListView<T>, ListCell<T>> forListView(Object controller) {
    return list -> new CustomJFXButtonListCell<>(controller);
  }
}
