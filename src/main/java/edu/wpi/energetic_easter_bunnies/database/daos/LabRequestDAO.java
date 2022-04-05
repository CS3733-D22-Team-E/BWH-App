package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.entity.labRequest;
import java.sql.SQLException;
import java.util.List;

public interface LabRequestDAO {

  List<labRequest> getAllLabRequests();

  void addLabRequest(labRequest labRequest) throws SQLException;

  void updateLabServiceRequest(labRequest labRequest, String newRequestStatus) throws SQLException;

  void deleteLabRequest(labRequest labRequest) throws SQLException;
}
