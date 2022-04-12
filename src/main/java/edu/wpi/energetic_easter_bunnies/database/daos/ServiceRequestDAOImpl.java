package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnect;
import edu.wpi.energetic_easter_bunnies.database.medicineDelivery;
import edu.wpi.energetic_easter_bunnies.entity.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequestDAOImpl implements DAO<serviceRequest> {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
  List<serviceRequest> serviceRequests;

  public ServiceRequestDAOImpl() throws SQLException {
    serviceRequests = new ArrayList<>();
    DAO<medicalEquipmentRequest> medicalEquipmentServiceRequestDAO =
        new MedicalEquipmentServiceRequestDAOImpl();
    DAO<labRequest> labRequestDAO = new LabRequestDAOImpl();
    DAO<languageInterpreterRequest> languageInterpreterRequestDAO = new LanguageRequestDAOImpl();
    DAO<mealDeliveryRequest> mealDeliveryRequestDAO = new MealDeliveryRequestDAOImpl();
    DAO<medicineDelivery> medicineDeliveryDAO = new MedicineDeliveryDAOImpl();
    DAO<sanitationRequest> sanitationRequestDAO = new SanitationRequestDAOImpl();

    serviceRequests.addAll(medicalEquipmentServiceRequestDAO.getAll());
    serviceRequests.addAll(labRequestDAO.getAll());
    serviceRequests.addAll(languageInterpreterRequestDAO.getAll());
    serviceRequests.addAll(mealDeliveryRequestDAO.getAll());
    serviceRequests.addAll(medicineDeliveryDAO.getAll());
    serviceRequests.addAll(sanitationRequestDAO.getAll());
  }

  @Override
  public List<serviceRequest> getAll() {
    return serviceRequests;
  }

  public void printAll() {
    for (serviceRequest request : serviceRequests) {
      System.out.println(request.getRequestType());
    }
  }

  @Override
  public serviceRequest get(String id) {
    for (serviceRequest request : serviceRequests) {
      if (request.getServiceRequestID().equals(id)) return request;
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
