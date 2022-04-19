package edu.wpi.cs3733.D22.teamE.pathfinding;

import edu.wpi.cs3733.D22.teamE.database.Location;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class hospitalGraph {

  ArrayList<Node> graph;
  List<Node> allNodes;
  List<Edge> allEdges;

  public hospitalGraph() throws SQLException {
    populateNodes();
    populateEdges();
  }

  private void populateNodes() throws SQLException {
    DAOSystem system = new DAOSystem();

    for(Location location : system.getAllLocations()) {
      Node node = new Node(location, null);
      allNodes.add(node);
    }
  }

  private void populateEdges() {

  }

  private void setAllEdges() {

  }
}
