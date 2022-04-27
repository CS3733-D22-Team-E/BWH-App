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
  DAOSystem subject;
  ArrayList<RequestInterface> allRequestList;
  ArrayList<RequestInterface> currentRequestList;

  public DashboardServiceRequestHandler(DashboardController dashboardController) {
    this.dashboardController = dashboardController;
    this.subject = dashboardController.database;
    allRequestList = (ArrayList<RequestInterface>) subject.getAllServiceRequests();
  }

  public void update() {
    allRequestList = (ArrayList<RequestInterface>) subject.getAllServiceRequests();
  }

  protected void updateServiceRequestTable() {
    filterRequests();
    // TODO: uncomment this out once the null roomID error from the transport service request is fixed
    // displayRequests();
  }

  private void filterRequests() {
    System.out.println("Current requests: " + currentRequestList);
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
            System.out.println("Current request: " + curRequest);
            System.out.println("Current room ID: " + curRequest.getRoomID());
            return new SimpleStringProperty(
                subject.getLocation(curRequest.getRoomID()).getShortName());
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
