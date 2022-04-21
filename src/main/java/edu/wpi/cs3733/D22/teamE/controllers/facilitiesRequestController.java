package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.FacilitiesRequestDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.facilitiesRequest;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * This is the controller class for the gift delivery service request. Inherits from the
 * serviceDeliveryController super class.
 */
public class facilitiesRequestController extends serviceRequestPageController
    implements Initializable {
  @FXML JFXComboBox<String> facilitiesOptionType;
  @FXML DatePicker deliveryDate; // when it will be serviced?
  @FXML DatePicker requestDate;
  @FXML CheckBox isUrgent;
  @FXML TextField timeFrame;
  @FXML TableView<facilitiesRequest> requestsTable;
  // facilitiesRequest
  @FXML TableColumn<facilitiesRequest, String> tableRequestType;
  @FXML TableColumn<facilitiesRequest, String> tableFloorID;
  @FXML TableColumn<facilitiesRequest, String> tableRoomID; // TL
  @FXML TableColumn<facilitiesRequest, String> tableStaffAssignee;
  @FXML TableColumn<facilitiesRequest, String> tableTimeFrame;
  @FXML TableColumn<facilitiesRequest, LocalDate> tableDeliveryDate;
  @FXML TableColumn<facilitiesRequest, String> tableRequestStatus;
  @FXML TableColumn<facilitiesRequest, Boolean> tableIsUrgent;
  @FXML TableColumn<facilitiesRequest, String> tableNotes;

  FacilitiesRequestDAOImpl facilitiesRequestDAO;
  ObservableList<facilitiesRequest> tableList;

  facilitiesRequest request = new facilitiesRequest(); // object to store inputted page data

  DAOSystem system;

  /** Constructor */
  public facilitiesRequestController() {
    try {
      system = new DAOSystem();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
    try {
      facilitiesRequestDAO = new FacilitiesRequestDAOImpl();
      populateLocationComboBoxes();
      populateFacilitiesReqTable();
      facilitiesOptionType
          .getItems()
          .addAll("Power Outage", "Maintenance", "Network Problem", "Other");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // TODO: Create a method to populate the gift types combo box

  /**
   * Gets the full list of gift delivery requests currently in the database.
   *
   * @return list of giftDeliveryRequest objects in the database
   */
  protected ObservableList<facilitiesRequest> populateFacilitiesRequestsList() {
    List<facilitiesRequest> requests = facilitiesRequestDAO.getAll();
    tableList = FXCollections.observableArrayList();
    for (facilitiesRequest request : requests) {
      tableList.add(request);
    }
    return tableList;
  }

  private void populateFacilitiesReqTable() {
    ObservableList<facilitiesRequest> facilitiesRequests = populateFacilitiesRequestsList();
    // tableRequestType.setCellValueFactory(new PropertyValueFactory<>("facilities"));
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
    tableFloorID.setCellValueFactory(new PropertyValueFactory<>("FloorID"));
    tableStaffAssignee.setCellValueFactory(new PropertyValueFactory<>("staffAssignee"));
    tableDeliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
    tableRequestStatus.setCellValueFactory(new PropertyValueFactory<>("requestStatus"));
    // tableNotes.setCellValueFactory(new PropertyValueFactory<>("otherNotes"));
    tableIsUrgent.setCellValueFactory(new PropertyValueFactory<>("isUrgent"));
    // tableTimeFrame.setCellValueFactory(new PropertyValueFactory<>("timeFrame"));

    requestsTable.setItems(facilitiesRequests);
  }

  @Override
  public void submitButton(ActionEvent event) throws SQLException {
    try {
      request.setFacilitiesRequestType(facilitiesOptionType.getValue());
      request.setFloorID(floor.getValue());
      request.setRoomID(roomNameToRoomID.get(room.getValue()));
      request.setStaffAssignee(staffAssignee.getText());
      request.setRequestDate(LocalDate.now());
      request.setDeliveryDate(deliveryDate.getValue());
      request.setRequestStatus(requestStatus.getText());
      request.setOtherNotes(notes.getText());
      request.setIsUrgent(isUrgent.isSelected());
      request.setTimeFrame(timeFrame.getText());

      facilitiesSendToDB();

    } catch (NullPointerException e) {
      System.out.println("Error : Some Value is NULL");
      PopUp.createWarning(
          "Warning : A required value was not filled", drawer.getScene().getWindow());
    }
  }

  private void facilitiesSendToDB() {
    try {
      request.setRequestDate(LocalDate.now());
      system.updateFacilitiesRequest(request);
      tableList.add(request);
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
    isUrgent.setSelected(false);
    deliveryDate.getEditor().clear();
    requestDate.getEditor().clear();
    requestStatus.clear();
    staffAssignee.clear();
    notes.clear();
  }
}
