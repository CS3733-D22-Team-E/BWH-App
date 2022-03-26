package edu.wpi.energetic_easter_bunnies;

import java.util.List;

public interface LocationDAO {
  public List<Location> getAllLocations();

  public Location getLocation(int numID);

  public void updateLocation(Location location);

  public void deleteLocation(Location location);
}
