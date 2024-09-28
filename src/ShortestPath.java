import java.util.*;
public class ShortestPath {
    private static class Edge {
        int dest;
        int weight;
        public Edge(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;        }
        public int getDest() {
            return dest;        }
        public int getWeight() {
            return weight;        }    }
    private static class Node {
        int node;
        int distance;
        public Node(int node, int distance) {
            this.node = node;
            this.distance = distance;        }
        public int getNode() {
            return node;        }
        public int getDistance() {
            return distance;
        }    }
    private final List<Edge>[] adjList;
    public ShortestPath(int numNodes) {
        adjList = new List[numNodes];
        for (int i = 0; i < numNodes; i++) {
            adjList[i] = new ArrayList<>();
        }    }
    public void addEdge(int src, int dest, int weight) {
        adjList[src].add(new Edge(dest, weight));
        adjList[dest].add(new Edge(src, weight));     }
    public List<Edge> getEdges(int node) {
        return adjList[node];    }
    public int getNumNodes() {
        return adjList.length;    }
    public int[] dijkstra(int start) {
        int numNodes = getNumNodes();
        int[] distances = new int[numNodes];
        int[] previous = new int[numNodes];
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getDistance));
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);
        distances[start] = 0;
        priorityQueue.add(new Node(start, 0));
        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.poll();
            int currentNode = current.getNode();
            for (Edge edge : getEdges(currentNode)) {
                int neighbor = edge.getDest();
                int newDist = distances[currentNode] + edge.getWeight();
                if (newDist < distances[neighbor]) {
                    distances[neighbor] = newDist;
                    previous[neighbor] = currentNode;
                    priorityQueue.add(new Node(neighbor, newDist));
                }            }        }
        return previous;    }
    public List<Integer> reconstructPath(int[] previous, int start, int end) {
        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = previous[at]) {
            path.add(at);        }
        Collections.reverse(path);
        return path;    }
    public static void main(String[] args) {
        int numNodes = 5;
        int start = 0;
        int end = 4;
        ShortestPath graph = new ShortestPath(numNodes);
        int EDINBURGH = 0;
        int GLASGOW = 1;
        int STIRLING = 2;
        int PERTH = 3;
        int DUNDEE = 4;
        graph.addEdge(EDINBURGH, PERTH, 100);
        graph.addEdge(PERTH, STIRLING, 40);
        graph.addEdge(STIRLING, EDINBURGH, 50);
        graph.addEdge(GLASGOW, EDINBURGH, 70);
        graph.addEdge(PERTH, DUNDEE, 60);
        graph.addEdge(STIRLING, GLASGOW, 50);
        int[] previous = graph.dijkstra(start);
        List<Integer> path = graph.reconstructPath(previous, start, end);
        String[] nodeNames = {"Edinburgh", "Glasgow", "Stirling", "Perth", "Dundee"};
        System.out.print(" ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(nodeNames[path.get(i)]);
            if (i < path.size() - 1) {
                System.out.print("  ");
            }        }
        int totalDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int currentNode = path.get(i);
            for (Edge edge : graph.getEdges(currentNode)) {
                if (edge.getDest() == path.get(i + 1)) {
                    totalDistance += edge.getWeight();
                    break;
                }            }        }
        System.out.println("\ndistance: " + totalDistance);    }}
