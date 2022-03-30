package edu.wpi.energetic_easter_bunnies.database_testing;

import edu.wpi.energetic_easter_bunnies.database.CSVManager;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class CSVManagerTesting {
  @Test
  public void testLoadFile() throws SQLException, IOException {
    CSVManager.loadLocationCSV("TowerLocations.csv");
  }

  @Test
  public void testSaveFile() throws SQLException, IOException {
    CSVManager.saveLocationCSV("saveFile.csv");
  }
}
