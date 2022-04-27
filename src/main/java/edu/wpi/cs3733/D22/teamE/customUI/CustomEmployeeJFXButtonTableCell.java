package edu.wpi.cs3733.D22.teamE.customUI;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.controllers.employeePageController;
import edu.wpi.cs3733.D22.teamE.entity.Employee;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class CustomEmployeeJFXButtonTableCell<S> extends TableCell<S, Employee> {
  JFXButton button = new JFXButton();
  employeePageController controller;

  public static <S> Callback<TableColumn<S, Employee>, TableCell<S, Employee>> forTableColumn(
      employeePageController controller) {
    return list -> new CustomEmployeeJFXButtonTableCell<>(controller);
  }

  CustomEmployeeJFXButtonTableCell(employeePageController controller) {
    super();
    this.controller = controller;
    button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
  }

  @Override
  protected void updateItem(Employee item, boolean empty) {
    super.updateItem(item, empty);
    button.setOnAction(
        event -> {
          PopUp.createEmployee(item, button.getScene().getWindow(), controller);
        });
    if (empty || item == null) {
      setText(null);
      setGraphic(null);
    } else {
      setText(null);
      button.setText(item.getName());
      setGraphic(button);
    }
  }
}
