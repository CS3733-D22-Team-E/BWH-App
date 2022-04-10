package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnect;
import edu.wpi.energetic_easter_bunnies.database.medicineDelivery;

import java.sql.Connection;
import java.util.List;

public class MedicineDeliveryDAOImpl implements DAO<medicineDelivery> {
    static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
    List<medicineDelivery> medicineRequests;

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

    }

    @Override
    public void delete(medicineDelivery item) {

    }
}
