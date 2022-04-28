package edu.wpi.cs3733.D22.teamE.database.daos;

import edu.wpi.cs3733.D22.teamE.CallAPI;
import edu.wpi.cs3733.D22.teamE.controllers.dashboard.DashboardHandler;
import edu.wpi.cs3733.D22.teamE.entity.*;
import edu.wpi.cs3733.D22.teamE.entity.Employee;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import edu.wpi.cs3733.D22.teamE_api.APIDatabase.dao.FloralRequestDAOImpl;
import edu.wpi.cs3733.D22.teamE_api.entity.FloralServiceRequest;
import edu.wpi.cs3733.D22.teamZ.api.API;
import edu.wpi.cs3733.D22.teamZ.api.entity.ExternalTransportRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOSystem {
  private final AccountDAOImpl accountDAO;
  private final EmployeeDAOImpl employeeDAO;
  private final FacilitiesRequestDAOImpl facilitiesRequestDAO;
  private final FloralRequestDAOImpl floralRequestDAO;
  private final GiftRequestDAOImpl giftRequestDAO;
  private final LabRequestDAOImpl labRequestDAO;
  private final LanguageRequestDAOImpl languageRequestDAO;
  private final LocationDAOImpl locationDAO;
  private final MealDeliveryRequestDAOImpl mealDeliveryRequestDAO;
  private final MedicalEquipmentDAOImpl medicalEquipmentDAO;
  private final MedicalEquipmentServiceRequestDAOImpl medicalEquipmentServiceRequestDAO;
  private final MedicineDeliveryDAOImpl medicineDeliveryDAO;
  private final SanitationRequestDAOImpl sanitationRequestDAO;
  private final SecurityRequestDAOImpl securityRequestDAO;
  private final ServiceRequestDAOImpl serviceRequestDAO;
  private final API externalTransportAPI;

  private final List<DashboardHandler> observers; // for observer functionality

  public DAOSystem() throws SQLException {
    accountDAO = new AccountDAOImpl();
    employeeDAO = new EmployeeDAOImpl();
    facilitiesRequestDAO = new FacilitiesRequestDAOImpl();
    floralRequestDAO = new FloralRequestDAOImpl();
    giftRequestDAO = new GiftRequestDAOImpl();
    labRequestDAO = new LabRequestDAOImpl();
    languageRequestDAO = new LanguageRequestDAOImpl();
    locationDAO = new LocationDAOImpl();
    mealDeliveryRequestDAO = new MealDeliveryRequestDAOImpl();
    medicalEquipmentDAO = new MedicalEquipmentDAOImpl();
    medicalEquipmentServiceRequestDAO = new MedicalEquipmentServiceRequestDAOImpl();
    medicineDeliveryDAO = new MedicineDeliveryDAOImpl();
    sanitationRequestDAO = new SanitationRequestDAOImpl();
    securityRequestDAO = new SecurityRequestDAOImpl();
    serviceRequestDAO = new ServiceRequestDAOImpl();
    externalTransportAPI = CallAPI.getInstance().getExternalTransportAPI();
    observers = new ArrayList<>();
  }

  public List<Account> getAllAccounts() {
    return accountDAO.getAll();
  }

  public List<Employee> getAllEmployee() {
    return employeeDAO.getAll();
  }

  public List<facilitiesRequest> getAllFacilitiesRequests() {
    return facilitiesRequestDAO.getAll();
  }

  public List<FloralServiceRequest> getAllFloralRequests() {
    return floralRequestDAO.getAll();
  }

  public List<giftDeliveryRequest> getAllGiftRequests() {
    return giftRequestDAO.getAll();
  }

  public List<labRequest> getAllLabRequests() {
    return labRequestDAO.getAll();
  }

  public List<languageInterpreterRequest> getAllLanguageRequests() {
    return languageRequestDAO.getAll();
  }

  public List<Location> getAllLocations() {
    return locationDAO.getAll();
  }

  public Location getLocation(int x, int y) {
    return locationDAO.get(x, y);
  }

  public Location getClosestLocation(int x, int y, String floor) {
    return locationDAO.getClosest(x, y, floor);
  }

  public void updateLocation(Location location) {
    locationDAO.update(location);
    notifyAllObservers();
  }

  public void deleteLocation(Location location) {
    locationDAO.delete(location);
  }

  public List<mealDeliveryRequest> getAllMealDelivReq() {
    return mealDeliveryRequestDAO.getAll();
  }

  public List<MedicalEquipment> getAllMedicalEquipments() {
    return medicalEquipmentDAO.getAll();
  }

  public List<medicalEquipmentRequest> getAllMedicalEquipmentRequests() {
    return medicalEquipmentServiceRequestDAO.getAll();
  }

  public List<medicineDelivery> getAllMedicineRequests() {
    return medicineDeliveryDAO.getAll();
  }

  public List<sanitationRequest> getAllSanitationRequests() {
    return sanitationRequestDAO.getAll();
  }

  public List<securityRequest> getAllSecurityRequests() {
    return securityRequestDAO.getAll();
  }

  public List<RequestInterface> getAllServiceRequests() {
    List<RequestInterface> l = serviceRequestDAO.getAll();
    // now handle API service requests
    List<FloralServiceRequest> floralL = this.getAllFloralRequests();
    List<ExternalTransportRequest> externalL = this.getAllExternalTransportRequests();
    for (FloralServiceRequest r : floralL) {
      FloralRequestAdapter convertedReq = new FloralRequestAdapter(r);
      if (!l.contains(convertedReq)) l.add(convertedReq);
    }
    for (ExternalTransportRequest r : externalL) {
      ExternalTransportAdapter convertedReq = new ExternalTransportAdapter(r);
      if (!l.contains(convertedReq)) l.add(convertedReq);
    }
    return l;
  }

  public Account getAccount(String id) {
    return accountDAO.get(id);
  }

  public Employee getEmployee(String id) {
    return employeeDAO.get(id);
  }

  public facilitiesRequest getFacilityRequest(String id) {
    return facilitiesRequestDAO.get(id);
  }

  // TODO: Floral get/update/delete methods?

  public giftDeliveryRequest getGiftRequest(String id) {
    return giftRequestDAO.get(id);
  }

  public labRequest getLabRequest(String id) {
    return labRequestDAO.get(id);
  }

  public languageInterpreterRequest getLanguageRequest(String id) {
    return languageRequestDAO.get(id);
  }

  public Location getLocation(String id) {
    return locationDAO.get(id);
  }

  public mealDeliveryRequest getMealRequest(String id) {
    return mealDeliveryRequestDAO.get(id);
  }

  public MedicalEquipment getMedicalEquipment(String id) {
    return medicalEquipmentDAO.get(id);
  }

  public medicalEquipmentRequest getMedicalEquipmentRequest(String id) {
    return medicalEquipmentServiceRequestDAO.get(id);
  }

  public medicineDelivery getMedicineRequest(String id) {
    return medicineDeliveryDAO.get(id);
  }

  public sanitationRequest getSanitationRequest(String id) {
    return sanitationRequestDAO.get(id);
  }

  public securityRequest getSecurityRequest(String id) {
    return securityRequestDAO.get(id);
  }

  public List<ExternalTransportRequest> getAllExternalTransportRequests() {
    return externalTransportAPI.getAllExternalTransportRequests();
    // return new ArrayList<FloralServiceRequest>();
  }

  public RequestInterface getServiceRequest(String id) {
    return serviceRequestDAO.get(id);
  }

  public void update(Account account) {
    accountDAO.update(account);
    notifyAllObservers();
  }

  public void update(Employee employee) {
    employeeDAO.update(employee);
    notifyAllObservers();
  }

  public void update(facilitiesRequest request) {
    facilitiesRequestDAO.update(request);
    notifyAllObservers();
  }

  public void update(giftDeliveryRequest request) {
    giftRequestDAO.update(request);
    notifyAllObservers();
  }

  public void update(labRequest request) {
    labRequestDAO.update(request);
    notifyAllObservers();
  }

  public void update(languageInterpreterRequest request) {
    languageRequestDAO.update(request);
    notifyAllObservers();
  }

  public void update(Location location) {
    locationDAO.update(location);
    notifyAllObservers();
  }

  public void update(mealDeliveryRequest request) {
    mealDeliveryRequestDAO.update(request);
    notifyAllObservers();
  }

  public void update(MedicalEquipment equipment) {
    medicalEquipmentDAO.update(equipment);
    notifyAllObservers();
  }

  public void update(medicalEquipmentRequest request) {
    medicalEquipmentServiceRequestDAO.update(request);
    notifyAllObservers();
  }

  public void update(medicineDelivery request) {
    medicineDeliveryDAO.update(request);
    notifyAllObservers();
  }

  public void update(sanitationRequest request) {
    sanitationRequestDAO.update(request);
    notifyAllObservers();
  }

  public void update(securityRequest request) {
    securityRequestDAO.update(request);
    notifyAllObservers();
  }

  public void update(RequestInterface request) {
    if (request.getRequestType().equals(serviceRequest.Type.SERVICEREQUEST)) {
      if (request instanceof FloralRequestAdapter) {
        FloralServiceRequest newReq = ((FloralRequestAdapter) request).getRequest();
        floralRequestDAO.delete(floralRequestDAO.get(newReq.getServiceRequestID()));
        floralRequestDAO.update(newReq);
      } else if (request instanceof ExternalTransportAdapter) {
        ExternalTransportRequest newReq = ((ExternalTransportAdapter) request).getRequest();
        externalTransportAPI.updateExternalTransportRequest(newReq);
      }
    } else {
      serviceRequestDAO.update((serviceRequest) request);
    }
    notifyAllObservers();
  }

  public void delete(Account account) {
    accountDAO.delete(account);
    notifyAllObservers();
  }

  public void delete(Employee employee) {
    employeeDAO.delete(employee);
    notifyAllObservers();
  }

  public void delete(facilitiesRequest request) {
    facilitiesRequestDAO.delete(request);
    notifyAllObservers();
  }

  public void delete(giftDeliveryRequest request) {
    giftRequestDAO.delete(request);
    notifyAllObservers();
  }

  public void delete(labRequest request) {
    labRequestDAO.delete(request);
    notifyAllObservers();
  }

  public void delete(languageInterpreterRequest request) {
    languageRequestDAO.delete(request);
    notifyAllObservers();
  }

  public void delete(Location location) {
    locationDAO.delete(location);
    notifyAllObservers();
  }

  public void delete(mealDeliveryRequest request) {
    mealDeliveryRequestDAO.delete(request);
    notifyAllObservers();
  }

  public void delete(MedicalEquipment equipment) {
    medicalEquipmentDAO.delete(equipment);
    notifyAllObservers();
  }

  public void delete(medicalEquipmentRequest request) {
    medicalEquipmentServiceRequestDAO.delete(request);
    notifyAllObservers();
  }

  public void delete(medicineDelivery request) {
    medicineDeliveryDAO.delete(request);
    notifyAllObservers();
  }

  public void delete(sanitationRequest request) {
    sanitationRequestDAO.delete(request);
    notifyAllObservers();
  }

  public void delete(securityRequest request) {
    securityRequestDAO.delete(request);
    notifyAllObservers();
  }

  public void delete(RequestInterface request) {
    serviceRequestDAO.delete(request);
    notifyAllObservers();
  }

  /*
    public Location get(String nodeID) {
      return locationDAO.get(nodeID);
    }
  */
  public void updateLocation(Location location, String newFloor, String newNodeType)
      throws SQLException {
    locationDAO.updateLocation(location, newFloor, newNodeType);
    notifyAllObservers();
  }

  public void updateCoord(Location location, int newXCoord, int newYCoord) throws SQLException {
    locationDAO.updateCoord(location, newXCoord, newYCoord);
    notifyAllObservers();
  }

  public List<MedicalEquipment> getMedicalEquipments(
      String equipmentType, int equipmentQuantity, String roomID, String MED_EQUIPMENTID)
      throws SQLException {
    notifyAllObservers();
    return medicalEquipmentDAO.getMedicalEquipments(
        equipmentType, equipmentQuantity, roomID, MED_EQUIPMENTID);
  }

  public void sendToCleaning(List<MedicalEquipment> equipmentList) throws SQLException {
    medicalEquipmentDAO.sendToCleaning(equipmentList);
    notifyAllObservers();
  }

  public void addMedEquipReq(medicalEquipmentRequest medicalEquipmentRequest) throws SQLException {
    medicalEquipmentServiceRequestDAO.addMedEquipReq(medicalEquipmentRequest);
    notifyAllObservers();
  }

  public void updateRoomLocation(serviceRequest request, int newXCoord, int newYCoord)
      throws SQLException {
    serviceRequestDAO.updateRoomLocation(request, newXCoord, newYCoord);
    notifyAllObservers();
  }

  public void updateCurrentLocation(MedicalEquipment equipment, int newXCoord, int newYCoord)
      throws SQLException {
    medicalEquipmentDAO.updateCurrentLocation(equipment, newXCoord, newYCoord);
    notifyAllObservers();
  }

  public void deleteGiftDelivery(giftDeliveryRequest request) {
    giftRequestDAO.delete(request);
    notifyAllObservers();
  }

  public void update(EntityInterface node) {
    if (node instanceof MedicalEquipment) update((MedicalEquipment) node);
    else if (node instanceof RequestInterface) update((RequestInterface) node);
    notifyAllObservers();
  }

  public List<MedicalEquipment> getAllMedEquip() {
    return medicalEquipmentDAO.getAll();
  }

  public void attach(DashboardHandler observer) {
    observers.add(observer);
  }

  public void notifyAllObservers() {
    for (DashboardHandler observer : observers) {
      observer.update();
    }
  }
}
