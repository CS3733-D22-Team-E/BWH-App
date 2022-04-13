package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUp;
import edu.wpi.energetic_easter_bunnies.database.daos.LabRequestDAOImpl;
import edu.wpi.energetic_easter_bunnies.database.daos.LocationDAOImpl;
import edu.wpi.energetic_easter_bunnies.entity.labRequest;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * This is the controller class for the Lab Request service page. It inherits from the
 * serviceRequestController super class.
 */
public class labRequestController extends serviceRequestPageController {

  @FXML ComboBox<String> labRequestType;
  @FXML ComboBox<String> timeFrameComboBox;

  @FXML TableView<labRequest> requestsTable;

  @FXML TableColumn<labRequest, String> tableLabRequestType;
  @FXML TableColumn<labRequest, String> tableStaffAssignee;
  @FXML TableColumn<labRequest, String> tableLocNodeID;
  @FXML TableColumn<labRequest, String> tableTimeFrame;
  @FXML TableColumn<labRequest, String> tableRequestStatus;
  @FXML TableColumn<labRequest, String> tableOtherNotes;
  ObservableList<labRequest> tableList;

  LocationDAOImpl locationDB;
  LabRequestDAOImpl labRequestDB;
  labRequest labReq = new labRequest();

  /** Constructor */
  public labRequestController() {}

  /**
   * Initializes the
   *
   * @param location
   * @param resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      super.initialize(location, resources);
      labRequestType.getItems().addAll("Blood Sample", "Urine Sample", "X-Ray", "CAT Scan", "MRI");
      timeFrameComboBox.getItems().addAll("ASAP", "<1 day", "<1 week");

      labRequestDB = new LabRequestDAOImpl();
      populateLabRequestTable();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void populateLabRequestTable() {
    ObservableList<labRequest> labRequests = populateLabRequestList();
    tableLabRequestType.setCellValueFactory(
        new PropertyValueFactory<labRequest, String>("labRequestType"));
    tableStaffAssignee.setCellValueFactory(
        new PropertyValueFactory<labRequest, String>("staffAssignee"));
    tableLocNodeID.setCellValueFactory(
        new Callback<TableColumn.CellDataFeatures<labRequest, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<labRequest, String> param) {
            labRequest curLabRequest = param.getValue();
            return new SimpleStringProperty(roomIDToRoomName.get(curLabRequest.getRoomID()));
          }
        });
    tableTimeFrame.setCellValueFactory(new PropertyValueFactory<labRequest, String>("timeFrame"));
    tableRequestStatus.setCellValueFactory(
        new PropertyValueFactory<labRequest, String>("requestStatus"));
    tableOtherNotes.setCellValueFactory(new PropertyValueFactory<labRequest, String>("otherNotes"));

    requestsTable.setItems(labRequests);
  }

  protected ObservableList<labRequest> populateLabRequestList() {
    List<labRequest> list = labRequestDB.getAll();
    tableList = FXCollections.observableArrayList();
    for (labRequest l : list) {
      tableList.add(l);
    }
    return tableList;
  }

  @Override
  public void submitButton(ActionEvent event) throws SQLException {
    try {
      labReq.setLabRequestType(labRequestType.getValue());
      labReq.setTimeFrame(timeFrameComboBox.getValue());
      labReq.setFloorID(floor.getValue());
      labReq.setRoomID(roomNameToRoomID.get(room.getValue()));
      labReq.setOtherNotes(notes.getText());
      labReq.setRequestStatus(requestStatus.getText());
      labReq.setStaffAssignee(staffAssignee.getText());
      labSendToDB(labReq);

    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUp.createWarning("Warning : A required value was not filled", null);
    }
  }

  private void labSendToDB(labRequest labReq) throws SQLException {
    labRequestDB.update(labReq);
    tableList.add(labReq);
  }

  @FXML
  private void resetButton(ActionEvent event) {
    floor.getSelectionModel().clearSelection();
    room.getSelectionModel().clearSelection();
    labRequestType.getSelectionModel().clearSelection();
    timeFrameComboBox.getSelectionModel().clearSelection();
    requestStatus.clear();
    staffAssignee.clear();
    notes.clear();
  }
}
