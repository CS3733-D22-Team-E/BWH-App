package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnect;
import edu.wpi.energetic_easter_bunnies.database.medicineDelivery;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineDeliveryDAOImpl implements DAO<medicineDelivery> {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
  List<medicineDelivery> medicineRequests;
  /*
    public static void main(String[] args) throws SQLException {
      MedicineDeliveryDAOImpl db = new MedicineDeliveryDAOImpl();
      medicineDelivery delivery = new medicineDelivery();
      db.update(delivery);
    }
  */
  public MedicineDeliveryDAOImpl() throws SQLException {
    medicineRequests = new ArrayList<>();
    String query = "SELECT * FROM MEDICINEREQUEST ORDER BY MEDICINE_REQ_ID DESC";
    PreparedStatement statement = connection.prepareStatement(query);
    ResultSet rs = statement.executeQuery();

    while (rs.next()) {
      String medicineReqID = rs.getString("MEDICINE_REQ_ID");
      String otherNotes = rs.getString("OTHERNOTES");
      String floorID = rs.getString("FLOOR");
      String roomID = rs.getString("DELIVERYLOCATIONID");
      Boolean isUrgent = rs.getBoolean("ISURGENT");
      String status = rs.getString("STATUS");
      String staffAssignee = rs.getString("ASSIGNEE");
      java.sql.Date requestDate = rs.getDate("REQUEST_DATE");
      java.sql.Date deliveryDate = rs.getDate("DELIVERY_DATE");
      String medicine = rs.getString("MEDICINETYPE");
      String medicineQuantity = rs.getString("MEDICINEQUANTITY");
      String medicineUnit = rs.getString("MEDICINEUNIT");
      String reocurringDays = rs.getString("REOCURRINGDAYS");
      String deliveryTime = rs.getString("DELIVERYTIME");

      boolean[] repeatDays = getRepeatingDays(reocurringDays);

      medicineDelivery delivery =
          new medicineDelivery(
              medicineReqID,
              otherNotes,
              floorID,
              roomID,
              isUrgent,
              status,
              staffAssignee,
              requestDate.toLocalDate(),
              deliveryDate.toLocalDate(),
              medicine,
              medicineQuantity,
              medicineUnit,
              deliveryTime,
              repeatDays[1],
              repeatDays[2],
              repeatDays[3],
              repeatDays[4],
              repeatDays[5],
              repeatDays[6],
              repeatDays[0]);
      medicineRequests.add(delivery);
    }
    rs.close();
  }

  @Override
  public List<medicineDelivery> getAll() {
    return medicineRequests;
  }

  @Override
  public medicineDelivery get(String id) {
    for (medicineDelivery request : medicineRequests) {
      if (request.getServiceRequestID().equals(id)) return request;
    }

    System.out.println("Medicine Request with medicine request id " + id + " not found");
    throw new NullPointerException();
  }

  @Override
  public void update(medicineDelivery item) {
    medicineRequests.add(item);

    try {
      String query =
          "INSERT INTO MEDICINEREQUEST VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, item.getServiceRequestID());
      statement.setDate(2, Date.valueOf(item.getRequestDate()));
      statement.setDate(3, Date.valueOf(item.getDeliveryDate()));
      statement.setString(4, item.getRequestStatus());
      statement.setString(5, item.getStaffAssignee());
      statement.setBoolean(6, item.getIsUrgent());
      statement.setString(7, item.getRoomID());
      statement.setString(8, item.getFloorID());
      statement.setString(9, item.getMedicine());
      statement.setString(10, item.getAmount());
      statement.setString(11, item.getUnit());
      statement.setString(12, item.getRepeatingDays());
      statement.setString(13, item.getOtherNotes());
      statement.setString(14, item.getDeliveryTime());
      statement.executeUpdate();

    } catch (SQLException e) {
      System.out.println("Add Medicine Request failed!"); // TODO: Come up with a better catch block
    }
  }

  @Override
  public void delete(
      medicineDelivery
          item) { // TODO: Figure out what to do with Request in DB after its marked as done
    medicineRequests.remove(item);
  }

  public boolean[] getRepeatingDays(String reocurringDays) { // converting string to booleans
    String[] reocurringDaysArr = reocurringDays.toUpperCase().split(" ");
    boolean[] result = new boolean[7]; // SUN MON TUES WED THURS FRI SAT

    for (String str : reocurringDaysArr) {
      switch (str) {
        case "SUN":
          result[0] = true;
          break;
        case "MON":
          result[1] = true;
          break;
        case "TUES":
          result[2] = true;
          break;
        case "WED":
          result[3] = true;
          break;
        case "THURS":
          result[4] = true;
          break;
        case "FRI":
          result[5] = true;
          break;
        case "SAT":
          result[6] = true;
          break;
      }
    }
    return result;
  }
}
