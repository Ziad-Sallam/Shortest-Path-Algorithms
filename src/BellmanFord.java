import java.util.ArrayList;
import java.util.Collections;

public class BellmanFord {
    ArrayList<ArrayList<int[]>> adjacencyList;
    ArrayList<Integer> distanceList;
    ArrayList<Integer> path;
    final int INF = Integer.MAX_VALUE / 2;

    BellmanFord(ArrayList<ArrayList<int[]>> adjacencyList) {
        this.adjacencyList = new ArrayList<>(adjacencyList);
        distanceList = new ArrayList<>();
        path = new ArrayList<>();
        // Initialize distances to infinity
        for (int i = 0; i < adjacencyList.size(); i++) {
            distanceList.add(INF);
            path.add(-1);
        }
    }

    ArrayList<Integer> bellmanFord(int start) {
        distanceList.set(start, 0);
        path.set(start, start);

        System.out.println("Iteration\tDistance List");

        // Relax all edges V-1 times
        for (int i = 0; i < adjacencyList.size() - 1; i++) {
            boolean updated = false;
            for (int u = 0; u < adjacencyList.size(); u++) {
                for (int[] edge : adjacencyList.get(u)) {
                    int v = edge[0];
                    int weight = edge[1];

                    if (distanceList.get(u) != INF &&
                            distanceList.get(u) + weight < distanceList.get(v)) {
                        distanceList.set(v, distanceList.get(u) + weight);
                        path.set(v, u);
                        updated = true;
                    }
                }
            }

            System.out.println((i+1) + "\t\t" + formatDistanceList());
            if (!updated) break;  // Early termination if no updates
        }
        for (int i = 0; i < adjacencyList.size() - 1; i++) {
            for (int u = 0; u < adjacencyList.size(); u++) {
                for (int[] edge : adjacencyList.get(u)) {
                    int v = edge[0];
                    int weight = edge[1];
                    if (distanceList.get(u) != INF &&
                            distanceList.get(u) + weight < distanceList.get(v)) {
                        distanceList.set(v, -INF);
                    }
                }
            }
        }

        // Check for negative weight cycles
        if (hasNegativeCycle()) {
            System.out.println("Warning: Graph contains a negative weight cycle");
            throw new IllegalStateException("Graph contains a negative weight cycle");
            
        }

        return new ArrayList<>(distanceList);
    }

    private boolean hasNegativeCycle() {
        for (int u = 0; u < adjacencyList.size(); u++) {
            for (int[] edge : adjacencyList.get(u)) {
                int v = edge[0];
                int weight = edge[1];
                if (distanceList.get(u) != INF &&
                        distanceList.get(u) + weight < distanceList.get(v)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String formatDistanceList() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < distanceList.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(distanceList.get(i) == INF ? "∞" : distanceList.get(i));
        }
        sb.append("]");
        return sb.toString();
    }

    ArrayList<Integer> getPath(int start, int end) {
        ArrayList<Integer> p = new ArrayList<>();
        if (distanceList.get(end) == INF) {
            return p;  // No path exists
        }
        int current = end;
        int c=0;
        while (current != start && c < adjacencyList.size()) {
            p.add(current);
            current = path.get(current);
            if (current == -1) {
                return new ArrayList<>();  // No path exists
            }
            c++;
        }
        if(c==adjacencyList.size())return new ArrayList<>();
        p.add(start);
        Collections.reverse(p);
        return p;
    }


}