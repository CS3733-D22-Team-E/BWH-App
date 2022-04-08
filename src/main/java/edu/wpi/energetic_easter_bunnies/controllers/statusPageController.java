package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.database.daos.ServiceRequestDAOImpl;
import edu.wpi.energetic_easter_bunnies.entity.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class statusPageController extends containsSideMenu {
  @FXML VBox root;
  @FXML TableView<serviceRequestModel> requestTable;
  @FXML TableColumn<serviceRequestModel, String> idColumn;
  @FXML TableColumn<serviceRequestModel, String> typeColumn;
  @FXML TableColumn<serviceRequestModel, String> statusColumn;
  @FXML TableColumn<serviceRequestModel, String> assignedColumn;
  @FXML TableColumn<serviceRequestModel, String> dateColumn;
  ServiceRequestDAOImpl db;

  public statusPageController() {
    super();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    super.initialize(url, rb);
    try {
      db = new ServiceRequestDAOImpl();
      ObservableList<serviceRequestModel> requestList = populateList();
      idColumn.setCellValueFactory(new PropertyValueFactory<serviceRequestModel, String>("ID"));
      typeColumn.setCellValueFactory(new PropertyValueFactory<serviceRequestModel, String>("Type"));
      statusColumn.setCellValueFactory(
          new PropertyValueFactory<serviceRequestModel, String>("Status"));
      assignedColumn.setCellValueFactory(
          new PropertyValueFactory<serviceRequestModel, String>("Assignee"));
      dateColumn.setCellValueFactory(
          new PropertyValueFactory<serviceRequestModel, String>("Request Date"));
      requestTable.setItems(requestList);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  protected ObservableList<serviceRequestModel> populateList() {
    List<serviceRequest> list = db.getAll();
    ObservableList<serviceRequestModel> tableList = FXCollections.observableArrayList();
    for (serviceRequest r : list) {
      tableList.add(
          new serviceRequestModel(
              r.getServiceRequestID(),
              r.getRequestStatus(),
              r.getRequestType().toString(),
              r.getStaffAssignee(),
              r.getRequestDate().toString(),
              ((r.getDeliveryDate() == null) ? "" : r.getDeliveryDate().toString()),
              r.getIsUrgent()));
    }
    return tableList;
  }
}
