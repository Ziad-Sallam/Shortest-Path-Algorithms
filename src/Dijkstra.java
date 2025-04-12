import java.util.*;

public class Dijkstra {
    // Each inner ArrayList represents [neighbor, weight] pairs
    ArrayList<ArrayList<int[]>> adjacencyList;
    ArrayList<Integer> distanceList;
    ArrayList<Integer> path;
    final int INF = Integer.MAX_VALUE / 2;  // To prevent overflow when adding weights

    Dijkstra(ArrayList<ArrayList<int[]>> adjacencyList) {
        if (adjacencyList == null) {
            throw new IllegalArgumentException("Adjacency list cannot be null");
        }
        this.adjacencyList = new ArrayList<>(adjacencyList);
        distanceList = new ArrayList<>();
        path = new ArrayList<>();

        // Initialize distances to infinity and paths to -1 (unreachable)
        for (int i = 0; i < adjacencyList.size(); i++) {
            distanceList.add(INF);
            path.add(-1);
        }
    }

    ArrayList<Integer> dijkstra(int start) {
        if (start < 0 || start >= adjacencyList.size()) {
            throw new IllegalArgumentException("Invalid start node");
        }

        // Priority queue of [node, distance] pairs, sorted by distance
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        // Initialize with start node (distance 0)
        pq.add(new int[]{start, 0});
        distanceList.set(start, 0);
        path.set(start, start);  // The path to start is itself

        System.out.println("Iteration\tVisited\t\tDistance List");
        int iteration = 0;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int distance = current[1];

            // Format the distance list for better readability
            String distanceStr = "[";
            for (int i = 0; i < distanceList.size(); i++) {
                if (i > 0) distanceStr += ", ";
                distanceStr += (distanceList.get(i) == INF ? "∞" : distanceList.get(i));
            }
            distanceStr += "]";

            System.out.printf("%d\t\t\t%c\t\t\t%s%n", iteration, (char) (node + 'A'), distanceStr);
            iteration++;

            // Skip if we already found a better path
            if (distance > distanceList.get(node)) continue;

            // Visit all neighbors
            for (int[] neighbor : adjacencyList.get(node)) {
                int nextNode = neighbor[0];
                int weight = neighbor[1];
                int newDistance = distance + weight;

                if (newDistance < distanceList.get(nextNode)) {
                    distanceList.set(nextNode, newDistance);
                    path.set(nextNode, node);  // Update the best path to nextNode
                    pq.add(new int[]{nextNode, newDistance});
                }
            }
        }
        return new ArrayList<>(distanceList);  // Return a copy
    }

    ArrayList<Integer> getPath(int start, int end) {
        if (start < 0 || start >= adjacencyList.size() || end < 0 || end >= adjacencyList.size()) {
            throw new IllegalArgumentException("Invalid node index");
        }

        ArrayList<Integer> p = new ArrayList<>();
        if (distanceList.get(end) == INF) {
            return p;  // No path exists
        }

        // Reconstruct path backwards
        int current = end;
        while (current != start) {
            p.add(current);
            current = path.get(current);
            if (current == -1) {
                return new ArrayList<>();  // No path exists
            }
        }
        p.add(start);
        Collections.reverse(p);
        return p;
    }
}