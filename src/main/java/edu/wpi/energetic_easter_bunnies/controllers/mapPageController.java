package edu.wpi.energetic_easter_bunnies.controllers;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import edu.wpi.energetic_easter_bunnies.Main;
import edu.wpi.energetic_easter_bunnies.database.MedicalEquipment;
import edu.wpi.energetic_easter_bunnies.database.daos.LocationDAOImpl;
import edu.wpi.energetic_easter_bunnies.database.daos.MedicalEquipmentDAOImpl;
import edu.wpi.energetic_easter_bunnies.database.daos.ServiceRequestDAOImpl;
import edu.wpi.energetic_easter_bunnies.entity.serviceRequest;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.*;

public class mapPageController implements Initializable {
  FXMLLoader loader = new FXMLLoader();
  Parent root;
  @FXML MenuBar menuBar;
  LocationDAOImpl db;
  MedicalEquipmentDAOImpl medEq;
  ServiceRequestDAOImpl servReq;
  List<MedicalEquipment> medEqList;
  List<MedicalEquipment> filteredMedEqList;
  List<serviceRequest> servReqList;
  List<serviceRequest> filteredServReqList;

  int zoomIncrement = 50;
  int maxZoom = 1035;
  int minZoom = 435;

  @FXML AnchorPane mapBox;
  @FXML ImageView mapImage;
  @FXML ComboBox floorDropdown;
  @FXML Button mapEditorButton;
  @FXML Button zoomUp;
  @FXML Button zoomDown;
  @FXML JFXSlider zoomSlider;
  @FXML JFXToggleButton filterMode;

  ObservableList<String> floors = FXCollections.observableArrayList("L1", "L2", "1", "2", "3", "4", "5");

  public mapPageController() throws SQLException {
    int i = 3;
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    // Add items to dropdown
    floorDropdown.setItems(floors);
    floorDropdown.setValue("1");

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

    } catch (SQLException e) {
      e.printStackTrace();
    }

