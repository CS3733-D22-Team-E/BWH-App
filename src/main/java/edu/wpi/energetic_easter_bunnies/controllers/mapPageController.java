package edu.wpi.energetic_easter_bunnies.controllers;

import com.jfoenix.controls.pannable.PannableScrollPane;
import edu.wpi.energetic_easter_bunnies.Main;
import edu.wpi.energetic_easter_bunnies.database.Location;
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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class mapPageController implements Initializable {
  FXMLLoader loader = new FXMLLoader();
  Parent root;
  @FXML MenuBar menuBar;
  LocationDAOImpl db;
  MedicalEquipmentDAOImpl medEq;
  ServiceRequestDAOImpl servReq;
  List<MedicalEquipment> medEqList;
  List<serviceRequest> servReqList;

  @FXML PannableScrollPane mapBox;
  @FXML ComboBox floorDropdown;
  @FXML Button mapEditorButton;
  @FXML Button showMedicalEquipment;
  @FXML Button showServiceRequests;

  ObservableList<String> floors = FXCollections.observableArrayList("1", "2", "3", "L1", "L2");

  public mapPageController() throws SQLException {
    int i = 3;
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    // Add items to dropdown
    floorDropdown.setItems(floors);
    floorDropdown.setValue("1");

    try {
      db = new LocationDAOImpl();
      medEq = new MedicalEquipmentDAOImpl();
      servReq = new ServiceRequestDAOImpl();
      medEqList = medEq.getAll();
      servReqList = servReq.getAll();

    } catch (SQLException e) {
      e.printStackTrace();
    }
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

  private void displayMedEquipLocations(List<MedicalEquipment> medEquipList)
      throws FileNotFoundException, SQLException {

    double imageX = 535;
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    System.out.println(medEquipList);

    mapBox.getChildren().clear();
    for (MedicalEquipment e : medEquipList) {
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

  private void switchMap(String floor) {

    // Clear and add back mapBox CSS class
    mapBox.getStyleClass().clear();
    mapBox.getStyleClass().add("mapBox");

    // Clears the medical Equipment Icons
    mapBox.getChildren().clear();

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
        mapBox.getStyleClass().add("floor1Map");
    }
  }

  @FXML
  public void floorDropdown(ActionEvent event) throws IOException {

    switchMap(floorDropdown.getValue().toString());
  }

  @FXML
  public void filterMedEquip(ActionEvent event) throws SQLException, FileNotFoundException {

    String floor = floorDropdown.getValue().toString();
    List<MedicalEquipment> medEqList = medEq.getAll();

    List<MedicalEquipment> filteredEquipment =
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
    displayMedEquipLocations(filteredEquipment);
  }

  // Starter code to implement showing all service requests on the map
  @FXML
  public void filterServReq(ActionEvent event) throws SQLException, FileNotFoundException {

    String floor = floorDropdown.getValue().toString();
    // servReqList = servReq.getAll();

    //    List<serviceRequest> filteredRequests =
    //            medEqList.stream()
    //                    .filter(
    //                            medicalEquipment -> {
    //                              try {
    //                                if (Objects.equals(medicalEquipment.getFloor(), floor)) {
    //                                  return true;
    //                                }
    //                              } catch (SQLException e) {
    //                                e.printStackTrace();
    //                              }
    //                              return false;
    //                            })
    //                    .collect(Collectors.toList());
    //    displayMedEquipLocations(filteredEquipment);
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
