package edu.ser222.m03_04;

/**
 * A symbol table implemented using a hashtable with quadratic probing.
 * 
 * @author Pin-Yang Wang, Acuna
 */
public class CompletedQuadProbingHT<Key, Value> extends CompletedLinearProbingHT<Key, Value> {

    //any constructors must be made public

    public CompletedQuadProbingHT() {
        super();
    }

    public CompletedQuadProbingHT(int capacity) {
        super(capacity);
    }

    @Override
    public int hash(Key key, int i) {
        int hash_code = (key.hashCode() & 0x7fffffff);
        return (hash_code + (i * i)) % getM();
    }
}