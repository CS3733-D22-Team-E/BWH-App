package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import edu.wpi.cs3733.D22.teamE.database.Location;
import edu.wpi.cs3733.D22.teamE.database.MedicalEquipment;
import edu.wpi.cs3733.D22.teamE.database.daos.MedicalEquipmentDAOImpl;
import edu.wpi.cs3733.D22.teamE.database.daos.MedicalEquipmentServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.medicalEquipmentRequest;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class DashboardAlertHandler extends DashboardHandler {

  ArrayList<MedicalEquipment> allEquipment;
  HashMap<Location, ArrayList<MedicalEquipment>> dirtyAreaInfusionPumps;
  ArrayList<Location> dirtyFusionPumpAlertLocs;
  HashMap<Location, ArrayList<MedicalEquipment>> cleanAreaInfusionPumps;
  HashMap<Location, ArrayList<MedicalEquipment>> dirtyAreaBeds;
  ArrayList<Location> cleanFusionPumpAlertLocs;
  Integer infusionPumpAlertCounter;
  Integer bedAlertCounter;

  MedicalEquipmentServiceRequestDAOImpl medEqServiceReqDB;

  public DashboardAlertHandler(
      MedicalEquipmentDAOImpl medicalEquipmentDAO, DashboardController dashboardController) {
    super(medicalEquipmentDAO, dashboardController);
    this.allEquipment = (ArrayList<MedicalEquipment>) this.medicalEquipmentDAO.getAll();
    initialize();
  }

  @Override
  public void update() {
    infusionPumpAlertCounter = 0;
    bedAlertCounter = 0;

    generatesAlertsForFloor("L2");
    generatesAlertsForFloor("L2");
    generatesAlertsForFloor("1");
    generatesAlertsForFloor("2");
    generatesAlertsForFloor("3");
    generatesAlertsForFloor("4");
    generatesAlertsForFloor("5");
    dashboardController.infusionPumpAlertCircle.setVisible(true);
    dashboardController.infusionPumpAlertLabel.setText(infusionPumpAlertCounter.toString());

    generateBedAlerts();
    dashboardController.bedAlertCircle.setVisible(true);
    dashboardController.bedAlertLabel.setText(bedAlertCounter.toString());
  }

  private void generateBedAlerts() {
    getDirtyAndCleaningLocsBeds();
    checkDirtyBedCounts();
  }

  private void checkDirtyBedCounts() {
    for (Location dirtyLoc : dirtyAreaBeds.keySet()) {
      ArrayList<MedicalEquipment> dirtyBedsAtLoc = dirtyAreaBeds.get(dirtyLoc);
      if (dirtyBedsAtLoc.size() >= 6) {
        sendBedsToCleaning(dirtyBedsAtLoc);
        bedAlertCounter++;
      }
    }
  }

  private void sendBedsToCleaning(ArrayList<MedicalEquipment> dirtyBedsAtLoc) {
    medicalEquipmentRequest cleaningRequest = new medicalEquipmentRequest();
    cleaningRequest.setFloorID("1");
    cleaningRequest.setRoomID("eSTOR001L1");
    cleaningRequest.setEquipment("Bed");
    cleaningRequest.setStaffAssignee("Pending");
    cleaningRequest.setRequestStatus("To Do");
    cleaningRequest.setDeliveryDate(LocalDate.now());
    cleaningRequest.setRequestDate(LocalDate.now());
    cleaningRequest.setIsUrgent(true);
    cleaningRequest.setOtherNotes("Automated request");
    cleaningRequest.setEquipmentQuantity(dirtyBedsAtLoc.size());

    try {
      medEqServiceReqDB.addMedEquipReq(cleaningRequest);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void getDirtyAndCleaningLocsBeds() {
    for (Location curLoc : dashboardController.locationDAO.getAll()) {
      if (curLoc.getNodeType().equals("DIRT")) {
        dirtyAreaBeds.put(curLoc, new ArrayList<>());
      }
    }
  }

  private void generatesAlertsForFloor(String floor) {
    getLocsOnFloor(floor);
    infusionPumpCleaningOnFloor(floor);
  }

  @Override
  public void initialize() {
    dirtyAreaInfusionPumps = new HashMap<>();
    cleanAreaInfusionPumps = new HashMap<>();
    dirtyFusionPumpAlertLocs = new ArrayList<>();
    cleanFusionPumpAlertLocs = new ArrayList<>();
    dirtyAreaBeds = new HashMap<>();

    try {
      medEqServiceReqDB = new MedicalEquipmentServiceRequestDAOImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    getDirtyAndCleaningLocs();
    getPumps();
    update();
  }

  private void infusionPumpCleaningOnFloor(String floor) {
    boolean needToSendToCleaning = false;
    for (Location curLoc : dirtyAreaInfusionPumps.keySet()) {
      ArrayList<MedicalEquipment> dirtyPumpsInArea = dirtyAreaInfusionPumps.get(curLoc);
      if (dirtyPumpsInArea.size() >= 10) {
        dirtyFusionPumpAlertLocs.add(curLoc);
        infusionPumpAlertCounter++;
        needToSendToCleaning = true;
      }
    }

    for (Location curLoc : cleanAreaInfusionPumps.keySet()) {
      ArrayList<MedicalEquipment> cleanPumpsInArea = cleanAreaInfusionPumps.get(curLoc);
      if (cleanPumpsInArea.size() < 5) {
        cleanFusionPumpAlertLocs.add(curLoc);
        infusionPumpAlertCounter++;
        needToSendToCleaning = true;
      }
    }

    if (needToSendToCleaning) {
      ArrayList<MedicalEquipment> pumpsToClean = getDirtyInfusionPumpsOnFloor(floor);
      sendPumpsToCleaning(pumpsToClean);
    }
  }

  private ArrayList<MedicalEquipment> getDirtyInfusionPumpsOnFloor(String floor) {
    ArrayList<MedicalEquipment> infusionPumpsOnFloor = new ArrayList<>();
    for (Location curLoc : dirtyAreaInfusionPumps.keySet()) {
      if (curLoc.getFloor().equals(floor)) {
        infusionPumpsOnFloor.addAll(dirtyAreaInfusionPumps.get(curLoc));
      }
    }
    return infusionPumpsOnFloor;
  }

  private ArrayList<Location> getLocsOnFloor(String floor) {
    ArrayList<Location> locationsOnFloor = new ArrayList<Location>();
    for (Location curLoc : dashboardController.locationDAO.getAll()) {
      if (curLoc.getFloor().equals(floor)) {
        locationsOnFloor.add(curLoc);
      }
    }
    return locationsOnFloor;
  }

  private void getPumps() {
    for (MedicalEquipment curMedEq : allEquipment) {
      Location equipmentLocation =
          dashboardController.locationDAO.get(curMedEq.getCurrentLocation());
      if (dirtyAreaInfusionPumps.containsKey(equipmentLocation)) {
        ArrayList<MedicalEquipment> equipmentAtLoc = dirtyAreaInfusionPumps.get(equipmentLocation);
        equipmentAtLoc.add(curMedEq);
        dirtyAreaInfusionPumps.put(equipmentLocation, equipmentAtLoc);
      }
      if (cleanAreaInfusionPumps.containsKey(equipmentLocation)) {
        ArrayList<MedicalEquipment> equipmentAtLoc = cleanAreaInfusionPumps.get(equipmentLocation);
        equipmentAtLoc.add(curMedEq);
        cleanAreaInfusionPumps.put(equipmentLocation, equipmentAtLoc);
      }
    }
  }

  private void getDirtyAndCleaningLocs() {
    for (Location curLoc : dashboardController.locationDAO.getAll()) {
      if (curLoc.getNodeType().equals("DIRT")) {
        dirtyAreaInfusionPumps.put(curLoc, new ArrayList<>());
      } else if (curLoc.getNodeType().equals("STOR")) {
        cleanAreaInfusionPumps.put(curLoc, new ArrayList<>());
      }
    }
  }

  private void sendPumpsToCleaning(ArrayList<MedicalEquipment> dirtyInfusionPumps) {
    medicalEquipmentRequest cleaningRequest = new medicalEquipmentRequest();
    cleaningRequest.setFloorID("1");
    cleaningRequest.setRoomID("eSTOR00101");
    cleaningRequest.setEquipment("Infusion Pump");
    cleaningRequest.setStaffAssignee("Pending");
    cleaningRequest.setRequestStatus("To Do");
    cleaningRequest.setDeliveryDate(LocalDate.now());
    cleaningRequest.setRequestDate(LocalDate.now());
    cleaningRequest.setIsUrgent(true);
    cleaningRequest.setOtherNotes("Automated request");
    cleaningRequest.setEquipmentQuantity(dirtyInfusionPumps.size());

    try {
      medEqServiceReqDB.addMedEquipReq(cleaningRequest);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
