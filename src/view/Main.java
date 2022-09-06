package view;

import model.Airport;
import model.AirportDAO;
import model.Graph;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws SQLException {
        /*Cria um objeto aeroporto para cada instância da tabela*/
        Graph<Airport> airportGraph = new Graph<>();
        AirportDAO airportDAO = new AirportDAO();
        ResultSet resultSet = airportDAO.selectAirports();
        while (resultSet.next()) {
            Airport airport = new Airport(resultSet.getString("iata"),
                    resultSet.getString("name"),
                    resultSet.getString("city"),
                    resultSet.getString("state"),
                    resultSet.getDouble("latitud"),
                    resultSet.getDouble("longitud"));
            airportGraph.addVertex(airport);
        }

        /*Gera um grafo completo*/
        for(Airport origin : airportGraph.getVertexes()) {
            for(Airport neighbor : airportGraph.getVertexes()) {
                if(origin.equals(neighbor)) continue;
                airportGraph.addEdge(origin, neighbor, origin.distanceTo(neighbor));
            }
        }

        Airport airportOrigin = null, airportDestination = null;
        for(Airport target : airportGraph.getVertexes()) {
            if(target.getIata().equals("FOR")) airportOrigin = target;
            if(target.getIata().equals("GIG")) airportDestination = target;
        }

        System.out.println(airportGraph.distance(airportOrigin, airportDestination));
        HashMap<Airport, Airport> path = airportGraph.route(airportOrigin, airportDestination);
        Airport tmp = path.getOrDefault(airportDestination, null);
        System.out.print(airportDestination.getIata());
        while(tmp != null) {
            System.out.print(" <- " + tmp.getIata());
            tmp = path.getOrDefault(tmp, null);
        }
    }
}