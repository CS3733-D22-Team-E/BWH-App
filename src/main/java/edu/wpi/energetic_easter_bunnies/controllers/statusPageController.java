package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.entity.serviceRequest;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class statusPageController extends containsSideMenu {
  @FXML VBox root;
  @FXML TableView<serviceRequest> requestTable;
  @FXML TableColumn<serviceRequest, Integer> idColumn;
  @FXML TableColumn<serviceRequest, String> typeColumn;
  @FXML TableColumn<serviceRequest, String> statusColumn;
  @FXML TableColumn<serviceRequest, String> assignedColumn;
  @FXML TableColumn<serviceRequest, String> dateColumn;

  public statusPageController() {}
}
