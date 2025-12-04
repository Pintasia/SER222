package edu.ser222.m04_02;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Editable directed graph implementation using adjacency lists.
 * Supports sparse graphs and fast indegree lookup.
 *
 * Implements EditableDiGraph for use with kanji dependency graphs.
 */
public class BetterDiGraph implements EditableDiGraph {

    // adjacency list: vertex -> list of outgoing neighbors
    private HashMap<Integer, LinkedList<Integer>> adj;

    // indegree table: vertex -> number of incoming edges
    private HashMap<Integer, Integer> indegree;

    // edge count for O(1) retrieval
    private int edgeCount;

    /**
     * Default constructor: creates an empty graph.
     */
    public BetterDiGraph() {
        adj = new HashMap<>();
        indegree = new HashMap<>();
        edgeCount = 0;
    }

    @Override
    public void addVertex(int v) {
        if (!adj.containsKey(v)) {
            adj.put(v, new LinkedList<Integer>());
            indegree.put(v, 0);
        }
    }

    @Override
    public void addEdge(int v, int w) {
        // ensure both vertices exist
        addVertex(v);
        addVertex(w);

        LinkedList<Integer> list = adj.get(v);

        // prevent parallel edges
        if (!list.contains(w)) {
            list.add(w);
            edgeCount++;

            // update indegree of w
            indegree.put(w, indegree.get(w) + 1);
        }
    }

    @Override
    public Iterable<Integer> getAdj(int v) {
        LinkedList<Integer> list = adj.get(v);
        if (list == null) {
            return new LinkedList<Integer>(); // empty iterable
        }
        return list;
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public int getIndegree(int v) throws NoSuchElementException {
        if (!indegree.containsKey(v)) {
            throw new NoSuchElementException("Vertex does not exist: " + v);
        }
        return indegree.get(v);
    }

    @Override
    public int getVertexCount() {
        return adj.size();
    }

    @Override
    public void removeEdge(int v, int w) {
        LinkedList<Integer> list = adj.get(v);
        if (list != null) {
            if (list.remove((Integer) w)) {
                edgeCount--;
                if (indegree.containsKey(w)) {
                    indegree.put(w, indegree.get(w) - 1);
                }
            }
        }
    }

    @Override
    public void removeVertex(int v) {
        if (!adj.containsKey(v)) {
            return;
        }

        // 1. Remove all incoming edges x -> v
        for (Integer u : adj.keySet()) {
            if (u.equals(v)) {
                continue; // skip self, handle later
            }
            LinkedList<Integer> list = adj.get(u);
            if (list != null && list.remove((Integer) v)) {
                edgeCount--;
                if (indegree.containsKey(v)) {
                    indegree.put(v, indegree.get(v) - 1);
                }
            }
        }

        // 2. Remove all outgoing edges v -> w
        LinkedList<Integer> outList = adj.get(v);
        if (outList != null) {
            for (Integer w : outList) {
                if (indegree.containsKey(w)) {
                    indegree.put(w, indegree.get(w) - 1);
                }
                edgeCount--;
            }
        }

        // 3. Finally remove the vertex itself
        adj.remove(v);
        indegree.remove(v);
    }

    @Override
    public Iterable<Integer> vertices() {
        return adj.keySet();
    }

    @Override
    public boolean isEmpty() {
        return adj.isEmpty();
    }

    @Override
    public boolean containsVertex(int v) {
        return adj.containsKey(v);
    }
}
