package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import edu.wpi.cs3733.D22.teamE.entity.DashboardPage;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class DashboardHandler {

    protected DashboardPage dashboardEntity;
    protected DashboardController dashboardController;

    public abstract void update();
}
