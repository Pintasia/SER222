package edu.ser222.m03_04;

/**
 * A symbol table implemented using a hashtable with chaining.
 * Does not support load balancing or resizing.
 * 
 * @author Pin-Yang Wang, Sedgewick and Wayne, Acuna
 */
import java.util.LinkedList;
import java.util.Queue;

public class CompletedTwoProbeChainHT<Key, Value> implements TwoProbeChainHT<Key, Value> {

    //any constructors must be made public
    private class Entry {
        Key key;
        Value value;

        Entry(Key k, Value v) {
            this.key = k;
            this.value = v;
        }
    }

    private int M;
    private int N;
    private LinkedList<Entry>[] entries;

    /** default array size: 997 */
    public CompletedTwoProbeChainHT() {
        this(997);
    }

    @SuppressWarnings("unchecked")
    public CompletedTwoProbeChainHT(int capacity) {
        this.M = capacity;
        this.N = 0;
        this.entries = (LinkedList<Entry>[]) new LinkedList[capacity];
    }

    private LinkedList<Entry> getOrCreateChain(int idx) {
        if (entries[idx] == null) {
            entries[idx] = new LinkedList<Entry>();
        }
        return entries[idx];
    }

    @Override
    public int hash(Key key) {
        int hash_code = (key.hashCode() & 0x7fffffff);
        return hash_code % getM();
    }

    @Override
    public int hash2(Key key) {
        int hash_code2 = (key.hashCode() & 0x7fffffff) % M;
        return (hash_code2 * 31) % getM();
    }

    @Override
    public void put(Key key, Value val) {
        if (key == null) return;

        if (val == null) {
            delete(key);
            return;
        }

        int h1 = hash(key);
        int h2 = hash2(key);

        LinkedList<Entry> chain1 = getOrCreateChain(h1);
        LinkedList<Entry> chain2 = getOrCreateChain(h2);

        for (Entry e : chain1) {
            if (e.key.equals(key)) {
                e.value = val;
                return;
            }
        }
        for (Entry e : chain2) {
            if (e.key.equals(key)) {
                e.value = val;
                return;
            }
        }

        if (chain1.size() <= chain2.size()) {
            chain1.add(new Entry(key, val));
        } else {
            chain2.add(new Entry(key, val));
        }
        N++;
    }

    @Override
    public Value get(Key key) {
        if (key == null) return null;

        int h1 = hash(key);
        int h2 = hash2(key);

        if (h1 >= 0 && h1 < M && entries[h1] != null) {
            LinkedList<Entry> chain1 = entries[h1];
            for (Entry e : chain1) {
                if (e.key.equals(key)) {
                    return e.value;
                }
            }
        }

        if (h2 >= 0 && h2 < M && h2 != h1 && entries[h2] != null) {
            LinkedList<Entry> chain2 = entries[h2];
            for (Entry e : chain2) {
                if (e.key.equals(key)) {
                    return e.value;
                }
            }
        }

        return null;
    }

    @Override
    public void delete(Key key) {
        if (key == null) return;
        if (isEmpty()) return;

        int h1 = hash(key);
        int h2 = hash2(key);

        if (h1 >= 0 && h1 < M && entries[h1] != null) {
            LinkedList<Entry> chain1 = entries[h1];
            for (Entry e : chain1) {
                if (e.key.equals(key)) {
                    chain1.remove(e);
                    N--;
                    return;
                }
            }
        }

        if (h2 >= 0 && h2 < M && entries[h2] != null) {
            LinkedList<Entry> chain2 = entries[h2];
            for (Entry e : chain2) {
                if (e.key.equals(key)) {
                    chain2.remove(e);
                    N--;
                    return;
                }
            }
        }
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
            LinkedList<Entry> chain = entries[i];
            if (chain != null) {
                for (Entry e : chain) {
                    iter_queue.add(e.key);
                }
            }
        }
        return iter_queue;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE METHODS ARE ONLY FOR GRADING AND COME FROM THE TWOPROBECHAINHT INTERFACE.

    @Override
    public int getM() {
        return M;
    }

    @Override
    public int getChainSize(int i) {
        LinkedList<Entry> chain = entries[i];
        if (i < 0 || i >= M || chain == null) {
            return 0;
        }
        return chain.size();
    }
}