package edu.wpi.energetic_easter_bunnies.database_testing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.cs3733.D22.teamE.database.CSVManager;
import edu.wpi.cs3733.D22.teamE.database.DBCreation;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class CSVManagerTesting {

  @Test
  public void testLoadLocationFile() throws SQLException, IOException {
    DBCreation.createTables();
    assertTrue(CSVManager.loadLocationCSV("TowerLocations.csv"));
  }

  @Test
  public void testSaveLocationFile() throws SQLException, IOException {
    DBCreation.createTables();
    String filename = "saveLocationFile.csv";
    CSVManager.saveLocationCSV(filename);

    assertTrue(new File("./CSVsaveFiles/" + filename).exists());
  }
}