    filterMedicalEquipment();
    try {
      filterServiceRequests();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Display the currently selected filterMode
    if (filterMode.isSelected() == false) {
      try {
        displayMedEquipLocations(filteredMedEqList);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      try {
        displayServiceRequestLocations(filteredServReqList);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  // Display location on the map
  //  private void displayFloorLocations(List<Location> locationList) {
  //
  //    double imageX = mapImage.getFitWidth();
  //    double coordinateX = 935;
  //    double scaleFactor = imageX / coordinateX;
  //
  //    mapBox.getChildren().clear();
  //
  //    for (Location l : locationList) {
  //      Circle c = new Circle();
  //      c.setRadius(8);
  //      c.setCenterX(l.getXcoord() * scaleFactor);
  //      c.setCenterY(l.getYcoord() * scaleFactor);
  //      c.getStyleClass().add("locationDot");
  //      mapBox.getChildren().add(c);
  //    }
  //  }

  private void displayMedEquipLocations(List<MedicalEquipment> medEquipList)
      throws FileNotFoundException, SQLException {

    double imageX = mapImage.getFitWidth();
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    // Remove all the icons from the map
    mapBox.getChildren().clear();
    mapBox.getChildren().add(mapImage);

    // Display an icon for each item in the filtered list
    for (MedicalEquipment e : filteredMedEqList) {
      Image image =
          new Image(
              new FileInputStream(
                  "src/main/resources/edu/wpi/energetic_easter_bunnies/view/icons/microscope.png"));
      double prefWidth = (int) image.getWidth() / 2.5;
      double prefHeight = (int) image.getHeight() / 2.5;

      ImageView i = new ImageView(image);
      i.setFitWidth(prefWidth);
      i.setFitHeight(prefHeight);
      i.setX(e.getXCoord() * scaleFactor - (prefWidth / 2));
      i.setY(e.getYCoord() * scaleFactor - (prefHeight / 2));
      mapBox.getChildren().add(i);
    }
  }

  private void displayServiceRequestLocations(List<serviceRequest> servReqList)
      throws FileNotFoundException {

    System.out.println("Display Service Request Locations");

    double imageX = mapImage.getFitWidth();
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    // Remove all the icons from the map
    mapBox.getChildren().clear();
    mapBox.getChildren().add(mapImage);

    // Display an icon for each item in the filtered list
    for (serviceRequest e : filteredServReqList) {
      Image image =
          new Image(
              new FileInputStream(
                  "src/main/resources/edu/wpi/energetic_easter_bunnies/view/icons/medicine.png"));
      double prefWidth = (int) image.getWidth() / 2.5;
      double prefHeight = (int) image.getHeight() / 2.5;

      ImageView i = new ImageView(image);
      i.setFitWidth(prefWidth);
      i.setFitHeight(prefHeight);
      i.setX(e.getxCoord() * scaleFactor - (prefWidth / 2));
      i.setY(e.getyCoord() * scaleFactor - (prefHeight / 2));
      mapBox.getChildren().add(i);
    }
  }

  private void switchMap(String floor) throws FileNotFoundException, SQLException {

    // Clears the medical Equipment Icons
    mapBox.getChildren().clear();
    mapBox.getChildren().add(mapImage);

    switch (floor) {
      case "2":
        mapImage.setImage(
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/energetic_easter_bunnies/view/images/maps/02_thesecondfloor.png")));
        break;
      case "3":
        mapImage.setImage(
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/energetic_easter_bunnies/view/images/maps/03_thethirdfloor.png")));
        break;
      case "4":
        mapImage.setImage(
                new Image(
                        new FileInputStream(
                                "src/main/resources/edu/wpi/energetic_easter_bunnies/view/images/maps/04_thefourthfloor.png")));
        break;
      case "5":
        mapImage.setImage(
                new Image(
                        new FileInputStream(
                                "src/main/resources/edu/wpi/energetic_easter_bunnies/view/images/maps/05_thefifthfloor.png")));
        break;
      case "L1":
        mapImage.setImage(
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/energetic_easter_bunnies/view/images/maps/00_thelowerlevel1.png")));
        break;
      case "L2":
        mapImage.setImage(
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/energetic_easter_bunnies/view/images/maps/00_thelowerlevel2.png")));
        break;
      default:
        mapImage.setImage(
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/energetic_easter_bunnies/view/images/maps/01_thefirstfloor.png")));
        break;
    }

    filterMedicalEquipment();
    filterServiceRequests();

    // Display the currently selected filterMode
    if (filterMode.isSelected() == false) {
      displayMedEquipLocations(filteredMedEqList);
    } else {
      displayServiceRequestLocations(filteredServReqList);
    }
  }

  @FXML
  public void changeZoom(MouseEvent event) throws SQLException, FileNotFoundException {
    mapBox.setPrefHeight(zoomSlider.getValue());
    mapBox.setPrefWidth(zoomSlider.getValue());
    mapImage.setFitHeight(zoomSlider.getValue());
    mapImage.setFitWidth(zoomSlider.getValue());

    // Display the currently selected filterMode
    if (filterMode.isSelected() == false) {
      displayMedEquipLocations(filteredMedEqList);
    } else {
      displayServiceRequestLocations(filteredServReqList);
    }
  }

  @FXML
  public void zoomUp(ActionEvent event) throws SQLException, FileNotFoundException {
    if (mapBox.getPrefHeight() < zoomSlider.getMax()) {
      mapBox.setPrefHeight(mapBox.getPrefHeight() + zoomIncrement);
      mapBox.setPrefWidth(mapBox.getPrefWidth() + zoomIncrement);
      mapImage.setFitHeight(mapImage.getFitHeight() + zoomIncrement);
      mapImage.setFitWidth(mapImage.getFitWidth() + zoomIncrement);

      zoomSlider.setValue(mapImage.getFitWidth() + zoomIncrement);

      // Display the currently selected filterMode
      if (filterMode.isSelected() == false) {
        displayMedEquipLocations(filteredMedEqList);
      } else {
        displayServiceRequestLocations(filteredServReqList);
      }
    }
  }

  @FXML
  public void zoomDown(ActionEvent event) throws SQLException, FileNotFoundException {
    if (mapBox.getPrefHeight() > zoomSlider.getMin()) {
      mapBox.setPrefHeight(mapBox.getPrefHeight() - zoomIncrement);
      mapBox.setPrefWidth(mapBox.getPrefWidth() - zoomIncrement);
      mapImage.setFitHeight(mapImage.getFitHeight() - zoomIncrement);
      mapImage.setFitWidth(mapImage.getFitWidth() - zoomIncrement);

      zoomSlider.setValue(mapImage.getFitWidth() + zoomIncrement);

      // Display the currently selected filterMode
      if (filterMode.isSelected() == false) {
        displayMedEquipLocations(filteredMedEqList);
      } else {
        displayServiceRequestLocations(filteredServReqList);
      }
    }
  }

  @FXML
  public void floorDropdown(ActionEvent event) throws IOException, SQLException {

    switchMap(floorDropdown.getValue().toString());
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
    serviceRequestDAO.getCoords();

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

  @FXML
  public void setFilterMode(ActionEvent event) throws IOException, SQLException {
    // Get direction the filterMode toggle is switched to
    if (filterMode.isSelected() == false) {
      // Clear all icons
      mapBox.getChildren().clear();
      mapBox.getChildren().add(mapImage);

      // Filter and display medical equipment locations
      filterMedicalEquipment();
      displayMedEquipLocations(filteredMedEqList);
    } else {

      // Clear all icons
      mapBox.getChildren().clear();
      mapBox.getChildren().add(mapImage);

      filterServiceRequests();
    }
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
  public void mapEditorButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/mapEditor.fxml");
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
