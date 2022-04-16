package edu.wpi.cs3733.D22.teamE.customUI;

import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import edu.wpi.cs3733.D22.teamE.entity.serviceRequest;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ServiceRequestButtonListCell extends CustomJFXButtonListCell<serviceRequest> {
  public ServiceRequestButtonListCell() {
    super();
  }

  @Override
  protected void updateItem(serviceRequest item, boolean empty) {
    super.updateItem(item, empty);
    button.setOnAction(
        event -> {
          boolean edit =
              AccountsManager.getInstance().getAccount().getAuthorityLevel() >= Account.adminPerm;
          PopUp.createReq(item, button.getScene().getWindow(), edit);
        });
  }

  public static Callback<ListView<serviceRequest>, ListCell<serviceRequest>> forListView() {
    return list -> new ServiceRequestButtonListCell();
  }
}
