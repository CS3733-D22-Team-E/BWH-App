package edu.wpi.cs3733.D22.teamE;

import javax.xml.rpc.ServiceException;

public class CallAPI {
  private static CallAPI singleton;

  private CallAPI() {}

  public static CallAPI getInstance() {
    if (singleton == null) singleton = new CallAPI();

    return singleton;
  }

  public void openAPI() {
    try {
      Run.run(100, 100, 1100, 1000, "default.css", "", "");
    } catch (ServiceException e) {
      e.printStackTrace();
    }
  }
}
