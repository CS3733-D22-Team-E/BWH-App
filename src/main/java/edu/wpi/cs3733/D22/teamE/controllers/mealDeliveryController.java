package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.daos.MealDeliveryRequestDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.mealDeliveryRequest;
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
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * This is the controller class for the meal delivery service request. Inherits from the
 * serviceDeliveryController super class.
 */
public class mealDeliveryController extends serviceRequestPageController implements Initializable {
  @FXML JFXComboBox<String> entreeDropDown;
  @FXML JFXComboBox<String> beverageDropDown;
  @FXML JFXComboBox<String> dessertDropDown;
  @FXML DatePicker dateTime;
  @FXML TextField timeTxt;
  @FXML CheckBox isUrgent;
  @FXML TextField staffAssignee;
  @FXML TextField requestStatus;
  @FXML TextArea otherNotesTxt;
  @FXML TableView<mealDeliveryRequest> mealDeliveryTable;

  @FXML TableColumn<mealDeliveryRequest, String> tableEntree;
  @FXML TableColumn<mealDeliveryRequest, String> tableBeverage;
  @FXML TableColumn<mealDeliveryRequest, String> tableDessert;
  @FXML TableColumn<mealDeliveryRequest, String> tableRoomNumber;
  @FXML TableColumn<mealDeliveryRequest, String> tableFloorNumber;
  @FXML TableColumn<mealDeliveryRequest, LocalDate> tableDateTime;
  @FXML TableColumn<mealDeliveryRequest, LocalDate> tableRequestDate;
  @FXML TableColumn<mealDeliveryRequest, String> tableTime;
  @FXML TableColumn<mealDeliveryRequest, String> tableStaffAssignee;
  @FXML TableColumn<mealDeliveryRequest, String> tableRequestStatus;
  @FXML TableColumn<mealDeliveryRequest, Boolean> tableUrgent;
  @FXML TableColumn<mealDeliveryRequest, String> tableOtherNotes;
  MealDeliveryRequestDAOImpl mealRequestDB;
  ObservableList<mealDeliveryRequest> tableList;

  /** Creating a mealDeliveryRequest Object to store the inputted data. */
  mealDeliveryRequest mealDeliveryRequest = new mealDeliveryRequest();

  /** Constructor */
  public mealDeliveryController() {}

  /** Creating the ObservableLists for the pages' drop down. */
  ObservableList<String> meals =
      FXCollections.observableArrayList(
          "Chicken Parmesan", "Turkey Dinner", "Chicken Noodle Soup", "Two Cookies");

  ObservableList<String> beverages =
      FXCollections.observableArrayList("Water", "Coca-Cola", "Pepsi", "Root Beer");
  ObservableList<String> desserts =
      FXCollections.observableArrayList("Cookies", "Vanilla Cake", "Tiramisu", "Chocolate Cake");

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
    entreeDropDown.setItems(meals);
    beverageDropDown.setItems(beverages);
    dessertDropDown.setItems(desserts);
    try {
      mealRequestDB = new MealDeliveryRequestDAOImpl();
      populateMealRequestTable();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  protected ObservableList<mealDeliveryRequest> populateMealReqList() {
    List<mealDeliveryRequest> list = mealRequestDB.getAll();
    tableList = FXCollections.observableArrayList();
    for (mealDeliveryRequest mR : list) {
      tableList.add(mR);
    }
    return tableList;
  }

  private void populateMealRequestTable() {
    ObservableList<mealDeliveryRequest> mealDeliveryRequest = populateMealReqList();
    tableEntree.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, String>("entreeType"));
    tableBeverage.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, String>("beverageType"));
    tableDessert.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, String>("dessertType"));
    tableRoomNumber.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<mealDeliveryRequest, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<mealDeliveryRequest, String> param) {
            mealDeliveryRequest curMealDeliveryReq = param.getValue();
            return new SimpleStringProperty(roomIDToRoomName.get(curMealDeliveryReq.getRoomID()));
          }
        });
    tableFloorNumber.setCellValueFactory( // throwing npe
        new PropertyValueFactory<mealDeliveryRequest, String>("floorID"));
    tableDateTime.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, LocalDate>("deliveryDate"));
    tableRequestDate.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, LocalDate>("requestDate"));
    tableTime.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, String>("deliveryTime"));
    tableStaffAssignee.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, String>("staffAssignee"));
    tableRequestStatus.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, String>("requestStatus"));
    tableUrgent.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, Boolean>("isUrgent"));
    tableOtherNotes.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, String>("otherNotes"));

    mealDeliveryTable.setItems(mealDeliveryRequest);
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
      mealDeliveryRequest.setEntreeType(entreeDropDown.getValue());
      mealDeliveryRequest.setBeverageType(beverageDropDown.getValue());
      mealDeliveryRequest.setDessertType(dessertDropDown.getValue());
      mealDeliveryRequest.setRoomID(roomNameToRoomID.get(room.getValue()));
      mealDeliveryRequest.setFloorID(floor.getValue());
      mealDeliveryRequest.setRequestDate(LocalDate.now());
      // mealDeliveryRequest.setRoomID(Integer.parseInt("0" + roomNumberTxt.getText()));
      mealDeliveryRequest.setDeliveryDate(dateTime.getValue());
      mealDeliveryRequest.setDeliveryTime(timeTxt.getText());
      mealDeliveryRequest.setUrgent(isUrgent.isSelected());
      mealDeliveryRequest.setOtherNotes(otherNotesTxt.getText());
      mealDeliveryRequest.setStaffAssignee(staffAssignee.getText());
      mealDeliveryRequest.setRequestStatus(requestStatus.getText());
      mealSendToDB(mealDeliveryRequest);

    } catch (NullPointerException | SQLException error) {
      System.out.println("Error : Some Value is NULL");
      PopUp.createWarning("Warning : A required value was not filled", (Node) event.getSource());
    }
  }

  private void mealSendToDB(mealDeliveryRequest meal) throws SQLException {
    mealRequestDB.update(meal);
    tableList.add(meal);
  }

  /**
   * clears all the inputs on the page.
   *
   * @param event Pressing the resetButton
   */
  @FXML
  private void resetButton(ActionEvent event) {
    entreeDropDown.getSelectionModel().clearSelection();
    beverageDropDown.getSelectionModel().clearSelection();
    dessertDropDown.getSelectionModel().clearSelection();
    dateTime.getEditor().clear();
    timeTxt.clear();
    otherNotesTxt.clear();
  }
}