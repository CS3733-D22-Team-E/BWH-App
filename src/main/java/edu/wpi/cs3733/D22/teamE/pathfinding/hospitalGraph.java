package edu.wpi.cs3733.D22.teamE.pathfinding;

import edu.wpi.cs3733.D22.teamE.database.Location;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class hospitalGraph {

  DAOSystem system;
  ArrayList<Node> graph;
  List<Node> allNodes;
  List<Edge> allEdges;

  public hospitalGraph() throws SQLException, IOException {
    system = new DAOSystem();
    populateNodes();
    populateEdges();
    // setAllEdges();
  }

  private void populateNodes() throws SQLException {
    for (Location location : system.getAllLocations()) {
      Node node = new Node(location);
      allNodes.add(node);
    }
  }

  private void populateEdges() throws SQLException, IOException {
    //    for (Edge edge : system.getAllEdges()) {
    //      allEdges.add(edge);
    //    }
  }

  private void setAllEdges() {
    for (Node node : allNodes) {
      for (Edge edge : allEdges) {
        if (edge.getStartNode().equals(node) || edge.getEndNode().equals(node)) {
          node.addNeighbor(edge);
        }
      }
    }
  }

  public DAOSystem getSystem() {
    return system;
  }

  public void setSystem(DAOSystem system) {
    this.system = system;
  }

  public ArrayList<Node> getGraph() {
    return graph;
  }

  public void setGraph(ArrayList<Node> graph) {
    this.graph = graph;
  }

  public List<Node> getAllNodes() {
    return allNodes;
  }

  public void setAllNodes(List<Node> allNodes) {
    this.allNodes = allNodes;
  }

  public List<Edge> getAllEdges() {
    return allEdges;
  }

  public void setAllEdges(List<Edge> allEdges) {
    this.allEdges = allEdges;
  }
}
