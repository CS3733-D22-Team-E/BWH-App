package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;

public class DashboardFilters extends DashboardController implements Initializable {

  public DashboardFilters() {}

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    filters = new ToggleGroup();
    cleanFilter.setToggleGroup(filters);
    dirtyFilter.setToggleGroup(filters);
    inUseFilter.setToggleGroup(filters);
    allFilter.setToggleGroup(filters);
    allFilter.setSelected(true);
    currentFilter = allFilter;
  }
}
