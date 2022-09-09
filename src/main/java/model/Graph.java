package model;

import java.util.*;

public class Graph<T> {
    static class Node<T> implements Comparable<Node<T>> {
        T key;
        double weight;

        Node(T key, double weight) {
            this.key = key;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node<T> tNode) {
            return Double.compare(this.weight, tNode.weight);
        }
    }

    static class ShortestPath<T> {
        double distance;
        HashMap<T, T> path;

        public ShortestPath(double distance, HashMap<T, T> path) {
            this.distance = distance;
            this.path = path;
        }
    }

    private final Map<T, List<Node<T>>> map = new HashMap<>();

    public Set<T> getVertexes() {
        return map.keySet();
    }

    public void addVertex(T V) {
        map.put(V, new LinkedList<>());
    }

    public void addEdge(T origin, T destination, double weight) {
        /*Adiciona os vértices nas pontas da aresta caso estes não tenham sido gerados*/
        if (!map.containsKey(origin))
            addVertex(origin);
        if (!map.containsKey(destination))
            addVertex(destination);

        /*Atuliza a lista de adijacência dos nós envolvidos na aresta*/
        Node<T> start = new Node<>(origin, weight);
        map.get(destination).add(start);
        Node<T> end = new Node<>(destination, weight);
        map.get(origin).add(end);
    }

    private ShortestPath<T> dijkstra(T origin, T destination) {
        /*Inicializa Map com as distâncias nó até a origem*/
        HashMap<T, Double> distances = new HashMap<>();
        for (T node : map.keySet()) {
            distances.put(node, Double.POSITIVE_INFINITY); // Nós restantes têm distâncias iniciais infinitas
        }
        distances.put(origin, .0); // Nó de origem tem distância zero para ele mesmo

        /*Algoritmo de Dijkstra*/
        PriorityQueue<Node<T>> priorityQueue = new PriorityQueue<>();
        HashMap<T, T> path = new HashMap<>();
        priorityQueue.add(new Node<>(origin, .0));
        while (!priorityQueue.isEmpty()) {
            double distance = -priorityQueue.peek().weight;
            T root = priorityQueue.peek().key;
            priorityQueue.remove();
            if (distances.get(root) < distance) continue;
            for (Node<T> node : map.get(root)) {
                double weight = node.weight;
                T child = node.key;
                if (distances.get(child) > distances.get(root) + weight) {
                    distances.put(child, distances.get(root) + weight);
                    path.put(child, root);
                    priorityQueue.add(new Node<>(child, -distances.get(child)));
                }
            }
        }

        return new ShortestPath<>(distances.get(destination), path);
    }

    public double distance(T origin, T destination) {
        /*Guarda o valor do peso da aresta*/
        Node<T> node = map.get(origin).stream().filter(target -> destination.equals(target.key)).findAny().orElse(null);
        double weight = node.weight; // TODO: tratar execeção. Considerar grafo completo?

        /*Remove a aresta que conecta origem e destino*/
        map.get(origin).removeIf(target -> destination.equals(target.key));
        map.get(destination).removeIf(target -> origin.equals(target.key));

        /*Algoritmo de Dijkstra*/
        ShortestPath<T> shortestPath = dijkstra(origin, destination);

        /*Reinsere a aresta removida*/
        addEdge(origin, destination, weight);

        return shortestPath.distance;
    }

    public HashMap<T, T> route(T origin, T destination) {
        /*Guarda o valor do peso da aresta*/
        Node<T> node = map.get(origin).stream().filter(target -> destination.equals(target.key)).findAny().orElse(null);
        double weight = node.weight; // TODO: tratar execeção. Considerar grafo completo?

        /*Remove a aresta que conecta origem e destino*/
        map.get(origin).removeIf(target -> destination.equals(target.key));
        map.get(destination).removeIf(target -> origin.equals(target.key));

        /*Algoritmo de Dijkstra*/
        ShortestPath<T> shortestPath = dijkstra(origin, destination);

        /*Reinsere a aresta removida*/
        addEdge(origin, destination, weight);

        return shortestPath.path;
    }
}