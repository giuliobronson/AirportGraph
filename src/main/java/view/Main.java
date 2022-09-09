package view;

import model.Airport;
import model.AirportDAO;
import model.Graph;
import model.RouteDAO;
import tech.tablesaw.api.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        /*Cria um vértice do grafo para cada aeroporto*/
        Graph<Airport> airportGraph = new Graph<>();
        AirportDAO airportDAO = new AirportDAO();
        ResultSet airports = airportDAO.selectAirports();
        while (airports.next()) {
            Airport airport = new Airport(airports.getString("iata"),
                    airports.getDouble("latitude"),
                    airports.getDouble("longitude"));
            airportGraph.addVertex(airport);
        }

        /*Gera um grafo completo*/
        for (Airport origin : airportGraph.getVertexes()) {
            for (Airport neighbor : airportGraph.getVertexes()) {
                if (origin.equals(neighbor)) continue;
                airportGraph.addEdge(origin, neighbor, origin.distanceTo(neighbor));
            }
        }

        /*Início do menu*/
        Scanner input = new Scanner(System.in);
        Table table = Table.read().db(airportDAO.selectAirports(), "Airports");
        table.removeColumns("latitude", "longitude");
        do {
            System.out.println(table.print(40)); // Mostra tabela de aeroportos

            do {
                System.out.println();
                System.out.println("Search airport by location");
                System.out.print("Enter a city: ");
                String city = input.nextLine();
                System.out.print("Enter a state: ");
                String state = input.nextLine();

                Table row = Table.read().db(airportDAO.selectAirport(city, state), "Search Result");
                row.removeColumns("latitude", "longitude");
                System.out.println(row.print());

                System.out.println();
                System.out.print("Wish to do another search? [y/n] ");
            } while (input.nextLine().equalsIgnoreCase("y"));

            System.out.println();
            System.out.print("What is the origin airport code? ");
            String origin = input.next();
            System.out.print("What is the destination airport code? ");
            String destination = input.next();
            System.out.println();

            try {
                Airport airportOrigin = airportDAO.selectAirport(origin);
                Airport airportDestination = airportDAO.selectAirport(destination);

                HashMap<Airport, Airport> path = airportGraph.route(airportOrigin, airportDestination);
                String connection = path.get(airportDestination).getIata();
                String pathString = origin + " -> " + connection + " -> " + destination;
                double pathLength = airportGraph.distance(airportOrigin, airportDestination);

                RouteDAO routeDAO = new RouteDAO();
                routeDAO.insertRoute(origin, destination, connection, pathLength);
                System.out.format("Route: " + pathString + " | " + "Travel lenght: " + "%.2f" + "\n", pathLength);
            }
            catch (SQLException e) {
                System.out.println("Airport it's is not referenced");
            }

            System.out.println();
            System.out.print("Wish to continue? [y/n] ");
        } while (input.next().equalsIgnoreCase("y"));
    }
}