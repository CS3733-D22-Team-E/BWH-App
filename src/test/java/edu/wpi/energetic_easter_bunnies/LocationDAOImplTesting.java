package edu.wpi.energetic_easter_bunnies;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.energetic_easter_bunnies.database.Location;
import edu.wpi.energetic_easter_bunnies.database.LocationDAO;
import edu.wpi.energetic_easter_bunnies.database.LocationDAOImpl;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class LocationDAOImplTesting {
  @Test
  public void testGetLocation() throws SQLException {
    LocationDAO locationDAO = new LocationDAOImpl();
    System.out.println(locationDAO.getLocation(4));
  }

  @Test
  public void testUpdateLocation() throws SQLException {
    LocationDAO locationDAO = new LocationDAOImpl();
    Location location = new Location();
    locationDAO.updateLocation(location);
    assertTrue(locationDAO.getAllLocations().contains(location));
  }

  @Test
  public void testDeleteLocation() throws SQLException {
    LocationDAO locationDAO = new LocationDAOImpl();
    Location location = new Location();
    locationDAO.updateLocation(location);
    assertTrue(locationDAO.getAllLocations().contains(location));
    locationDAO.deleteLocation(location);
    assertTrue(!locationDAO.getAllLocations().contains(location));
  }
}
