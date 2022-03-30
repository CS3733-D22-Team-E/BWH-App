package edu.wpi.energetic_easter_bunnies;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public interface menuButtons {

  @FXML
  void homeButton(ActionEvent event) throws IOException;

  @FXML
  void sanitationButton(ActionEvent event) throws IOException;

  @FXML
  void mealDeliveryButton(ActionEvent event) throws IOException;

  @FXML
  void languageButton(ActionEvent event) throws IOException;

  @FXML
  void medicalEquipmentButton(ActionEvent event) throws IOException;

  @FXML
  void medicineDeliveryButton(ActionEvent event) throws IOException;

  @FXML
  void exitButton(ActionEvent event) throws IOException;

  @FXML
  void locationButton(ActionEvent event) throws IOException;
}
