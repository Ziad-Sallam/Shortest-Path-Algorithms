import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    final static int INF = Integer.MAX_VALUE/2;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the number of nodes: ");
            int N = in.nextInt();
            System.out.println("Enter the number of edges: ");
            int M = in.nextInt();
            boolean negative = false;
            ArrayList<ArrayList<int[]>> adjacencyList = new ArrayList<>();
            ArrayList<ArrayList<Integer>> adjacencyMatrix = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                adjacencyList.add(new ArrayList<>());
                adjacencyMatrix.add(new ArrayList<>());
                for (int j = 0; j < N; j++) {
                    adjacencyMatrix.get(i).add(INF);
                }
            }
            System.out.println("The Edges (from to weight): ");
            for (int i = 0; i < M; i++) {
                int u = in.nextInt();
                int v = in.nextInt();
                int weight = in.nextInt();
                if (weight < 0) negative = true;
                adjacencyList.get(u).add(new int[]{v, weight});
                adjacencyMatrix.get(u).set(v, weight);
            }
            System.out.println("Enter the algorithm: \n\t 1) dijkstra\n\t 2) bellman algorithm\n\t 3) floyd warshall algorithm");
            int choice = in.nextInt();
            if (choice == 1) {
                if (negative) {
                    System.out.println("There is a negative distance the algorithm may fail\n");
                    continue;
                }
                Dijkstra dijkstra = new Dijkstra(adjacencyList);
                System.out.println("Entre the source node: ");
                int startNode = in.nextInt();
                ArrayList<Integer> distances = dijkstra.dijkstra(startNode);
                System.out.println("Shortest distances from node " + startNode + ":");
                for (int i = 0; i < distances.size(); i++) {
                    System.out.println("Node " + i + ": " +
                            (distances.get(i) == Integer.MAX_VALUE ? "Unreachable" : distances.get(i)));
                }

                for (int i = 0; i < distances.size(); i++) {
                    ArrayList<Integer> p = dijkstra.getPath(startNode, i);
                    System.out.println("path to node " + i + ":" + p);
                }


            } else if (choice == 2) {
                BellmanFord bellmanFord = new BellmanFord(adjacencyList);
                System.out.println("Entre the source node: ");
                int startNode = in.nextInt();
                ArrayList<Integer> distances;
                try {
                   distances = bellmanFord.bellmanFord(startNode);
                } catch (Exception e) {
                    System.out.println("Graph contains a negative weight cycle");
                    continue;
                }

                System.out.println("Shortest distances from node " + startNode + ":");
                for (int i = 0; i < distances.size(); i++) {
                    System.out.println("Node " + i + ": " +
                            (distances.get(i) == Integer.MAX_VALUE ? "Unreachable" : distances.get(i)));
                }
                for (int i = 0; i < distances.size(); i++) {
                    ArrayList<Integer> p = bellmanFord.getPath(startNode, i);
                    System.out.println("path to node " + i + ":" + p);
                }
            } else if (choice == 3) {
                FloydWarshall fw = new FloydWarshall(adjacencyList);
                try {
                    fw.floydWarshall();
                } catch (IllegalStateException e) {
                    System.out.println("Graph contains a negative weight cycle");
                    continue;
                } 

                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        System.out.println("from " + i + " to " + j + "\t" +fw.getPath(i, j));
                    }
                }
                for(int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                       System.out.print (fw.nextNode[i][j]+" ");
                    }
                    System.out.println();
                }
                for(int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                       System.out.print (fw.distanceMatrix[i][j]+" ");
                    }
                    System.out.println();
                }
            }
        }
    }
}