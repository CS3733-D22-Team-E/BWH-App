package edu.wpi.cs3733.D22.teamE.entity;

public interface EntityInterface {

  int getNumID();

  void setNumID(int num);

  Location getLocation();

  String getRoomID();

  String getFloorID();

  void setLocation(String NodeID) throws NullPointerException;

  void setLocation(Location location) throws NullPointerException;

  void setLocation(int xcoord, int ycoord) throws NullPointerException;

  int getXCoord();

  int getYCoord();
}
