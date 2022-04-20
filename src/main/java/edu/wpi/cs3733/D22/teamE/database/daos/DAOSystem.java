package edu.wpi.cs3733.D22.teamE.database.daos;

import edu.wpi.cs3733.D22.teamE.database.Employee;
import edu.wpi.cs3733.D22.teamE.database.Location;
import edu.wpi.cs3733.D22.teamE.database.MedicalEquipment;
import edu.wpi.cs3733.D22.teamE.database.medicineDelivery;
import edu.wpi.cs3733.D22.teamE.entity.*;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import java.sql.SQLException;
import java.util.List;

public class DAOSystem {
  private final AccountDAOImpl accountDAO;
  private final EmployeeDAOImpl employeeDAO;
  private final LabRequestDAOImpl labRequestDAO;
  private final LanguageRequestDAOImpl languageRequestDAO;
  private final LocationDAOImpl locationDAO;
  private final MealDeliveryRequestDAOImpl mealDeliveryRequestDAO;
  private final MedicalEquipmentDAOImpl medicalEquipmentDAO;
  private final MedicalEquipmentServiceRequestDAOImpl medicalEquipmentServiceRequestDAO;
  private final MedicineDeliveryDAOImpl medicineDeliveryDAO;
  private final SanitationRequestDAOImpl sanitationRequestDAO;
  private final ServiceRequestDAOImpl serviceRequestDAO;
  private final FacilitiesRequestDAOImpl facilitiesRequestDAO;
  private final GiftRequestDAOImpl giftRequestDAO;

  public DAOSystem() throws SQLException {
    accountDAO = new AccountDAOImpl();
    employeeDAO = new EmployeeDAOImpl();
    labRequestDAO = new LabRequestDAOImpl();
    languageRequestDAO = new LanguageRequestDAOImpl();
    locationDAO = new LocationDAOImpl();
    mealDeliveryRequestDAO = new MealDeliveryRequestDAOImpl();
    medicalEquipmentDAO = new MedicalEquipmentDAOImpl();
    medicalEquipmentServiceRequestDAO = new MedicalEquipmentServiceRequestDAOImpl();
    medicineDeliveryDAO = new MedicineDeliveryDAOImpl();
    sanitationRequestDAO = new SanitationRequestDAOImpl();
    serviceRequestDAO = new ServiceRequestDAOImpl();
    facilitiesRequestDAO = new FacilitiesRequestDAOImpl();
    giftRequestDAO = new GiftRequestDAOImpl();
  }

  public List<Account> getAllAccounts() {
    return accountDAO.getAll();
  }

  public Account getAccount(String id) {
    return accountDAO.get(id);
  }

  public void updateAccount(Account account) {
    accountDAO.update(account);
  }

  public void deleteAccount(Account account) {
    accountDAO.delete(account);
  }

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

  public List<languageInterpreterRequest> getAllLangInterpRequests() {
    return languageRequestDAO.getAll();
  }

  public languageInterpreterRequest getLangInterpRequest(String id) {
    return languageRequestDAO.get(id);
  }

  public void updateLangInterpRequest(languageInterpreterRequest languageInterpreterRequest) {
    languageRequestDAO.update(languageInterpreterRequest);
  }

  public void deleteLangInterpRequest(languageInterpreterRequest languageInterpreterRequest) {
    languageRequestDAO.delete(languageInterpreterRequest);
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

  public List<mealDeliveryRequest> getAllMealDelivReq() {
    return mealDeliveryRequestDAO.getAll();
  }

  public mealDeliveryRequest getMealDelivReq(String id) {
    return mealDeliveryRequestDAO.get(id);
  }

  public void updateMealDelivReq(mealDeliveryRequest mealDeliveryRequest) {
    mealDeliveryRequestDAO.update(mealDeliveryRequest);
  }

  public void deleteMealDelivReq(mealDeliveryRequest mealDeliveryRequest) {
    mealDeliveryRequestDAO.delete(mealDeliveryRequest);
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

  public List<medicineDelivery> getAllMedDeliveries() {
    return medicineDeliveryDAO.getAll();
  }

  public medicineDelivery getMedDelivery(String id) {
    return medicineDeliveryDAO.get(id);
  }

  public void updateMedDelivery(medicineDelivery medicineDelivery) {
    medicineDeliveryDAO.update(medicineDelivery);
  }

  public void deleteMedDelivery(medicineDelivery medicineDelivery) {
    medicineDeliveryDAO.delete(medicineDelivery);
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

  public List<facilitiesRequest> getAllFacilitiesRequests() {
    return facilitiesRequestDAO.getAll();
  }

  public facilitiesRequest getFacilitiesRequest(String id) {
    return facilitiesRequestDAO.get(id);
  }

  public void updateFacilitiesRequest(facilitiesRequest request) {
    facilitiesRequestDAO.update(request);
  }

  public void deleteFacilitiesRequest(facilitiesRequest request) {
    facilitiesRequestDAO.delete(request);
  }

  public List<giftDeliveryRequest> getAllGifts() {
    return giftRequestDAO.getAll();
  }

  public giftDeliveryRequest getGiftDelivery(String id) {
    return giftRequestDAO.get(id);
  }

  public void updateGiftDelivery(giftDeliveryRequest request) {
    giftRequestDAO.update(request);
  }

  public void updateRoomLocation(serviceRequest request, int newXCoord, int newYCoord)
      throws SQLException {
    serviceRequestDAO.updateRoomLocation(request, newXCoord, newYCoord);
  }

  public void updateCurrentLocation(MedicalEquipment equipment, int newXCoord, int newYCoord)
      throws SQLException {
    medicalEquipmentDAO.updateCurrentLocation(equipment, newXCoord, newYCoord);
  }

  public void deleteGiftDelivery(giftDeliveryRequest request) {
    giftRequestDAO.delete(request);
  }
}
