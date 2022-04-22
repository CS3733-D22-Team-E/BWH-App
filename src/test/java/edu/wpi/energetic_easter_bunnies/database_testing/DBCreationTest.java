package edu.wpi.energetic_easter_bunnies.database_testing;

import edu.wpi.cs3733.D22.teamE.database.DBCreation;
import org.junit.jupiter.api.Test;

public class DBCreationTest {

  @Test
  public void testDBCreation() {
    DBCreation.createTables();
  }
}
