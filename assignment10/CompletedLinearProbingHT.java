package edu.ser222.m03_04;

/**
 * A symbol table implemented using a hashtable with linear probing.
 * 
 * @author Pin-Yang Wang, Sedgewick and Wayne, Acuna
 */
import java.util.LinkedList;
import java.util.Queue;

public class CompletedLinearProbingHT<Key, Value> implements ProbingHT<Key, Value> {

    //any constructors must be made public
    private class Entry {
        Key key;
        Value value;

        Entry(Key k, Value v) {
            this.key = k;
            this.value = v;
        }
    }

    // length of the array
    private int M;

    // number of key/value pairs currently stored
    private int N;

    // internal table
    private Object[] entries;

    // default array size: 997
    public CompletedLinearProbingHT() {
        this(997);
    }

    public CompletedLinearProbingHT(int capacity) {
        this.M = capacity;
        this.N = 0;
        this.entries = new Object[M];
    }

    @Override
    public int hash(Key key, int i) {
        int hash_code = (key.hashCode() & 0x7fffffff);
        return (hash_code + i) % getM();
    }

    @Override
    public void put(Key key, Value val) {
        if (key == null) return;

        if (val == null) {
            delete(key);
            return;
        }

        int i = 0;
        while (i < M) {
            int index = hash(key, i);
            @SuppressWarnings("unchecked")
            Entry e = (Entry) entries[index];

            if (e == null) {
                entries[index] = new Entry(key, val);
                N++;
                return;
            } else if (e.key.equals(key)) {
                e.value = val;
                return;
            }

            i++;
        }
    }

    @Override
    public Value get(Key key) {
        if (key == null) return null;

        int i = 0;
        while (i < M) {
            int idx = hash(key, i);
            @SuppressWarnings("unchecked")
            Entry e = (Entry) entries[idx];

            if (e == null) {
                // hit an empty slot – key cannot be in table
                return null;
            }
            if (e.key.equals(key)) {
                return e.value;
            }

            i++;
        }
        return null;
    }

    @Override
    public void delete(Key key) {
        //TODO
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterable<Key> keys() {
        Queue<Key> iter_queue = new LinkedList<Key>();
        for (int i = 0; i < M; i++) {
            @SuppressWarnings("unchecked")
            Entry e = (Entry) entries[i];
            if (e != null) {
                iter_queue.add(e.key);
            }
        }
        return iter_queue;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE METHODS ARE ONLY FOR GRADING AND COME FROM THE PROBINGHT INTERFACE.

    @Override
    public int getM() {
        return M;
    }

    @Override
    public Object getTableEntry(int i) {
        if (i < 0 || i >= M) {
            return null;
        }
        return entries[i];
    }
}