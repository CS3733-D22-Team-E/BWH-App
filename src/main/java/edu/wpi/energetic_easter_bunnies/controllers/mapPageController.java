package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.database.*;
import edu.wpi.energetic_easter_bunnies.menuButtons;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;


public class mapPageController implements menuButtons {
  FXMLLoader loader = new FXMLLoader();
  Parent root;
  LocationDAOImpl db;

  @FXML
  TableView<Location> locationTable;

  public mapPageController() throws SQLException {
    db = new LocationDAOImpl();
    List<Location> locationList = populateList();
    // todo : add locations to tableview
  }

  protected List<Location> populateList() {
    return db.getAllLocations();
  }

  @Override
  public void homeButton(ActionEvent event) throws IOException {}

  @Override
  public void sanitationButton(ActionEvent event) throws IOException {}

  @Override
  public void mealDeliveryButton(ActionEvent event) throws IOException {}

  @Override
  public void languageButton(ActionEvent event) throws IOException {}

  @Override
  public void medicalEquipmentButton(ActionEvent event) throws IOException {}

  @Override
  public void medicineDeliveryButton(ActionEvent event) throws IOException {}

  @Override
  public void exitButton(ActionEvent event) throws IOException {}
}
