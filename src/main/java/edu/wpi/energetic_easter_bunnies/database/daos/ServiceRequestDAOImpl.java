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
  DAO<medicalEquipmentRequest> medicalEquipmentServiceRequestDAO =
      new MedicalEquipmentServiceRequestDAOImpl();
  DAO<labRequest> labRequestDAO = new LabRequestDAOImpl();
  DAO<languageInterpreterRequest> languageInterpreterRequestDAO = new LanguageRequestDAOImpl();
  DAO<mealDeliveryRequest> mealDeliveryRequestDAO = new MealDeliveryRequestDAOImpl();
  DAO<medicineDelivery> medicineDeliveryDAO = new MedicineDeliveryDAOImpl();
  DAO<sanitationRequest> sanitationRequestDAO = new SanitationRequestDAOImpl();

  public ServiceRequestDAOImpl() throws SQLException {
    serviceRequests = new ArrayList<>();

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
    delete(request);
    serviceRequests.add(request);
    switch (request.getRequestType()) {
      case LAB_REQUEST:
        labRequestDAO.update((labRequest) request);
        break;
      case MED_DELIV_REQ:
        medicineDeliveryDAO.update((medicineDelivery) request);
        break;
      case MEAL_DELIV_REQ:
        mealDeliveryRequestDAO.update((mealDeliveryRequest) request);
        break;
      case MED_EQUIP_REQ:
        medicalEquipmentServiceRequestDAO.update((medicalEquipmentRequest) request);
        break;
      case SANITATION_REQ:
        sanitationRequestDAO.update((sanitationRequest) request);
        break;
      case LANG_INTERP_REQ:
        languageInterpreterRequestDAO.update((languageInterpreterRequest) request);
        break;
      default:
        break;
    }
  }

  @Override
  public void delete(serviceRequest request) {
    boolean found = false;
    for (serviceRequest r : serviceRequests) {
      if (r.getServiceRequestID().equals(request.getServiceRequestID())) {
        found = true;
        break;
      }
    }
    if (!found) return;
    serviceRequests.remove(request);

    switch (request.getRequestType()) {
      case LAB_REQUEST:
        labRequestDAO.delete((labRequest) request);
        break;
      case MED_DELIV_REQ:
        medicineDeliveryDAO.delete((medicineDelivery) request);
        break;
      case MEAL_DELIV_REQ:
        mealDeliveryRequestDAO.delete((mealDeliveryRequest) request);
        break;
      case MED_EQUIP_REQ:
        medicalEquipmentServiceRequestDAO.delete((medicalEquipmentRequest) request);
        break;
      case SANITATION_REQ:
        sanitationRequestDAO.delete((sanitationRequest) request);
        break;
      case LANG_INTERP_REQ:
        languageInterpreterRequestDAO.delete((languageInterpreterRequest) request);
        break;
      default:
        break;
    }
  }
}
