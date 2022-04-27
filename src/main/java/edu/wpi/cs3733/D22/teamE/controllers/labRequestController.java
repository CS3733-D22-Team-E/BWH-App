package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.labRequest;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 * This is the controller class for the Lab Request service page. It inherits from the
 * serviceRequestController super class.
 */
public class labRequestController extends serviceRequestPageController implements Initializable {

  @FXML JFXComboBox<String> labRequestType;
  @FXML JFXComboBox<String> timeFrameComboBox;
  @FXML CheckBox isUrgent;

  // @FXML TableView<labRequest> requestsTable;

  /*@FXML TableColumn<labRequest, String> tableLabRequestType;
  @FXML TableColumn<labRequest, String> tableStaffAssignee;
  @FXML TableColumn<labRequest, String> tableLocNodeID;
  @FXML TableColumn<labRequest, String> tableTimeFrame;
  @FXML TableColumn<labRequest, String> tableRequestStatus;
  @FXML TableColumn<labRequest, String> tableOtherNotes;
  ObservableList<labRequest> tableList;*/

  // LocationDAOImpl locationDB;
  // LabRequestDAOImpl labRequestDB;
  DAOSystem system;

  /** Constructor */
  public labRequestController() {
    system = DAOSystemSingleton.INSTANCE.getSystem();
  }

  /**
   * Initializes the
   *
   * @param location
   * @param resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
    setInfographicsCount("LAB_REQUEST");
    try {
      // populateLocationComboBoxes();
      labRequestType.getItems().addAll("Blood Sample", "Urine Sample", "X-Ray", "CAT Scan", "MRI");
      timeFrameComboBox.getItems().addAll("ASAP", "<1 day", "<1 week");
      // populateLabRequestTable();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void populateLabRequestTable() {
    /*ObservableList<labRequest> labRequests = populateLabRequestList();
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

    requestsTable.setItems(labRequests);*/
  }

  /*protected ObservableList<labRequest> populateLabRequestList() {
    List<labRequest> list = system.getAllLabRequests();
    tableList = FXCollections.observableArrayList();
    for (labRequest l : list) {
      tableList.add(l);
    }
    return tableList;
  }*/

  @Override
  public void submitButton(ActionEvent event) throws SQLException {
    try {
      labRequest labReq = new labRequest();
      labReq.setLabRequestType(labRequestType.getValue());
      labReq.setTimeFrame(timeFrameComboBox.getValue());
      labReq.setFloorID(floor.getValue());
      labReq.setRoomID(roomNameToRoomID.get(room.getValue()));
      labReq.setOtherNotes(notes.getText());
      labReq.setRequestStatus(requestStatus.getText());
      labReq.setStaffAssignee(staffAssignee.getText());
      labReq.setIsUrgent(isUrgent.isSelected());
      labSendToDB(labReq);
      setInfographicsCount("LAB_REQUEST");

    } catch (RuntimeException error) {
      System.out.println("Error : Some Value is NULL");
      PopUp.createWarning("Warning : A required value was not filled", null);
    }
  }

  private void labSendToDB(labRequest labReq) throws SQLException {
    system.update(labReq);
    floor.getSelectionModel().clearSelection();
    room.getSelectionModel().clearSelection();
    labRequestType.getSelectionModel().clearSelection();
    timeFrameComboBox.getSelectionModel().clearSelection();
    requestStatus.clear();
    staffAssignee.clear();
    notes.clear();
    isUrgent.setSelected(false);
    room.setVisible(false);
    // tableList.add(labReq);
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
    isUrgent.setSelected(false);
    room.setVisible(false);
  }
}
