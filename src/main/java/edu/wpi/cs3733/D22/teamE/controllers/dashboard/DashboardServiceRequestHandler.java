package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.entity.RequestInterface;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class DashboardServiceRequestHandler {

  DashboardController dashboardController;
  DAOSystem system;
  ArrayList<RequestInterface> allRequestList;
  ArrayList<RequestInterface> currentRequestList;

  public DashboardServiceRequestHandler(DashboardController dashboardController) {
    this.dashboardController = dashboardController;
    this.system = dashboardController.database;
    allRequestList = (ArrayList<RequestInterface>) system.getAllServiceRequests();
  }

  public void update() {
    allRequestList = (ArrayList<RequestInterface>) system.getAllServiceRequests();
  }

  protected void updateServiceRequestTable() {
    filterRequests();
    displayRequests();
  }

  private void filterRequests() {
    currentRequestList = new ArrayList<>();
    if (!dashboardController.currentFloorString.equals("All")) {
      for (RequestInterface curReq : allRequestList) {
        if (curReq.getFloorID().equals(dashboardController.currentFloorString)) {
          currentRequestList.add(curReq);
        }
      }
    } else {
      currentRequestList = allRequestList;
    }
  }

  private void displayRequests() {
    ObservableList<RequestInterface> tableRows =
        FXCollections.observableArrayList(currentRequestList);
    dashboardController.tableDate.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<RequestInterface, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<RequestInterface, String> param) {
            RequestInterface curRequest = param.getValue();
            return new SimpleStringProperty(curRequest.getDeliveryDate().toString());
          }
        });
    dashboardController.tableFloor.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<RequestInterface, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<RequestInterface, String> param) {
            RequestInterface curRequest = param.getValue();
            return new SimpleStringProperty(curRequest.getFloorID());
          }
        });
    dashboardController.tableRoom.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<RequestInterface, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<RequestInterface, String> param) {
            RequestInterface curRequest = param.getValue();
            String shortNameString = "";
            if (!curRequest.getRoomID().equals("null")) {
              shortNameString = system.getLocation(curRequest.getRoomID()).getShortName();
            }
            return new SimpleStringProperty(shortNameString);
          }
        });
    dashboardController.tableRequestType.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<RequestInterface, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<RequestInterface, String> param) {
            RequestInterface curRequest = param.getValue();
            return new SimpleStringProperty(curRequest.getRequestType().toString());
          }
        });
    dashboardController.tableStaffAssignee.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<RequestInterface, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<RequestInterface, String> param) {
            RequestInterface curRequest = param.getValue();
            return new SimpleStringProperty(curRequest.getStaffAssignee());
          }
        });
    dashboardController.tableProgress.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<RequestInterface, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<RequestInterface, String> param) {
            RequestInterface curRequest = param.getValue();
            return new SimpleStringProperty(curRequest.getRequestStatus());
          }
        });
    dashboardController.serviceRequestTable.setItems(tableRows);
  }
}
