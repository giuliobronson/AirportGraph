package model;

import server.Link;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AirportDAO {
    private Connection database;

    public AirportDAO() throws SQLException {
        this.database = (new Link(0)).connect();
    }

    public String selectAirport(Airport airport) throws SQLException {
        String query = "select customerName from customers where customerNumber = 124;";
        PreparedStatement command = database.prepareStatement(query);
        ResultSet result = command.executeQuery();
        result.next();
        return result.getString("customerName");
    }
}
