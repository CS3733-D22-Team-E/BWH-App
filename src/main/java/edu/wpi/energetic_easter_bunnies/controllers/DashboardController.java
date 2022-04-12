package edu.wpi.energetic_easter_bunnies.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.energetic_easter_bunnies.database.Equipment;
import edu.wpi.energetic_easter_bunnies.database.Location;
import edu.wpi.energetic_easter_bunnies.database.MedicalEquipment;
import edu.wpi.energetic_easter_bunnies.database.daos.LocationDAOImpl;
import edu.wpi.energetic_easter_bunnies.database.daos.MedicalEquipmentDAOImpl;
import edu.wpi.energetic_easter_bunnies.pageControlFacade;
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

public class DashboardController extends containsSideMenu implements Initializable {

  @FXML private VBox baseComponent;

  @FXML JFXComboBox<String> selectFloor;

  @FXML ToggleButton cleanFilter;
  @FXML ToggleButton dirtyFilter;
  @FXML ToggleButton inUseFilter;
  @FXML ToggleButton allFilter;
  ToggleGroup filters;
  Toggle currentToggle;

  @FXML TableView<Equipment> floorEquipmentTable;
  @FXML TableColumn<MedicalEquipment, String> tableEquipment;
  @FXML TableColumn<MedicalEquipment, String> tableLocation;

  @FXML Button floorViewButton;
  @FXML Button mapEditorButton;

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

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    super.initialize(url, rb);
    try {
      equipmentDAO = new MedicalEquipmentDAOImpl();
      locationDAO = new LocationDAOImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    filters = new ToggleGroup();
    cleanFilter.setToggleGroup(filters);
    dirtyFilter.setToggleGroup(filters);
    inUseFilter.setToggleGroup(filters);
    allFilter.setToggleGroup(filters);
    allFilter.setSelected(true);
    currentToggle = allFilter;
    filters
        .selectedToggleProperty()
        .addListener(
            new ChangeListener<Toggle>() {
              @Override
              public void changed(
                  ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                System.out.println("Toggle listener");
                currentToggle = newValue;
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
                  System.out.println("Floor Selection Listener");
                  populateFloorEquipmentTable(newValue);
                } catch (SQLException e) {
                  e.printStackTrace();
                }
              }
            });

    try {
      populateTooltip(ll2FloorTooltip, "LL2");
      populateTooltip(ll1FloorTooltip, "LL1");
      populateTooltip(firstFloorTooltip, "1");
      populateTooltip(secondFloorTooltip, "2");
      populateTooltip(thirdFloorTooltip, "3");
      populateTooltip(fourthFloorTooltip, "4");
      populateTooltip(fifthFloorTooltip, "5");
    } catch (SQLException e) {
      e.printStackTrace();
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
    System.out.println("Floor ID:" + floorID);
    ArrayList<MedicalEquipment> equipmentOnFloor = new ArrayList<>();
    for (MedicalEquipment curEquipment : allEquipment) {
      if (curEquipment.getFloor().equals(floorID)) {
        equipmentOnFloor.add(curEquipment);
      }
    }
    System.out.println("Equipment on floor: " + equipmentOnFloor);
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
        filterEquipment(equipmentOnFloor, currentToggle);
    System.out.println("Filtered equipment: " + filteredEquipment);

    ObservableList<Equipment> equipmentList = FXCollections.observableArrayList(filteredEquipment);
    tableEquipment.setCellValueFactory(
        new PropertyValueFactory<MedicalEquipment, String>("equipmentType"));
    System.out.println("Populating location column");
    tableLocation.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<MedicalEquipment, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<MedicalEquipment, String> param) {
            MedicalEquipment curEquipment = param.getValue();
            Location curEquipmentLocation = locationDAO.get(curEquipment.getCurrentLocation());
            System.out.println("Equipment location: " + curEquipmentLocation);
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
            + "\nEquipment Count: "
            + allEquipment
            + "\nClean Equipment Count: "
            + cleanEquipment
            + "\nDirty Equipment Count: "
            + dirtyEquipment
            + "\nIn Use Equipment Count: "
            + inUseEquipment);
  }

  @FXML
  private void floorViewButton(ActionEvent event) {
    Stage thisStage = (Stage) baseComponent.getScene().getWindow();

    pageControlFacade.loadPage("map.fxml", thisStage);
  }

  @FXML
  private void mapEditorButton(ActionEvent event) {
    Stage thisStage = (Stage) baseComponent.getScene().getWindow();

    pageControlFacade.loadPage("map.fxml", thisStage);
  }
}
