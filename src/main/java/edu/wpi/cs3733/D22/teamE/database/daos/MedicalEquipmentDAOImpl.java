package edu.wpi.cs3733.D22.teamE.database.daos;

import edu.wpi.cs3733.D22.teamE.controllers.dashboard.DashboardHandler;
import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.entity.MedicalEquipment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalEquipmentDAOImpl implements DAO<MedicalEquipment> {
  static Connection connection = AccountsManager.getInstance().getConnection();
  List<MedicalEquipment> equipmentList;
  ArrayList<DashboardHandler> observers;

  public MedicalEquipmentDAOImpl() throws SQLException {
    equipmentList = new ArrayList<>();
    observers = new ArrayList<>();
    String query = "SELECT * FROM EQUIPMENT ORDER BY EQUIPMENTID DESC";
    PreparedStatement statement = connection.prepareStatement(query);
    ResultSet rs = statement.executeQuery();
    int numID = 0;
    while (rs.next()) {
      String equipmentID = rs.getString("EQUIPMENTID");
      String med_EquipmentID = rs.getString("MED_EQUIP_REQ_ID");
      boolean isInUse = rs.getBoolean("isInUse");
      boolean isClean = rs.getBoolean("isClean");
      String cleanLocation = rs.getString("cleanLocationID");
      String storageLocation = rs.getString("storageLocationID");
      String currentLocation = rs.getString("currentLocationID");
      String equipmentType = rs.getString("equipmentType");

      MedicalEquipment equipment =
          new MedicalEquipment(
              equipmentID,
              med_EquipmentID,
              isInUse,
              isClean,
              cleanLocation,
              storageLocation,
              currentLocation,
              equipmentType,
              numID);
      equipmentList.add(equipment);
      numID++;
    }
    rs.close();
  }

  @Override
  public List<MedicalEquipment> getAll() {
    return equipmentList;
  }

  @Override
  public MedicalEquipment get(String id) {
    for (MedicalEquipment equipment : equipmentList) {
      if (equipment.getEquipmentID().equals(id)) return equipment;
    }
    System.out.println("Location with equipment id " + id + " not found");
    throw new NullPointerException();
  }

  @Override
  public void update(MedicalEquipment equipment) {
    try {
      get(equipment.getEquipmentID());
      delete(equipment);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    equipmentList.add(equipment);
    try {
      String query = "INSERT INTO EQUIPMENT VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, equipment.getEquipmentID());
      statement.setString(2, equipment.getMed_equipmentID());
      statement.setBoolean(3, equipment.isInUse());
      statement.setBoolean(4, equipment.isClean());
      statement.setString(5, equipment.getCleanLocation());
      statement.setString(6, equipment.getStorageLocation());
      statement.setString(7, equipment.getCurrentLocation());
      statement.setString(8, equipment.getEquipmentType());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Add equipment failed!"); // TODO: Come up with a better catch block
    }
    notifyObservers();
  }

  @Override
  public void delete(MedicalEquipment equipment) {
    try {
      get(equipment.getEquipmentID());
      String query =
          "DELETE FROM EQUIPMENT WHERE EQUIPMENTID = ('" + equipment.getEquipmentID() + "')";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate();
      equipmentList.remove(equipment);
      notifyObservers();
    } catch (NullPointerException | SQLException e) {
      e.printStackTrace();
    }
  }

  public List<MedicalEquipment> getMedicalEquipments(
      String equipmentType, int equipmentQuantity, String roomID, String MED_EQUIPMENTID)
      throws SQLException { // TODO: Maybe figure out better way than a double for-loop
    List<MedicalEquipment> equipments = new ArrayList<>();

    // TODO: make this loop and break logic bit more elegant if you get the chance
    // I made it and it's a little wonky - Tim
    int i = 0;
    for (MedicalEquipment equipment : equipmentList) {
      if (equipment.getEquipmentType().equalsIgnoreCase(equipmentType)
          && equipment.checkIsClean()) {
        equipment.use(); // Sets it to be in use and be not clean anymore
        equipment.setMed_equipmentID(MED_EQUIPMENTID); // Binds equipment to the MedEquip Request
        equipment.setCurrentLocation(roomID); // TODO: Add Patient Rooms to TowerLocations.CSV
        equipments.add(equipment);

        // DB Query to update said values
        String query =
            "UPDATE EQUIPMENT SET "
                + "\""
                + "isInUse"
                + "\""
                + " = "
                + true
                + ","
                + "\""
                + "isClean"
                + "\""
                + " = "
                + false
                + ","
                + "\""
                + "currentLocationID"
                + "\""
                + " = '"
                + roomID
                + "', MED_EQUIP_REQ_ID = '"
                + MED_EQUIPMENTID
                + "' WHERE EQUIPMENTID = '"
                + equipment.getEquipmentID()
                + "'"; // Make sure this is actually formatted right
        PreparedStatement statement = connection.prepareStatement(query);
        System.out.println(query);
        statement.executeUpdate(); // TODO: FIX "currentLocationID" can't be found??"
        i++;
      }
      if (i == equipmentQuantity) {
        break;
      }
    }
    // notifyObservers();
    return equipments;
  }

  public void sendToCleaning(List<MedicalEquipment> equipments) throws SQLException {
    for (MedicalEquipment equipment : equipments) {
      equipment.setCurrentLocation(
          equipment.getCleanLocation()); // Sets current location to where the cleaning location is

      // TODO: Add MedicalEquipment.cleanEquipment() here?

      // DB Query to set currentLocation to cleaningLocation
      String query =
          "UPDATE EQUIPMENT SET CURRENTLOCATIONID = '"
              + equipment.getCleanLocation()
              + "' WHERE EQUIPMENTID = '"
              + equipment.getEquipmentID()
              + "'";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate();
    }
    notifyObservers();
  }

  public void attach(DashboardHandler observer) {
    observers.add(observer);
  }

  private void notifyObservers() {
    for (DashboardHandler observer : observers) {
      observer.update();
    }
  }

  public void updateCurrentLocation(MedicalEquipment equipment, int newXCoord, int newYCoord)
      throws SQLException {
    String query =
        "UPDATE TOWERLOCATIONS SET XCOORD = "
            + newXCoord
            + ", YCOORD = "
            + newYCoord
            + " WHERE NODEID = '"
            + equipment.getCurrentLocation()
            + "'";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
  }
}
