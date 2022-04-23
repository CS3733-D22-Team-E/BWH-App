package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import edu.wpi.cs3733.D22.teamE.entity.MedicalEquipment;
import edu.wpi.cs3733.D22.teamE.database.daos.MedicalEquipmentDAOImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class DashboardTooltip extends DashboardHandler {

  ArrayList<MedicalEquipment> allEquipment;

  public DashboardTooltip(
      MedicalEquipmentDAOImpl medicalEquipmentDAO, DashboardController dashboardController) {
    super(medicalEquipmentDAO, dashboardController);
    this.allEquipment = (ArrayList<MedicalEquipment>) this.medicalEquipmentDAO.getAll();
  }

  @Override
  public void update() {
    this.allEquipment = (ArrayList<MedicalEquipment>) medicalEquipmentDAO.getAll();
    populateTooltips();
  }

  @Override
  public void initialize() {}

  public void populateTooltips() {
    try {
      populateTooltip(dashboardController.ll2FloorTooltip, "LL2");
      populateTooltip(dashboardController.ll1FloorTooltip, "LL1");
      populateTooltip(dashboardController.firstFloorTooltip, "1");
      populateTooltip(dashboardController.secondFloorTooltip, "2");
      populateTooltip(dashboardController.thirdFloorTooltip, "3");
      populateTooltip(dashboardController.fourthFloorTooltip, "4");
      populateTooltip(dashboardController.fifthFloorTooltip, "5");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void populateTooltip(Tooltip tooltip, String floor) throws SQLException {
    ArrayList<MedicalEquipment> equipmentOnFloor = getEquipmentOnFloor(floor, this.allEquipment);
    Integer allEquipment = equipmentOnFloor.size();
    Integer cleanEquipment =
        filterEquipment(equipmentOnFloor, dashboardController.cleanFilter).size();
    Integer dirtyEquipment =
        filterEquipment(equipmentOnFloor, dashboardController.dirtyFilter).size();
    Integer inUseEquipment =
        filterEquipment(equipmentOnFloor, dashboardController.inUseFilter).size();

    tooltip.setText(
        "Floor: "
            + floor
            + "\n"
            + "Total "
            + dashboardController.equipmentSelectedTooltipText
            + " Count: "
            + allEquipment
            + "\nClean "
            + dashboardController.equipmentSelectedTooltipText
            + " Count: "
            + cleanEquipment
            + "\nDirty "
            + dashboardController.equipmentSelectedTooltipText
            + " Count: "
            + dirtyEquipment
            + "\nIn Use "
            + dashboardController.equipmentSelectedTooltipText
            + " Count: "
            + inUseEquipment);

    tooltip.setShowDelay(Duration.seconds(.2));
  }
}
