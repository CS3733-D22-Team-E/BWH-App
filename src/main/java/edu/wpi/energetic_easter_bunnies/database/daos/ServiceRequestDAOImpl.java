package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnection;
import edu.wpi.energetic_easter_bunnies.database.Equipment;
import edu.wpi.energetic_easter_bunnies.database.MedicalEquipment;
import edu.wpi.energetic_easter_bunnies.entity.serviceRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequestDAOImpl implements ServiceRequestDAO {
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
                            requestID, "", "", "", isUrgent, status, assignee);

            serviceRequests.add(request);
            numID++;
        }
        rs.close();
    }

    @Override
    public List<serviceRequest> getAllServiceRequests() {
        return serviceRequests;
    }

    //TODO Implement this dao
    @Override
    public serviceRequest getServiceRequest(int numID) {
        return null;
    }

    @Override
    public void updateServiceRequest(serviceRequest request) {}

    @Override
    public void deleteServiceRequest(serviceRequest request) {}
}
