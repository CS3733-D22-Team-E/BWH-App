package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnection;
import edu.wpi.energetic_easter_bunnies.entity.serviceRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequestDAOImpl implements DAO<serviceRequest> {
  static Connection connection = DBConnection.getConnection();
  List<serviceRequest> serviceRequests;

  public ServiceRequestDAOImpl() throws SQLException {
    serviceRequests = new ArrayList<>();
    Statement statement = connection.createStatement();
    String query = "SELECT * FROM SERVICEREQUEST ORDER BY REQUESTID DESC";
    ResultSet rs = statement.executeQuery(query);
    int numID = 0;
    while (rs.next()) {
      String requestID = rs.getString("REQUESTID");
      String status = rs.getString("STATUS");
      String type = rs.getString("TYPE");
      String assignee = rs.getString("ASSIGNEE");
      Date requestDate = rs.getDate("REQUEST_DATE");
      Date deliveryDate = rs.getDate("DELIVERY_DATE");
      boolean isUrgent = rs.getBoolean("ISURGENT");

      serviceRequest request =
          new serviceRequest(
              requestID,
              type,
              "",
              "",
              "",
              isUrgent,
              status,
              assignee,
              requestDate.toLocalDate(),
              deliveryDate.toLocalDate());

      serviceRequests.add(request);
      numID++;
    }
    rs.close();
  }

  @Override
  public List<serviceRequest> getAll() {
    return serviceRequests;
  }

  @Override
  public serviceRequest get(String id) {
    for(serviceRequest request : serviceRequests) {
      if(request.getServiceRequestID().equals(id))
        return request;
    }
    System.out.println("Service Request with service request id " + id + " not found");
    throw new NullPointerException();
  }

  @Override
  public void update(serviceRequest request) {
    serviceRequests.add(request);
  }

  @Override
  public void delete(serviceRequest request) {
    serviceRequests.remove(request);
  }
}
