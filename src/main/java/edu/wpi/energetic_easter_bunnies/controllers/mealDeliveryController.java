package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.entity.mealDeliveryRequest;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class mealDeliveryController extends serviceRequestPageController implements Initializable {
  @FXML ComboBox<String> entreeDropDown;
  @FXML ComboBox<String> beverageDropDown;
  @FXML ComboBox<String> dessertDropDown;
  @FXML TextField roomNumberTxt;
  @FXML DatePicker dateTime;
  @FXML TextField timeTxt;
  @FXML TextField staffAssignee;
  @FXML TextField requestStatus;
  @FXML CheckBox asapCheck;
  @FXML TextArea otherNotesTxt;
  @FXML TableView<mealDeliveryRequest> mealDeliveryTable;

  @FXML TableColumn<mealDeliveryRequest, String> tableEntree;
  @FXML TableColumn<mealDeliveryRequest, String> tableBeverage;
  @FXML TableColumn<mealDeliveryRequest, String> tableDessert;
  @FXML TableColumn<mealDeliveryRequest, String> tableRoomNumber;
  @FXML TableColumn<mealDeliveryRequest, LocalDate> tableDateTime;
  @FXML TableColumn<mealDeliveryRequest, String> tableTime;
  @FXML TableColumn<mealDeliveryRequest, String> tableStaffAssignee;
  @FXML TableColumn<mealDeliveryRequest, String> tableRequestStatus;
  @FXML TableColumn<mealDeliveryRequest, Boolean> tableASAP;
  @FXML TableColumn<mealDeliveryRequest, String> tableOtherNotes;

  // todo:  add DAOImpl
  // work with Jeremy

  mealDeliveryRequest mealDeliveryRequest = new mealDeliveryRequest();

  public mealDeliveryController() {}

  ObservableList<String> meals =
      FXCollections.observableArrayList(
          "Chicken Parmesan", "Turkey Dinner", "Chicken Noodle Soup", "Two Cookies");
  ObservableList<String> beverages =
      FXCollections.observableArrayList("Water", "Coca-Cola", "Pepsi", "Root Beer");
  ObservableList<String> desserts =
      FXCollections.observableArrayList("Cookies", "Vanilla Cake", "Tiramisu", "Chocolate Cake");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    entreeDropDown.setItems(meals);
    beverageDropDown.setItems(beverages);
    dessertDropDown.setItems(desserts);
    // try {
    // mealDeliveryDB = new MealDeliveryServiceRequestDAOImpl();
    // ObservableList<mealDeliveryRequest> mealDeliveryRequest = populateMealDeliveryList();
    tableEntree.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, String>("entree"));
    tableBeverage.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, String>("beverage"));
    tableDessert.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, String>("dessert"));
    tableRoomNumber.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, String>("roomNumber"));
    tableDateTime.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, LocalDate>("dateTime"));
    tableTime.setCellValueFactory(new PropertyValueFactory<mealDeliveryRequest, String>("time"));
    tableStaffAssignee.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, String>("staffAssignee"));
    tableRequestStatus.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, String>("requestStatus"));
    tableASAP.setCellValueFactory(new PropertyValueFactory<mealDeliveryRequest, Boolean>("asap"));
    tableOtherNotes.setCellValueFactory(
        new PropertyValueFactory<mealDeliveryRequest, String>("Other Notes"));

    //      mealDeliveryTable.setItems(mealDeliveryRequest);
    //    } catch (SQLException e) {
    //      e.printStackTrace();
    //    }
  }

  @FXML
  public void submitButton(ActionEvent event) {
    try {
      mealDeliveryRequest.setEntreeType(entreeDropDown.getValue());
      mealDeliveryRequest.setBeverageType(beverageDropDown.getValue());
      mealDeliveryRequest.setDessertType(dessertDropDown.getValue());
      mealDeliveryRequest.setRoomNumber(Integer.parseInt("0" + roomNumberTxt.getText()));
      mealDeliveryRequest.setDeliveryDate(dateTime.getValue());
      mealDeliveryRequest.setDeliveryTime(Integer.parseInt("0" + timeTxt.getText()));
      mealDeliveryRequest.setASAP(asapCheck.isSelected());
      mealDeliveryRequest.setOtherNotes(otherNotesTxt.getText());
      mealDeliveryRequest.setStaffAssignee(staffAssignee.getText());
      mealDeliveryRequest.setRequestStatus(requestStatus.getText());
      if (entreeDropDown.getValue().isEmpty()
          || entreeDropDown.getValue().isBlank()
          || beverageDropDown.getValue().isEmpty()
          || beverageDropDown.getValue().isBlank()
          || dessertDropDown.getValue().isEmpty()
          || dessertDropDown.getValue().isBlank()
          || dateTime.getValue().equals(0)
          || dateTime.getValue().equals(0)
          || staffAssignee.getText().isEmpty()
          || staffAssignee.getText().isBlank()
          || requestStatus.getText().isBlank()
          || requestStatus.getText().isEmpty()) {
        throw new NullPointerException();
      }
    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }

  @FXML
  private void resetButton(ActionEvent event) {
    entreeDropDown.getSelectionModel().clearSelection();
    beverageDropDown.getSelectionModel().clearSelection();
    dessertDropDown.getSelectionModel().clearSelection();
    roomNumberTxt.clear();
    dateTime.getEditor().clear();
    timeTxt.clear();
    asapCheck.setSelected(false);
    otherNotesTxt.clear();
  }
}
