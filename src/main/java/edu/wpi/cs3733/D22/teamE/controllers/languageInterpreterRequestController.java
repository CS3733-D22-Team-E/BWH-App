package edu.wpi.cs3733.D22.teamE.controllers;

import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.languageInterpreterRequest;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class languageInterpreterRequestController extends serviceRequestPageController
    implements Initializable {

  @FXML ComboBox<String> languageOptions;
  @FXML CheckBox isUrgent;
  @FXML TextField requestStatus;
  @FXML TextField staffAssignee;
  @FXML DatePicker startDate;
  @FXML DatePicker endDate;
  @FXML TextField notes;

  /*@FXML TableView<languageInterpreterRequest> requestsTable;

  @FXML TableColumn<languageInterpreterRequest, String> tableLanguageType;
  @FXML TableColumn<languageInterpreterRequest, String> tableStaffAssignee;
  @FXML TableColumn<languageInterpreterRequest, Boolean> tableUrgent;
  @FXML TableColumn<languageInterpreterRequest, String> tableLocNodeID;
  @FXML TableColumn<languageInterpreterRequest, String> tableRequestDate;
  @FXML TableColumn<languageInterpreterRequest, LocalDate> tableDeliveryDate;
  @FXML TableColumn<languageInterpreterRequest, LocalDate> tableOtherNotes;*/

  // LanguageRequestDAOImpl languageRequestDAO;
  // ObservableList<languageInterpreterRequest> tableList;
  DAOSystem system;

  public languageInterpreterRequestController() {
    system = DAOSystemSingleton.INSTANCE.getSystem();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      super.initialize(location, resources);
      languageOptions.getItems().addAll("English", "Spanish", "Russian", "Mandarin Chinese");
      populateLanguageRequestTable();

    } catch (Exception e) {
      System.out.println("Language Controller initialization failed!");
      e.printStackTrace();
    }
  }

  private void populateLanguageRequestTable() {
    /*ObservableList<languageInterpreterRequest> languageRequests = populateLanguageRequestList();

    tableLanguageType.setCellValueFactory(new PropertyValueFactory<>("language"));
    tableStaffAssignee.setCellValueFactory(new PropertyValueFactory<>("staffAssignee"));
    tableUrgent.setCellValueFactory(new PropertyValueFactory<>("isUrgent"));
    tableLocNodeID.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<languageInterpreterRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<languageInterpreterRequest, String> param) {
            languageInterpreterRequest curLangRequest = param.getValue();
            return new SimpleStringProperty(roomIDToRoomName.get(curLangRequest.getRoomID()));
          }
        });
    tableRequestDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
    tableDeliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
    tableOtherNotes.setCellValueFactory(new PropertyValueFactory<>("otherNotes"));

    requestsTable.setItems(languageRequests);*/
  }

  /*protected ObservableList<languageInterpreterRequest> populateLanguageRequestList() {
    List<languageInterpreterRequest> requests = system.getAllLanguageRequests();
    tableList = FXCollections.observableArrayList();
    for (languageInterpreterRequest request : requests) {
      tableList.add(request);
    }
    return tableList;
  }*/

  @FXML
  public void submitButton(ActionEvent event) throws SQLException {
    try {
      languageInterpreterRequest request = new languageInterpreterRequest();
      request.setFloorID(floor.getValue());
      request.setRoomID(roomNameToRoomID.get(room.getValue()));
      request.setRequestStatus(requestStatus.getText());
      request.setStaffAssignee(staffAssignee.getText());
      request.setIsUrgent(isUrgent.isSelected());
      request.setOtherNotes(notes.getText());
      request.setLanguage(languageOptions.getValue());
      request.setRequestDate(startDate.getValue());
      request.setDeliveryDate(endDate.getValue());

      langSendToDB(request);

    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUp.createWarning(
          "Warning : A required value was not filled", drawer.getScene().getWindow());
      error.printStackTrace();
    }
  }

  private void langSendToDB(languageInterpreterRequest request) throws SQLException {
    try {
      system.update(request);
      floor.getSelectionModel().clearSelection();
      room.getSelectionModel().clearSelection();
      languageOptions.getSelectionModel().clearSelection();
      isUrgent.setSelected(false);
      requestStatus.clear();
      staffAssignee.clear();
      startDate.getEditor().clear();
      endDate.getEditor().clear();
      notes.clear();
      room.setVisible(false);
      // tableList.add(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void resetButton(ActionEvent event) {
    floor.getSelectionModel().clearSelection();
    room.getSelectionModel().clearSelection();
    languageOptions.getSelectionModel().clearSelection();
    isUrgent.setSelected(false);
    requestStatus.clear();
    staffAssignee.clear();
    startDate.getEditor().clear();
    endDate.getEditor().clear();
    notes.clear();
    room.setVisible(false);
  }
}
