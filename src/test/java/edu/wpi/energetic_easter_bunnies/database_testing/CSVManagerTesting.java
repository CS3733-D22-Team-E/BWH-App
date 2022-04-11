package edu.wpi.energetic_easter_bunnies.database_testing;

import edu.wpi.energetic_easter_bunnies.database.CSVManager;
import edu.wpi.energetic_easter_bunnies.database.DBCreation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;
import java.time.Instant;

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

  private boolean fileCreatedinLastMinute(String filename) {

    try {
      Path fileDir = Paths.get(filename);
      System.out.println(fileDir.toAbsolutePath());

      BasicFileAttributes attr =
              Files.readAttributes(fileDir, BasicFileAttributes.class);

      System.out.println("creationTime: " + attr.creationTime());
      System.out.println("lastAccessTime: " + attr.lastAccessTime());
      System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

      Instant I = attr.creationTime().toInstant();
      I.getEpochSecond();// seconds since 1970

    } catch (IOException e) {
      e.printStackTrace();
    }

    Instant.getEpochSeconds();
  }
}
