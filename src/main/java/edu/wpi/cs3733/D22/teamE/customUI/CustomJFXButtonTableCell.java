package edu.wpi.cs3733.D22.teamE.customUI;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import edu.wpi.cs3733.D22.teamE.entity.serviceRequest;
import java.sql.SQLException;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class CustomJFXButtonTableCell<S> extends TableCell<S, serviceRequest> {
  JFXButton button = new JFXButton();
  DAOSystem db;
  Object controller;

  public static <S>
      Callback<TableColumn<S, serviceRequest>, TableCell<S, serviceRequest>> forTableColumn(
          Object controller) {
    return list -> new CustomJFXButtonTableCell<>(controller);
  }

  CustomJFXButtonTableCell(Object controller) {
    super();
    this.controller = controller;
    try {
      db = new DAOSystem();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
  }

  @Override
  protected void updateItem(serviceRequest item, boolean empty) {
    super.updateItem(item, empty);
    button.setOnAction(
        event -> {
          boolean edit =
              AccountsManager.getInstance().getAccount().getAuthorityLevel() >= Account.adminPerm;
          System.out.println(edit);
          PopUp.createReq(item, button.getScene().getWindow(), edit, controller);
          db.updateServiceRequest(item);
        });
    if (empty || item == null) {
      setText(null);
      setGraphic(null);
    } else {
      setText(null);
      button.setText(item.toString());
      setGraphic(button);
    }
  }
}
