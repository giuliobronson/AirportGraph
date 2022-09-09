package model;

import server.Link;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RouteDAO {
    private final Connection database;

    public RouteDAO() throws SQLException {
        this.database = (new Link(0)).connect();
    }

    public void insertRoute(String origin, String destination, String path, Double distance) throws SQLException {
        String query = "INSERT INTO route (origin, destination, path, distance) VALUES (?, ?, ?, ?);";
        PreparedStatement command = database.prepareStatement(query);
        command.setString(1, origin);
        command.setString(2, destination);
        command.setString(3, path);
        command.setDouble(4, distance);
        command.executeUpdate();
    }
}
