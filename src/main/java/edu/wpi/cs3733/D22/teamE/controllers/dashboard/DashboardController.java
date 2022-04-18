package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamE.controllers.containsSideMenu;
import edu.wpi.cs3733.D22.teamE.database.Equipment;
import edu.wpi.cs3733.D22.teamE.database.Location;
import edu.wpi.cs3733.D22.teamE.database.MedicalEquipment;
import edu.wpi.cs3733.D22.teamE.database.daos.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamE.database.daos.MedicalEquipmentDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.DashboardPage;
import edu.wpi.cs3733.D22.teamE.pageControlFacade;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class DashboardController extends containsSideMenu implements Initializable {

  @FXML private VBox baseComponent;

  @FXML JFXComboBox<String> selectFloor;
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
  DashboardPage dashboardEntity;

  String equipmentSelected;
  String equipmentSelectedFilter;
  String equipmentSelectedTooltipText;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    super.initialize(url, rb);
    dashboardEntity = new DashboardPage("All", "All", null);
    try {
      equipmentDAO = new MedicalEquipmentDAOImpl();
      locationDAO = new LocationDAOImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    DashboardFilters filtersHandler = new DashboardFilters();
    filtersHandler.initialize(url, rb);
    filters
        .selectedToggleProperty()
        .addListener(
            new ChangeListener<Toggle>() {
              @Override
              public void changed(
                  ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                currentFilter = newValue;
                if (selectFloor.getValue() != null) {
                  try {
                    populateFloorEquipmentTable(selectFloor.getValue());
                  } catch (SQLException e) {
                    e.printStackTrace();
                  }
                }
              }
            });

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

    selectEquipmentType
        .getItems()
        .addAll("All Equipment Types", "Beds", "Infusion Pumps", "Recliners", "X-ray Machines");
    selectEquipmentType.getSelectionModel().select(0);
    equipmentSelected = "All Equipment Types";
    equipmentSelectedFilter = "";
    equipmentSelectedTooltipText = "Equipment ";
    try {
      populateTooltips();
    } catch (SQLException e) {
      e.printStackTrace();
    }
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
                try {
                  populateFloorEquipmentTable(selectFloor.getValue());
                  populateTooltips();
                } catch (SQLException e) {
                  e.printStackTrace();
                }
              }
            });
  }

  private void populateTooltips() throws SQLException {
    populateTooltip(ll2FloorTooltip, "LL2");
    populateTooltip(ll1FloorTooltip, "LL1");
    populateTooltip(firstFloorTooltip, "1");
    populateTooltip(secondFloorTooltip, "2");
    populateTooltip(thirdFloorTooltip, "3");
    populateTooltip(fourthFloorTooltip, "4");
    populateTooltip(fifthFloorTooltip, "5");
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
    for (MedicalEquipment curEquipment : allEquipment) {
      if (curEquipment.getFloor().equals(floorID)) {
        if (equipmentSelectedFilter.equals("")
            || curEquipment.getEquipmentType().equals(equipmentSelectedFilter)) {
          equipmentOnFloor.add(curEquipment);
        }
      }
    }
    return equipmentOnFloor;
  }

  private ArrayList<MedicalEquipment> filterEquipment(
      ArrayList<MedicalEquipment> equipmentOnFloor, Toggle filter) {
    ArrayList<MedicalEquipment> filteredEquipment = new ArrayList<>();

    // TODO: optimize this so that it isn't spaghetti
    if (filter.equals(cleanFilter)) {
      for (MedicalEquipment curEquipment : equipmentOnFloor) {
        if (curEquipment.isClean()) {
          filteredEquipment.add(curEquipment);
        }
      }
    } else if (filter.equals(dirtyFilter)) {
      for (MedicalEquipment curEquipment : equipmentOnFloor) {
        if (!curEquipment.isClean()) {
          filteredEquipment.add(curEquipment);
        }
      }
    } else if (filter.equals(inUseFilter)) {
      for (MedicalEquipment curEquipment : equipmentOnFloor) {
        if (curEquipment.isInUse()) {
          filteredEquipment.add(curEquipment);
        }
      }
    } else if (filter.equals(allFilter)) {
      filteredEquipment = equipmentOnFloor;
    }
    return filteredEquipment;
  }

  private void populateFloorEquipmentTable(String floor) throws SQLException {
    ArrayList<MedicalEquipment> equipmentOnFloor = getEquipmentOnFloor(floor);
    ArrayList<MedicalEquipment> filteredEquipment =
        filterEquipment(equipmentOnFloor, currentFilter);

    ObservableList<Equipment> equipmentList = FXCollections.observableArrayList(filteredEquipment);
    tableEquipment.setCellValueFactory(
        new PropertyValueFactory<MedicalEquipment, String>("equipmentID"));
    tableLocation.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<MedicalEquipment, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<MedicalEquipment, String> param) {
            MedicalEquipment curEquipment = param.getValue();
            Location curEquipmentLocation = locationDAO.get(curEquipment.getCurrentLocation());
            return new SimpleStringProperty(curEquipmentLocation.getLongName());
          }
        });

    floorEquipmentTable.setItems(equipmentList);
  }

  private void populateTooltip(Tooltip tooltip, String floor) throws SQLException {
    ArrayList<MedicalEquipment> equipmentOnFloor = getEquipmentOnFloor(floor);
    Integer allEquipment = equipmentOnFloor.size();
    Integer cleanEquipment = filterEquipment(equipmentOnFloor, cleanFilter).size();
    Integer dirtyEquipment = filterEquipment(equipmentOnFloor, dirtyFilter).size();
    Integer inUseEquipment = filterEquipment(equipmentOnFloor, inUseFilter).size();

    tooltip.setText(
        "Floor: "
            + floor
            + "\n"
            + "Total "
            + equipmentSelectedTooltipText
            + " Count: "
            + allEquipment
            + "\nClean "
            + equipmentSelectedTooltipText
            + " Count: "
            + cleanEquipment
            + "\nDirty "
            + equipmentSelectedTooltipText
            + " Count: "
            + dirtyEquipment
            + "\nIn Use "
            + equipmentSelectedTooltipText
            + " Count: "
            + inUseEquipment);

    tooltip.setShowDelay(Duration.seconds(.2));
  }

  // TODO: implement the floor view button

  @FXML
  private void mapEditorButton(ActionEvent event) {
    Stage thisStage = (Stage) baseComponent.getScene().getWindow();

    pageControlFacade.loadPage("map.fxml", thisStage);
  }
}
