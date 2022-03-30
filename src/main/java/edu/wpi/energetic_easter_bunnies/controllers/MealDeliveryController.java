package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.entity.MealDeliveryRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MealDeliveryController extends ServiceRequestPageController {
  @FXML MenuButton entreeDropDown;
  @FXML MenuButton beverageDropDown;
  @FXML MenuButton dessertDropDown;
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
      mealDeliveryRequest.setEntreeType(entreeDropDown.getText()); // need to figure this out
      mealDeliveryRequest.setBeverageType(beverageDropDown.getText());
      mealDeliveryRequest.setDessertType(dessertDropDown.getText());
      mealDeliveryRequest.setRoomNumber(Integer.parseInt(roomNumberTxt.getText()));
      mealDeliveryRequest.setDeliveryDate(dateTime.getValue());
      mealDeliveryRequest.setDeliveryTime(Integer.parseInt(timeTxt.getText()));
      mealDeliveryRequest.setASAP(asapCheck.isSelected());
      mealDeliveryRequest.setOtherNotes(otherNotesTxt.getText());
    } catch (NullPointerException error) {
      System.out.println(error.getMessage());
      System.out.println(error.getCause());
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }
}
