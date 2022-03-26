package edu.wpi.energetic_easter_bunnies;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class LocationDAOImplTesting {
  @Test
  public void testLocation() throws SQLException {
    LocationDAO locationDAO = new LocationDAOImpl();
    System.out.println(locationDAO.getLocation(4));
  }

  @Test
  public void testLocation2() throws SQLException {
    LocationDAO locationDAO = new LocationDAOImpl();
    Location location = new Location();
    locationDAO.updateLocation(location);
    assertTrue(locationDAO.getAllLocations().contains(location));
  }
}
