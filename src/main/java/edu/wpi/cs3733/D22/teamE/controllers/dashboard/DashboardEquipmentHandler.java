package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.entity.MedicalEquipment;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardEquipmentHandler extends DashboardHandler {

  DashboardController dashboardController;
  DAOSystem subject;
  ArrayList<MedicalEquipment> allEquipmentList;
  ArrayList<MedicalEquipment> currentEquipmentList;
  Integer clean;
  Integer inUse;
  Integer dirty;

  public DashboardEquipmentHandler(DashboardController dashboardController) {
    this.dashboardController = dashboardController;
    this.subject = dashboardController.database;
    allEquipmentList = (ArrayList<MedicalEquipment>) subject.getAllMedicalEquipments();
  }

  public void update() {
    allEquipmentList = (ArrayList<MedicalEquipment>) subject.getAllMedicalEquipments();
    updateEquipmentReports();
  }

  public void updateEquipmentReports() {
    filterEquipment();
    setEquipmentCounts();
  }

  private void filterEquipment() {
    currentEquipmentList = new ArrayList<>();
    if (!dashboardController.currentFloorString.equals("All")) {
      for (MedicalEquipment curEquip : allEquipmentList) {
        try {
          if (curEquip.getFloor().equals(dashboardController.currentFloorString)) {
            currentEquipmentList.add(curEquip);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    } else {
      currentEquipmentList = allEquipmentList;
    }
  }

  private void setEquipmentCounts() {
    countEquipmentType("XRAY");
    dashboardController.xRayClean.setText(String.valueOf(clean));
    dashboardController.xRayInUse.setText(String.valueOf(inUse));
    dashboardController.xRayDirty.setText(String.valueOf(dirty));
    countEquipmentType("BED");
    dashboardController.bedClean.setText(String.valueOf(clean));
    dashboardController.bedInUse.setText(String.valueOf(inUse));
    dashboardController.bedDirty.setText(String.valueOf(dirty));
    countEquipmentType("RECLINER");
    dashboardController.reclinerClean.setText(String.valueOf(clean));
    dashboardController.reclinerInUse.setText(String.valueOf(inUse));
    dashboardController.reclinerDirty.setText(String.valueOf(dirty));
    countEquipmentType("INFUSION PUMP");
    dashboardController.infusionPumpClean.setText(String.valueOf(clean));
    dashboardController.infusionPumpInUse.setText(String.valueOf(inUse));
    dashboardController.infusionPumpDirty.setText(String.valueOf(dirty));
  }

  private void countEquipmentType(String equipmentType) {
    clean = 0;
    inUse = 0;
    dirty = 0;

    for (MedicalEquipment curEq : currentEquipmentList) {
      if (curEq.getEquipmentType().equals(equipmentType)) {
        if (curEq.isClean()) {
          clean++;
        } else {
          dirty++;
        }
        if (curEq.isInUse()) {
          inUse++;
        }
      }
    }
  }
}
