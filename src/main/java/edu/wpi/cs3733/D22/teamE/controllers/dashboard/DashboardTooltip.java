package edu.wpi.cs3733.D22.teamE.controllers.dashboard;
import edu.wpi.cs3733.D22.teamE.entity.DashboardPage;

public class DashboardTooltip extends DashboardHandler {

    public DashboardTooltip(DashboardPage dashboardEntity, DashboardController dashboardController) {
        this.dashboardEntity = dashboardEntity;
        this.dashboardController = dashboardController;
        dashboardEntity.attach(this);
    }

    @Override
    public void update() {
        updateTooltips();
    }

    public void updateTooltips() {

    }

    public void updateTooltip() {

    }
}
