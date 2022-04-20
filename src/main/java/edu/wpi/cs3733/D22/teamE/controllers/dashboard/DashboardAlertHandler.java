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

    generatesAlertsForFloor("L2");
    generatesAlertsForFloor("L2");
    generatesAlertsForFloor("1");
    generatesAlertsForFloor("2");
    generatesAlertsForFloor("3");
    generatesAlertsForFloor("4");
    generatesAlertsForFloor("5");

    dashboardController.infusionPumpAlertCircle.setVisible(true);
    dashboardController.infusionPumpAlertLabel.setText(infusionPumpAlertCounter.toString());
    dashboardController.infusionPumpAlertLabel.setVisible(true);

    generateBedAlerts();

    dashboardController.bedAlertCircle.setVisible(true);
    dashboardController.bedAlertLabel.setText(bedAlertCounter.toString());
    dashboardController.infusionPumpAlertLabel.setVisible(true);
  }

  private void generateBedAlerts() {
    getDirtyAndCleaningLocsBeds();
    getBeds();
    checkDirtyBedCounts();
  }

  private void getBeds() {
    for (MedicalEquipment curMedEq : allEquipment) {
      if (curMedEq.getEquipmentType().equals("BED")) {
        Location equipmentLocation =
            dashboardController.locationDAO.get(curMedEq.getCurrentLocation());
        if (dirtyAreaBeds.containsKey(equipmentLocation)) {
          ArrayList<MedicalEquipment> equipmentAtLoc = dirtyAreaBeds.get(equipmentLocation);
          equipmentAtLoc.add(curMedEq);
          dirtyAreaBeds.put(equipmentLocation, equipmentAtLoc);
        }
      }
    }
  }

  private void checkDirtyBedCounts() {
    System.out.println("Checking dirty bed counts");
    ArrayList<MedicalEquipment> allDirtyBeds = new ArrayList<>();
    for (Location dirtyLoc : dirtyAreaBeds.keySet()) {
      ArrayList<MedicalEquipment> dirtyBedsAtLoc = dirtyAreaBeds.get(dirtyLoc);
      allDirtyBeds.addAll(dirtyBedsAtLoc);
    }
    if (allDirtyBeds.size() >= 6) {
      System.out.println("Sending beds to cleaning: ");
      for (MedicalEquipment bed : allDirtyBeds) {
        System.out.println("\t" + bed.getEquipmentID());
      }
      sendBedsToCleaning(allDirtyBeds);
      bedAlertCounter += allDirtyBeds.size();
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
    update();
  }

  private void infusionPumpCleaningOnFloor(String floor) {
    boolean needToSendToCleaning = false;
    for (Location curLoc : dirtyAreaInfusionPumps.keySet()) {
      ArrayList<MedicalEquipment> dirtyPumpsInArea = dirtyAreaInfusionPumps.get(curLoc);
      if (dirtyPumpsInArea.size() >= 10) {
        dirtyFusionPumpAlertLocs.add(curLoc);
        needToSendToCleaning = true;
      }
    }

    for (Location curLoc : cleanAreaInfusionPumps.keySet()) {
      ArrayList<MedicalEquipment> cleanPumpsInArea = cleanAreaInfusionPumps.get(curLoc);
      if (cleanPumpsInArea.size() < 5) {
        cleanFusionPumpAlertLocs.add(curLoc);
        needToSendToCleaning = true;
      }
    }

    if (needToSendToCleaning) {
      ArrayList<MedicalEquipment> pumpsToClean = getDirtyInfusionPumpsOnFloor(floor);
      if (pumpsToClean.size() > 0) {
        infusionPumpAlertCounter += pumpsToClean.size();
        System.out.println("Sending pumps to cleaning: ");
        for (MedicalEquipment pump : pumpsToClean) {
          System.out.println("\t" + pump.getEquipmentID());
        }
        sendPumpsToCleaning(pumpsToClean);
      }
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
      if (curMedEq.getEquipmentType().equals("INFUSION PUMP")) {
        Location equipmentLocation =
            dashboardController.locationDAO.get(curMedEq.getCurrentLocation());
        if (dirtyAreaInfusionPumps.containsKey(equipmentLocation)) {
          ArrayList<MedicalEquipment> equipmentAtLoc =
              dirtyAreaInfusionPumps.get(equipmentLocation);
          equipmentAtLoc.add(curMedEq);
          dirtyAreaInfusionPumps.put(equipmentLocation, equipmentAtLoc);
        }
        if (cleanAreaInfusionPumps.containsKey(equipmentLocation)) {
          ArrayList<MedicalEquipment> equipmentAtLoc =
              cleanAreaInfusionPumps.get(equipmentLocation);
          equipmentAtLoc.add(curMedEq);
          cleanAreaInfusionPumps.put(equipmentLocation, equipmentAtLoc);
        }
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
