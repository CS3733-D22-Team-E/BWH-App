package edu.wpi.cs3733.D22.teamE.entity;

public interface EntityInterface {

    int getNumID();

    void setNumID(int num);

    Location getLocation();

    void setLocation(String NodeID);

    void setLocation(Location location);

    void setLocation(int xcoord, int ycoord);
}
