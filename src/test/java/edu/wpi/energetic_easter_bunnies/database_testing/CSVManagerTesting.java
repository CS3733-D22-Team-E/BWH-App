package edu.wpi.energetic_easter_bunnies.database_testing;

import edu.wpi.energetic_easter_bunnies.database.CSVManager;
import edu.wpi.energetic_easter_bunnies.database.DBCreation;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class CSVManagerTesting {
  @Test
  public void testLoadLocationFile() throws SQLException, IOException {
    DBCreation.createTables();
    CSVManager.loadLocationCSV("TowerLocations.csv");
  }

  @Test
  public void testSaveLocationFile() throws SQLException, IOException {
    DBCreation.createTables();
    CSVManager.saveLocationCSV("saveLocationFile.csv");
  }

  /*@Test
  public void testLoadMedEquipReqFile() throws SQLException, IOException {
    DBCreation.createTables();
    CSVManager.loadMedEquipReqCSV("MedEquipRequest.csv");
  }

  @Test
  public void testSaveMedEquipReqFile() throws SQLException, IOException {
    DBCreation.createTables();
    CSVManager.saveLocationCSV("saveMedEquipRequestFile.csv");
  }*/
}
