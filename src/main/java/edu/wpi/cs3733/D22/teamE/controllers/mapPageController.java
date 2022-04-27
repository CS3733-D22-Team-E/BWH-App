package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamE.Main;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.customUI.EntityView;
import edu.wpi.cs3733.D22.teamE.customUI.LocationView;
import edu.wpi.cs3733.D22.teamE.customUI.NodeImageView;
import edu.wpi.cs3733.D22.teamE.customUI.customImageViewTesting;
import edu.wpi.cs3733.D22.teamE.database.daos.*;
import edu.wpi.cs3733.D22.teamE.entity.*;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

public class mapPageController implements Initializable {
  List<Location> locations;
  List<Location> filteredLocations;

  List<EntityInterface> entityInterfaceList;

  List<NodeImageView> nodeViews;

  NodeImageView nodeSelected;

  double zoomIncrement = 0.1;
  String viewMode = "Tower Locations";
  String editMode = "false";
  int mouseX;
  int mouseY;
  double[] selectedLoc;

  @FXML StackPane mapBox;
  @FXML ImageView mapImage;
  Pane floorLocationsPane = new Pane();
  Pane floorEquipmentPane = new Pane();
  Pane floorServiceRequestPane = new Pane();
  @FXML ComboBox floorDropdown;
  @FXML ComboBox locationTypeDropdown;
  @FXML VBox towerLocationsLegend;
  @FXML VBox medicalEquipmentLegend;
  @FXML VBox serviceRequestLegend;
  @FXML ScrollPane scroller;

  @FXML HBox helpIcon;

  // Map editor components
  @FXML JFXButton mapEditorButton;
  @FXML HBox editorModeContainer;
  @FXML VBox editorParent;
  @FXML VBox addLocationPane;
  @FXML VBox deleteLocationPane;
  // Update location components
  // Add tower location components
  @FXML TextField addShortName;
  @FXML TextField addLongName;
  @FXML JFXComboBox addNodeType;
  @FXML JFXButton smallAddLocation;
  // Delete location components
  @FXML Text deleteText;
  @FXML JFXButton smallDeleteLocation;
  @FXML StackPane canvasPane;

  @FXML JFXButton legendOpenButton;

  @FXML VBox sideParent;

  private boolean openLegend = false;

  protected String curFloor = "1";

  ObservableList<String> floors =
      FXCollections.observableArrayList("L1", "L2", "1", "2", "3", "4", "5");
  ObservableList<String> nodes =
      FXCollections.observableArrayList(
          "PATI", "STOR", "DIRT", "HALL", "ELEV", "REST", "STAI", "DEPT", "LABS", "INFO", "CONF",
          "EXIT", "RETL", "SERV");
  ObservableList<String> locationTypes =
      FXCollections.observableArrayList("Tower Locations", "Medical Equipment", "Service Requests");

  public mapPageController() throws SQLException {
    int i = 3;
  }

  private void legendLogic() {
    towerLocationsLegend.setVisible(
        (viewMode.equals("Tower Locations")) && !towerLocationsLegend.isVisible() && openLegend);
    medicalEquipmentLegend.setVisible(
        (viewMode.equals("Medical Equipment"))
            && !medicalEquipmentLegend.isVisible()
            && openLegend);
    serviceRequestLegend.setVisible(
        (viewMode.equals("Service Requests")) && !serviceRequestLegend.isVisible() && openLegend);
    sideParent.setVisible(
        towerLocationsLegend.isVisible()
            || medicalEquipmentLegend.isVisible()
            || serviceRequestLegend.isVisible()
            || editorParent.isVisible());
  }

