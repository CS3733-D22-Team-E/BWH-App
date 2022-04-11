package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.entity.labRequest;
import java.sql.SQLException;

public class DAOSystem {
  private EmployeeDAOImpl employeeDAO;
  private LabRequestDAOImpl labRequestDAO;
  private LocationDAOImpl locationDAO;
  private MedicalEquipmentDAOImpl medicalEquipmentDAO;
  private MedicalEquipmentServiceRequestDAOImpl medicalEquipmentServiceRequestDAO;
  private ServiceRequestDAOImpl serviceRequestDAO;

  public DAOSystem() throws SQLException {
    employeeDAO = new EmployeeDAOImpl();
    labRequestDAO = new LabRequestDAOImpl();
    locationDAO = new LocationDAOImpl();
    medicalEquipmentDAO = new MedicalEquipmentDAOImpl();
    medicalEquipmentServiceRequestDAO = new MedicalEquipmentServiceRequestDAOImpl();
    serviceRequestDAO = new ServiceRequestDAOImpl();
  }

  // TODO Implement multiple constructors for any of subsystems that have multiple constructors
  // TODO Implement all methods that are not overrides of DAO<T>
  public void updateLabServiceRequest(labRequest labRequest, String newRequestStatus)
      throws SQLException {
    labRequestDAO.updateLabServiceRequest(labRequest, newRequestStatus);
  }
}
