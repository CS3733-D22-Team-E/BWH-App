package edu.wpi.cs3733.D22.teamE.pathfinding;

import edu.wpi.cs3733.D22.teamE.database.Location;
import java.util.List;

public class Node {
  Location location;
  private Node prevNode;
  private double dist;
  private final List<Edge> neighboringEdges;

  public Node(Location location, List<Edge> neighboringEdges) {
    this.location = location;
    this.neighboringEdges = neighboringEdges;
  }

  public void setPrevNode(Node n) {
    this.prevNode = n;
  }

  public Node getPrevNode() {
    return this.prevNode;
  }

  public void setDist(double d) {
    this.dist = d;
  }

  public double getDist() {
    return this.dist;
  }

  public int compareTo(Node node) {
    if (node.dist > this.dist) {
      return 1;
    } else if (node.dist < this.dist) {
      return -1;
    } else {
      return 0;
    }
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public void setNeighbors() {}

  public List<Edge> getNeighbors() {
    return this.neighboringEdges;
  }
}
