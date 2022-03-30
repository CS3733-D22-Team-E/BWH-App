package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.database.*;
import edu.wpi.energetic_easter_bunnies.entity.locationModel;
import edu.wpi.energetic_easter_bunnies.menuButtons;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableView;

public class mapPageController implements menuButtons, Initializable {
  FXMLLoader loader = new FXMLLoader();
  Parent root;
  LocationDAOImpl db;

  @FXML TableView<locationModel> locationTable;

  public mapPageController() throws SQLException {}

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    try {
      db = new LocationDAOImpl();
      ObservableList<locationModel> locationList = populateList();
      locationTable.setItems(locationList);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  protected ObservableList<locationModel> populateList() {
    List<Location> list = db.getAllLocations();
    ObservableList<locationModel> tableList = FXCollections.observableArrayList();
    for (Location l : list) {
      tableList.add(
          new locationModel(
              l.getNodeID(),
              l.getXcoord(),
              l.getYcoord(),
              l.getFloor(),
              l.getBuilding(),
              l.getNodeType(),
              l.getLongName(),
              l.getShortName()));
    }
    return tableList;
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

  @Override
  public void locationButton(ActionEvent event) throws IOException {}
}
