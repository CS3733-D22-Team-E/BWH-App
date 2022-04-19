package edu.wpi.cs3733.D22.teamE.database.daos;

import edu.wpi.cs3733.D22.teamE.database.DBConnect;
import edu.wpi.cs3733.D22.teamE.pathfinding.Edge;
import edu.wpi.cs3733.D22.teamE.pathfinding.Node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EdgesDAOImpl implements DAO<Edge> {
    List<Edge> edges;
    Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();

    public EdgesDAOImpl() throws SQLException {
        edges = new ArrayList<Edge>();
        DAOSystem system = new DAOSystem();
        String query = "SELECT * FROM EDGES ORDER BY EDGEID DESC";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.execute();
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            String edgeID = rs.getString("EDGEID");
            String start = rs.getString("START_NODE");
            String end = rs.getString("END_NODE");

            Node startNode = new Node(system.getLocation(start));
            Node endNode = new Node(system.getLocation(end));
            Edge edge =
                    new Edge(
                            edgeID, startNode, endNode);

            edges.add(edge);
        }
    }

    @Override
    public List<Edge> getAll() {
        return edges;
    }

    @Override
    public Edge get(String id) {
        for (Edge edge : edges) {
            if (edge.getEdgeID().equals(id)) return edge;
        }

        System.out.println("Edge with edgeID " + id + " not found");
        throw new NullPointerException();
    }

    @Override
    public void update(Edge item) {
        edges.add(item);
    }

    @Override
    public void delete(Edge item) {
        edges.remove(item);
    }
}
