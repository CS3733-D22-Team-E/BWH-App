package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;

public abstract class DashboardHandler {

  public abstract void update();

  public abstract void setSubject(DAOSystem subject);
}
