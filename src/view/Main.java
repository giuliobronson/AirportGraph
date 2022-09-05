package view;

import model.Airport;
import model.AirportDAO;
import model.Graph;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Graph<Airport> graph = new Graph<>();
        AirportDAO airportDAO = new AirportDAO();

        ResultSet result = airportDAO.selectAirports();
        while(result.next()) {
            Airport airport = new Airport(result.getString("iata"),
                    result.getString("name"),
                    result.getString("city"),
                    result.getString("state"),
                    result.getDouble("latitud"),
                    result.getDouble("longitud"));
            graph.addVertex(airport);
        }
    }
}