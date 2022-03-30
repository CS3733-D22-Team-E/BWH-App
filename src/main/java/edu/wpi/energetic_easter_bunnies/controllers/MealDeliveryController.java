package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.entity.MealDeliveryRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MealDeliveryController extends ServiceRequestPageController {
  @FXML ComboBox<String> entreeDropDown;
  @FXML ComboBox<String> beverageDropDown;
  @FXML ComboBox<String> dessertDropDown;
  @FXML TextField roomNumberTxt;
  @FXML DatePicker dateTime;
  @FXML TextField timeTxt;
  @FXML CheckBox asapCheck;
  @FXML TextArea otherNotesTxt;

  MealDeliveryRequest mealDeliveryRequest = new MealDeliveryRequest();

  public MealDeliveryController() {}

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
      if (entreeDropDown.getValue().isEmpty()
          || entreeDropDown.getValue().isBlank()
          || beverageDropDown.getValue().isEmpty()
          || beverageDropDown.getValue().isBlank()
          || dessertDropDown.getValue().isEmpty()
          || dessertDropDown.getValue().isBlank()
          || dateTime.getValue().equals(0)
          || dateTime.getValue().equals(0)) {
        throw new NullPointerException();
      }
    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }

  //  @FXML
  //  private void resetButton(ActionEvent event) {
  //    entreeDropDown.clearSelection();
  //    room.getSelectionModel().clearSelection();
  //    equipmentType.getSelectionModel().clearSelection();
  //    equipmentQuantity.getSelectionModel().clearSelection();
  //    deliveryDate.clear();
  //    deliveryTime.clear();
  //    isUrgent.setSelected(false);
  //    notes.clear();
  //  }
}
