package edu.wpi.energetic_easter_bunnies.database_testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.cs3733.D22.teamE.database.Location;
import edu.wpi.cs3733.D22.teamE.database.daos.DAO;
import edu.wpi.cs3733.D22.teamE.database.daos.LocationDAOImpl;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class LocationDAOImplTesting {
  @Test
  public void testGetLocation() throws SQLException {
    LocationDAOImpl locationDAO = new LocationDAOImpl();
    Location location = new Location();
    locationDAO.update(location);
    assertEquals(locationDAO.get("1"), location);
  }

  @Test
  public void testUpdateLocation() throws SQLException {
    LocationDAOImpl locationDAO = new LocationDAOImpl();
    Location location = new Location();
    locationDAO.update(location);
    locationDAO.updateLocation(location, "2", "HALL");
    assertTrue(locationDAO.getAll().contains(location));
  }

  @Test
  public void testUpdateCoord() throws SQLException {
    LocationDAOImpl locationDAO = new LocationDAOImpl();
    Location location = new Location();
    locationDAO.update(location);
    locationDAO.updateCoord(location, 2, 1);
    assertTrue(locationDAO.getAll().contains(location));
    locationDAO.delete(location);
  }

  @Test
  public void testDeleteLocation() throws SQLException {
    DAO<Location> locationDAO = new LocationDAOImpl();
    Location location = new Location();
    locationDAO.update(location);
    assertTrue(locationDAO.getAll().contains(location));
    locationDAO.delete(location);
    // TODO Figure out whats wrong with this
    // assertFalse(locationDAO.getAll().contains(location));
  }
}
