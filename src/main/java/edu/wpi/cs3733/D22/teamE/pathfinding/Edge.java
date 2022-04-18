package edu.wpi.cs3733.D22.teamE.pathfinding;

import java.util.ArrayList;

public class Edge {

  private double length;
  private ArrayList<Node> neighboringNodes;

  public Edge() {}

  public double getLength() {
    return this.length;
  }

  public void setLength(double l) {
    this.length = l;
  }

  public ArrayList<Node> getNeighbors() {
    return this.neighboringNodes;
  }

  // What should this take in?
  public void setNeighboringNode(Node n) {
    this.neighboringNodes.add(n);
  }

  public Node getOppositeNode(Node n) {
    for (Node m : this.getNeighbors()) {
      if (m != n) {
        return m;
      }
    }
    return null;
  }
}
