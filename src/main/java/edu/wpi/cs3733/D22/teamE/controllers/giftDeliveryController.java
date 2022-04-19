package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.GiftRequestDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.giftDeliveryRequest;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 * This is the controller class for the gift delivery service request. Inherits from the
 * serviceDeliveryController super class.
 */
public class giftDeliveryController extends serviceRequestPageController implements Initializable {
  @FXML JFXComboBox<String> giftOptionType;
  @FXML DatePicker deliveryDate;
  @FXML CheckBox isUrgent;
  @FXML TextField patientName;
  @FXML TextArea notes;
  @FXML TableView<giftDeliveryRequest> requestsTable;

  @FXML TableColumn<giftDeliveryRequest, String> tableGiftType;
  @FXML TableColumn<giftDeliveryRequest, String> tablePatientName;
  @FXML TableColumn<giftDeliveryRequest, String> tableLocNodeID;
  @FXML TableColumn<giftDeliveryRequest, String> tableStaffAssignee;
  @FXML TableColumn<giftDeliveryRequest, LocalDate> tableDeliveryDate;
  @FXML TableColumn<giftDeliveryRequest, String> tableRequestStatus;
  @FXML TableColumn<giftDeliveryRequest, String> tableGreetingCard;
  @FXML TableColumn<giftDeliveryRequest, Boolean> tableIsUrgent;

  GiftRequestDAOImpl giftRequestDAO;
  ObservableList<giftDeliveryRequest> tableList;

  giftDeliveryRequest request = new giftDeliveryRequest(); // object to store inputted page data

  DAOSystem system;

  /** Constructor */
  public giftDeliveryController() {
    try {
      system = new DAOSystem();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) { // TODO: Implement
  }

  // TODO: Create a method to populate the gift types combo box

  /**
   * Gets the full list of gift delivery requests currently in the database.
   *
   * @return list of giftDeliveryRequest objects in the database
   */
  protected ObservableList<giftDeliveryRequest> populateGiftRequestsList() {
    List<giftDeliveryRequest> requests = giftRequestDAO.getAll();
    tableList = FXCollections.observableArrayList();
    for (giftDeliveryRequest request : requests) {
      tableList.add(request);
    }
    return tableList;
  }

  private void populateGiftReqTable() {
    ObservableList<giftDeliveryRequest> giftDeliveryRequests = populateGiftRequestsList();

    // TODO: Set Cell Factory stuff?
  }

  @Override
  public void submitButton(ActionEvent event) throws SQLException {
    try {
      request.setGift(giftOptionType.getValue());
      request.setPatientName(patientName.getText());
      request.setFloorID(floor.getValue());
      request.setRoomID(roomNameToRoomID.get(room.getValue()));
      request.setStaffAssignee(staffAssignee.getText());
      request.setRequestDate(LocalDate.now());
      request.setDeliveryDate(deliveryDate.getValue());
      request.setRequestStatus(requestStatus.getText());
      request.setOtherNotes(notes.getText());
      request.setUrgent(isUrgent.isSelected());

      giftSendToDB();

    } catch (NullPointerException e) {
      System.out.println("Error : Some Value is NULL");
      PopUp.createWarning("Warning : A required value was not filled", drawer.getScene().getWindow());
    }
  }

  private void giftSendToDB() {
    try {
      request.setRequestDate(LocalDate.now());
      system.addGiftDelivery(request);
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
    giftOptionType.getSelectionModel().clearSelection();
    isUrgent.setSelected(false);
    deliveryDate.getEditor().clear();
    requestStatus.clear();
    staffAssignee.clear();
    patientName.clear();
    notes.clear();
  }
}
