package view;

import model.Airport;
import model.AirportDAO;
import model.Graph;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Graph<Integer> graph = new Graph<>();

        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 0, 2);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 1, 2);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 2, 1);
        graph.addEdge(1, 3, 2);
        graph.addEdge(3, 1, 2);

        System.out.println(graph.route(0, 3));
    }
}