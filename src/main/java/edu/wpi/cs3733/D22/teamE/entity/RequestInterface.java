package edu.wpi.cs3733.D22.teamE.entity;

import java.time.LocalDate;
import java.util.Random;

public interface RequestInterface {

  public serviceRequest.Type getRequestType();

  public void setRequestType(serviceRequest.Type requestType);

  public String getServiceRequestID();

  public void setServiceRequestID(String requestID);

  public LocalDate getRequestDate();

  public void setRequestDate(LocalDate requestDate);

  public LocalDate getDeliveryDate();

  public void setDeliveryDate(LocalDate deliveryDate);

  public String getRequestStatus();

  public void setRequestStatus(String status);

  public boolean getIsUrgent();

  public void setIsUrgent(boolean urgent);

  public String getStaffAssignee();

  public void setStaffAssignee(String assignee);

  public String getRoomID();

  public void setRoomID(String roomID);

  public String getFloorID();

  public void setFloorID(String floor);

  public String getOtherNotes();

  public void setOtherNotes(String otherNotes);

  public static String generateRandomID(int length) {
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringBuilder result = new StringBuilder();
    Random random = new Random();

    for (int i = 0; i < length; i++) {
      char randChar = alphabet.charAt(random.nextInt(alphabet.length()));
      result.append(randChar);
    }
    return result.toString();
  }

  double getxCoord();

  double getyCoord();
}
