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
            return Double.compare(weight, tNode.weight);
        }
    }
    private final Map<T, List<Node<T>>> map = new HashMap<>();

    public void addVertex(T V) {
        map.put(V, new LinkedList<>());
    }

    public void addEdge(T origin, T destination, double weight) {
        /*Adiciona os vértices nas pontas da aresta caso estes não tenham sido gerados*/
        if(!map.containsKey(origin))
            addVertex(origin);
        if(!map.containsKey(destination))
            addVertex(destination);

        /*Atuliza a lista de adijacência dos nós envolvidos na aresta*/
        Node<T> start = new Node<>(origin, weight);
        map.get(destination).add(start);
        Node<T> end = new Node<>(destination, weight);
        map.get(origin).add(end);
    }

    public double route(T origin, T destination) {
        /*Inicializa Map com as distâncias nó até a origem*/
        Map<T, Double> distances = new HashMap<>();
        for(T node : map.keySet()) {
            distances.put(node, Double.POSITIVE_INFINITY); // Nós restantes têm distâncias iniciais infinitas
        }
        distances.put(origin, .0); // Nó de origem tem distância zero para ele mesmo

        /*Algoritmo de Dijkstra*/
        PriorityQueue<Node<T>> priorityQueue = new PriorityQueue<>();
        ArrayList<T> path = new ArrayList<>();
        priorityQueue.add(new Node<>(origin, .0));
        while(!priorityQueue.isEmpty()) {
            double distance = -priorityQueue.peek().weight;
            T root = priorityQueue.peek().key; priorityQueue.remove();
            if(distances.get(root) < distance) continue;
            for(Node<T> node : map.get(root)) {
                double w = node.weight;
                T child = node.key;
                if(distances.get(child) > distances.get(root) + w) {
                    distances.put(child, distances.get(root) + w);
                    path.add(node.key);
                    priorityQueue.add(new Node<>(child, -distances.get(child)));
                }
            }
        }
        return distances.get(destination);
    }
}
