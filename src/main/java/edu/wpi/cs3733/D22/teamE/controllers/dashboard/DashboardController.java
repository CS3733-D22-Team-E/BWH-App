package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.MedicalEquipment;
import edu.wpi.cs3733.D22.teamE.entity.RequestInterface;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

  @FXML ImageView mapButton;
  @FXML ImageView requestStatusButton;
  @FXML ImageView profileButton;
  @FXML ImageView submitRequestButton;

  @FXML JFXToggleNode ll2Button;
  @FXML JFXToggleNode ll1Button;
  @FXML JFXToggleNode firstFloorButton;
  @FXML JFXToggleNode secondFloorButton;
  @FXML JFXToggleNode thirdFloorButton;
  @FXML JFXToggleNode fourthFloorButton;
  @FXML JFXToggleNode fifthFloorButton;
  ToggleGroup floorButtons;
  JFXToggleNode currentFloor;
  String currentFloorString;

  @FXML TableView<RequestInterface> serviceRequestTable;
  @FXML TableColumn<RequestInterface, String> tableDate;
  @FXML TableColumn<RequestInterface, String> tableFloor;
  @FXML TableColumn<RequestInterface, String> tableRoom;
  @FXML TableColumn<RequestInterface, String> tableRequestType;
  @FXML TableColumn<RequestInterface, String> tableStaffAssignee;
  @FXML TableColumn<RequestInterface, String> tableProgress;

  @FXML Label xRayClean;
  @FXML Label xRayInUse;
  @FXML Label xRayDirty;

  @FXML Label bedClean;
  @FXML Label bedInUse;
  @FXML Label bedDirty;

  @FXML Label reclinerClean;
  @FXML Label reclinerInUse;
  @FXML Label reclinerDirty;

  @FXML Label infusionPumpClean;
  @FXML Label infusionPumpInUse;
  @FXML Label infusionPumpDirty;

  @FXML VBox bedBox;
  @FXML JFXBadge bedAlertBadge;

  @FXML VBox infusionPumpBox;
  @FXML JFXBadge infusionPumpAlertBadge;

  DAOSystem database;
  ArrayList<MedicalEquipment> currentEquipment;
  ArrayList<RequestInterface> currentServiceRequests;
  DashboardEquipmentHandler dashboardEquipmentHandler;
  DashboardServiceRequestHandler dashboardServiceRequestHandler;

  public DashboardController() {}

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    bedAlertBadge = new JFXBadge();
    // bedBox.getChildren().add(bedAlertBadge);

    database = DAOSystemSingleton.INSTANCE.getSystem();

    currentFloorString = "All";
    floorButtonsHandler();

    currentEquipment = (ArrayList<MedicalEquipment>) database.getAllMedicalEquipments();
    currentServiceRequests = (ArrayList<RequestInterface>) database.getAllServiceRequests();

    dashboardEquipmentHandler = new DashboardEquipmentHandler(this);
    dashboardServiceRequestHandler = new DashboardServiceRequestHandler(this);

    dashboardEquipmentHandler.updateEquipmentReports();
    dashboardServiceRequestHandler.updateServiceRequestTable();

    quickAccessButtonHandler();
  }

  private void floorButtonsHandler() {
    floorButtons = new ToggleGroup();
    ll2Button.setToggleGroup(floorButtons);
    ll1Button.setToggleGroup(floorButtons);
    firstFloorButton.setToggleGroup(floorButtons);
    secondFloorButton.setToggleGroup(floorButtons);
    thirdFloorButton.setToggleGroup(floorButtons);
    fourthFloorButton.setToggleGroup(floorButtons);
    fifthFloorButton.setToggleGroup(floorButtons);
    currentFloor = (JFXToggleNode) floorButtons.getSelectedToggle();

    floorButtons
        .selectedToggleProperty()
        .addListener(
            new ChangeListener<Toggle>() {
              @Override
              public void changed(
                  ObservableValue<? extends Toggle> observableValue, Toggle oldVal, Toggle newVal) {
                currentFloor = (JFXToggleNode) newVal;
                updateFloorString(newVal);
                if (dashboardEquipmentHandler.updateEquipmentReports()) {
                  bedAlertDisplay();
                }
                dashboardServiceRequestHandler.updateServiceRequestTable();
              }
            });
  }

  private void bedAlertDisplay() {
    PopUp.createWarning(
        "There are too many beds in a dirty area!", bedDirty.getScene().getWindow());
  }

  private void updateFloorString(Toggle newVal) {
    if (ll2Button.equals(newVal)) {
      currentFloorString = "L2";
    } else if (ll1Button.equals(newVal)) {
      currentFloorString = "L1";
    } else if (firstFloorButton.equals(newVal)) {
      currentFloorString = "1";
    } else if (secondFloorButton.equals(newVal)) {
      currentFloorString = "2";
    } else if (thirdFloorButton.equals(newVal)) {
      currentFloorString = "3";
    } else if (fourthFloorButton.equals(newVal)) {
      currentFloorString = "4";
    } else if (fifthFloorButton.equals(newVal)) {
      currentFloorString = "5";
    } else {
      currentFloorString = "All";
    }
  }

  private void quickAccessButtonHandler() {
    quickAccessButtonClickedHandler(mapButton, "map.fxml");
    quickAccessButtonClickedHandler(requestStatusButton, "serviceRequestLanding.fxml");
    quickAccessButtonClickedHandler(profileButton, "profilePage.fxml");
    quickAccessButtonClickedHandler(submitRequestButton, "aboutPage.fxml");
  }

  private void quickAccessButtonClickedHandler(ImageView quickAccessButton, String url) {
    quickAccessButton.setOnMouseClicked(
        mouseEvent -> {
          pageControl.loadCenter(url, (Stage) quickAccessButton.getScene().getWindow());
        });
  }
}
