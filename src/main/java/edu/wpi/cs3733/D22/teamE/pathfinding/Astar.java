package edu.wpi.cs3733.D22.teamE.pathfinding;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Astar {
  ArrayList<Node> path;
  PriorityQueue<Node> prioQ = new PriorityQueue<>();

  public Astar() {}

  public double heuristic(Node a, Node b) {
    return abs(a.getLocation().getXcoord() - b.getLocation().getXcoord())
        + abs(a.getLocation().getYcoord() - b.getLocation().getYcoord());
  }

  public ArrayList<Node> constructPath(Node n) {
    while (n.getPrevNode() != null) {
      path.add(0, n);
    }
    return path;
  }

  public ArrayList<Node> findPath(ArrayList<Node> graph, Node startPos, Node finalPos) {
    for (Node node : graph) {
      node.setDist(1000000000);
      node.setPrevNode(null);
    }

    prioQ.add(startPos);

    while (prioQ.size() > 0) {
      Node u = prioQ.poll();
      for (Edge e : u.getNeighbors()) { // need to change to incorporate edge lengths
        Node n = e.getOppositeNode(u);
        double alt = u.getDist() + e.getLength() + heuristic(finalPos, n);
        if (alt < n.getDist()) {
          n.setDist(alt);
          n.setPrevNode(u);
          prioQ.add(n);
        }
      }
    }
    return constructPath(finalPos);
  }
}
