package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnect;
import edu.wpi.energetic_easter_bunnies.entity.mealDeliveryRequest;

import java.sql.Connection;
import java.util.List;

public class MealDeliveryRequestDAOImpl implements DAO<mealDeliveryRequest> {
    static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
    List<mealDeliveryRequest> mealDeliveryRequests;

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

    }

    @Override
    public void delete(mealDeliveryRequest item) {

    }
}
