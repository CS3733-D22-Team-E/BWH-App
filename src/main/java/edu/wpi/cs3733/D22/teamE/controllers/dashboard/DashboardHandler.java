package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import edu.wpi.cs3733.D22.teamE.database.daos.MedicalEquipmentDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.MedicalEquipment;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Toggle;

public abstract class DashboardHandler {

  MedicalEquipmentDAOImpl medicalEquipmentDAO;
  OldDashboardController dashboardController;

  public abstract void update();

  public abstract void initialize();

  public DashboardHandler(
      MedicalEquipmentDAOImpl medicalEquipmentDAO, OldDashboardController dashboardController) {
    this.medicalEquipmentDAO = medicalEquipmentDAO;
    this.dashboardController = dashboardController;
    this.medicalEquipmentDAO.attach(this);
  }

  protected ArrayList<MedicalEquipment> getEquipmentOnFloor(
      String floor, ArrayList<MedicalEquipment> allEquipment) throws SQLException {
    String floorID;
    switch (floor) {
      case "LL2":
        floorID = "L2";
        break;
      case "LL1":
        floorID = "L1";
        break;
      case "All Floors":
        return allEquipment;
      default:
        floorID = floor;
        break;
    }
    ArrayList<MedicalEquipment> equipmentOnFloor = new ArrayList<>();
    for (MedicalEquipment curEquipment : allEquipment) {
      if (curEquipment.getFloor().equals(floorID)) {
        equipmentOnFloor.add(curEquipment);
      }
    }
    return equipmentOnFloor;
  }

  protected ArrayList<MedicalEquipment> filterByEquipmentType(
      String equipmentType, ArrayList<MedicalEquipment> allEquipment) {
    ArrayList<MedicalEquipment> filteredByEquipment = new ArrayList<>();
    for (MedicalEquipment curEquipment : allEquipment) {
      if (equipmentType.equals("") || curEquipment.getEquipmentType().equals(equipmentType)) {
        filteredByEquipment.add(curEquipment);
      }
    }
    return filteredByEquipment;
  }

  protected ArrayList<MedicalEquipment> filterEquipment(
      ArrayList<MedicalEquipment> allEquipment, Toggle filter) {
    ArrayList<MedicalEquipment> filteredEquipment = new ArrayList<>();

    // TODO: optimize this so that it isn't spaghetti
    if (filter.equals(dashboardController.cleanFilter)) {
      for (MedicalEquipment curEquipment : allEquipment) {
        if (curEquipment.isClean()) {
          filteredEquipment.add(curEquipment);
        }
      }
    } else if (filter.equals(dashboardController.dirtyFilter)) {
      for (MedicalEquipment curEquipment : allEquipment) {
        if (!curEquipment.isClean()) {
          filteredEquipment.add(curEquipment);
        }
      }
    } else if (filter.equals(dashboardController.inUseFilter)) {
      for (MedicalEquipment curEquipment : allEquipment) {
        if (curEquipment.isInUse()) {
          filteredEquipment.add(curEquipment);
        }
      }
    } else if (filter.equals(dashboardController.allFilter)) {
      filteredEquipment = allEquipment;
    }
    return filteredEquipment;
  }

  protected ArrayList<MedicalEquipment> populateEquipmentList(
      Toggle filter, String floor, String equipmentType, ArrayList<MedicalEquipment> allEquipment)
      throws SQLException {
    ArrayList<MedicalEquipment> equipmentOnFloor = getEquipmentOnFloor(floor, allEquipment);
    ArrayList<MedicalEquipment> filteredEquipmentByType =
        filterByEquipmentType(equipmentType, equipmentOnFloor);
    ArrayList<MedicalEquipment> filteredByState =
        filterEquipment(filteredEquipmentByType, dashboardController.currentFilter);
    return filteredByState;
  }
}
