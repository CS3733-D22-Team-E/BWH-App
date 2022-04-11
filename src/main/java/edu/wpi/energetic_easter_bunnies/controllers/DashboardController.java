package edu.wpi.energetic_easter_bunnies.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.energetic_easter_bunnies.database.Equipment;
import edu.wpi.energetic_easter_bunnies.database.Location;
import edu.wpi.energetic_easter_bunnies.database.MedicalEquipment;
import edu.wpi.energetic_easter_bunnies.database.daos.LocationDAOImpl;
import edu.wpi.energetic_easter_bunnies.database.daos.MedicalEquipmentDAOImpl;
import java.awt.*;
import java.awt.Button;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class DashboardController extends containsSideMenu implements Initializable {

  @FXML JFXComboBox<String> selectFloor;

  @FXML ToggleButton cleanFilter;
  @FXML ToggleButton dirtyFilter;
  @FXML ToggleButton inUseFilter;
  @FXML ToggleButton allFilter;
  ToggleGroup filters;
  Toggle currentToggle;

  @FXML TableView<Equipment> floorEquipmentTable;
  @FXML TableColumn<Equipment, String> tableEquipment;
  @FXML TableColumn<Equipment, String> tableLocation;

  @FXML Button floorViewButton;
  @FXML Button mapEditorButton;

  LocationDAOImpl locationDAO;
  MedicalEquipmentDAOImpl equipmentDAO;
  List<MedicalEquipment> allEquipment;

  public void initialize(URL url, ResourceBundle rb) {
    try {
      equipmentDAO = new MedicalEquipmentDAOImpl();
      locationDAO = new LocationDAOImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    cleanFilter.setToggleGroup(filters);
    cleanFilter.setSelected(true);
    currentToggle = cleanFilter;
    dirtyFilter.setToggleGroup(filters);
    inUseFilter.setToggleGroup(filters);
    allFilter.setToggleGroup(filters);
    filters
            .selectedToggleProperty()
            .addListener(
                    new ChangeListener<Toggle>() {
                      @Override
                      public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                        try {
                          populateFloorEquipmentTable(selectFloor.getValue());
                        } catch (SQLException e) {
                          e.printStackTrace();
                        }
                      }
                    }
            );

    allEquipment = equipmentDAO.getAll();
    selectFloor.getItems().addAll("LL2", "LL1", "1", "2", "3", "4", "5");
    selectFloor
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                  populateFloorEquipmentTable(newValue);
                } catch (SQLException e) {
                  e.printStackTrace();
                }
              }
            });
  }

  private ArrayList<MedicalEquipment> getEquipmentOnFloor(String floor) throws SQLException {
    String floorID;
    switch (floor) {
      case "LL2":
        floorID = "L2";
        break;
      case "LL1":
        floorID = "L1";
        break;
      default:
        floorID = floor;
        break;
    }
    ArrayList<MedicalEquipment> equipmentOnFloor = new ArrayList<>();
    for (MedicalEquipment curEquipment: allEquipment) {
      if (curEquipment.getFloor().equals(floorID)) {
        equipmentOnFloor.add(curEquipment);
      }
    }
    return equipmentOnFloor;
  }

  private ArrayList<MedicalEquipment> filterEquipment(ArrayList<MedicalEquipment> equipmentOnFloor, Toggle filter) {
    String filterString = filter.toString();
    ArrayList<MedicalEquipment> filteredEquipment = new ArrayList<>();

    //TODO: optimize this so that it isn't spaghetti
    if (filter.equals(cleanFilter)) {
      for (MedicalEquipment curEquipment: equipmentOnFloor) {
        if (curEquipment.isClean()) {
          filteredEquipment.add(curEquipment);
        }
      }
    }
    else if (filter.equals(dirtyFilter)) {
      for (MedicalEquipment curEquipment: equipmentOnFloor) {
        if (!curEquipment.isClean()) {
          filteredEquipment.add(curEquipment);
        }
      }
    }
    else if (filter.equals(inUseFilter)) {
      for (MedicalEquipment curEquipment: equipmentOnFloor) {
        if (curEquipment.isInUse()) {
          filteredEquipment.add(curEquipment);
        }
      }
    }
    else if (filter.equals(allFilter)) {
      filteredEquipment = equipmentOnFloor;
    }
    return filteredEquipment;
  }

  private void populateFloorEquipmentTable(String floor) throws SQLException {
    ArrayList<MedicalEquipment> equipmentOnFloor = getEquipmentOnFloor(floor);
    ArrayList<MedicalEquipment> filteredEquipment = filterEquipment(equipmentOnFloor, currentToggle);

    ObservableList<Equipment> equipmentList = FXCollections.observableArrayList(filteredEquipment);
    tableEquipment.setCellValueFactory(
            new PropertyValueFactory<Equipment, String>("equipmentID")
    );
    tableEquipment.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Equipment, String>, ObservableValue<String>>() {
              @Override
              public ObservableValue<String> call(TableColumn.CellDataFeatures<Equipment, String> param) {
                Equipment curEquipment = param.getValue();
                Location curEquipmentLocation = locationDAO.get(curEquipment.getCurrentLocation());
                return new SimpleStringProperty(curEquipmentLocation.getShortName());
              }
            }
    );
  }
}
