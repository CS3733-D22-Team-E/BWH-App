package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import edu.wpi.cs3733.D22.teamE.entity.DashboardPage;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class DashboardFilters extends DashboardHandler {

  public DashboardFilters(DashboardPage dashboardEntity, DashboardController dashboardController) {
    this.dashboardEntity = dashboardEntity;
    this.dashboardController = dashboardController;
    dashboardEntity.attach(this);
  }

  @Override
  public void update() {
      if (!dashboardController.currentFilter.equals(dashboardEntity.getFilterSelection())) {
        dashboardController.currentFilter = dashboardEntity.getFilterSelection();
      }

  }

}
