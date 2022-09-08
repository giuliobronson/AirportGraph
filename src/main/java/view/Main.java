package view;

import model.Airport;
import model.AirportDAO;
import model.Graph;
import model.RouteDAO;
import tech.tablesaw.api.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        /*Cria um vértice do grafo para cada aeroporto*/
        Graph<Airport> airportGraph = new Graph<>();
        AirportDAO airportDAO = new AirportDAO();
        ResultSet airports = airportDAO.selectAirports();
        while (airports.next()) {
            Airport airport = new Airport(airports.getString("iata"),
                    airports.getString("name"),
                    airports.getString("city"),
                    airports.getString("state"),
                    airports.getDouble("latitud"),
                    airports.getDouble("longitud"));
            airportGraph.addVertex(airport);
        }

        /*Gera um grafo completo*/
        for(Airport origin : airportGraph.getVertexes()) {
            for(Airport neighbor : airportGraph.getVertexes()) {
                if(origin.equals(neighbor)) continue;
                airportGraph.addEdge(origin, neighbor, origin.distanceTo(neighbor));
            }
        }

        /*Início do menu*/
        Scanner input = new Scanner(System.in);
        Table table = Table.read().db(airportDAO.selectAirports(), "Airports");
        table.removeColumns("latitud", "longitud");
        String answer = "y";
        while(Objects.equals(answer, "y")) {
            System.out.println(table.print(40));

            System.out.println();
            System.out.print("What is the origin airport? ");
            String origin = input.next();
            System.out.print("What is the destination airport? ");
            String destination = input.next();
            System.out.println();

            Airport airportOrigin = airportDAO.selectAirport(origin);
            Airport airportDestination = airportDAO.selectAirport(destination);

            HashMap<Airport, Airport> path = airportGraph.route(airportOrigin, airportDestination);
            Airport airport = path.getOrDefault(airportDestination, null);
            RouteDAO routeDAO = new RouteDAO();
            StringBuilder pathString = new StringBuilder(airportDestination.getIata());
            while(airport != null) {
                pathString.append(", ").append(airport.getIata());
                airport = path.getOrDefault(airport, null);
            }
            double pathLength = airportGraph.distance(airportOrigin, airportDestination);

            routeDAO.insertRoute(String.valueOf(pathString), pathLength);
            System.out.format("Route: " + pathString + " | " + "Travel lenght: " + "%.2f" + "\n", pathLength);
            System.out.println();

            System.out.print("Wish to continue? [y/n] ");
            answer = input.next();
        }

    }
}