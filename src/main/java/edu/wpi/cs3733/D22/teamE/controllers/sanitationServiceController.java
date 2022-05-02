package edu.wpi.cs3733.D22.teamE.controllers;

import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.sanitationRequest;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 * Controller Class for the Sanitation Service Request. Inherits from the serviceRequestController
 * super class.
 */
public class sanitationServiceController extends serviceRequestPageController
    implements Initializable {

  /*@FXML TableColumn<sanitationRequestModel, String> floorCol;
  @FXML TableColumn<sanitationRequestModel, String> roomCol;
  @FXML TableColumn<sanitationRequestModel, String> size;
  @FXML TableColumn<sanitationRequestModel, String> bio;
  @FXML TableColumn<sanitationRequestModel, String> id;
  @FXML TableColumn<sanitationRequestModel, String> status;
  @FXML TableColumn<sanitationRequestModel, String> assign;
  @FXML TableColumn<sanitationRequestModel, String> urgent;
  @FXML TableView<sanitationRequestModel> table;*/
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
    system = DAOSystemSingleton.INSTANCE.getSystem();
  }

  // ObservableList<sanitationRequestModel> tableList = FXCollections.observableArrayList();

  /**
   * Initializes the super class.
   *
   * @param location ??
   * @param resources ??
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
    setInfographicsCount("SANITATION_REQ");
    // populateSanReqTable();
  }

  /*private void populateSanReqTable() {
    populateList();

    floorCol.setCellValueFactory(new PropertyValueFactory<>("floorID"));
    floorCol.setCellFactory(CustomTextFieldTableCell.forTableColumn());
    roomCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));
    roomCol.setCellFactory(CustomTextFieldTableCell.forTableColumn());
    id.setCellValueFactory(new PropertyValueFactory<>("ID"));
    id.setCellFactory(CustomTextFieldTableCell.forTableColumn());
    status.setCellValueFactory(new PropertyValueFactory<>("Status"));
    status.setCellFactory(CustomTextFieldTableCell.forTableColumn());
    assign.setCellValueFactory(new PropertyValueFactory<>("Assignee"));
    assign.setCellFactory(CustomTextFieldTableCell.forTableColumn());
    urgent.setCellValueFactory(new PropertyValueFactory<>("isUrgent"));
    urgent.setCellFactory(CustomTextFieldTableCell.forTableColumn());
    size.setCellValueFactory(new PropertyValueFactory<>("sizeString"));
    size.setCellFactory(CustomTextFieldTableCell.forTableColumn());
    bio.setCellValueFactory(new PropertyValueFactory<>("bioString"));
    bio.setCellFactory(CustomTextFieldTableCell.forTableColumn());

    table.setItems(tableList);
  }*/

  /*private void populateList() {
    //tableList = FXCollections.observableArrayList();
    List<sanitationRequest> l = system.getAllSanitationRequests();
    for (sanitationRequest r : l) {
      //tableList.add(new sanitationRequestModel(r));
    }
  }*/

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
          request.setIsUrgent(true);
          break;
        case "NotCritical":
          request.setIsUrgent(false);
          break;
      }

      request.setOtherNotes(notes.getText());

      if (staffAssignee.getValue().isEmpty() || staffAssignee.getValue().isBlank())
        throw new NullPointerException();
      else request.setStaffAssignee(staffAssignee.getValue());

      request.setRequestStatus(requestStatus.getValue());

      if (floor.getValue() != null && room.getValue() != null) {
        request.setFloorID(floor.getValue());
        request.setRoomID(roomNameToRoomID.get(room.getValue()));
      } else throw new NullPointerException();

      System.out.println(floor.getValue());
      System.out.println(room.getValue());

      System.out.println(request);
      sanSendToDB(request);
      setInfographicsCount("SANITATION_REQ");
      PopUp.submissionConfirmation(
          "Your sanitation service request has been submitted.",
          submitButton.getScene().getWindow());
      resetFields(new ActionEvent());

    } catch (RuntimeException error) {
      error.printStackTrace();
      System.out.println(error.getMessage());
      PopUp.createWarning(
          "Warning : A required value was not filled or was incorrectly formatted",
          drawer.getScene().getWindow());
    }
  }

  private void sanSendToDB(sanitationRequest request) {
    try {
      request.setDeliveryDate(LocalDate.now());
      request.setRequestDate(LocalDate.now());
      system.update(request);
      floor.getSelectionModel().clearSelection();
      room.getSelectionModel().clearSelection();
      sizeGroup.selectToggle(lightSelect);
      biohazardGroup.selectToggle(bioNo);
      urgencyGroup.selectToggle(notUrgent);
      notes.clear();
      staffAssignee.getSelectionModel().clearSelection();
      ;
      requestStatus.getSelectionModel().clearSelection();
      room.setVisible(false);
      // populateSanReqTable();
      // tableList.add(new sanitationRequestModel(request));
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
  public void resetFields(ActionEvent event) {
    floor.getSelectionModel().clearSelection();
    room.getSelectionModel().clearSelection();
    sizeGroup.selectToggle(lightSelect);
    biohazardGroup.selectToggle(bioNo);
    urgencyGroup.selectToggle(notUrgent);
    notes.clear();
    staffAssignee.getSelectionModel().clearSelection();
    ;
    requestStatus.getSelectionModel().clearSelection();
    room.setVisible(false);
  }
}
