package edu.wpi.cs3733.D22.teamE;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;

public interface pageButtons {

  void statusButton(ActionEvent event) throws IOException;

  void languageButton(ActionEvent event) throws IOException;

  void medicalEquipmentButton(ActionEvent event) throws IOException;

  void medicineDeliveryButton(ActionEvent event) throws IOException;

  void exitButton(ActionEvent event) throws IOException, SQLException;

  void mapButton(ActionEvent event) throws IOException;

  void aboutButton(ActionEvent event) throws IOException;

  void helpButton(ActionEvent event) throws IOException;

  void dashboardButton(ActionEvent event) throws IOException;
}
