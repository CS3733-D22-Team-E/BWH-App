package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.Main;
import edu.wpi.energetic_easter_bunnies.database.Location;
import edu.wpi.energetic_easter_bunnies.database.daos.LocationDAOImpl;
import edu.wpi.energetic_easter_bunnies.entity.locationModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class mapPageController implements Initializable {
  FXMLLoader loader = new FXMLLoader();
  Parent root;
  @FXML MenuBar menuBar;
  LocationDAOImpl db;

  @FXML TableView<locationModel> locationTable;
  @FXML TableColumn<locationModel, String> nodeID;
  @FXML TableColumn<locationModel, Integer> xcoord;
  @FXML TableColumn<locationModel, Integer> ycoord;
  @FXML TableColumn<locationModel, String> floor;
  @FXML TableColumn<locationModel, String> building;
  @FXML TableColumn<locationModel, String> nodeType;
  @FXML TableColumn<locationModel, String> longName;
  @FXML TableColumn<locationModel, String> shortName;
  @FXML AnchorPane mapBox;
  @FXML ComboBox floorDropdown;
  @FXML ComboBox nodeTypeDropdown;

  ObservableList<String> floors = FXCollections.observableArrayList("1", "2", "3", "L1", "L2");
  ObservableList<String> nodeTypes =
      FXCollections.observableArrayList(
          "DEPT", "EXIT", "HALL", "INFO", "LABS", "REST", "RETL", "SERV", "STAI", "ELEV", "BATH");

  public mapPageController() throws SQLException {}

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    // Add items to dropdown
    floorDropdown.setItems(floors);
    nodeTypeDropdown.setItems(nodeTypes);

    try {
      db = new LocationDAOImpl();
      ObservableList<locationModel> locationList = populateList();
      nodeID.setCellValueFactory(new PropertyValueFactory<locationModel, String>("nodeID"));
      xcoord.setCellValueFactory(new PropertyValueFactory<locationModel, Integer>("xcoord"));
      ycoord.setCellValueFactory(new PropertyValueFactory<locationModel, Integer>("ycoord"));
      floor.setCellValueFactory(new PropertyValueFactory<locationModel, String>("floor"));
      building.setCellValueFactory(new PropertyValueFactory<locationModel, String>("building"));
      nodeType.setCellValueFactory(new PropertyValueFactory<locationModel, String>("nodeType"));
      longName.setCellValueFactory(new PropertyValueFactory<locationModel, String>("longName"));
      shortName.setCellValueFactory(new PropertyValueFactory<locationModel, String>("shortName"));
      locationTable.setItems(locationList);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Populate locations table
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

  // Display location on the map
  private void displayFloorLocations(List<Location> locationList) {

    double imageX = 535;
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    mapBox.getChildren().clear();

    for (Location l : locationList) {
      Circle c = new Circle();
      c.setRadius(8);
      c.setCenterX(l.getXcoord() * scaleFactor);
      c.setCenterY(l.getYcoord() * scaleFactor);
      c.getStyleClass().add("locationDot");
      mapBox.getChildren().add(c);
    }
  }

  private void switchMap(String floor) {

    // Clear and add back mapBox CSS class
    mapBox.getStyleClass().clear();
    mapBox.getStyleClass().add("mapBox");

    switch (floor) {
      case "1":
        mapBox.getStyleClass().add("floor1Map");
        break;
      case "2":
        mapBox.getStyleClass().add("floor2Map");
        break;
      case "3":
        mapBox.getStyleClass().add("floor3Map");
        break;
      case "L1":
        mapBox.getStyleClass().add("floorL1Map");
        break;
      case "L2":
        mapBox.getStyleClass().add("floorL2Map");
        break;
      default:
        mapBox.getStyleClass().add("floorDefaultMap");
    }
  }

  @FXML
  public void mapClick(MouseEvent event) throws IOException {
    Circle c = new Circle();
    c.setRadius(8);
    c.setCenterX(event.getX());
    c.setCenterY(event.getY());
    c.getStyleClass().add("locationDot");
    mapBox.getChildren().add(c);
  }

  @FXML
  public void floorDropdown(ActionEvent event) throws IOException {

    switchMap(floorDropdown.getValue().toString());
  }

  @FXML
  public void nodeTypeDropdown(ActionEvent event) throws IOException {

    String floor = floorDropdown.getValue().toString();
    String nodeType = nodeTypeDropdown.getValue().toString();

    List<Location> locationList = db.getAllLocations();

    List<Location> filteredLocations =
        locationList.stream()
            .filter(
                location -> {
                  if (Objects.equals(location.getFloor(), floor)
                      && Objects.equals(location.getNodeType(), nodeType)) {
                    return true;
                  }
                  return false;
                })
            .collect(Collectors.toList());

    displayFloorLocations(filteredLocations);
  }

  @FXML
  public void mealDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/mealDeliveryPage.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  @FXML
  public void languageButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/languagePage.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  @FXML
  public void medicalEquipmentButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/medicalEquipmentPage.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  @FXML
  public void medicineDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/medicineDelivery.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  public void exitButton(ActionEvent event) throws IOException {
    System.exit(0);
  }

  @FXML
  public void sanitationButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/sanitationPage.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  @FXML
  public void labRequestButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/labRequestPage.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  @FXML
  public void homeButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/defaultPage.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  @FXML
  public void locationButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/map.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }
}
