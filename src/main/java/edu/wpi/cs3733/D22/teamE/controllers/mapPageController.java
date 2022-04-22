package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import edu.wpi.cs3733.D22.teamE.database.Location;
import edu.wpi.cs3733.D22.teamE.database.MedicalEquipment;
import edu.wpi.cs3733.D22.teamE.database.daos.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamE.database.daos.MedicalEquipmentDAOImpl;
import edu.wpi.cs3733.D22.teamE.database.daos.ServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.serviceRequest;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class mapPageController extends containsSideMenu implements Initializable {
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
  List<serviceRequest> servReqList;
  List<serviceRequest> filteredServReqList;

  double zoomIncrement = 0.1;
  String viewMode = "Tower Locations";
  String editMode = "false";
  int mouseX;
  int mouseY;
  double[] selectedLoc;

  @FXML StackPane mapBox;
  @FXML ImageView mapImage;
  Pane floorLocationsPane;
  Pane floorEquipmentPane;
  Pane floorServiceRequestPane;
  @FXML ComboBox floorDropdown;
  @FXML ComboBox locationTypeDropdown;
  @FXML Button zoomUp;
  @FXML Button zoomDown;
  @FXML JFXSlider zoomSlider;
  @FXML AnchorPane towerLocationsLegend;
  @FXML AnchorPane medicalEquipmentLegend;
  @FXML AnchorPane serviceRequestLegend;

  // Map editor components
  @FXML Button mapEditorButton;
  @FXML Pane editorModeContainer;
  @FXML Pane addLocationPane;
  @FXML Pane updateLocationPane;
  @FXML Pane deleteLocationPane;
  // Update location components
  @FXML Button changePosition;
  @FXML Button smallUpdateLocation;
  // Add tower location components
  @FXML TextField addShortName;
  @FXML TextField addLongName;
  @FXML JFXComboBox addNodeType;
  @FXML Button smallAddLocation;
  // Delete location components
  @FXML Text deleteText;
  @FXML Button smallDeleteLocation;

  ObservableList<String> floors =
      FXCollections.observableArrayList("L1", "L2", "1", "2", "3", "4", "5");
  ObservableList<String> nodes =
      FXCollections.observableArrayList(
          "PATI", "STOR", "DIRT", "HALL", "ELEV", "REST", "STAI", "DEPT", "LABS", "INFO", "CONF",
          "EXIT", "RETL", "SERV");
  ObservableList<String> locationTypes =
      FXCollections.observableArrayList(
          "Tower Locations", "Medical Equipment", "Service Requests", "All");

  public mapPageController() throws SQLException {
    int i = 3;
  }

  @Override // Initialize the map page
  public void initialize(URL url, ResourceBundle rb) {
    super.initialize(url, rb);

    // Add items to dropdown
    addNodeType.setItems(nodes);
    floorDropdown.setItems(floors);
    floorDropdown.setValue("1");
    locationTypeDropdown.setItems(locationTypes);
    locationTypeDropdown.setValue("Medical Equipment");

    // Set the image size to the default slider value
    mapBox.setScaleX(zoomSlider.getValue());
    mapBox.setScaleY(zoomSlider.getValue());
    mapImage.setScaleX(zoomSlider.getValue());
    mapImage.setScaleY(zoomSlider.getValue());

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

  // Re-fetch data from database after location update
  private void fetchDB() throws SQLException, FileNotFoundException {
    db = new LocationDAOImpl();
    medEq = new MedicalEquipmentDAOImpl();
    servReq = new ServiceRequestDAOImpl();
    medEqList = medEq.getAll();
    servReqList = servReq.getAll();
    locations = db.getAll();

    setViewMode(viewMode);
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
    medicalEquipmentLegend.setVisible(false);
    serviceRequestLegend.setVisible(false);

    // Enable edit button
    mapEditorButton.setDisable(false);

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
      medicalEquipmentLegend.setVisible(true);
    } else if (view.equals("Service Requests")) {
      // Filter and display service requests
      filterServiceRequests();
      displayServiceRequestLocations();

      // Set the legend to visible
      serviceRequestLegend.setVisible(true);
    } else if (view.equals("All")) {
      // Filter and display all locations
      filterTowerLocations();
      filterServiceRequests();
      filterMedicalEquipment();
      displayTowerLocations();
      displayServiceRequestLocations();
      displayMedEquipLocations();

      towerLocationsLegend.setVisible(true);

      // Disable map editor button and hide map editor functions
      mapEditorButton.setDisable(true);
      setEditMode("false");
    }

    // Set the view mode
    viewMode = view;
  }

  // Set which map editor is visible
  private void setEditMode(String view) {

    // Hide all editors
    editorModeContainer.setVisible(false);
    addLocationPane.setVisible(false);
    updateLocationPane.setVisible(false);
    deleteLocationPane.setVisible(false);

    // Reset selected location
    selectedLoc = null;

    if (view.equals("add")) {
      // Clear all icons
      mapBox.getChildren().clear();
      mapBox.getChildren().add(mapImage);

      // Set the add editor to visible
      addLocationPane.setVisible(true);
      editorModeContainer.setVisible(true);
    } else if (view.equals("update") || view.equals("changePosition")) {
      // Set the update editor to visible
      updateLocationPane.setVisible(true);
      editorModeContainer.setVisible(true);
    } else if (view.equals("delete")) {
      // Set the delete editor to visible
      deleteLocationPane.setVisible(true);
      editorModeContainer.setVisible(true);
    }

    // Set the view mode
    editMode = view;
  }

  // Display location on the map
  private void displayTowerLocations() throws FileNotFoundException, SQLException {

    floorLocationsPane = new Pane();

    double imageX = mapImage.getFitWidth();
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    // Display an icon for each item in the filtered list
    for (Location l : filteredLocations) {

      double prefWidth = ((int) nodeToIcon(l.getNodeType()).getWidth() / 6) * scaleFactor;
      double prefHeight = ((int) nodeToIcon(l.getNodeType()).getWidth() / 6) * scaleFactor;

      ImageView i = new ImageView(nodeToIcon(l.getNodeType()));
      i.setFitWidth(prefWidth);
      i.setFitHeight(prefHeight);
      i.setX(l.getXcoord() * scaleFactor - (prefWidth / 2));
      i.setY(l.getYcoord() * scaleFactor - (prefHeight / 2));

      // Highlight the selected location
      if (selectedLoc != null) {
        if (selectedLoc[0] == l.getXcoord() && selectedLoc[1] == l.getYcoord()) {
          i.getStyleClass().add("selectedLocationDot");
        }
      }
      floorLocationsPane.getChildren().add(i);
    }
    mapBox.getChildren().add(floorLocationsPane);
  }

  // Display medical equipment on the map
  private void displayMedEquipLocations() throws FileNotFoundException, SQLException {

    floorEquipmentPane = new Pane();

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

      // Highlight the selected location
      if (selectedLoc != null) {
        if (e.getXCoord() == selectedLoc[0] && e.getYCoord() == selectedLoc[1]) {
          i.getStyleClass().add("selectedLocationDot");
        }
      }

      floorEquipmentPane.getChildren().add(i);
    }
    mapBox.getChildren().add(floorEquipmentPane);
  }

  // Display service request locations
  private void displayServiceRequestLocations() throws FileNotFoundException {

    floorServiceRequestPane = new Pane();

    double imageX = mapImage.getFitWidth();
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    // Display an icon for each item in the filtered list
    for (serviceRequest e : filteredServReqList) {
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

      // Highlight the selected location
      if (selectedLoc != null) {
        if (e.getxCoord() == selectedLoc[0] && e.getyCoord() == selectedLoc[1]) {
          i.getStyleClass().add("selectedLocationDot");
        }
      }

      floorEquipmentPane.getChildren().add(i);
    }
    mapBox.getChildren().add(floorEquipmentPane);
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

  // Convert tower locations nodeType to an icon
  private Image nodeToIcon(String nodeType) throws FileNotFoundException {

    Image image =
        new Image(
            new FileInputStream(
                "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/location.png"));

    // If the node type is null, return the default location icon
    if (nodeType == null) {
      return image;
    }

    // Apply the correct image based on the location type
    switch (nodeType) {
      case "ELEV":
        image =
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/elev.png"));
        break;
      case "STAI":
        image =
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/stai.png"));
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
      case "STOR":
        image =
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/stor.png"));
        break;
      case "DEPT":
        image =
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/dept.png"));
        break;
      case "HALL":
        image =
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/hall.png"));
        break;
      case "DIRT":
        image =
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/dirt.png"));
        break;
      case "EXIT":
        image =
            new Image(
                new FileInputStream(
                    "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/towerLocations/exit.png"));
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
      default:
        break;
    }

    return image;
  }

  @FXML // Handle zoom scroll bar
  public void changeZoom(MouseEvent event) throws SQLException, FileNotFoundException {
    zoomHandler(zoomSlider.getValue());
  }

  private void zoomHandler(double zoomValue) {
    System.out.println("Changing zoom");

    mapBox.setScaleX(zoomValue);
    mapBox.setScaleY(zoomValue);

    for (Node child : mapBox.getChildren()) {
      child.setScaleX(zoomValue);
      child.setScaleY(zoomValue);
    }
  }

  @FXML // Handle zoom + button
  public void zoomUp(ActionEvent event) throws SQLException, FileNotFoundException {
    double incrementedZoom = zoomSlider.getValue() + zoomIncrement;

    if (incrementedZoom <= zoomSlider.getMax()) {
      zoomHandler(incrementedZoom);
      zoomSlider.setValue(incrementedZoom);
    }
  }

  @FXML // Handle zoom - button
  public void zoomDown(ActionEvent event) {
    double decrementedZoom = zoomSlider.getValue() - zoomIncrement;

    if (decrementedZoom >= zoomSlider.getMin()) {
      zoomHandler(decrementedZoom);
      zoomSlider.setValue(decrementedZoom);
    }
  }

  @FXML
  public void mouseScrollZoom(ScrollEvent event) {
    double deltaZoom = zoomSlider.getValue() + event.getDeltaY();

    if (event.getDeltaY() > 0 && deltaZoom <= zoomSlider.getMax()) {
      zoomHandler(deltaZoom);
    } else if (event.getDeltaY() < 0 && deltaZoom >= zoomSlider.getMin()) {
      zoomHandler(deltaZoom);
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
    // Set editor mode to true
    if (editMode != "false") {
      // Set editor mode to false
      setEditMode("false");

      // Hide map edit mode container
      editorModeContainer.setVisible(false);

      // Change the button text
      mapEditorButton.setText("Exit Map Editor");
    } else {
      // Set editor mode to update
      setEditMode("update");

      // Show map edit mode container
      editorModeContainer.setVisible(true);

      // Change the button text
      mapEditorButton.setText("Map Editor");
    }
  }

  @FXML // Change edit mode to add
  public void addLocation(ActionEvent event) {
    setEditMode("add");
  }

  @FXML // Change edit mode to update
  public void updateLocation(ActionEvent event) {
    setEditMode("update");
  }

  @FXML // Change edit mode to delete
  public void deleteLocation(ActionEvent event) {
    setEditMode("delete");
  }

  @FXML // Handle adding a new tower location
  public void smallAddLocationButton(ActionEvent event) throws SQLException, FileNotFoundException {

    double imageX = mapImage.getFitWidth();
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    int nodeTypeCount = filteredLocations.size() + 1;

    String nodeID =
        "e"
            + addNodeType.getValue()
            + ("000" + nodeTypeCount).substring(Integer.toString(nodeTypeCount).length())
            + ("00" + floorDropdown.getValue())
                .substring(floorDropdown.getValue().toString().length());
    int numID = 0;
    Location location =
        new Location(
            nodeID,
            Math.toIntExact(Math.round(mouseX / scaleFactor)),
            Math.toIntExact(Math.round(mouseY / scaleFactor)),
            floorDropdown.getValue().toString(),
            "Tower",
            addNodeType.getValue().toString(),
            addLongName.getText().toString(),
            addShortName.getText().toString(),
            numID);
    db.update(location);

    // Fetch and switch and to update pane
    fetchDB();
    setEditMode("update");
  }

  @FXML // Handle updating a location
  public void smallUpdateLocationButton(ActionEvent event)
      throws SQLException, FileNotFoundException {

    double imageX = mapImage.getFitWidth();
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    double locationPadding = 22 * scaleFactor;

    // Check if the location has been moved
    if ((selectedLoc[0] >= (mouseX - locationPadding) / scaleFactor
            && selectedLoc[0] <= (mouseX + locationPadding) / scaleFactor)
        && (selectedLoc[1] >= (mouseY - locationPadding) / scaleFactor
            && selectedLoc[1] <= (mouseY + locationPadding) / scaleFactor)) {
      // Location has not been moved
    } else {

      // Get a whole number from the scaled up mouseX and mouseY
      int newX = Math.toIntExact(Math.round(mouseX / scaleFactor));
      int newY = Math.toIntExact(Math.round(mouseY / scaleFactor));

      // Update the location in the db with the new coordinates
      if (viewMode == "Tower Locations") {
        // Get location by coordinates
        for (int i = 0; i < filteredLocations.size(); i++) {
          Location location = filteredLocations.get(i);
          if (location.getXcoord() == selectedLoc[0] && location.getYcoord() == selectedLoc[1]) {
            db.updateCoord(location, newX, newY);
          }
        }
      } else if (viewMode == "Service Requests") {
        // Update service request location
      } else if (viewMode == "Medical Equipment") {
        // Update medical equipment location
      }

      // Fetch the updates from the db
      fetchDB();
    }
  }

  @FXML // Handle deleting a location
  public void smallDeleteLocationButton(ActionEvent event)
      throws SQLException, FileNotFoundException {

    // Reset delete text
    deleteText.setText("Click on the location you would like to delete");
    // Remove the currently selected location from the db

    // Get location by coordinates
    for (int i = 0; i < filteredLocations.size(); i++) {
      Location location = filteredLocations.get(i);
      if (location.getXcoord() == selectedLoc[0] && location.getYcoord() == selectedLoc[1]) {
        db.delete(location);
      }
    }
    fetchDB();
  }

  @FXML // Handle clicking on the map
  public void mapClick(MouseEvent event) throws IOException, SQLException {

    // Clear all icons
    mapBox.getChildren().clear();
    mapBox.getChildren().add(mapImage);

    double imageX = mapImage.getFitWidth();
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    // Set mouseX and mouseY to the current location of the cursor
    mouseX = (int) event.getX();
    mouseY = (int) event.getY();

    if (Objects.equals(editMode, "delete") && selectedLoc != null) {
      // Enable the smallDeleteButton
      smallDeleteLocation.setDisable(false);
    } else if (Objects.equals(editMode, "update") && selectedLoc != null) {

      // Enable the smallUpdateButton and changePositionButton
      changePosition.setDisable(false);
      smallUpdateLocation.setDisable(false);
    } else if (Objects.equals(editMode, "add")) {
      // Clear all icons
      mapBox.getChildren().clear();
      mapBox.getChildren().add(mapImage);

      // Place new dot
      Circle c = new Circle();
      c.setRadius(12);
      c.setCenterX(event.getX());
      c.setCenterY(event.getY());
      // Set the location dot image
      c.setFill(new ImagePattern(nodeToIcon(addNodeType.getValue().toString())));

      c.getStyleClass().add("selectedLocationDot");
      mapBox.getChildren().add(c);

      // Enable smallAddButton
      smallAddLocation.setDisable(false);
    }

    // Get a selected location based on where the user clicked
    if (Objects.equals(editMode, "delete") || Objects.equals(editMode, "update")) {

      double locationPadding = 22 * scaleFactor;

      System.out.println(viewMode);

      if (viewMode == "Tower Locations") {
        for (Location location : locations) {

          // If the user has selected a dot on the map
          if ((location.getXcoord() * scaleFactor >= mouseX - locationPadding
                  && location.getXcoord() * scaleFactor <= mouseX + locationPadding)
              && (location.getYcoord() * scaleFactor >= mouseY - locationPadding
                  && location.getYcoord() * scaleFactor <= mouseY + locationPadding)) {

            // Update the current selected location variable
            selectedLoc = new double[] {location.getXcoord(), location.getYcoord()};
          }
        }
        displayTowerLocations();
      } else if (viewMode == "Medical Equipment") {
        for (MedicalEquipment medEquip : filteredMedEqList) {
          // If the user has selected a dot on the map
          if ((medEquip.getXCoord() * scaleFactor >= mouseX - locationPadding
                  && medEquip.getXCoord() * scaleFactor <= mouseX + locationPadding)
              && (medEquip.getYCoord() * scaleFactor >= mouseY - locationPadding
                  && medEquip.getYCoord() * scaleFactor <= mouseY + locationPadding)) {

            // Update the current selected location variable
            selectedLoc = new double[] {medEquip.getXCoord(), medEquip.getYCoord()};
          }
        }
        displayMedEquipLocations();
      } else if (viewMode == "Service Requests") {
        for (serviceRequest servReq : filteredServReqList) {
          // If the user has selected a dot on the map
          if ((servReq.getxCoord() * scaleFactor >= mouseX - locationPadding
                  && servReq.getxCoord() * scaleFactor <= mouseX + locationPadding)
              && (servReq.getyCoord() * scaleFactor >= mouseY - locationPadding
                  && servReq.getyCoord() * scaleFactor <= mouseY + locationPadding)) {

            // Update the current selected location variable
            selectedLoc = new double[] {servReq.getxCoord(), servReq.getyCoord()};
          }
        }
        displayServiceRequestLocations();
      }
    }

    // Handle moving the location dot on the map
    if (Objects.equals(editMode, "changePosition")) {

      // Update change position button text
      changePosition.setText("Change Position");

      // Re-render location dots
      if (viewMode == "Tower Locations") {
        filterTowerLocations();
        // Filter out the old location dot
        filteredLocations =
            filteredLocations.stream()
                .filter(
                    location -> {
                      if (selectedLoc[0] == location.getXcoord()
                          && selectedLoc[1] == location.getYcoord()) {
                        return false;
                      }
                      return true;
                    })
                .collect(Collectors.toList());

        System.out.println(filteredLocations.size());
        // Display Tower Locations
        displayTowerLocations();
      } else if (viewMode == "Medical Equipment") {
        // Filter out the old location dot
        filteredMedEqList =
            filteredMedEqList.stream()
                .filter(
                    location -> {
                      try {
                        if (selectedLoc[0] == location.getXCoord()
                            && selectedLoc[1] == location.getYCoord()) {
                          return false;
                        }
                      } catch (SQLException e) {
                        e.printStackTrace();
                      }
                      return true;
                    })
                .collect(Collectors.toList());

        // Display Medical Equipment
        displayMedEquipLocations();
      } else if (viewMode == "Service Requests") {
        // Filter out the old location dot
        filteredServReqList =
            filteredServReqList.stream()
                .filter(
                    location -> {
                      if (selectedLoc[0] == location.getxCoord()
                          && selectedLoc[1] == location.getyCoord()) {
                        return false;
                      }
                      return true;
                    })
                .collect(Collectors.toList());

        // Display Service Requests
        displayServiceRequestLocations();
      }

      // Add circle to map
      Circle c = new Circle();
      c.setRadius(12);
      c.setCenterX(mouseX);
      c.setCenterY(mouseY);
      // Set the location dot image
      c.setFill(
          new ImagePattern(
              new Image(
                  new FileInputStream(
                      "src/main/resources/edu/wpi/cs3733/D22/teamE/view/icons/" + viewMode
                              == "Service Requests"
                          ? "serviceRequests"
                          : viewMode == "Medical Equipment"
                              ? "medicalEquipment"
                              : "towerLocations" + "/location.png"))));
      c.getStyleClass().add("selectedLocationDot");

      // Change edit mode back to update
      setEditMode("update");
    }
  }

  @FXML
  public void changePositionButton(ActionEvent event) throws IOException {

    if (changePosition.getText().equals("Cancel")) {
      changePosition.setText("Change Position");
      setEditMode("update");
    } else {
      changePosition.setText("Cancel");
      setEditMode("changePosition");
    }
  }
}
