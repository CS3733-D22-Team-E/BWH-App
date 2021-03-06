package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.facilitiesRequest;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 * This is the controller class for the gift delivery service request. Inherits from the
 * serviceDeliveryController super class.
 */
public class facilitiesRequestController extends serviceRequestPageController
    implements Initializable {
  @FXML JFXComboBox<String> facilitiesOptionType;
  @FXML DatePicker deliveryDate; // when it will be serviced?
  // @FXML DatePicker requestDate;
  @FXML CheckBox isUrgent;
  @FXML TextField timeFrame;
  /*@FXML TableView<facilitiesRequest> requestsTable;
  // facilitiesRequest
  @FXML TableColumn<facilitiesRequest, String> tableRequestType;
  @FXML TableColumn<facilitiesRequest, String> tableFloorID;
  @FXML TableColumn<facilitiesRequest, String> tableRoomID; // TL
  @FXML TableColumn<facilitiesRequest, String> tableStaffAssignee;
  @FXML TableColumn<facilitiesRequest, String> tableTimeFrame;
  @FXML TableColumn<facilitiesRequest, LocalDate> tableDeliveryDate;
  @FXML TableColumn<facilitiesRequest, LocalDate> tableRequestDate;
  @FXML TableColumn<facilitiesRequest, String> tableRequestStatus;
  @FXML TableColumn<facilitiesRequest, Boolean> tableIsUrgent;
  @FXML TableColumn<facilitiesRequest, String> tableNotes;*/

  // FacilitiesRequestDAOImpl facilitiesRequestDAO;
  // ObservableList<facilitiesRequest> tableList;

  DAOSystem system;

  /** Constructor */
  public facilitiesRequestController() {
    system = DAOSystemSingleton.INSTANCE.getSystem();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
    setInfographicsCount("FACILITIES_REQ");
    try {
      // populateLocationComboBoxes();
      // populateFacilitiesReqTable();
      facilitiesOptionType
          .getItems()
          .addAll("Power Outage", "Maintenance", "Network Problem", "Other");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // TODO: Create a method to populate the gift types combo box

  /**
   * Gets the full list of gift delivery requests currently in the database.
   *
   * @return list of giftDeliveryRequest objects in the database
   */
  /*protected ObservableList<facilitiesRequest> populateFacilitiesRequestsList() {
    List<facilitiesRequest> requests = system.getAllFacilitiesRequests();
    tableList = FXCollections.observableArrayList();
    for (facilitiesRequest request : requests) {
      tableList.add(request);
    }
    return tableList;
  }*/

  private void populateFacilitiesReqTable() {
    /*ObservableList<facilitiesRequest> facilitiesRequests = populateFacilitiesRequestsList();
    tableRequestType.setCellValueFactory(new PropertyValueFactory<>("requestType"));
    tableRoomID.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<facilitiesRequest, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<facilitiesRequest, String> param) {
            facilitiesRequest curFacReq = param.getValue();
            return new SimpleStringProperty(roomIDToRoomName.get(curFacReq.getRoomID()));
          }
        });
    tableFloorID.setCellValueFactory(new PropertyValueFactory<>("floorID"));
    tableStaffAssignee.setCellValueFactory(new PropertyValueFactory<>("staffAssignee"));
    // tableDeliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
    tableRequestStatus.setCellValueFactory(new PropertyValueFactory<>("requestStatus"));
    tableNotes.setCellValueFactory(new PropertyValueFactory<>("otherNotes"));
    tableIsUrgent.setCellValueFactory(new PropertyValueFactory<>("isUrgent"));
    // tableTimeFrame.setCellValueFactory(new PropertyValueFactory<>("timeFrame"));
    tableRequestDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
    requestsTable.setItems(facilitiesRequests);*/
  }

  @Override
  public void submitButton(ActionEvent event) throws SQLException {
    try {
      facilitiesRequest request = new facilitiesRequest(); // object to store inputted page data
      request.setFacilitiesRequestType(facilitiesOptionType.getValue()); // getValue());
      request.setFloorID(floor.getValue());
      request.setRoomID(roomNameToRoomID.get(room.getValue()));
      request.setStaffAssignee(staffAssignee.getValue());
      request.setRequestDate(LocalDate.now());
      request.setDeliveryDate(deliveryDate.getValue());
      request.setRequestStatus(requestStatus.getValue());
      request.setOtherNotes(notes.getText());
      request.setIsUrgent(isUrgent.isSelected());
      request.setTimeFrame(timeFrame.getText());

      facilitiesSendToDB(request);
      setInfographicsCount("FACILITIES_REQ");
      PopUp.submissionConfirmation(
          "Your facilities service request has been submitted.",
          submitButton.getScene().getWindow());

    } catch (NullPointerException e) {
      System.out.println("Error : Some Value is NULL");
      PopUp.createWarning(
          "Warning : A required value was not filled", drawer.getScene().getWindow());
    }
  }

  private void facilitiesSendToDB(facilitiesRequest request) {
    try {
      request.setRequestDate(LocalDate.now());
      system.update(request);
      floor.getSelectionModel().clearSelection();
      room.getSelectionModel().clearSelection();
      facilitiesOptionType.getSelectionModel().clearSelection();
      timeFrame.clear();
      isUrgent.setSelected(false);
      deliveryDate.getEditor().clear();
      staffAssignee.getSelectionModel().clearSelection();
      requestStatus.getSelectionModel().clearSelection();
      notes.clear();
      room.setVisible(false);
      // tableList.add(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * clears all of the inputs on the page.
   *
   * @param event Pressing the resetButton
   */
  @FXML
  private void resetButton(ActionEvent event) {
    floor.getSelectionModel().clearSelection();
    room.getSelectionModel().clearSelection();
    facilitiesOptionType.getSelectionModel().clearSelection();
    timeFrame.clear();
    isUrgent.setSelected(false);
    deliveryDate.getEditor().clear();
    staffAssignee.getSelectionModel().clearSelection();
    requestStatus.getSelectionModel().clearSelection();
    notes.clear();
    room.setVisible(false);
  }
}
