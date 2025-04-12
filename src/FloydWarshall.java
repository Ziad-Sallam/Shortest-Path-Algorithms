import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class FloydWarshall {
    ArrayList<ArrayList<int[]>> adjacencyList;
    int[][] distanceMatrix;
    int[][] nextNode;
    final int INF = Integer.MAX_VALUE / 2;
    int size;

    FloydWarshall(ArrayList<ArrayList<int[]>> adjacencyList) {
        this.adjacencyList = new ArrayList<>(adjacencyList);
        this.size = adjacencyList.size();
        this.distanceMatrix = new int[size][size];
        this.nextNode = new int[size][size];

        // Initialize distance matrix and next node matrix
        for (int i = 0; i < size; i++) {
            Arrays.fill(distanceMatrix[i], INF);
            Arrays.fill(nextNode[i], -1);
            distanceMatrix[i][i] = 0;

            for (int[] edge : adjacencyList.get(i)) {
                int j = edge[0];
                int weight = edge[1];
                distanceMatrix[i][j] = weight;
                nextNode[i][j] = j;
            }
        }
    }

    void floydWarshall() {
        System.out.println("Initial distance matrix:");
        printDistanceMatrix();

        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (distanceMatrix[i][k] + distanceMatrix[k][j] < distanceMatrix[i][j]) {
                        distanceMatrix[i][j] = distanceMatrix[i][k] + distanceMatrix[k][j];
                        nextNode[i][j] = nextNode[i][k];
                    }
                }
            }


            System.out.println("\nAfter considering intermediate node " + k + ":");
            printDistanceMatrix();
        }

        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (distanceMatrix[i][k] + distanceMatrix[k][j] < distanceMatrix[i][j]) {
                        distanceMatrix[i][j] = -INF;
                        nextNode[i][j] = nextNode[i][k];
                    }
                }
            }

        }
        System.out.println("\nAfter considering negative cycles :");
        printDistanceMatrix();


        // Check for negative cycles
        if (hasNegativeCycle()) {
            System.out.println("Warning: Graph contains a negative weight cycle");
        }
    }

    private boolean hasNegativeCycle() {
        for (int i = 0; i < size; i++) {
            if (distanceMatrix[i][i] < 0) {
                return true;
            }
        }
        return false;
    }

    ArrayList<Integer> getPath(int start, int end) {
        ArrayList<Integer> path = new ArrayList<>();

        if (distanceMatrix[start][end] == INF) {
            return path;  // No path exists
        }

        int at = start;
        path.add(at);
        while (at != end) {
            at = nextNode[at][end];
            if (at == -1) return new ArrayList<>();
            path.add(at);
        }

        return path;
    }

    private void printDistanceMatrix() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (distanceMatrix[i][j] == INF) {
                    System.out.print("INF\t");
                }
                else if (distanceMatrix[i][j] == -INF) {
                    System.out.print("-INF\t");
                }
                else {
                    System.out.print(distanceMatrix[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int n,m;
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();

        ArrayList<ArrayList<int[]>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int weight = sc.nextInt();

            adjacencyList.get(u).add(new int[]{v, weight});
        }
        FloydWarshall fw = new FloydWarshall(adjacencyList);
        fw.floydWarshall();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println("from " + i + " to " + j + "\t" +fw.getPath(i, j));
            }
        }

    }
}