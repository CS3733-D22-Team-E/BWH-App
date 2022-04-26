package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.entity.RequestInterface;
import java.util.ArrayList;

public class DashboardServiceRequestHandler {

  DashboardController dashboardController;
  DAOSystem subject;
  ArrayList<RequestInterface> currentEquipmentList;

  public DashboardServiceRequestHandler(DashboardController dashboardController) {
    this.dashboardController = dashboardController;
    this.subject = dashboardController.database;
  }

  public void update() {
    currentEquipmentList = (ArrayList<RequestInterface>) subject.getAllServiceRequests();
  }

  protected void updateServiceRequestTable() {}
}
