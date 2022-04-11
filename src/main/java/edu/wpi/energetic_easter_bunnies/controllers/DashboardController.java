package edu.wpi.energetic_easter_bunnies.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.energetic_easter_bunnies.database.Equipment;
import edu.wpi.energetic_easter_bunnies.database.daos.MedicalEquipmentDAOImpl;
import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class DashboardController extends containsSideMenu implements Initializable {

  @FXML JFXComboBox<String> selectFloor;

  @FXML ToggleButton cleanFilter;
  @FXML ToggleButton dirtyFilter;
  @FXML ToggleButton inUseFilter;
  @FXML ToggleButton allFilter;
  ToggleGroup filters;

  @FXML TableView<Equipment> floorEquipmentTable;
  @FXML TableColumn<Equipment, String> tableEquipment;
  @FXML TableColumn<Equipment, String> tableLocation;

  @FXML Button floorViewButton;
  @FXML Button mapEditorButton;

  MedicalEquipmentDAOImpl equipmentDAO;

  public void initialize(URL url, ResourceBundle rb) {
    try {
      equipmentDAO = new MedicalEquipmentDAOImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    cleanFilter.setToggleGroup(filters);
    dirtyFilter.setToggleGroup(filters);
    inUseFilter.setToggleGroup(filters);
    allFilter.setToggleGroup(filters);

    selectFloor.getItems().addAll("LL2", "LL1", "1", "2", "3", "4", "5");
    selectFloor
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                populateFloorEquipmentTable(newValue);
              }
            });
  }

  private void populateFloorEquipmentTable(String floor) {
    switch (floor) {
      case "LL1":
    }
  }
}
