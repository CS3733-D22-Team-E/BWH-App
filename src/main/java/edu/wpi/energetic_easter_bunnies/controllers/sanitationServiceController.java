package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.database.daos.DAOSystem;
import edu.wpi.energetic_easter_bunnies.entity.sanitationRequest;
import edu.wpi.energetic_easter_bunnies.entity.sanitationRequestModel;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller Class for the Sanitation Service Request. Inherits from the serviceRequestController
 * super class.
 */
public class sanitationServiceController extends serviceRequestPageController {

  @FXML TableColumn<sanitationRequestModel, String> floorCol;
  @FXML TableColumn<sanitationRequestModel, String> roomCol;
  @FXML TableColumn<sanitationRequestModel, String> size;
  @FXML TableColumn<sanitationRequestModel, String> bio;
  @FXML TableColumn<sanitationRequestModel, String> id;
  @FXML TableColumn<sanitationRequestModel, String> status;
  @FXML TableColumn<sanitationRequestModel, String> assign;
  @FXML TableColumn<sanitationRequestModel, SimpleBooleanProperty> urgent;
  @FXML TableView<sanitationRequestModel> table;
  @FXML RadioButton mediumSelect;
  @FXML RadioButton heavySelect;
  @FXML RadioButton lightSelect;
  @FXML RadioButton notUrgent;
  @FXML RadioButton Urgent;
  @FXML RadioButton bioNo;
  @FXML RadioButton bioUnsure;
  @FXML RadioButton bioYes;
  @FXML ToggleGroup biohazardGroup;
  @FXML ToggleGroup urgencyGroup;
  @FXML ToggleGroup sizeGroup;

  DAOSystem system;

  /** Constructor */
  public sanitationServiceController() {
    try {
      system = new DAOSystem();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  ObservableList<sanitationRequestModel> tableList = FXCollections.observableArrayList();

  /**
   * Initializes the super class.
   *
   * @param location ??
   * @param resources ??
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
    populateSanReqTable();
  }

  private void populateSanReqTable() {
    populateList();

    floorCol.setCellValueFactory(new PropertyValueFactory<>("floorID"));
    roomCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));
    id.setCellValueFactory(new PropertyValueFactory<>("ID"));
    status.setCellValueFactory(new PropertyValueFactory<>("Status"));
    assign.setCellValueFactory(new PropertyValueFactory<>("Assignee"));
    urgent.setCellValueFactory(new PropertyValueFactory<>("isUrgent"));
    size.setCellValueFactory(new PropertyValueFactory<>("sizeString"));
    bio.setCellValueFactory(new PropertyValueFactory<>("bioString"));

    table.setItems(tableList);
  }

  private void populateList() {
    List<sanitationRequest> l = system.getAllSanReq();
    for (sanitationRequest r : l) {
      tableList.add(new sanitationRequestModel(r));
    }
  }

  /**
   * Takes the inputs from the buttons, drop downs, text fields etc. and stores that data in the
   * sanitationService object.
   *
   * @param event Pressing the submitButton
   */
  @FXML
  public void submitButton(ActionEvent event) throws SQLException {
    sanitationRequest request = new sanitationRequest();
    try {
      RadioButton selectBiohazard = (RadioButton) biohazardGroup.getSelectedToggle();
      switch (selectBiohazard.getText()) {
        case "Yes":
          request.setBiohazardOnSite(sanitationRequest.Biohazard.Yes);
          break;
        case "No":
          request.setBiohazardOnSite(sanitationRequest.Biohazard.No);
          break;
        case "Unsure":
          request.setBiohazardOnSite(sanitationRequest.Biohazard.Unsure);
          break;
      }

      RadioButton selectSize = (RadioButton) sizeGroup.getSelectedToggle();
      switch (selectSize.getText()) {
        case "Light":
          request.setSizeOfCleaning(sanitationRequest.Size.Light);
          break;
        case "Medium":
          request.setSizeOfCleaning(sanitationRequest.Size.Medium);
          break;
        case "Heavy":
          request.setSizeOfCleaning(sanitationRequest.Size.Heavy);
          break;
      }

      RadioButton selectUrgency = (RadioButton) urgencyGroup.getSelectedToggle();
      switch (selectUrgency.getText()) {
        case "Critical":
          request.setUrgent(true);
          break;
        case "NotCritical":
          request.setUrgent(false);
          break;
      }

      request.setOtherNotes(notes.getText());

      if (staffAssignee.getText().isEmpty() || staffAssignee.getText().isBlank())
        throw new NullPointerException();
      else request.setStaffAssignee(staffAssignee.getText());

      request.setRequestStatus("To Do");

      request.setFloorID(floor.getValue());
      request.setRoomID(room.getValue());

      System.out.println(request);
      sanSendToDB(request);

    } catch (NullPointerException error) {
      error.printStackTrace();
      System.out.println(error.getMessage());
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }

  private void sanSendToDB(sanitationRequest request) {
    try {
      request.setDeliveryDate(LocalDate.now());
      request.setRequestDate(LocalDate.now());
      system.addSanReq(request);
      populateList();
      // tableList.add(new sanitationRequestModel(request));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * clears all of the inputs on the page.
   *
   * @param event Pressing the resetButton
   */
  @FXML
  public void resetFields(ActionEvent event) {
    floor.getSelectionModel().clearSelection();
    room.getSelectionModel().clearSelection();
    sizeGroup.selectToggle(lightSelect);
    biohazardGroup.selectToggle(bioNo);
    urgencyGroup.selectToggle(notUrgent);
    notes.clear();
    staffAssignee.clear();
    room.setVisible(false);
  }
}
