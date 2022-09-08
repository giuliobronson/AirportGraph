package model;

import server.Link;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AirportDAO {
    private final Connection database;

    public AirportDAO() throws SQLException {
        this.database = (new Link(0)).connect();
    }

    public ResultSet selectAirports() throws SQLException {
        String query = "SELECT * FROM airport;";
        PreparedStatement command = database.prepareStatement(query);
        return command.executeQuery();
    }

    public Airport selectAirport(String iata) throws SQLException {
        String query = "SELECT * FROM airport WHERE iata = ?";
        PreparedStatement command = database.prepareStatement(query);
        command.setString(1, iata);
        ResultSet result = command.executeQuery(); result.next();
        return new Airport(result.getString("iata"),
                result.getString("name"),
                result.getString("city"),
                result.getString("state"),
                result.getDouble("latitud"),
                result.getDouble("longitud"));
    }

    public ResultSet selectAirport(String city, String state) throws SQLException {
        String query = "SELECT * FROM airport WHERE city = ? AND state = ?";
        PreparedStatement command = database.prepareStatement(query);
        command.setString(1, city);
        command.setString(2, state);
        return command.executeQuery();
    }
}