  @Override // Initialize the map page
  public void initialize(URL url, ResourceBundle rb) {

    scroller.addEventFilter(ScrollEvent.ANY, this::mouseScrollZoom);

    scroller.addEventFilter(
        MouseEvent.ANY,
        event -> {
          scroller.setPannable(!event.getButton().equals(MouseButton.SECONDARY));
        });

    legendOpenButton.setOnAction(
        event -> {
          openLegend = !openLegend;
          legendLogic();
          if (openLegend) legendOpenButton.setText("Close Legend");
          else legendOpenButton.setText("Open Legend");
        });

    // Add items to dropdown
    addNodeType.setItems(nodes);
    floorDropdown.setItems(floors);
    floorDropdown.setValue("1");
    locationTypeDropdown.setItems(locationTypes);
    locationTypeDropdown.setValue("Tower Locations");

    // Set the image size to the default slider value
    // zoomHandler(zoomSlider.getValue());

    // Set initial viewMode
    try {
      viewMode = "Tower Locations";
      fetchDB();
    } catch (SQLException | FileNotFoundException e) {
      e.printStackTrace();
    }

    final Popup popup = new Popup();
    popup.setAutoHide(true);

    final EventHandler<MouseEvent> hoverListener =
        event -> {
          Label popupContent1 =
              new Label(
                  "\uD835\uDC02\uD835\uDC28\uD835\uDC27\uD835\uDC2D\uD835\uDC2B\uD835\uDC28\uD835\uDC25\uD835\uDC2C\n"
                      + "Drag with M1 to move around the Map\n"
                      + "Zoom with Scroll Wheel\n"
                      + "Interact with Nodes on the map with M2\n"
                      + "Click on Location Nodes, then go to editor, and select the Delete Locations button to delete them\n"
                      + "Add Location Nodes with the add section of the map editor menu, you will find added Nodes in the center of the Map\n"
                      + "Drag Nodes to update their location\n"
                      + "Shift-Click(M2) on Entity Nodes such as Medical Equipment or Service Requests to open their info pages\n"
                      + "Entity Nodes may only be placed on Location Nodes, whereas Location Nodes may be placed freely\n"
                      + "Entity Nodes will snap to the nearest Location Node to ensure this\n");
          popupContent1.setStyle(
              "-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 5px; -fx-text-fill: black;");

          VBox popupContent = new VBox();
          popupContent.getChildren().add(popupContent1);
          popup.getContent().clear();
          popup.getContent().addAll(popupContent);

          if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
            popup.hide();
          } else if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            popup.show(helpIcon, event.getScreenX() + 10, event.getScreenY());
          }
        };

    this.helpIcon.setOnMouseEntered(hoverListener);
    this.helpIcon.setOnMouseExited(hoverListener);

    setZoom(1.3);
  }

  // Re-fetch data from database after location update
  public void fetchDB() throws SQLException, FileNotFoundException {
    locations = DAOSystemSingleton.INSTANCE.getSystem().getAllLocations();
    entityInterfaceList = new ArrayList<>();
    entityInterfaceList.addAll(DAOSystemSingleton.INSTANCE.getSystem().getAllMedEquip());
    entityInterfaceList.addAll(DAOSystemSingleton.INSTANCE.getSystem().getAllServiceRequests());

    setViewMode(viewMode);
  }

  // Switch which floor is displayed
  private void switchMap(String floor) throws FileNotFoundException, SQLException {

    // Clears the medical Equipment Icons
    mapBox.getChildren().clear();
    mapBox.getChildren().add(mapImage);
    floorEquipmentPane.getChildren().clear();
    floorLocationsPane.getChildren().clear();
    floorServiceRequestPane.getChildren().clear();
    curFloor = floor;

    switch (floor) {
      case "2":
        mapImage.setImage(
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/images/maps/02_thesecondfloor.png"))));
        break;
      case "3": //
        mapImage.setImage(
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/images/maps/03_thethirdfloor.png"))));
        break;
      case "4": //
        mapImage.setImage(
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/images/maps/04_thefourthfloor.png"))));
        break;
      case "5": //
        mapImage.setImage(
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/images/maps/05_thefifthfloor.png"))));
        break;
      case "L1": //
        mapImage.setImage(
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/images/maps/00_thelowerlevel1.png"))));
        break;
      case "L2": //
        mapImage.setImage(
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/images/maps/00_thelowerlevel2.png"))));
        break;
      default: //
        mapImage.setImage(
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/images/maps/01_thefirstfloor.png"))));
        break;
    }

    // Display the currently selected viewMode
    filterTowerLocations();
    setViewMode(viewMode);
  }

  // Set which legend and set view mode is visible
  private void setViewMode(String view) throws SQLException, FileNotFoundException {

    // Clear all icons
    mapBox.getChildren().clear();
    mapBox.getChildren().add(mapImage);
    viewMode = view;
    // legendLogic();

    // Hide all legends
    /*  towerLocationsLegend.setVisible(false);
        medicalEquipmentLegend.setVisible(false);
        serviceRequestLegend.setVisible(false);
    */
    // Enable edit button
    // mapEditorButton.setDisable(false);

    switch (view) {
      case "Tower Locations":
        // Filter and display the tower locations
        filterTowerLocations();
        displayTowerLocations();
        // Set the legend to visible
        // towerLocationsLegend.setVisible(true);
        break;
      case "Medical Equipment":
        // Filter and display medical equipment
        displayMedEquipLocations();

        // Set the legend to visible
        // medicalEquipmentLegend.setVisible(true);
        break;
      case "Service Requests":
        // Filter and display service requests
        displayServiceRequestLocations();

        // Set the legend to visible
        // serviceRequestLegend.setVisible(true);
        break;
    }

    // Set the view mode
  }

  // Set which map editor is visible
  private void setEditMode(String view) {

    // Hide all editors
    editorModeContainer.setVisible(false);
    addLocationPane.setVisible(false);
    deleteLocationPane.setVisible(false);

    // Reset selected location
    selectedLoc = null;

    switch (view) {
      case "add":
        // Clear all icons
        // mapBox.getChildren().clear();
        // mapBox.getChildren().add(mapImage);

        // Set the add editor to visible
        addLocationPane.setVisible(true);
        editorModeContainer.setVisible(true);
        break;
      case "delete":
        // Set the delete editor to visible
        deleteLocationPane.setVisible(true);
        editorModeContainer.setVisible(true);
        break;
    }

    // Set the view mode
    editMode = view;
  }

  // Display location on the map
  private void displayTowerLocations() throws FileNotFoundException, SQLException {

    floorLocationsPane.getChildren().clear();

    nodeViews = new ArrayList<>();

    double imageX = mapImage.getFitWidth();
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    // Display an icon for each item in the filtered list
    for (Location l : filteredLocations) {

      Image image = nodeToIcon(l.getNodeType());

      double prefWidth = (image.getWidth() / 12.0) * scaleFactor;
      double prefHeight = (image.getHeight() / 12.0) * scaleFactor;

      double widthOffset = (prefWidth / 2);
      double heightOffset = (prefHeight / 2);
      // prefWidth *= 0.5;
      // prefHeight *= 0.5;

      LocationView i = new LocationView(image, l);
      i.setFitWidth(prefWidth);
      i.setFitHeight(prefHeight);
      double x = l.getXCoord() * (scaleFactor) - widthOffset;
      double y = l.getYCoord() * (scaleFactor) - heightOffset;
      i.setX(x);
      i.setStartX(x);
      i.setY(y);

      i.setStartY(y);
      i.setModifier(scaleFactor);
      i.setWidthOffset(widthOffset);
      i.setHeightOffset(heightOffset);
      nodeViews.add(i);
      i.attach(this);

      floorLocationsPane.getChildren().add(i);
    }
    floorLocationsPane.setScaleX(mapBox.getScaleX());
    floorLocationsPane.setScaleY(mapBox.getScaleY());
    mapBox.getChildren().add(floorLocationsPane);
  }

  private Image equipGetImage(String type) {
    Image image =
        new Image(
            Objects.requireNonNull(
                Main.class.getResourceAsStream("view/icons/medicalEquipment/location.png")));

    // Apply the correct icon based on the equipment type
    switch (type) {
      case "RECLINER":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/medicalEquipment/recliner.png")));
        break;
      case "BED":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/medicalEquipment/bed.png")));
        break;
      case "INFUSION PUMP":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/medicalEquipment/infusion.png")));
        break;
      case "XRAY":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/medicalEquipment/xray.png")));
        break;
    }

    return image;
  }

  ArrayList<customImageViewTesting> staticLocations = new ArrayList<>();

  private void displayStaticLocations(Pane pane) throws FileNotFoundException {
    staticLocations.clear();

    nodeViews = new ArrayList<>();

    double imageX = mapImage.getFitWidth();
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    // Display an icon for each item in the filtered list
    for (Location l : filteredLocations) {

      Image image = nodeToIcon(l.getNodeType());

      double prefWidth = (image.getWidth() / 12.0) * scaleFactor;
      double prefHeight = (image.getHeight() / 12.0) * scaleFactor;

      double widthOffset = (prefWidth / 2);
      double heightOffset = (prefHeight / 2);
      // prefWidth *= 0.5;
      // prefHeight *= 0.5;

      customImageViewTesting i = new customImageViewTesting(image, l);
      i.setFitWidth(prefWidth);
      i.setFitHeight(prefHeight);
      double x = l.getXCoord() * (scaleFactor) - widthOffset;
      double y = l.getYCoord() * (scaleFactor) - heightOffset;
      i.setX(x);
      i.setY(y);

      pane.getChildren().add(i);
      staticLocations.add(i);
    }
  }

  // Display medical equipment on the map
  private void displayMedEquipLocations() throws FileNotFoundException, SQLException {

    floorEquipmentPane.getChildren().clear();

    displayStaticLocations(floorEquipmentPane);

    double imageX = mapImage.getFitWidth();
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    // Display an icon for each item in the filtered list
    for (EntityInterface et : entityInterfaceList) {
      if (et instanceof MedicalEquipment) {
        if (et.getFloorID().equals(curFloor)) {

          MedicalEquipment e = (MedicalEquipment) et;

          Image image = equipGetImage(e.getEquipmentType());

          double prefWidth = (image.getWidth() / 12.0) * scaleFactor;
          double prefHeight = (image.getHeight() / 12.0) * scaleFactor;

          double widthOffset = (prefWidth / 2);
          double heightOffset = (prefHeight / 2);
          // prefWidth *= 0.5;
          // prefHeight *= 0.5;

          ImageView myLoc = null;
          for (customImageViewTesting loc : staticLocations) {
            if (loc.getL().getNodeID().equals(et.getRoomID())) myLoc = loc;
          }

          assert myLoc != null;
          EntityView i = new EntityView(image, et, myLoc);
          i.setFitWidth(prefWidth);
          i.setFitHeight(prefHeight);
          double x = et.getLocation().getXCoord() * (scaleFactor) - widthOffset;
          double y = et.getLocation().getYCoord() * (scaleFactor) - heightOffset;
          i.setX(x);
          i.setStartX(x);
          i.setY(y);

          i.setStartY(y);
          i.setModifier(scaleFactor);
          i.setWidthOffset(widthOffset);
          i.setHeightOffset(heightOffset);
          nodeViews.add(i);
          i.attach(this);

          // Highlight the selected location
          if (selectedLoc != null) {
            if (e.getXCoord() == selectedLoc[0] && e.getYCoord() == selectedLoc[1]) {
              i.getStyleClass().add("selectedLocationDot");
            }
          }

          floorEquipmentPane.getChildren().add(i);
        }
      }
    }
    floorEquipmentPane.setScaleX(mapBox.getScaleX());
    floorEquipmentPane.setScaleY(mapBox.getScaleY());
    mapBox.getChildren().add(floorEquipmentPane);
  }

  private Image reqGetImage(String type) {
    Image image =
        new Image(
            Objects.requireNonNull(
                Main.class.getResourceAsStream("view/icons/serviceRequests/location.png")));

    if (type.equals(serviceRequest.Type.MED_DELIV_REQ.toString())) {
      image =
          new Image(
              Objects.requireNonNull(
                  Main.class.getResourceAsStream("view/icons/serviceRequests/medicine.png")));
    } else if (type.equals(serviceRequest.Type.LAB_REQUEST.toString())) {
      image =
          new Image(
              Objects.requireNonNull(
                  Main.class.getResourceAsStream("view/icons/serviceRequests/labs.png")));
    } else if (type.equals(serviceRequest.Type.MED_EQUIP_REQ.toString())) {
      image =
          new Image(
              Objects.requireNonNull(
                  Main.class.getResourceAsStream("view/icons/serviceRequests/equipment.png")));
    } else if (type.equals(serviceRequest.Type.MEAL_DELIV_REQ.toString())) {
      image =
          new Image(
              Objects.requireNonNull(
                  Main.class.getResourceAsStream("view/icons/serviceRequests/meal.png")));
    } else if (type.equals(serviceRequest.Type.SANITATION_REQ.toString())) {
      image =
          new Image(
              Objects.requireNonNull(
                  Main.class.getResourceAsStream("view/icons/serviceRequests/sanitation.png")));
    } else if (type.equals(serviceRequest.Type.LANG_INTERP_REQ.toString())) {
      image =
          new Image(
              Objects.requireNonNull(
                  Main.class.getResourceAsStream("view/icons/serviceRequests/language.png")));
    }

    return image;
  }

  // Display service request locations
  private void displayServiceRequestLocations() throws FileNotFoundException {

    floorServiceRequestPane.getChildren().clear();

    displayStaticLocations(floorServiceRequestPane);

    double imageX = mapImage.getFitWidth();
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    // Display an icon for each item in the filtered list
    for (EntityInterface et : entityInterfaceList) {
      if (et instanceof RequestInterface) {
        if (filteredLocations.contains(et.getLocation())) {
          if (et.getFloorID().equals(curFloor)) {
            RequestInterface e = (RequestInterface) et;

            Image image = reqGetImage(e.getRequestType().toString());

            double prefWidth = (image.getWidth() / 12.0) * scaleFactor;
            double prefHeight = (image.getHeight() / 12.0) * scaleFactor;

            double widthOffset = (prefWidth / 2);
            double heightOffset = (prefHeight / 2);

            ImageView myLoc = null;
            for (customImageViewTesting loc : staticLocations) {
              if (loc.getL().getNodeID().equals(et.getRoomID())) myLoc = loc;
            }

            assert myLoc != null;
            EntityView i = new EntityView(image, et, myLoc);
            i.setFitWidth(prefWidth);
            i.setFitHeight(prefHeight);
            double x = et.getLocation().getXCoord() * (scaleFactor) - widthOffset;
            double y = et.getLocation().getYCoord() * (scaleFactor) - heightOffset;
            i.setX(x);
            i.setStartX(x);
            i.setY(y);

            i.setStartY(y);
            i.setModifier(scaleFactor);
            i.setWidthOffset(widthOffset);
            i.setHeightOffset(heightOffset);
            nodeViews.add(i);
            i.attach(this);

            // Highlight the selected location
            if (selectedLoc != null) {
              if (e.getXCoord() == selectedLoc[0] && e.getYCoord() == selectedLoc[1]) {
                i.getStyleClass().add("selectedLocationDot");
              }
            }

            floorServiceRequestPane.getChildren().add(i);
          }
        }
      }
    }
    floorServiceRequestPane.setScaleX(mapBox.getScaleX());
    floorServiceRequestPane.setScaleY(mapBox.getScaleY());
    mapBox.getChildren().add(floorServiceRequestPane);
  }

  // Filter tower locations by floor
  public void filterTowerLocations() {
    // Get the current floor
    String floor = floorDropdown.getValue().toString();

    // Filter tower locations list to only show the current floor
    filteredLocations =
        locations.stream()
            .filter(location -> Objects.equals(location.getFloor(), floor))
            .collect(Collectors.toList());
  }

  // Convert tower locations nodeType to an icon
  private Image nodeToIcon(String nodeType) throws FileNotFoundException {

    Image image =
        new Image(
            Objects.requireNonNull(
                Main.class.getResourceAsStream("view/icons/towerLocations/location.png")));

    // If the node type is null, return the default location icon
    if (nodeType == null) {
      return image;
    }

    // Apply the correct image based on the location type
    switch (nodeType) {
      case "ELEV":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/towerLocations/elev.png")));
        break;
      case "STAI":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/towerLocations/stai.png")));
        break;
      case "REST":
      case "BATH":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/towerLocations/rest.png")));
        break;
      case "STOR":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/towerLocations/stor.png")));
        break;
      case "DEPT":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/towerLocations/dept.png")));
        break;
      case "HALL":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/towerLocations/hall.png")));
        break;
      case "DIRT":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/towerLocations/dirt.png")));
        break;
      case "EXIT":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/towerLocations/exit.png")));
        break;
      case "LABS":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/towerLocations/labs.png")));
        break;
      case "PATI":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/towerLocations/pati.png")));
        break;
      case "RETL":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/towerLocations/retl.png")));
        break;
      case "SERV":
        image =
            new Image(
                Objects.requireNonNull(
                    Main.class.getResourceAsStream("view/icons/towerLocations/serv.png")));
        break;
      default:
        break;
    }

    return image;
  }

  @FXML Group group;

  private void zoomHandler(double zoomValue) {
    // System.out.println("Changing zoom");

    // zoomValue /= 10;

    // group.setScaleX(group.getScaleX() * zoomValue);
    //  group.setScaleY(group.getScaleY() * zoomValue);

    if (mapBox.getScaleX() * zoomValue >= 1.3) {

      mapBox.setScaleX(mapBox.getScaleX() * zoomValue);
      mapBox.setScaleY(mapBox.getScaleY() * zoomValue);

      for (Node child : mapBox.getChildren()) {
        child.setScaleX(child.getScaleX() * zoomValue);
        child.setScaleY(child.getScaleY() * zoomValue);
      }
    }
  }

  private void setZoom(double zoom) {
    mapBox.setScaleX(zoom);
    mapBox.setScaleY(zoom);

    for (Node child : mapBox.getChildren()) {
      child.setScaleX(zoom);
      child.setScaleY(zoom);
    }
  }

  @FXML
  public void mouseScrollZoom(ScrollEvent event) {
    event.consume();
    if (event.getDeltaY() == 0) return;

    final double SCALE_DELTA = 1.1;

    double deltaZoom = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;

    // zoomSlider.setValue(deltaZoom);
    zoomHandler(deltaZoom);
  }

  @FXML // Handle floor selection
  public void floorDropdown(ActionEvent event) throws IOException, SQLException {

    switchMap(floorDropdown.getValue().toString());
  }

  @FXML // Handle location type selection
  public void locationTypeDropdown(ActionEvent event) throws SQLException, FileNotFoundException {
    String locationType = locationTypeDropdown.getValue().toString();

    setViewMode(locationType);
    legendLogic();
  }

  @FXML // Handle navigating to the map editor
  public void mapEditorButton(ActionEvent event) throws IOException {
    // Set editor mode to true
    if (!Objects.equals(editMode, "false")) {
      // Set editor mode to false
      setEditMode("false");

      // Hide map edit mode container
      editorModeContainer.setVisible(false);
      editorParent.setVisible(false);
      if (!openLegend) sideParent.setVisible(false);

      // Change the button text
      mapEditorButton.setText("Open Editor");
    } else {
      // Set editor mode to update
      setEditMode("add");

      // Show map edit mode container
      editorModeContainer.setVisible(true);
      editorParent.setVisible(true);
      sideParent.setVisible(true);

      // Change the button text
      mapEditorButton.setText("Close Editor");
    }
  }

  @FXML // Change edit mode to add
  public void addLocation(ActionEvent event) {
    setEditMode("add");
  }

  @FXML // Change edit mode to delete
  public void deleteLocation(ActionEvent event) {
    setEditMode("delete");
  }

  @FXML // Handle adding a new tower location
  public void smallAddLocationButton(ActionEvent event) throws SQLException, FileNotFoundException {

    try {

      double imageX = mapImage.getFitWidth();
      double coordinateX = 935;
      double scaleFactor = imageX / coordinateX;

      int nodeTypeCount = filteredLocations.size() + 1;

      if (addLongName.getText().isEmpty() || addLongName.getText().isBlank())
        throw new NullPointerException("Long Name");
      if (addShortName.getText().isEmpty() || addShortName.getText().isBlank())
        throw new NullPointerException("Short Name");
      if (addNodeType.getValue() == null) throw new NullPointerException("Node Type");

      String nodeID =
          "e"
              + addNodeType.getValue()
              + ("000" + nodeTypeCount).substring(Integer.toString(nodeTypeCount).length())
              + ("00" + floorDropdown.getValue())
                  .substring(floorDropdown.getValue().toString().length());
      Location location =
          new Location(
              nodeID,
              935 / 2,
              935 / 2,
              floorDropdown.getValue().toString(),
              "Tower",
              addNodeType.getValue().toString(),
              addLongName.getText(),
              addShortName.getText(),
              locations.size());
      DAOSystemSingleton.INSTANCE.getSystem().updateLocation(location);

      // Fetch and switch and to update pane
      fetchDB();
    } catch (NullPointerException e) {
      PopUp.createWarning(
          "A value was not filled : " + e.getMessage(),
          ((Node) event.getSource()).getScene().getWindow());
    } catch (DerbySQLIntegrityConstraintViolationException er) {
      PopUp.createWarning(
          er.getCause()
              + " : "
              + er.getMessage()
              + " for "
              + er.getConstraintName()
              + " in "
              + er.getTableName(),
          ((Node) event.getSource()).getScene().getWindow());
    }
  }

  @FXML // Handle deleting a location
  public void smallDeleteLocationButton(ActionEvent event)
      throws SQLException, FileNotFoundException {

    // Reset delete text
    deleteText.setText("Click on the location you would like to delete");
    // Remove the currently selected location from the db

    // Get location by coordinates
    ArrayList<NodeImageView> deleted = new ArrayList<>();
    for (NodeImageView node : nodeViews) {
      try {
        if (node.isClicked()) {
          node.deleteFromDB();
          deleted.add(node);
        }
      } catch (Exception e) {
        PopUp.createWarning(e.getMessage(), ((Node) event.getSource()).getScene().getWindow());
      }
    }

    for (NodeImageView node : deleted) {
      nodeViews.remove(node);
    }
    fetchDB();
  }

  /*@FXML // Handle clicking on the map
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

      if (Objects.equals(viewMode, "Tower Locations")) {
        for (Location location : locations) {

          // If the user has selected a dot on the map
          if ((location.getXCoord() * scaleFactor >= mouseX - locationPadding
                  && location.getXCoord() * scaleFactor <= mouseX + locationPadding)
              && (location.getYCoord() * scaleFactor >= mouseY - locationPadding
                  && location.getYCoord() * scaleFactor <= mouseY + locationPadding)) {

            // Update the current selected location variable
            selectedLoc = new double[] {location.getXCoord(), location.getYCoord()};
          }
        }
        displayTowerLocations();
      } else if (Objects.equals(viewMode, "Medical Equipment")) {
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
      } else if (Objects.equals(viewMode, "Service Requests")) {
        for (RequestInterface servReq : filteredServReqList) {
          // If the user has selected a dot on the map
          if ((servReq.getXCoord() * scaleFactor >= mouseX - locationPadding
                  && servReq.getXCoord() * scaleFactor <= mouseX + locationPadding)
              && (servReq.getYCoord() * scaleFactor >= mouseY - locationPadding
                  && servReq.getYCoord() * scaleFactor <= mouseY + locationPadding)) {

            // Update the current selected location variable
            selectedLoc = new double[] {servReq.getXCoord(), servReq.getYCoord()};
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
      if (Objects.equals(viewMode, "Tower Locations")) {
        filterTowerLocations();
        // Filter out the old location dot
        filteredLocations =
            filteredLocations.stream()
                .filter(
                    location -> {
                      if (selectedLoc[0] == location.getXCoord()
                          && selectedLoc[1] == location.getYCoord()) {
                        return false;
                      }
                      return true;
                    })
                .collect(Collectors.toList());

        System.out.println(filteredLocations.size());
        // Display Tower Locations
        displayTowerLocations();
      } else if (Objects.equals(viewMode, "Medical Equipment")) {
        // Filter out the old location dot
        filteredMedEqList =
            filteredMedEqList.stream()
                .filter(
                    location ->
                        (selectedLoc[0] != location.getXCoord()
                            || selectedLoc[1] != location.getYCoord()))
                .collect(Collectors.toList());

        // Display Medical Equipment
        displayMedEquipLocations();
      } else if (Objects.equals(viewMode, "Service Requests")) {
        // Filter out the old location dot
        filteredServReqList =
            filteredServReqList.stream()
                .filter(
                    location -> {
                      if (selectedLoc[0] == location.getXCoord()
                          && selectedLoc[1] == location.getYCoord()) {
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
  }*/

  public void selectNode(NodeImageView node) {
    if (node != null) {
      this.nodeSelected = node;
    }
  }

  public <T> void updateNode(NodeImageView<T> NodeImageView) {
    if (NodeImageView.isClicked()) {
      selectNode(NodeImageView);
    }
  }
}
