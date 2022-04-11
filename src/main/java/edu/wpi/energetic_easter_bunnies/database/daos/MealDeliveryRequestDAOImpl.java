package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnect;
import edu.wpi.energetic_easter_bunnies.entity.mealDeliveryRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealDeliveryRequestDAOImpl implements DAO<mealDeliveryRequest> {
    static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
    List<mealDeliveryRequest> mealDeliveryRequests;

    public MealDeliveryRequestDAOImpl() throws SQLException {
        mealDeliveryRequests = new ArrayList<>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM MEALDELIVERYREQUEST ORDER BY MEAL_REQ_ID DESC";
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()) {
            String medicineReqID = rs.getString("MEAL_REQ_ID");
            String otherNotes = rs.getString("OTHERNOTES");
            String floorID = rs.getString("FLOOR");
            String roomID = rs.getString("ROOMID");
            boolean isUrgent = rs.getBoolean("ISURGENT");
            String status = rs.getString("STATUS");
            String staffAssignee = rs.getString("ASSIGNEE");
            java.sql.Date requestDate = rs.getDate("REQUEST_DATE");
            java.sql.Date deliveryDate = rs.getDate("DELIVERY_DATE");
            String entree = rs.getString("ENTREE");
            String beverage = rs.getString("BEVERAGE");
            String dessert = rs.getString("DESSERT");
            int deliveryTime = rs.getInt("DELIVERYTIME");

            mealDeliveryRequest request = new mealDeliveryRequest(medicineReqID, otherNotes, floorID, roomID, isUrgent, status, staffAssignee, requestDate.toLocalDate(), deliveryDate.toLocalDate(), entree, beverage, dessert, deliveryTime);
            mealDeliveryRequests.add(request);
        }
        rs.close();
    }

    @Override
    public List<mealDeliveryRequest> getAll() {
        return mealDeliveryRequests;
    }

    @Override
    public mealDeliveryRequest get(String id) {
        for (mealDeliveryRequest request : mealDeliveryRequests) {
            if (request.getServiceRequestID().equals(id)) return request;
        }

        System.out.println("Meal Delivery Request with meal request id " + id + " not found");
        throw new NullPointerException();
    }

    @Override
    public void update(mealDeliveryRequest item) {
        mealDeliveryRequests.add(item);

        try {
            String query = "INSERT INTO MEALDELIVERYREQUEST VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, item.getServiceRequestID());
            statement.setObject(2, item.getRequestDate());
            statement.setObject(3, item.getDeliveryDate());
            statement.setString(4, item.getRequestStatus());
            statement.setString(5, item.getStaffAssignee());
            statement.setBoolean(6, item.getIsUrgent());
            statement.setString(7, item.getRoomID());
            statement.setString(8, item.getFloorID());
            statement.setString(9, item.getEntreeType());
            statement.setString(10, item.getBeverageType());
            statement.setString(11, item.getDessertType());
            statement.setInt(12, item.getDeliveryTime());
            statement.setString(13, item.getOtherNotes());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add Meal Request failed!"); // TODO: Come up with a better catch block
        }

    }

    @Override
    public void delete(mealDeliveryRequest item) {
        mealDeliveryRequests.remove(item);
    }
}
