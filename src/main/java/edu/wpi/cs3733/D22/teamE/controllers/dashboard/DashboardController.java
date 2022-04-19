package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamE.controllers.containsSideMenu;
import edu.wpi.cs3733.D22.teamE.database.Equipment;
import edu.wpi.cs3733.D22.teamE.database.MedicalEquipment;
import edu.wpi.cs3733.D22.teamE.database.daos.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamE.database.daos.MedicalEquipmentDAOImpl;
import edu.wpi.cs3733.D22.teamE.pageControlFacade;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardController extends containsSideMenu implements Initializable {

  @FXML private VBox baseComponent;

  @FXML JFXComboBox<String> selectFloor;
  String currentFloor;
  @FXML JFXComboBox<String> selectEquipmentType;

  @FXML ToggleButton cleanFilter;
  @FXML ToggleButton dirtyFilter;
  @FXML ToggleButton inUseFilter;
  @FXML ToggleButton allFilter;
  ToggleGroup filters;
  Toggle currentFilter;

  @FXML TableView<Equipment> floorEquipmentTable;
  @FXML TableColumn<MedicalEquipment, String> tableEquipment;
  @FXML TableColumn<MedicalEquipment, String> tableLocation;

  @FXML JFXButton mapEditorButton;
  @FXML JFXButton addEquipmentButton;

  @FXML JFXButton ll2Floor;
  @FXML Tooltip ll2FloorTooltip;
  @FXML JFXButton ll1Floor;
  @FXML Tooltip ll1FloorTooltip;
  @FXML JFXButton firstFloor;
  @FXML Tooltip firstFloorTooltip;
  @FXML JFXButton secondFloor;
  @FXML Tooltip secondFloorTooltip;
  @FXML JFXButton thirdFloor;
  @FXML Tooltip thirdFloorTooltip;
  @FXML JFXButton fourthFloor;
  @FXML Tooltip fourthFloorTooltip;
  @FXML JFXButton fifthFloor;
  @FXML Tooltip fifthFloorTooltip;

  LocationDAOImpl locationDAO;
  MedicalEquipmentDAOImpl equipmentDAO;
  List<MedicalEquipment> allEquipment;

  String equipmentSelected;
  String equipmentSelectedFilter;
  String equipmentSelectedTooltipText;

  DashboardTableViewHandler tableViewHandler;
  DashboardTooltip toolTipHandler;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    super.initialize(url, rb);

    try {
      equipmentDAO = new MedicalEquipmentDAOImpl();
      locationDAO = new LocationDAOImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    tableViewHandler = new DashboardTableViewHandler(equipmentDAO, this);
    toolTipHandler = new DashboardTooltip(equipmentDAO, this);

    initializeFilters();
    initializeFloorSelection();
    initializeEquipmentSelection();
    toolTipHandler.populateTooltips();
    tableViewHandler.displayEquipmentTable(currentFilter, currentFloor, equipmentSelectedFilter);
  }

  private void initializeEquipmentSelection() {
    selectEquipmentType
        .getItems()
        .addAll("All Equipment Types", "Beds", "Infusion Pumps", "Recliners", "X-ray Machines");
    selectEquipmentType.getSelectionModel().select(0);
    equipmentSelected = "All Equipment Types";
    equipmentSelectedFilter = "";
    equipmentSelectedTooltipText = "Equipment ";

    selectEquipmentType
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                equipmentSelected = newValue;
                generateEquipmentStrings();
                tableViewHandler.displayEquipmentTable(
                    currentFilter, currentFloor, equipmentSelectedFilter);
                toolTipHandler.populateTooltips();
              }
            });
  }

  private void initializeFloorSelection() {
    selectFloor.getItems().addAll("All Floors", "LL2", "LL1", "1", "2", "3", "4", "5");
    selectFloor.getSelectionModel().select(0);
    currentFloor = selectFloor.getSelectionModel().getSelectedItem();
    selectFloor
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentFloor = newValue;
                tableViewHandler.displayEquipmentTable(
                    currentFilter, currentFloor, equipmentSelectedFilter);
              }
            });
  }

  private void initializeFilters() {
    filters = new ToggleGroup();
    cleanFilter.setToggleGroup(filters);
    dirtyFilter.setToggleGroup(filters);
    inUseFilter.setToggleGroup(filters);
    allFilter.setToggleGroup(filters);
    allFilter.setSelected(true);
    currentFilter = allFilter;

    filters
        .selectedToggleProperty()
        .addListener(
            new ChangeListener<Toggle>() {
              @Override
              public void changed(
                  ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                currentFilter = newValue;
                tableViewHandler.displayEquipmentTable(
                    currentFilter, currentFloor, equipmentSelectedFilter);
              }
            });
  }

  private void generateEquipmentStrings() {
    switch (equipmentSelected) {
      case "All Equipment Types":
        equipmentSelectedFilter = "";
        equipmentSelectedTooltipText = "Equipment";
        break;
      case "Beds":
        equipmentSelectedFilter = "BED";
        equipmentSelectedTooltipText = "Beds";
        break;
      case "Infusion Pumps":
        equipmentSelectedFilter = "INFUSION PUMP";
        equipmentSelectedTooltipText = "Infusion Pumps";
        break;
      case "Recliners":
        equipmentSelectedFilter = "RECLINER";
        equipmentSelectedTooltipText = "Recliners";
        break;
      case "X-ray Machines":
        equipmentSelectedFilter = "XRAY";
        equipmentSelectedTooltipText = "X-ray Machines";
        break;
      default:
        break;
    }
  }

  // TODO: implement the floor view button

  @FXML
  private void mapEditorButton(ActionEvent event) {
    Stage thisStage = (Stage) baseComponent.getScene().getWindow();

    pageControlFacade.loadPage("map.fxml", thisStage);
  }

  //TODO: Remove, for testing purposes only. Or flesh out into a service request-esque user interface
  @FXML
  private void addEquipment(ActionEvent event) {
    MedicalEquipment newEquipment = new MedicalEquipment(
            "BED41",
            null,
            false,
            false,
            "eSTOR001L1",
            "eSTOR001L1",
            "eSTOR001L1",
            "BED",
            489

    );
    equipmentDAO.update(newEquipment);
  }
}
