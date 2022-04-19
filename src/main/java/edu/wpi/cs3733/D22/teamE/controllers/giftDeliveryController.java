package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.GiftRequestDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.*;
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
  @FXML JFXComboBox<String> giftOptionsDropDown;
  @FXML DatePicker deliveryDateTime; // TODO: Make requestDate just get local time?
  @FXML CheckBox isUrgent;
  @FXML TextField staffAssignee;
  @FXML TextField requestStatus;
  @FXML TextField patientName;
  @FXML TextArea otherNotesTxt; // TODO: Rename to gift message text
  @FXML TableView<giftDeliveryRequest> giftDeliveryTable;

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

  private void populateGiftReqTable() { // TODO: Implement
    ObservableList<giftDeliveryRequest> giftDeliveryRequests = populateGiftRequestsList();

    // TODO: Set Cell Factory stuff?
  }

  @Override
  public void submitButton(ActionEvent event) throws SQLException { // TODO: Implement
  }

  private void giftSendToDB(
      giftDeliveryRequest request) { // TODO: Can just be the class variable object instead?
    try {
      request.setRequestDate(LocalDate.now());
      system.addGiftDelivery(request);
      // TODO: Populate table here
    } catch (Exception e) { // TODO: Should be SQLException
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
    // TODO: Reset le other fields
    requestStatus.clear();
    staffAssignee.clear();
    notes.clear();
  }
}
