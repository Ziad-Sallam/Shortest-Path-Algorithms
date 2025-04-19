import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class ShortestPathAlgorithmsTest {
    ArrayList<ArrayList<int[]>> adjacencyList = new ArrayList<>();
    private static final String LOG_FILE = "test_results.txt";
    private long testStartTime;

    @Rule
    public TestRule watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            testStartTime = System.currentTimeMillis();
            String message = String.format("\nStarting test: %s", description.getMethodName());
            logToFile(message);
        }

        @Override
        protected void succeeded(Description description) {
            long duration = System.currentTimeMillis() - testStartTime;
            String message = String.format("Test PASSED: %s | Execution time: %d ms",
                    description.getMethodName(), duration);
            logToFile(message);
        }

        @Override
        protected void failed(Throwable e, Description description) {
            long duration = System.currentTimeMillis() - testStartTime;
            String message = String.format("Test FAILED: %s | Execution time: %d ms | Reason: %s",
                    description.getMethodName(), duration, e.getMessage());
            logToFile(message);
        }

        private void logToFile(String message) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
                writer.println(message);
            } catch (IOException e) {
                System.err.println("Could not write to log file: " + e.getMessage());
            }
        }
    };

    void setup(int nodeNumber) {
        // Initialize the adjacency list and matrix for testing
        for (int i = 0; i < nodeNumber; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    void addEdge(int from, int to, int weight) {
        // Add edge to adjacency list
        adjacencyList.get(from).add(new int[] { to, weight });
        // Add edge to adjacency matrix

    }

    // normal test case
    @Test
    public void floydWarshallGraphWith3Nodes() {
        setup(3);
        addEdge(0, 1, 1);
        addEdge(1, 2, 2);
        addEdge(0, 2, 4);
        addEdge(2, 0, 3);
        FloydWarshall floydWarshall = new FloydWarshall(adjacencyList);
        long startTime = System.currentTimeMillis();
        floydWarshall.floydWarshall();
        ArrayList<Integer> path = floydWarshall.getPath(0, 2);
        long endTime = System.currentTimeMillis();

        int[] expected = { 0, 1, 3 };
        int[] expectedPath = { 0, 1, 2 };

        assertArrayEquals(expected, floydWarshall.distanceMatrix[0]);

        for (int i = 0; i < path.size(); i++) {
            assert path.get(i) == expectedPath[i] : "floydWarshall's algorithm failed at node " + i;
        }
    }

    @Test
    public void dijkstraGraphWith3Nodes() {
        setup(3);
        addEdge(0, 1, 1);
        addEdge(1, 2, 2);
        addEdge(0, 2, 4);
        addEdge(2, 0, 3);
        Dijkstra dijkstra = new Dijkstra(adjacencyList);
        long startTime = System.currentTimeMillis();
        ArrayList<Integer> dijkstraResult = dijkstra.dijkstra(0);
        ArrayList<Integer> path = dijkstra.getPath(0, 2);
        long endTime = System.currentTimeMillis();
        System.out.println("Dijkstra's algorithm time: " + (endTime - startTime) + " ns");
        int[] expectedDijkstra = { 0, 1, 3 };
        int[] expectedPath = { 0, 1, 2 };
        for (int i = 0; i < path.size(); i++) {
            assert path.get(i) == expectedPath[i] : "Dijkstra's algorithm failed at node " + i;
        }
        for (int i = 0; i < dijkstraResult.size(); i++) {
            assert dijkstraResult.get(i) == expectedDijkstra[i] : "Dijkstra's algorithm failed at node " + i;
        }
    }

    @Test
    public void bellmanFordGraphWith3Nodes() {
        setup(3);
        addEdge(0, 1, 1);
        addEdge(1, 2, 2);
        addEdge(0, 2, 4);
        addEdge(2, 0, 3);
        BellmanFord bellmanFord = new BellmanFord(adjacencyList);
        long startTime = System.currentTimeMillis();
        ArrayList<Integer> bellmanFordResult = bellmanFord.bellmanFord(0);
        ArrayList<Integer> path = bellmanFord.getPath(0, 2);
        long endTime = System.currentTimeMillis();
        System.out.println("BellmanFord's algorithm time: " + (endTime - startTime) + " ns");
        int[] expected = { 0, 1, 3 };
        int[] expectedPath = { 0, 1, 2 };

        for (int i = 0; i < bellmanFordResult.size(); i++) {
            assert bellmanFordResult.get(i) == expected[i] : "BellmanFord's algorithm failed at node " + i;
        }
        for (int i = 0; i < path.size(); i++) {
            assert path.get(i) == expectedPath[i] : "BellmanFord's algorithm failed at node " + i;
        }
    }

    

    @Test
    public void dijkstraGraphWith7Nodes() {
        setup(7);
        addEdge(0, 1, 2);
        addEdge(0, 2, 7);
        addEdge(0, 3, 12);
        addEdge(1, 4, 2);
        addEdge(2, 1, 3);
        addEdge(2, 4, -1);
        addEdge(2, 3, 2);
        addEdge(3, 0, -4);
        addEdge(3, 6, -7);
        addEdge(4, 5, 2);
        addEdge(5, 6, 2);
        addEdge(6, 4, 1);
        Dijkstra dijkstra = new Dijkstra(adjacencyList);
        long startTime = System.currentTimeMillis();
        ArrayList<Integer> dijkstraResult = dijkstra.dijkstra(0);
        ArrayList<Integer> path = dijkstra.getPath(0, 5);
        long endTime = System.currentTimeMillis();
        System.out.println("Dijkstra's algorithm time: " + (endTime - startTime) + " ns");
        int[] expectedDijkstra = { 0, 2, 7, 9, 3, 5, 2 };
        int[] expectedPath = { 0, 2, 3, 6, 4, 5 };

        for (int i = 0; i < path.size(); i++) {
            assert path.get(i) == expectedPath[i]
                    : "Dijkstra's algorithm failed at node " + i + "as there negtive weigth ";
        }
        for (int i = 0; i < dijkstraResult.size(); i++) {
            assert dijkstraResult.get(i) == expectedDijkstra[i] : "Dijkstra's algorithm failed at node " + i;
        }
    }

    @Test
    public void bellmanFordGraphWith5Nodes() {
        setup(5);
        addEdge(0, 1, 1);
        addEdge(1, 2, 2);
        addEdge(0, 2, 4);
        addEdge(2, 0, 3);
        addEdge(1, 3, 5);
        addEdge(3, 4, 6);
        BellmanFord bellmanFord = new BellmanFord(adjacencyList);
        long startTime = System.currentTimeMillis();
        ArrayList<Integer> bellmanFordResult = bellmanFord.bellmanFord(0);
        ArrayList<Integer> path = bellmanFord.getPath(0, 4);
        long endTime = System.currentTimeMillis();

        System.out.println("bellmanFord's algorithm time: " + (endTime - startTime) + " ns");
        int[] expected = { 0, 1, 3, 6, 12 };
        int[] expectedPath = { 0, 1, 3, 4 };

        for (int i = 0; i < bellmanFordResult.size(); i++) {
            assert bellmanFordResult.get(i) == expected[i] : "bellmanFord's algorithm failed at node " + i;
        }
        for (int i = 0; i < path.size(); i++) {
            assert path.get(i) == expectedPath[i] : "bellmanFord's algorithm failed at node " + i;
        }
    }

    @Test
    public void floydWarshallGraphWith5Nodes() {
        setup(5);
        addEdge(0, 1, 1);
        addEdge(1, 2, 2);
        addEdge(0, 2, 4);
        addEdge(2, 0, 3);
        addEdge(1, 3, 5);
        addEdge(3, 4, 6);
        FloydWarshall floydWarshall = new FloydWarshall(adjacencyList);
        long startTime = System.currentTimeMillis();
        floydWarshall.floydWarshall();
        ArrayList<Integer> path = floydWarshall.getPath(0, 4);
        long endTime = System.currentTimeMillis();
        System.out.println("floydWarshall's algorithm time: " + (endTime - startTime) + " ns");

        int[] expected = { 0, 1, 3, 6, 12 };
        int[] expectedPath = { 0, 1, 3, 4 };

        assertArrayEquals(expected, floydWarshall.distanceMatrix[0]);

        for (int i = 0; i < path.size(); i++) {
            assert path.get(i) == expectedPath[i] : "floydWarshall's algorithm failed at node " + i;
        }
    }

    // test case with negative edges but no negative cycle
    @Test
    public void dijkstraGraphWithNegativeEdges() {
        setup(6);
        addEdge(0, 1, 2);
        addEdge(0, 2, 5);
        addEdge(1, 2, 1);
        addEdge(1, 3, 2);
        addEdge(2, 4, -4);
        addEdge(4, 5, 2);
        addEdge(3, 5, 10);
        Dijkstra dijkstra = new Dijkstra(adjacencyList);
        long startTime = System.currentTimeMillis();
        ArrayList<Integer> dijkstraResult = dijkstra.dijkstra(0);
        long endTime = System.currentTimeMillis();
        System.out.println("Dijkstra's algorithm time: " + (endTime - startTime) + " ns");
        int[] expectedDijkstra = { 0, 2, 3, 4, -1, 1 };
        int[] result = dijkstraResult.stream().mapToInt(i -> i).toArray();
        assertArrayEquals(expectedDijkstra, result);
    }

    @Test
    public void bellmanFordGraphWithNegativeEdges() {
        setup(6);
        addEdge(0, 1, 2);
        addEdge(0, 2, 5);
        addEdge(1, 2, 1);
        addEdge(1, 3, 2);
        addEdge(2, 4, -4);
        addEdge(4, 5, 2);
        addEdge(3, 5, 10);
        BellmanFord bellmanFord = new BellmanFord(adjacencyList);
        long startTime = System.currentTimeMillis();
        ArrayList<Integer> bellmanFordResult = bellmanFord.bellmanFord(0);
        ArrayList<Integer> path = bellmanFord.getPath(0, 4);
        long endTime = System.currentTimeMillis();
        System.out.println("bellmanFord's algorithm time: " + (endTime - startTime) + " ns");
        int[] expected = { 0, 2, 3, 4, -1, 1 };
        int[] expectedPath = { 0, 1, 2, 4 };
        for (int i = 0; i < bellmanFordResult.size(); i++) {
            assert bellmanFordResult.get(i) == expected[i] : "bellmanFord's algorithm failed at node " + i;
        }
        for (int i = 0; i < path.size(); i++) {
            assert path.get(i) == expectedPath[i] : "bellmanFord's algorithm failed at node " + i;
        }
    }

    @Test
    public void floydWarshallGraphWithNegativeEdges() {
        setup(6);
        addEdge(0, 1, 2);
        addEdge(0, 2, 5);
        addEdge(1, 2, 1);
        addEdge(1, 3, 2);
        addEdge(2, 4, -4);
        addEdge(4, 5, 2);
        addEdge(3, 5, 10);
        FloydWarshall floydWarshall = new FloydWarshall(adjacencyList);
        long startTime = System.currentTimeMillis();
        floydWarshall.floydWarshall();
        ArrayList<Integer> path = floydWarshall.getPath(0, 4);
        long endTime = System.currentTimeMillis();
        System.out.println("floydWarshall's algorithm time: " + (endTime - startTime) + " ns");
        int[] expected = { 0, 2, 3, 4, -1, 1 };
        int[] expectedPath = { 0, 1, 2, 4 };
        assertArrayEquals(expected, floydWarshall.distanceMatrix[0]);
        for (int i = 0; i < path.size(); i++) {
            assert path.get(i) == expectedPath[i] : "bellmanFord's algorithm failed at node " + i;
        }
    }

    // test case with negative edges but no negative cycle more coplex graph
    @Test
    public void dijkstraGraphWithNegativeEdges7Node() {
        setup(7);
        addEdge(0, 1, 4);
        addEdge(0, 2, 3);
        addEdge(1, 3, 2);
        addEdge(2, 3, 1);
        addEdge(3, 4, -6);
        addEdge(4, 5, 2);
        addEdge(1, 5, 10);
        addEdge(2, 6, 5);
        addEdge(6, 5, 1);
        Dijkstra dijkstra = new Dijkstra(adjacencyList);
        long startTime = System.currentTimeMillis();
        ArrayList<Integer> dijkstraResult = dijkstra.dijkstra(0);
        long endTime = System.currentTimeMillis();
        System.out.println("Dijkstra's algorithm time: " + (endTime - startTime) + " ns");
        int[] expectedDijkstra = { 0, 4, 3, 4, -2, 0, 8 };
        int[] result = dijkstraResult.stream().mapToInt(i -> i).toArray();
        assertArrayEquals(expectedDijkstra, result);
    }

    @Test
    public void bellmanFordGraphWithNegativeEdges7Node() {
        setup(7);
        addEdge(0, 1, 4);
        addEdge(0, 2, 3);
        addEdge(1, 3, 2);
        addEdge(2, 3, 1);
        addEdge(3, 4, -6);
        addEdge(4, 5, 2);
        addEdge(1, 5, 10);
        addEdge(2, 6, 5);
        addEdge(6, 5, 1);
        BellmanFord bellmanFord = new BellmanFord(adjacencyList);
        long startTime = System.currentTimeMillis();
        ArrayList<Integer> bellmanFordResult = bellmanFord.bellmanFord(0);
        ArrayList<Integer> path = bellmanFord.getPath(0, 4);
        long endTime = System.currentTimeMillis();
        System.out.println("bellmanFord's algorithm time: " + (endTime - startTime) + " ns");
        int[] expected = { 0, 4, 3, 4, -2, 0, 8 };
        int[] expectedPath = { 0, 2, 3, 4, 5 };
        for (int i = 0; i < bellmanFordResult.size(); i++) {
            assert bellmanFordResult.get(i) == expected[i] : "bellmanFord's algorithm failed at node " + i;
        }
        for (int i = 0; i < path.size(); i++) {
            assert path.get(i) == expectedPath[i] : "bellmanFord's algorithm failed at node " + i;
        }
    }

    @Test
    public void floydWarshallGraphWithNegativeEdges7Nodes() {
        setup(7);
        addEdge(0, 1, 4);
        addEdge(0, 2, 3);
        addEdge(1, 3, 2);
        addEdge(2, 3, 1);
        addEdge(3, 4, -6);
        addEdge(4, 5, 2);
        addEdge(1, 5, 10);
        addEdge(2, 6, 5);
        addEdge(6, 5, 1);
        FloydWarshall floydWarshall = new FloydWarshall(adjacencyList);
        long startTime = System.currentTimeMillis();
        floydWarshall.floydWarshall();
        ArrayList<Integer> path = floydWarshall.getPath(0, 5);
        long endTime = System.currentTimeMillis();
        System.out.println("floydWarshall's algorithm time: " + (endTime - startTime) + " ns");
        int[] expected = { 0, 4, 3, 4, -2, 0, 8 };
        int[] expectedPath = { 0, 2, 3, 4, 5 };
        assertArrayEquals(expected, floydWarshall.distanceMatrix[0]);
        for (int i = 0; i < path.size(); i++) {
            assert path.get(i) == expectedPath[i] : "bellmanFord's algorithm failed at node " + i;
        }
    }

    // test case with negative cycle
    @Test
    public void dijkstraGraphWithNegativeCycle() {
        setup(5);
        addEdge(0, 1, 3);
        addEdge(1, 2, -2);
        addEdge(0, 2, 2);
        addEdge(2, 0, -4);
        addEdge(1, 3, 5);
        addEdge(3, 4, -2);
        Dijkstra dijkstra = new Dijkstra(adjacencyList);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {
                    dijkstra.dijkstra(0);
                    ;
                });
        assertEquals("Graph contains a negative weight cycle",
                exception.getMessage());

    }// infinte loop occur

    @Test
    public void bellmanFordGraphWithNegativeCycle() {
        setup(5);
        addEdge(0, 1, 3);
        addEdge(1, 2, -2);
        addEdge(0, 2, 2);
        addEdge(2, 0, -4);
        addEdge(1, 3, 5);
        addEdge(3, 4, -2);
        BellmanFord bellmanFord = new BellmanFord(adjacencyList);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            bellmanFord.bellmanFord(0);
        });
        assertEquals("Graph contains a negative weight cycle", exception.getMessage());

    }

    @Test
    public void floydWarshallGraphWithNegativeCycle() {
        setup(5);
        addEdge(0, 1, 3);
        addEdge(1, 2, -2);
        addEdge(0, 2, 2);
        addEdge(2, 0, -4);
        addEdge(1, 3, 5);
        addEdge(3, 4, -2);
        FloydWarshall floydWarshall = new FloydWarshall(adjacencyList);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            floydWarshall.floydWarshall();
        });
        assertEquals("Graph contains a negative weight cycle", exception.getMessage());

    }
}
