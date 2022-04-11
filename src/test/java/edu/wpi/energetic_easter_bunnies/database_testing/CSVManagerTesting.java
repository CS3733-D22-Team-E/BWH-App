package edu.wpi.energetic_easter_bunnies.database_testing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.energetic_easter_bunnies.database.CSVManager;
import edu.wpi.energetic_easter_bunnies.database.DBCreation;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
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
    assertTrue(new File("c:/temp/temp.txt").exists());
    // assertTrue(fileCreatedinLast15s(filename));
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

  private boolean fileCreatedinLast15s(String filename) {

    try {
      Path fileDir = Paths.get(filename);
      fileDir = fileDir.toAbsolutePath();

      BasicFileAttributes attr = Files.readAttributes(fileDir, BasicFileAttributes.class);

      System.out.println("creationTime: " + attr.creationTime());

      Instant I = attr.creationTime().toInstant();
      long fileTime = I.getEpochSecond(); // file creation time in seconds
      long currentTime =
          (new Date()).getTime() / 1000; // current system time in ms converted to seconds
      return (currentTime - fileTime < 15); // if file was created less than 15 seconds ago

    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }
}
