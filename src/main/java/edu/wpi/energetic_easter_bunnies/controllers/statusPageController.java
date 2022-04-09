package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.database.daos.ServiceRequestDAOImpl;
import edu.wpi.energetic_easter_bunnies.entity.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;

public class statusPageController extends containsSideMenu {
  @FXML TextField filterFieldDate;
  @FXML TextField filterFieldID;
  @FXML TextField filterFieldType;
  @FXML TextField filterFieldStatus;
  @FXML TextField filterFieldAssign;
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
      FilteredList<serviceRequestModel> filteredData = new FilteredList<>(requestList, p -> true);
      filterFieldID
          .textProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                filteredData.setPredicate(
                    requestModel -> {
                      // If filter text is empty, display all persons.
                      if (newValue == null || newValue.isEmpty()) {
                        return true;
                      }

                      String lowerCaseFilter = newValue.toLowerCase();

                      return requestModel.getID().toLowerCase().contains(lowerCaseFilter);
                    });
              });

      filterFieldType
          .textProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                filteredData.setPredicate(
                    requestModel -> {
                      // If filter text is empty, display all persons.
                      if (newValue == null || newValue.isEmpty()) {
                        return true;
                      }

                      String lowerCaseFilter = newValue.toLowerCase();

                      return requestModel.getType().toLowerCase().contains(lowerCaseFilter);
                    });
              });

      filterFieldStatus
          .textProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                filteredData.setPredicate(
                    requestModel -> {
                      // If filter text is empty, display all persons.
                      if (newValue == null || newValue.isEmpty()) {
                        return true;
                      }

                      String lowerCaseFilter = newValue.toLowerCase();

                      return requestModel.getStatus().toLowerCase().contains(lowerCaseFilter);
                    });
              });

      filterFieldAssign
          .textProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                filteredData.setPredicate(
                    requestModel -> {
                      // If filter text is empty, display all persons.
                      if (newValue == null || newValue.isEmpty()) {
                        return true;
                      }

                      String lowerCaseFilter = newValue.toLowerCase();

                      return requestModel.getAssignee().toLowerCase().contains(lowerCaseFilter);
                    });
              });
      filterFieldDate
          .textProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                filteredData.setPredicate(
                    requestModel -> {
                      // If filter text is empty, display all persons.
                      if (newValue == null || newValue.isEmpty()) {
                        return true;
                      }

                      String lowerCaseFilter = newValue.toLowerCase();

                      return requestModel.getRequestDate().toLowerCase().contains(lowerCaseFilter);
                    });
              });
      idColumn.setCellValueFactory(new PropertyValueFactory<serviceRequestModel, String>("ID"));
      typeColumn.setCellValueFactory(new PropertyValueFactory<serviceRequestModel, String>("Type"));
      statusColumn.setCellValueFactory(
          new PropertyValueFactory<serviceRequestModel, String>("Status"));
      statusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
      statusColumn.setOnEditCommit(
          t ->
              t.getTableView()
                  .getItems()
                  .get(t.getTablePosition().getRow())
                  .setStatus(t.getNewValue()));
      assignedColumn.setCellValueFactory(
          new PropertyValueFactory<serviceRequestModel, String>("Assignee"));

      assignedColumn.setCellFactory(TextFieldTableCell.forTableColumn());
      assignedColumn.setOnEditCommit(
          t ->
              t.getTableView()
                  .getItems()
                  .get(t.getTablePosition().getRow())
                  .setAssignee(t.getNewValue()));
      dateColumn.setCellValueFactory(
          new PropertyValueFactory<serviceRequestModel, String>("requestDate"));
      requestTable.setItems(filteredData);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  protected ObservableList<serviceRequestModel> populateList() {
    List<serviceRequest> list = db.getAll();
    ObservableList<serviceRequestModel> tableList = FXCollections.observableArrayList();
    /*for (serviceRequest r : list) {
      tableList.add(
          new serviceRequestModel(
              r.getServiceRequestID(),
              r.getRequestStatus(),
              r.getRequestType().toString(),
              r.getStaffAssignee(),
              r.getRequestDate().toString(),
              ((r.getDeliveryDate() == null) ? "" : r.getDeliveryDate().toString()),
              r.getIsUrgent()));
    }*/
    for (int i = 0; i < 100; i++)
      tableList.add(
          new serviceRequestModel(String.valueOf(i), "Done", "Temp", "Temp", "Now", "Now", false));
    return tableList;
  }
}
