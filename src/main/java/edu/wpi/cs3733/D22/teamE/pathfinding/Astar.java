package edu.wpi.cs3733.D22.teamE.pathfinding;

import java.util.ArrayList;
import java.util.Optional;
import java.util.PriorityQueue;

import static java.lang.Math.abs;

public class Astar {
    ArrayList<Node> path;
    PriorityQueue<Node> prioQ = new PriorityQueue();

    public Astar(){}

    public double heuristic(Node a, Node b) {
        return abs(a.getXCoord() - b.getXCoord()) + abs(a.getYCoord() - b.getYCoord());
    }

    public ArrayList<Node> constructPath(Node n){
        while (n.getPrevNode() != null){
            path.add(0, n);
        }
        return path;
    }

    public ArrayList<Node> findPath(ArrayList<Node> graph, Node startPos, Node finalPos){
        for (Node node : graph){
            node.setDist(1000000000);
            node.setPrevNode(null);
        }

        prioQ.add(startPos);

        while(prioQ.size() > 0){
            Node u = prioQ.poll();
            for (Node n : u.getNeighbors()){        //need to change to incorporate edge lengths
                double alt = u.getDist() + n.getEdgeLength() + heuristic(finalPos, n);
                if (alt < n.getDist()){
                    n.setDist(alt);
                    n.setPrevNode(u);
                    prioQ.add(n);
                }
            }
        }
        return constructPath(finalPos);
    }

}
