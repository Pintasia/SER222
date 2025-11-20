package edu.ser222.m02_02;

/**
 * Implements various divide and conquer algorithms.
 *
 * Last updated 11/08/2025.
 *
 * Completion time: (your completion time)
 *
 * @author Pin-Yang Wang, Acuna, Sedgewick and Wayne
 * @verison 1.1
 */
import java.util.Random;

public class CompletedMerging implements MergingAlgorithms {

    //TODO: implement interface methods.
    @Override
    public <T extends Comparable> Queue<T> mergeQueues(Queue<T> q1, Queue<T> q2) {
        Queue<T> result = new ListQueue<>();

        while (!q1.isEmpty() && !q2.isEmpty()) {
            if (q1.peek().compareTo(q2.peek()) <= 0)
                result.enqueue(q1.dequeue());
            else
                result.enqueue(q2.dequeue());
        }

        while (!q1.isEmpty())
            result.enqueue(q1.dequeue());
        while (!q2.isEmpty())
            result.enqueue(q2.dequeue());

        return result;
    }

    @Override
    public void sort(Comparable[] a) {
        Comparable[] sorted = mergesort(a);
        System.arraycopy(sorted, 0, a, 0, a.length);
    }

    @Override
    public Comparable[] mergesort(Comparable[] a) {
        if (a.length <= 1)
            return a;

        int mid = a.length / 2;
        Comparable[] left = new Comparable[mid];
        Comparable[] right = new Comparable[a.length - mid];

        System.arraycopy(a, 0, left, 0, mid);
        System.arraycopy(a, mid, right, 0, a.length - mid);

        left = mergesort(left);
        right = mergesort(right);

        return merge(left, right);
    }

    @Override
    public Comparable[] merge(Comparable[] a, Comparable[] b) {
        Comparable[] result = new Comparable[a.length + b.length];
        int i = 0, j = 0, k = 0;

        while (i < a.length && j < b.length) {
            if (a[i].compareTo(b[j]) <= 0)
                result[k++] = a[i++];
            else
                result[k++] = b[j++];
        }

        while (i < a.length)
            result[k++] = a[i++];
        while (j < b.length)
            result[k++] = b[j++];

        return result;
    }

    @Override
    public void shuffle(Object[] a) {
        if (a.length <= 1)
            return;

        int mid = a.length / 2;
        Object[] left = new Object[mid];
        Object[] right = new Object[a.length - mid];

        System.arraycopy(a, 0, left, 0, mid);
        System.arraycopy(a, mid, right, 0, a.length - mid);

        shuffle(left);
        shuffle(right);

        mergeShuffle(a, left, right);
    }

    private void mergeShuffle(Object[] result, Object[] a, Object[] b) {
        int i = 0, j = 0, k = 0;
        Random rand = new Random();

        while (i < a.length && j < b.length) {
            if (rand.nextBoolean())     // randomly pick
                result[k++] = a[i++];
            else
                result[k++] = b[j++];
        }

        while (i < a.length)
            result[k++] = a[i++];
        while (j < b.length)
            result[k++] = b[j++];
    }
     
    /**
     * entry point for sample output.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Queue<String> q1 = new ListQueue<>(); q1.enqueue("E"); q1.enqueue("L"); q1.enqueue("O"); q1.enqueue("R"); q1.enqueue("T");
        Queue<String> q2 = new ListQueue<>(); q2.enqueue("A"); q2.enqueue("E"); q2.enqueue("M"); q2.enqueue("P"); q2.enqueue("S"); q2.enqueue("X");
        Queue<Integer> q3 = new ListQueue<>(); q3.enqueue(5); q3.enqueue(12); q3.enqueue(15); q3.enqueue(17); q3.enqueue(20);
        Queue<Integer> q4 = new ListQueue<>(); q4.enqueue(1); q4.enqueue(4); q4.enqueue(12); q4.enqueue(13); q4.enqueue(16); q4.enqueue(18);

        MergingAlgorithms ma = new CompletedMerging();

        //Q1 - sample test cases
        Queue merged1 = ma.mergeQueues(q1, q2);
        System.out.println(merged1.toString());
        Queue merged2 = ma.mergeQueues(q3, q4);
        System.out.println(merged2.toString());
        
        //Q2 - sample test cases
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        ma.sort(a);
        assert isSorted(a);
        show(a);
        
        //Q3 - sample test cases
        String[] b = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        ma.shuffle(b);
        show(b);
        
        ma.shuffle(b);
        show(b);
    }

    //below are utilities functions, please do not change them.
        
    //sorting helper from text
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    //sorting helper from text
    private static void show(Comparable[] a) {
        for (Comparable a1 : a)
            System.out.print(a1 + " ");

        System.out.println();
    }
    
    //sorting helper from text
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1]))
                return false;
        
        return true;
    }
}