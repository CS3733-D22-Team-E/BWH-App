package edu.wpi.cs3733.D22.teamE;

import edu.wpi.cs3733.D22.teamE.database.CSVManager;
import edu.wpi.cs3733.D22.teamZ.api.API;
import javax.xml.rpc.ServiceException;

public class CallAPI {
  private static CallAPI singleton;
  private static API transportAPISingleton;

  private CallAPI() {}

  public static CallAPI getInstance() {
    if (singleton == null) singleton = new CallAPI();

    return singleton;
  }

  public void openFloralAPI() {
    try {
      Run.run(100, 100, 1100, 1000, "default.css", "", "");
    } catch (ServiceException e) {
      e.printStackTrace();
    }
  }

  public API getExternalTransportAPI() {
    if (transportAPISingleton == null) {
      transportAPISingleton = new API();
    }

    return transportAPISingleton;
  }

  public void openExternalTransportAPI() {
    try {
      getExternalTransportAPI()
          .run(
              100,
              100,
              1100,
              1000,
              "edu/wpi/cs3733/D22/teamZ/api/styles/ServiceRequestDefault.css",
              "",
              "");
      CSVManager.saveExternalTransportCSV();
    } catch (edu.wpi.cs3733.D22.teamZ.api.exception.ServiceException e) {
      e.printStackTrace();
    }
  }
}
