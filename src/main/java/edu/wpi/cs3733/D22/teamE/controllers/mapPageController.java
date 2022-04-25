package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXSlider;
import edu.wpi.cs3733.D22.teamE.database.Location;
import edu.wpi.cs3733.D22.teamE.database.MedicalEquipment;
import edu.wpi.cs3733.D22.teamE.database.daos.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamE.database.daos.MedicalEquipmentDAOImpl;
import edu.wpi.cs3733.D22.teamE.database.daos.ServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.RequestInterface;
import edu.wpi.cs3733.D22.teamE.entity.serviceRequest;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class mapPageController implements Initializable {
  FXMLLoader loader = new FXMLLoader();
  Parent root;
  @FXML MenuBar menuBar;
  LocationDAOImpl db;
  MedicalEquipmentDAOImpl medEq;
  ServiceRequestDAOImpl servReq;
  List<Location> locations;
  List<Location> filteredLocations;
  List<MedicalEquipment> medEqList;
  List<MedicalEquipment> filteredMedEqList;
  List<RequestInterface> servReqList;
  List<RequestInterface> filteredServReqList;

  int zoomIncrement = 50;
  String viewMode = "Tower Locations";

  @FXML AnchorPane mapBox;
  @FXML StackPane mapOuter;
  @FXML ImageView mapImage;
  @FXML ComboBox floorDropdown;
  @FXML ComboBox locationTypeDropdown;
  @FXML Button mapEditorButton;
  @FXML Button zoomUp;
  @FXML Button zoomDown;
  @FXML JFXSlider zoomSlider;
  @FXML VBox towerLocationsLegend;
  @FXML VBox medEquipLocationsLegend;
  @FXML VBox serviceLocationsLegend;
  @FXML ScrollPane scroll;

  ObservableList<String> floors =
      FXCollections.observableArrayList("L1", "L2", "1", "2", "3", "4", "5");
  ObservableList<String> locationTypes =
      FXCollections.observableArrayList(
          "Tower Locations", "Medical Equipment", "Service Requests", "All");

  public mapPageController() throws SQLException {
    int i = 3;
  }

  @Override // Initialize the map page
  public void initialize(URL url, ResourceBundle rb) {
    // super.initialize(url, rb);

    // Add items to dropdown
    floorDropdown.setItems(floors);
    floorDropdown.setValue("1");
    locationTypeDropdown.setItems(locationTypes);
    locationTypeDropdown.setValue("Medical Equipment");

    /*scroll
        .widthProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              mapOuter.setMaxWidth(newVal.doubleValue());
            });

    scroll
        .heightProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              mapOuter.setMaxHeight(newVal.doubleValue());
            });*/

    // Set the image size to the default slider value
    mapBox.setPrefHeight(zoomSlider.getValue());
    mapBox.setPrefWidth(zoomSlider.getValue());
    mapImage.setFitHeight(zoomSlider.getValue());
    mapImage.setFitWidth(zoomSlider.getValue());

    try {
      db = new LocationDAOImpl();
      medEq = new MedicalEquipmentDAOImpl();
      servReq = new ServiceRequestDAOImpl();
      medEqList = medEq.getAll();
      servReqList = servReq.getAll();
      locations = db.getAll();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Set initial viewMode
    try {
      setViewMode("Medical Equipment");
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  // Switch which floor is displayed
  private void switchMap(String floor) throws FileNotFoundException, SQLException {

    // Clears the medical Equipment Icons
    mapBox.getChildren().clear();
    mapBox.getChildren().add(mapImage);

    switch (floor) {
      case "2":
        mapImage.setImage(
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/images/maps/02_thesecondfloor.png")));
        break;
      case "3":
        mapImage.setImage(
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/images/maps/03_thethirdfloor.png")));
        break;
      case "4":
        mapImage.setImage(
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/images/maps/04_thefourthfloor.png")));
        break;
      case "5":
        mapImage.setImage(
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/images/maps/05_thefifthfloor.png")));
        break;
      case "L1":
        mapImage.setImage(
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/images/maps/00_thelowerlevel1.png")));
        break;
      case "L2":
        mapImage.setImage(
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/images/maps/00_thelowerlevel2.png")));
        break;
      default:
        mapImage.setImage(
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/images/maps/01_thefirstfloor.png")));
        break;
    }

    filterMedicalEquipment();
    filterServiceRequests();

    // Display the currently selected viewMode
    setViewMode(viewMode);
  }

  // Set which legend and set view mode is visible
  private void setViewMode(String view) throws SQLException, FileNotFoundException {

    // Clear all icons
    mapBox.getChildren().clear();
    mapBox.getChildren().add(mapImage);

    // Hide all legends
    towerLocationsLegend.setVisible(false);
    medEquipLocationsLegend.setVisible(false);
    serviceLocationsLegend.setVisible(false);

    if (view.equals("Tower Locations")) {
      // Filter and display the tower locations
      filterTowerLocations();
      displayTowerLocations();
      // Set the legend to visible
      towerLocationsLegend.setVisible(true);
    } else if (view.equals("Medical Equipment")) {
      // Filter and display medical equipment
      filterMedicalEquipment();
      displayMedEquipLocations();

      // Set the legend to visible
      medEquipLocationsLegend.setVisible(true);
    } else if (view.equals("Service Requests")) {
      // Filter and display service requests
      filterServiceRequests();
      displayServiceRequestLocations();

      // Set the legend to visible
      serviceLocationsLegend.setVisible(true);
    } else if (view.equals("All")) {
      // Filter and display all locations
      filterTowerLocations();
      filterServiceRequests();
      filterMedicalEquipment();
      displayTowerLocations();
      displayServiceRequestLocations();
      displayMedEquipLocations();

      towerLocationsLegend.setVisible(true);
    }

    // Set the view mode
    viewMode = view;
  }

  // Display location on the map
  private void displayTowerLocations() throws FileNotFoundException, SQLException {

    double imageX = mapImage.getFitWidth();
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    // Display an icon for each item in the filtered list
    for (Location l : filteredLocations) {

      Image image =
          new Image(
              new FileInputStream(
                  "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/location.png"));

      // Apply the correct icon based on the equipment type
      switch (l.getNodeType()) {
        case "DEPT":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/dept.png"));
          break;
        case "DIRT":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/dirt.png"));
          break;
        case "ELEV":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/elev.png"));
          break;
        case "EXIT":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/exit.png"));
          break;
        case "HALL":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/hall.png"));
          break;
        case "LABS":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/labs.png"));
          break;
        case "PATI":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/pati.png"));
          break;
        case "REST":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/rest.png"));
          break;
        case "BATH":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/rest.png"));
          break;
        case "RETL":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/retl.png"));
          break;
        case "SERV":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/serv.png"));
          break;
        case "STAI":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/stai.png"));
          break;
        case "STOR":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/stor.png"));
          break;
      }

      double prefWidth = ((int) image.getWidth() / 6) * scaleFactor;
      double prefHeight = ((int) image.getWidth() / 6) * scaleFactor;

      ImageView i = new ImageView(image);
      i.setFitWidth(prefWidth);
      i.setFitHeight(prefHeight);
      i.setX(l.getXcoord() * scaleFactor - (prefWidth / 2));
      i.setY(l.getYcoord() * scaleFactor - (prefHeight / 2));
      mapBox.getChildren().add(i);
    }
  }

  // Display medical equipment on the map
  private void displayMedEquipLocations() throws FileNotFoundException, SQLException {

    double imageX = mapImage.getFitWidth();
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    // Display an icon for each item in the filtered list
    for (MedicalEquipment e : filteredMedEqList) {

      Image image =
          new Image(
              new FileInputStream(
                  "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/medicalEquipment/location.png"));

      // Apply the correct icon based on the equipment type
      switch (e.getEquipmentType()) {
        case "RECLINER":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/medicalEquipment/recliner.png"));
          break;
        case "BED":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/medicalEquipment/bed.png"));
          break;
        case "INFUSION PUMP":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/medicalEquipment/infusion.png"));
          break;
        case "XRAY":
          image =
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/medicalEquipment/xray.png"));
          break;
      }
      double prefWidth = ((int) image.getWidth() / 6) * scaleFactor;
      double prefHeight = ((int) image.getWidth() / 6) * scaleFactor;

      ImageView i = new ImageView(image);
      i.setFitWidth(prefWidth);
      i.setFitHeight(prefHeight);
      i.setX(e.getXCoord() * scaleFactor - (prefWidth / 2));
      i.setY(e.getYCoord() * scaleFactor - (prefHeight / 2));
      mapBox.getChildren().add(i);
    }
  }

  // Display service request locations
  private void displayServiceRequestLocations() throws FileNotFoundException {

    System.out.println("Display Service Request Locations");

    double imageX = mapImage.getFitWidth();
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    // Display an icon for each item in the filtered list
    for (RequestInterface e : filteredServReqList) {
      Image image =
          new Image(
              new FileInputStream(
                  "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/serviceRequests/location.png"));

      if (e.getRequestType() == serviceRequest.Type.MED_DELIV_REQ) {
        image =
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/serviceRequests/medicine.png"));
      } else if (e.getRequestType() == serviceRequest.Type.LAB_REQUEST) {
        image =
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/serviceRequests/labs.png"));
      } else if (e.getRequestType() == serviceRequest.Type.MED_EQUIP_REQ) {
        image =
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/serviceRequests/equipment.png"));
      } else if (e.getRequestType() == serviceRequest.Type.MEAL_DELIV_REQ) {
        image =
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/serviceRequests/meal.png"));
      } else if (e.getRequestType() == serviceRequest.Type.SANITATION_REQ) {
        image =
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/serviceRequests/sanitation.png"));
      } else if (e.getRequestType() == serviceRequest.Type.LANG_INTERP_REQ) {
        image =
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/serviceRequests/language.png"));
      }
      double prefWidth = ((int) image.getWidth() / 6) * scaleFactor;
      double prefHeight = ((int) image.getHeight() / 6) * scaleFactor;

      ImageView i = new ImageView(image);
      i.setFitWidth(prefWidth);
      i.setFitHeight(prefHeight);
      i.setX(e.getxCoord() * scaleFactor - (prefWidth / 2));
      i.setY(e.getyCoord() * scaleFactor - (prefHeight / 2));
      mapBox.getChildren().add(i);
    }
  }

  // Filter tower locations by floor
  public void filterTowerLocations() {
    // Get the current floor
    String floor = floorDropdown.getValue().toString();

    // Filter tower locations list to only show the current floor
    filteredLocations =
        locations.stream()
            .filter(
                location -> {
                  if (Objects.equals(location.getFloor(), floor)) {
                    return true;
                  }
                  return false;
                })
            .collect(Collectors.toList());
  }

  // Filter medical equipment by floor
  public void filterMedicalEquipment() {
    // Get the current floor
    String floor = floorDropdown.getValue().toString();

    // Filter medical equipment list to only show the current floor
    filteredMedEqList =
        medEqList.stream()
            .filter(
                medicalEquipment -> {
                  try {
                    if (Objects.equals(medicalEquipment.getFloor(), floor)) {
                      return true;
                    }
                  } catch (SQLException e) {
                    e.printStackTrace();
                  }
                  return false;
                })
            .collect(Collectors.toList());
  }

  // Filter service requests by floor
  public void filterServiceRequests() throws SQLException {
    // Starter code to implement showing all service requests on the map
    String floor = floorDropdown.getValue().toString();
    ServiceRequestDAOImpl serviceRequestDAO = new ServiceRequestDAOImpl();
    // serviceRequestDAO.getCoords();

    // Filter service request list to only show the current floor
    filteredServReqList =
        serviceRequestDAO.getAll().stream()
            .filter(
                serviceRequest -> {
                  if (Objects.equals(serviceRequest.getFloorID(), floor)) {
                    return true;
                  }
                  return false;
                })
            .collect(Collectors.toList());
  }

  @FXML // Handle zoom scroll bar
  public void changeZoom(MouseEvent event) throws SQLException, FileNotFoundException {
    mapBox.setPrefHeight(zoomSlider.getValue());
    mapBox.setPrefWidth(zoomSlider.getValue());
    mapImage.setFitHeight(zoomSlider.getValue());
    mapImage.setFitWidth(zoomSlider.getValue());

    // Display the currently selected viewMode
    setViewMode(viewMode);
  }

  @FXML // Handle zoom + button
  public void zoomUp(ActionEvent event) throws SQLException, FileNotFoundException {
    if (mapBox.getPrefHeight() < zoomSlider.getMax()) {
      mapBox.setPrefHeight(mapBox.getPrefHeight() + zoomIncrement);
      mapBox.setPrefWidth(mapBox.getPrefWidth() + zoomIncrement);
      mapImage.setFitHeight(mapImage.getFitHeight() + zoomIncrement);
      mapImage.setFitWidth(mapImage.getFitWidth() + zoomIncrement);

      zoomSlider.setValue(mapImage.getFitWidth() + zoomIncrement);

      // Display the currently selected viewMode
      setViewMode(viewMode);
    }
  }

  @FXML // Handle zoom - button
  public void zoomDown(ActionEvent event) throws SQLException, FileNotFoundException {
    if (mapBox.getPrefHeight() > zoomSlider.getMin()) {
      mapBox.setPrefHeight(mapBox.getPrefHeight() - zoomIncrement);
      mapBox.setPrefWidth(mapBox.getPrefWidth() - zoomIncrement);
      mapImage.setFitHeight(mapImage.getFitHeight() - zoomIncrement);
      mapImage.setFitWidth(mapImage.getFitWidth() - zoomIncrement);

      zoomSlider.setValue(mapImage.getFitWidth() + zoomIncrement);

      // Display the currently selected viewMode
      setViewMode(viewMode);
    }
  }

  @FXML // Handle floor selection
  public void floorDropdown(ActionEvent event) throws IOException, SQLException {

    switchMap(floorDropdown.getValue().toString());
  }

  @FXML // Handle location type selection
  public void locationTypeDropdown(ActionEvent event) throws SQLException, FileNotFoundException {
    String locationType = locationTypeDropdown.getValue().toString();

    setViewMode(locationType);
  }

  @FXML // Handle navigating to the map editor
  public void mapEditorButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mapBox.getScene().getWindow();

    pageControl.loadPage("mapEditor.fxml", thisStage);
  }
}
