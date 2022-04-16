package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.daos.MedicineDeliveryDAOImpl;
import edu.wpi.cs3733.D22.teamE.database.medicineDelivery;
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
 * Controller Class for the Medicine Delivery Service Request. Inherits from the
 * serviceRequestController super class.
 */
public class medicineDeliveryController extends serviceRequestPageController
    implements Initializable {

  @FXML JFXComboBox<String> medicine;
  @FXML CheckBox urgent;
  @FXML TextField amount;
  @FXML JFXComboBox<String> unit;
  @FXML DatePicker date;
  @FXML TextField time;
  @FXML CheckBox mon;
  @FXML CheckBox tues;
  @FXML CheckBox wed;
  @FXML CheckBox thurs;
  @FXML CheckBox fri;
  @FXML CheckBox sat;
  @FXML CheckBox sun;
  @FXML Button resetButton;
  @FXML TableView<medicineDelivery> medicineTable;

  @FXML TableColumn<medicineDelivery, String> tableFloor;
  @FXML TableColumn<medicineDelivery, String> tableRoom;
  @FXML TableColumn<medicineDelivery, String> tableMedicine;
  @FXML TableColumn<medicineDelivery, String> tableQuantity;
  @FXML TableColumn<medicineDelivery, String> tableUnit;
  @FXML TableColumn<medicineDelivery, String> tableDate;
  @FXML TableColumn<medicineDelivery, String> tableTime;
  @FXML TableColumn<medicineDelivery, String> tableReoccurringDays;
  @FXML TableColumn<medicineDelivery, String> tableStaff;
  @FXML TableColumn<medicineDelivery, String> tableProgress;
  @FXML TableColumn<medicineDelivery, String> tableNotes;

  /** Creating a medicineDeliveryRequest object to store the inputted data in. */
  medicineDelivery medicineDeliveryRequest = new medicineDelivery();

  MedicineDeliveryDAOImpl medicineDeliveryDB;

  ObservableList<medicineDelivery> tableList;

  /** Creating the ObservableList of medicines and units for the drop downs. */
  ObservableList<String> medicines =
      FXCollections.observableArrayList(
          "Halothane", "Isoflurane", "Propofol", "midazolam", "ibuprofen");

  ObservableList<String> units = FXCollections.observableArrayList("mg", "g", "mL");

  /** Constructor */
  public medicineDeliveryController() {}

  /**
   * Initializes the drops downs with the respective observable lists and the table columns with the
   * values from current service requests.
   *
   * @param location ??
   * @param resources ??
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
    medicine.setItems(medicines);
    unit.setItems(units);
    try {
      medicineDeliveryDB = new MedicineDeliveryDAOImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    populateMedicineTable();
  }

  /**
   * Takes the inputs from the buttons, drop downs, text fields etc. and stores that data in the
   * mealDeliveryRequest object.
   *
   * @param event Pressing the submitButton
   */
  @FXML
  public void submitButton(ActionEvent event) {
    try {
      medicineDeliveryRequest.setFloorID(floor.getValue());
      medicineDeliveryRequest.setRoomID(roomNameToRoomID.get(room.getValue()));
      medicineDeliveryRequest.setAmount(amount.getText());
      medicineDeliveryRequest.setMedicine(medicine.getValue());
      medicineDeliveryRequest.setUrgent(urgent.isSelected());
      medicineDeliveryRequest.setDeliveryDate(date.getValue());
      medicineDeliveryRequest.setUnit(unit.getValue());
      medicineDeliveryRequest.setDeliveryTime(time.getText());
      medicineDeliveryRequest.setMon(mon.isSelected());
      medicineDeliveryRequest.setTues(tues.isSelected());
      medicineDeliveryRequest.setWed(wed.isSelected());
      medicineDeliveryRequest.setThurs(thurs.isSelected());
      medicineDeliveryRequest.setFri(fri.isSelected());
      medicineDeliveryRequest.setSat(sat.isSelected());
      medicineDeliveryRequest.setSun(sun.isSelected());
      medicineDeliveryRequest.setReocurringDays(medicineDeliveryRequest.getRepeatingDays());
      medicineDeliveryRequest.setOtherNotes(notes.getText());
      medicineDeliveryRequest.setRequestDate(LocalDate.now());
      medicineDeliveryRequest.setStaffAssignee(staffAssignee.getText());
      medicineDeliveryRequest.setRequestStatus(requestStatus.getText());

      medicineSendToDB(medicineDeliveryRequest);

    }
    //    catch (NullPointerException error) {
    //      System.out.println("Error : Some Value is NULL");
    //      PopUpWarning.createWarning(
    //          "Warning : A required value was not filled"
    //              + "\n floor"
    //              + medicineDeliveryRequest.getFloorID()
    //              + "\n room"
    //              + medicineDeliveryRequest.getRoomID()
    //              + "\n medicine"
    //              + medicineDeliveryRequest.getMedicine()
    //              + "\n Delivery date"
    //              + medicineDeliveryRequest.getDeliveryDate()
    //              + "\n Request date"
    //              + medicineDeliveryRequest.getRequestDate()
    //              + "\n time"
    //              + medicineDeliveryRequest.getDeliveryTime()
    //              + "\n requestStatus"
    //              + medicineDeliveryRequest.getRequestStatus()
    //              + "\n staffAssignee"
    //              + medicineDeliveryRequest.getStaffAssignee()
    //              + "\n unit"
    //              + medicineDeliveryRequest.getUnit()
    //              + "\n mon"
    //              + medicineDeliveryRequest.getMon()
    //              + "\n Notes"
    //              + medicineDeliveryRequest.getOtherNotes());
    //    }
    catch (SQLException error) {
      System.out.println("SQL Error ");
      PopUp.createWarning(
          "Warning : A required value was not filled", drawer.getScene().getWindow());
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
    medicine.getSelectionModel().clearSelection();
    amount.clear();
    unit.getSelectionModel().clearSelection();
    date.getEditor().clear();
    time.clear();
    mon.setSelected(false);
    tues.setSelected(false);
    wed.setSelected(false);
    thurs.setSelected(false);
    fri.setSelected(false);
    sat.setSelected(false);
    sun.setSelected(false);
    notes.clear();
    staffAssignee.clear();
    requestStatus.clear();
  }

  private void medicineSendToDB(medicineDelivery medicineDelivery) throws SQLException {
    medicineDeliveryDB.update(medicineDelivery);
    tableList.add(medicineDelivery);
  }

  private void populateMedicineTable() {
    ObservableList<medicineDelivery> medicineRequests = populateMedicineDeliveriesList();
    tableFloor.setCellValueFactory(new PropertyValueFactory<medicineDelivery, String>("floorID"));
    tableRoom.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<medicineDelivery, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<medicineDelivery, String> param) {
            medicineDelivery curMedicineReq = param.getValue();
            return new SimpleStringProperty(roomIDToRoomName.get(curMedicineReq.getRoomID()));
          }
        });
    tableMedicine.setCellValueFactory(
        new PropertyValueFactory<medicineDelivery, String>("medicine"));
    tableQuantity.setCellValueFactory(new PropertyValueFactory<medicineDelivery, String>("amount"));
    tableUnit.setCellValueFactory(new PropertyValueFactory<medicineDelivery, String>("unit"));
    tableDate.setCellValueFactory(
        new PropertyValueFactory<medicineDelivery, String>("deliveryDate"));
    tableTime.setCellValueFactory(
        new PropertyValueFactory<medicineDelivery, String>("deliveryTime"));
    tableReoccurringDays.setCellValueFactory(
        new PropertyValueFactory<medicineDelivery, String>("reocurringDays"));
    tableStaff.setCellValueFactory(
        new PropertyValueFactory<medicineDelivery, String>("staffAssignee"));
    tableProgress.setCellValueFactory(
        new PropertyValueFactory<medicineDelivery, String>("requestStatus"));
    tableNotes.setCellValueFactory(
        new PropertyValueFactory<medicineDelivery, String>("otherNotes"));

    medicineTable.setItems(medicineRequests);
  }

  private ObservableList<medicineDelivery> populateMedicineDeliveriesList() {
    List<medicineDelivery> list = medicineDeliveryDB.getAll();
    tableList = FXCollections.observableArrayList();
    for (medicineDelivery m : list) {
      tableList.add(m);
    }
    return tableList;
  }
}
