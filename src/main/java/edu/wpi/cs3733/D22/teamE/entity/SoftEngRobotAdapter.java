package edu.wpi.cs3733.D22.teamE.entity;

import edu.wpi.cs3733.D22.teamZ.api.entity.ExternalTransportRequest;
import edu.wpi.cs3733.D22.teamZ.api.entity.RequestStatus;
import edu.wpi.teamname.RobotRequest;
import java.time.LocalDate;
import java.util.Arrays;

public class SoftEngRobotAdapter implements RequestInterface{

    private RobotRequest r;

    public SoftEngRobotAdapter(RobotRequest r) {
        this.r = r;
    }

    public RobotRequest getRequest() {
        return r;
    }

    public int getBotID(){
        return r.getBotID();
    }



    @Override
    public serviceRequest.Type getRequestType() {
        return serviceRequest.Type.SERVICEREQUEST;
    }

    @Override
    public void setRequestType(serviceRequest.Type requestType) {

    }

    @Override
    public String getServiceRequestID() {
        String id = String.valueOf(r.getServiceID());
        return id;
    }

    @Override
    public void setServiceRequestID(String requestID) {
        r.setServiceID();
    }

    @Override
    public LocalDate getRequestDate() {
        return LocalDate.now();
    }

    @Override
    public void setRequestDate(LocalDate requestDate) {

    }

    @Override
    public LocalDate getDeliveryDate() {
        return LocalDate.now();
    }

    @Override
    public void setDeliveryDate(LocalDate deliveryDate) {

    }

    @Override
    public String getRequestStatus() {
        return r.getStatus();
    }

    @Override
    public void setRequestStatus(String status) {
    }

    @Override
    public boolean getIsUrgent() {
        return false;
    }

    @Override
    public void setIsUrgent(boolean urgent) {
    }

    @Override
    public String getStaffAssignee() {
        String employee = String.valueOf(r.getEmployeeID());
        return employee;
    }

    @Override
    public void setStaffAssignee(String assignee) {
    }

    @Override
    public String getRoomID() {
        return r.getNodeID();
    }

    @Override
    public void setRoomID(String roomID) {
    }

    @Override
    public String getFloorID() {
        return null;
    }

    @Override
    public void setFloorID(String floor) {

    }

    @Override
    public String getOtherNotes() {
        return r.getDetails();
    }

    @Override
    public void setOtherNotes(String otherNotes) {
    }

    @Override
    public double getxCoord() {
        return 0;
    }

    @Override
    public double getyCoord() {
        return 0;
    }
}
