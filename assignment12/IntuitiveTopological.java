package edu.ser222.m04_02;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Topological sort implementation using the "intuitive" algorithm:
 * repeatedly remove a vertex with indegree 0.
 */
public class IntuitiveTopological implements TopologicalSort {

    private Iterable<Integer> order;  // topological order or null if not DAG
    private boolean isDAG;

    /**
     * Constructs a topological ordering for the given graph, if possible.
     * Does NOT modify the original graph; works on a copy.
     *
     * @param graph editable directed graph to sort
     */
    public IntuitiveTopological(EditableDiGraph graph) {
        // 1. Make a copy of the graph in a BetterDiGraph
        BetterDiGraph copy = new BetterDiGraph();

        // copy vertices
        for (Integer v : graph.vertices()) {
            copy.addVertex(v);
        }

        // copy edges
        for (Integer v : graph.vertices()) {
            for (Integer w : graph.getAdj(v)) {
                copy.addEdge(v, w);
            }
        }

        // 2. Find all vertices with indegree 0
        LinkedList<Integer> zeroInDeg = new LinkedList<Integer>();
        for (Integer v : copy.vertices()) {
            try {
                if (copy.getIndegree(v) == 0) {
                    zeroInDeg.add(v);
                }
            } catch (NoSuchElementException e) {
                // should not happen because we just added indegree entries
            }
        }

        LinkedList<Integer> result = new LinkedList<Integer>();

        // 3. Repeatedly remove vertices with indegree 0
        while (!zeroInDeg.isEmpty()) {
            int v = zeroInDeg.removeFirst();
            result.add(v);

            // record neighbors before removal
            LinkedList<Integer> neighbors = new LinkedList<Integer>();
            for (Integer w : copy.getAdj(v)) {
                neighbors.add(w);
            }

            // remove vertex (and its edges) from the copy
            copy.removeVertex(v);

            // check if neighbors now have indegree 0
            for (Integer w : neighbors) {
                if (copy.containsVertex(w)) {
                    try {
                        if (copy.getIndegree(w) == 0) {
                            zeroInDeg.add(w);
                        }
                    } catch (NoSuchElementException e) {
                        // ignore, should not occur
                    }
                }
            }
        }

        // 4. If copy still has vertices, there is a cycle.
        if (copy.getVertexCount() == 0) {
            isDAG = true;
            order = result;
        } else {
            isDAG = false;
            order = null;
        }
    }

    @Override
    public Iterable<Integer> order() {
        return order;
    }

    @Override
    public boolean isDAG() {
        return isDAG;
    }
}
