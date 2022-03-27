package edu.wpi.energetic_easter_bunnies;

import java.util.List;

public interface LocationDAO {
  List<Location> getAllLocations();

  Location getLocation(int numID);

  void updateLocation(Location location);

  void deleteLocation(Location location);
}
