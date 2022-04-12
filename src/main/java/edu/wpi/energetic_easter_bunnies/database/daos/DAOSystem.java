package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.Employee;
import edu.wpi.energetic_easter_bunnies.database.Location;
import edu.wpi.energetic_easter_bunnies.database.MedicalEquipment;
import edu.wpi.energetic_easter_bunnies.entity.labRequest;
import edu.wpi.energetic_easter_bunnies.entity.medicalEquipmentRequest;
import edu.wpi.energetic_easter_bunnies.entity.sanitationRequest;
import edu.wpi.energetic_easter_bunnies.entity.serviceRequest;
import java.sql.SQLException;
import java.util.List;

public class DAOSystem {
  private final EmployeeDAOImpl employeeDAO;
  private final LabRequestDAOImpl labRequestDAO;
  private final LocationDAOImpl locationDAO;
  private final MedicalEquipmentDAOImpl medicalEquipmentDAO;
  private final MedicalEquipmentServiceRequestDAOImpl medicalEquipmentServiceRequestDAO;
  private final SanitationRequestDAOImpl sanitationRequestDAO;
  private final ServiceRequestDAOImpl serviceRequestDAO;

  public List<Employee> getAllEmployee() {
    return employeeDAO.getAll();
  }

  public Employee getEmployee(String id) {
    return employeeDAO.get(id);
  }

  public void updateEmployee(Employee employee) {
    employeeDAO.update(employee);
  }

  public void deleteEmployee(Employee employee) {
    employeeDAO.delete(employee);
  }

  public List<labRequest> getAllLabRequests() {
    return labRequestDAO.getAll();
  }

  public labRequest getLabRequest(String id) {
    return labRequestDAO.get(id);
  }

  public void updateLabRequest(labRequest labRequest) {
    labRequestDAO.update(labRequest);
  }

  public void deleteLabRequest(labRequest labRequest) {
    labRequestDAO.delete(labRequest);
  }

  public List<Location> getAllLocations() {
    return locationDAO.getAll();
  }

  public Location getLocation(String id) {
    return locationDAO.get(id);
  }

  public void updateLocation(Location location) {
    locationDAO.update(location);
  }

  public void deleteLocation(Location location) {
    locationDAO.delete(location);
  }

  public List<MedicalEquipment> getAllMedEquip() {
    return medicalEquipmentDAO.getAll();
  }

  public MedicalEquipment getMedEquip(String id) {
    return medicalEquipmentDAO.get(id);
  }

  public void updateMedEquip(MedicalEquipment equipment) {
    medicalEquipmentDAO.update(equipment);
  }

  public void deleteMedEquip(MedicalEquipment equipment) {
    medicalEquipmentDAO.delete(equipment);
  }

  public List<medicalEquipmentRequest> getAllMedEquipReq() {
    return medicalEquipmentServiceRequestDAO.getAll();
  }

  public medicalEquipmentRequest getMedEquipReq(String id) {
    return medicalEquipmentServiceRequestDAO.get(id);
  }

  public void updateMedEquipReq(medicalEquipmentRequest request) {
    medicalEquipmentServiceRequestDAO.update(request);
  }

  public void deleteMedicalEquipReq(medicalEquipmentRequest request) {
    medicalEquipmentServiceRequestDAO.delete(request);
  }

  public List<serviceRequest> getAllServiceRequests() {
    return serviceRequestDAO.getAll();
  }

  public serviceRequest getServiceRequest(String id) {
    return serviceRequestDAO.get(id);
  }

  public void updateServiceRequest(serviceRequest request) {
    serviceRequestDAO.update(request);
  }

  public void deleteServiceRequest(serviceRequest request) {
    serviceRequestDAO.delete(request);
  }

  public DAOSystem() throws SQLException {
    employeeDAO = new EmployeeDAOImpl();
    labRequestDAO = new LabRequestDAOImpl();
    locationDAO = new LocationDAOImpl();
    medicalEquipmentDAO = new MedicalEquipmentDAOImpl();
    medicalEquipmentServiceRequestDAO = new MedicalEquipmentServiceRequestDAOImpl();
    serviceRequestDAO = new ServiceRequestDAOImpl();
    sanitationRequestDAO = new SanitationRequestDAOImpl();
  }

  public void updateLabServiceRequest(labRequest labRequest, String newRequestStatus)
      throws SQLException {
    labRequestDAO.updateLabServiceRequest(labRequest, newRequestStatus);
  }

  public Location get(String nodeID) {
    return locationDAO.get(nodeID);
  }

  public void updateLocation(Location location, String newFloor, String newNodeType)
      throws SQLException {
    locationDAO.updateLocation(location, newFloor, newNodeType);
  }

  public void updateCoord(Location location, int newXCoord, int newYCoord) throws SQLException {
    locationDAO.updateCoord(location, newXCoord, newYCoord);
  }

  public List<MedicalEquipment> getMedicalEquipments(
      String equipmentType, int equipmentQuantity, String roomID, String MED_EQUIPMENTID)
      throws SQLException {
    return medicalEquipmentDAO.getMedicalEquipments(
        equipmentType, equipmentQuantity, roomID, MED_EQUIPMENTID);
  }

  public void sendToCleaning(List<MedicalEquipment> equipmentList) throws SQLException {
    medicalEquipmentDAO.sendToCleaning(equipmentList);
  }

  public void addMedEquipReq(medicalEquipmentRequest medicalEquipmentRequest) throws SQLException {
    medicalEquipmentServiceRequestDAO.addMedEquipReq(medicalEquipmentRequest);
  }

  public void addSanReq(sanitationRequest r) throws SQLException {
    sanitationRequestDAO.update(r);
  }

  public List<sanitationRequest> getAllSanReq() {
    return sanitationRequestDAO.getAll();
  }
}
