package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import edu.wpi.cs3733.D22.teamE.database.daos.MedicalEquipmentDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.Equipment;
import edu.wpi.cs3733.D22.teamE.entity.Location;
import edu.wpi.cs3733.D22.teamE.entity.MedicalEquipment;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Toggle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class DashboardTableViewHandler extends DashboardHandler {

  ArrayList<MedicalEquipment> allEquipment;

  public DashboardTableViewHandler(
      MedicalEquipmentDAOImpl medicalEquipmentDAO, DashboardController dashboardController) {
    super(medicalEquipmentDAO, dashboardController);
    this.allEquipment = (ArrayList<MedicalEquipment>) this.medicalEquipmentDAO.getAll();
  }

  public void initialize() {}

  @Override
  public void update() {
    this.allEquipment = (ArrayList<MedicalEquipment>) medicalEquipmentDAO.getAll();
    displayEquipmentTable(
        dashboardController.currentFilter,
        dashboardController.currentFloor,
        dashboardController.equipmentSelectedFilter);
  }

  public void displayEquipmentTable(Toggle filter, String floor, String equipmentType) {
    try {
      ArrayList<MedicalEquipment> filteredEquipment =
          populateEquipmentList(filter, floor, equipmentType, this.allEquipment);
      ObservableList<Equipment> equipmentList =
          FXCollections.observableArrayList(filteredEquipment);
      dashboardController.tableEquipment.setCellValueFactory(
          new PropertyValueFactory<MedicalEquipment, String>("equipmentID"));
      dashboardController.tableLocation.setCellValueFactory(
          new Callback<
              TableColumn.CellDataFeatures<MedicalEquipment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                TableColumn.CellDataFeatures<MedicalEquipment, String> param) {
              MedicalEquipment curEquipment = param.getValue();
              Location curEquipmentLocation =
                  dashboardController.locationDAO.get(curEquipment.getCurrentLocation());
              return new SimpleStringProperty(curEquipmentLocation.getLongName());
            }
          });

      dashboardController.floorEquipmentTable.setItems(equipmentList);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
