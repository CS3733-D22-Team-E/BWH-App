package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import com.jfoenix.controls.JFXTooltip;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.MedicalEquipmentDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.Location;
import edu.wpi.cs3733.D22.teamE.entity.MedicalEquipment;
import edu.wpi.cs3733.D22.teamE.entity.medicalEquipmentRequest;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class DashboardEquipmentHandler extends DashboardHandler {

  DashboardController dashboardController;
  MedicalEquipmentDAOImpl subject;
  DAOSystem system;
  ArrayList<MedicalEquipment> allEquipmentList;
  ArrayList<MedicalEquipment> currentEquipmentList;
  Integer clean;
  Integer inUse;
  Integer dirty;
  boolean bedAlreadySent;

  public DashboardEquipmentHandler(DashboardController dashboardController) {
    this.dashboardController = dashboardController;
    this.system = dashboardController.database;
    allEquipmentList = (ArrayList<MedicalEquipment>) system.getAllMedicalEquipments();
  }

  public void update() {
    allEquipmentList = (ArrayList<MedicalEquipment>) system.getAllMedicalEquipments();
    updateEquipmentReports();
  }

  public void updateEquipmentReports() {
    dashboardController.bedBox.setStyle(null);
    dashboardController.infusionPumpBox.setStyle(null);
    filterEquipment();
    setEquipmentCounts();
    infusionPumpAlertHandler();
    bedAlertHandler();
  }

  private void bedAlertHandler() {
    ArrayList<MedicalEquipment> dirtyLocBeds = new ArrayList<>();
    for (MedicalEquipment curEq : allEquipmentList) {
      if (curEq.getEquipmentType().equals("BED")) {
        if (system.getLocation(curEq.getCurrentLocation()).getNodeType().equals("DIRT")) {
          dirtyLocBeds.add(curEq);
        }
      }
    }
    System.out.println("Dirty location beds: " + dirtyLocBeds);
    if (dirtyLocBeds.size() >= 6) {
      createBedAlert(dirtyLocBeds);
      dashboardController.bedBox.setStyle("-fx-background-color: red");
      dashboardController.bedBoxToolTip = new JFXTooltip();
    }
  }

  private void createBedAlert(ArrayList<MedicalEquipment> dirtyLocBeds) {

    medicalEquipmentRequest cleaningRequest = new medicalEquipmentRequest();
    cleaningRequest.setFloorID(dashboardController.currentFloorString);
    cleaningRequest.setRoomID("eSTOR001L1");
    cleaningRequest.setEquipment("Bed");
    cleaningRequest.setStaffAssignee("Pending");
    cleaningRequest.setRequestStatus("To Do");
    cleaningRequest.setDeliveryDate(LocalDate.now());
    cleaningRequest.setRequestDate(LocalDate.now());
    cleaningRequest.setIsUrgent(true);
    cleaningRequest.setOtherNotes("Automated request");
    cleaningRequest.setEquipmentQuantity(dirtyLocBeds.size());

    ArrayList<medicalEquipmentRequest> curMedReqs =
        (ArrayList<medicalEquipmentRequest>) system.getAllMedicalEquipmentRequests();
    bedAlreadySent = false;
    for (medicalEquipmentRequest curMedReq : curMedReqs) {
      if (curMedReq.getEquipment().equals(cleaningRequest.getEquipment())
          && curMedReq.getEquipmentQuantity() == cleaningRequest.getEquipmentQuantity()
          && curMedReq.getRoomID().equals(cleaningRequest.getRoomID())
          && curMedReq.getOtherNotes().equals(cleaningRequest.getOtherNotes())) {
        bedAlreadySent = true;
        break;
      }
    }

    if (!bedAlreadySent) {
      try {
        system.addMedEquipReq(cleaningRequest);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private void filterEquipment() {
    currentEquipmentList = new ArrayList<>();
    if (!dashboardController.currentFloorString.equals("All")) {
      for (MedicalEquipment curEquip : allEquipmentList) {
        if (curEquip.getFloorID().equals(dashboardController.currentFloorString)) {
          currentEquipmentList.add(curEquip);
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

  private void infusionPumpAlertHandler() {

    Integer cleanLocAlerts = 0;
    Integer dirtyLocAlerts = 0;
    ArrayList<MedicalEquipment> dirtyEquipmentToBeCleaned = new ArrayList<>();

    HashMap<Location, ArrayList<MedicalEquipment>> dirtyLocsOnFloor = new HashMap<>();
    HashMap<Location, ArrayList<MedicalEquipment>> cleanLocsOnFloor = new HashMap<>();

    for (Location curLoc : system.getAllLocations()) {
      if (curLoc.getFloor().equals(dashboardController.currentFloorString)) {
        if (curLoc.getNodeType().equals("DIRT")) {
          dirtyLocsOnFloor.put(curLoc, new ArrayList<MedicalEquipment>());
        }
        if (curLoc.getNodeType().equals("STOR")) {
          cleanLocsOnFloor.put(curLoc, new ArrayList<MedicalEquipment>());
        }
      }
    }

    for (MedicalEquipment curEq : currentEquipmentList) {
      if (curEq.getEquipmentType().equals("INFUSION PUMP")) {
        Location curLoc = system.getLocation(curEq.getCurrentLocation());
        if (dirtyLocsOnFloor.containsKey(curLoc)) {
          dirtyLocsOnFloor.get(curLoc).add(curEq);
        } else if (cleanLocsOnFloor.containsKey(curLoc)) {
          cleanLocsOnFloor.get(curLoc).add(curEq);
        }
      }
    }

    for (Location key : dirtyLocsOnFloor.keySet()) {
      if (dirtyLocsOnFloor.get(key).size() >= 10) {
        for (MedicalEquipment curEq : dirtyLocsOnFloor.get(key)) {
          if (!curEq.isClean()) {
            dirtyEquipmentToBeCleaned.add(curEq);
          }
        }
        dirtyLocAlerts++;
      }
    }

    for (Location key : cleanLocsOnFloor.keySet()) {
      if (cleanLocsOnFloor.get(key).size() < 5) {
        cleanLocAlerts++;
      }
    }

    if (cleanLocAlerts + dirtyLocAlerts > 0) {
      displayDirtyFusionPumpAlert(cleanLocAlerts + dirtyLocAlerts, dirtyEquipmentToBeCleaned);
      if (dirtyEquipmentToBeCleaned.size() > 0) {
        cleanInfusionPumps(dirtyEquipmentToBeCleaned);
      }
    }
  }

  private void displayDirtyFusionPumpAlert(
      Integer alertNumber, ArrayList<MedicalEquipment> dirtyEquipmentToBeCleaned) {
    dashboardController.infusionPumpBox.setStyle("-fx-background-color: red");
  }

  private void cleanInfusionPumps(ArrayList<MedicalEquipment> dirtyEquipmentToBeCleaned) {

    // dirtyEquipmentToBeCleaned.removeIf(curEq -> !curEq.getMed_equipmentID().equals("null"));

    medicalEquipmentRequest cleaningRequest = new medicalEquipmentRequest();
    cleaningRequest.setFloorID(dashboardController.currentFloorString);
    cleaningRequest.setRoomID("eSTOR00101");
    cleaningRequest.setEquipment("Infusion Pump");
    cleaningRequest.setStaffAssignee("Pending");
    cleaningRequest.setRequestStatus("To Do");
    cleaningRequest.setDeliveryDate(LocalDate.now());
    cleaningRequest.setRequestDate(LocalDate.now());
    cleaningRequest.setIsUrgent(true);
    cleaningRequest.setOtherNotes("Automated request");
    cleaningRequest.setEquipmentQuantity(dirtyEquipmentToBeCleaned.size());

    ArrayList<medicalEquipmentRequest> curMedReqs =
        (ArrayList<medicalEquipmentRequest>) system.getAllMedicalEquipmentRequests();
    boolean alreadySent = false;
    for (medicalEquipmentRequest curMedReq : curMedReqs) {
      if (curMedReq.getFloorID().equals(cleaningRequest.getFloorID())
          && curMedReq.getEquipment().equals(cleaningRequest.getEquipment())
          && curMedReq.getEquipmentQuantity() == cleaningRequest.getEquipmentQuantity()
          && curMedReq.getRoomID().equals(cleaningRequest.getRoomID())
          && curMedReq.getOtherNotes().equals(cleaningRequest.getOtherNotes())) {
        alreadySent = true;
        break;
      }
    }

    if (!alreadySent) {
      try {
        system.addMedEquipReq(cleaningRequest);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      /*
      try {
        subject.getMedicalEquipments(
            "INFUSION PUMP",
            dirtyEquipmentToBeCleaned.size(),
            "eSTOR00101",
            cleaningRequest.getServiceRequestID());
      } catch (SQLException e) {
        e.printStackTrace();
      }
       */
    }
  }
}
