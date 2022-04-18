package edu.wpi.cs3733.D22.teamE.pathfinding;

import java.util.ArrayList;

public class Node {
  private double xCoord;
  private double yCoord;
  private Node prevNode;
  private double dist;
  private ArrayList<Edge> neighboringEdges;
  private int edgeLength;

  public void setXCoord(double x) {
    this.xCoord = x;
  }

  public double getXCoord() {
    return this.xCoord;
  }

  public void setYCoord(double y) {
    this.yCoord = y;
  }

  public double getYCoord() {
    return this.yCoord;
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

  public void setNeighbors() {}

  public ArrayList<Edge> getNeighbors() {
    return this.neighboringEdges;
  }

  public void setEdgeLength(int edgeLength) {
    this.edgeLength = edgeLength;
  }

  public int getEdgeLength() {
    return edgeLength;
  }
}
