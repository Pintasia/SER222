package edu.ser222.m02_01;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * (basic description of the program or class)
 * 
 * Completion time: (estimation of hours spent on this program)
 *
 * @author Pin-Yang Wang, Acuna, Sedgewick
 * @version 2025/11/01
 */


public class CompletedBenchmarkTool implements BenchmarkTool {
    
    /***************************************************************************
     * START - SORTING UTILITIES, DO NOT MODIFY (FROM SEDGEWICK)               *
     **************************************************************************/

    private static final DecimalFormat DF = new DecimalFormat("0.000");
    private static final Random RNG = new Random();
    
    public static void insertionSort(Comparable[] a) {
        int N = a.length;
        
        for (int i = 1; i < N; i++)
        {
            // Insert a[i] among a[i-1], a[i-2], a[i-3]... ..          
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--)
                exch(a, j, j-1);
        }
    }
    
    
    public static void shellsort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        
        while (h < N/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...
        
        while (h >= 1) {
            // h-sort the array.
            for (int i = h; i < N; i++) {
                // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
                exch(a, j, j-h);
            }
            h = h/3;
        }
    }
    
    
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    
    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }
    
    /***************************************************************************
     * END - SORTING UTILITIES, DO NOT MODIFY                                  *
     **************************************************************************/

    //TODO: implement interface methods.

    // ex: [0,0,0,0,1,1,1,1]
    @Override
    public Integer[] generateTestDataBinary(int size) {
        Integer[] data = new Integer[size];
        int half = size / 2;

        for (int i = 0; i < half; i++)
            data[i] = 0;
        for (int i = half; i < size; i++)
            data[i] = 1;

        return data;
    }

    // ex: [0,0,0,0,1,1,2,3]
    @Override
    public Integer[] generateTestDataHalves(int size) {
        Integer[] data = new Integer[size];
        int value = 0;
        int half_stop = size / 2;
        int remaining = size - half_stop;

        for (int i = 0; i < size; i++) {
            if (i == size-1) {
                data[i] = value+1;
            }
            else if (i < half_stop) {
                data[i] = value;
            }
            else if (i == half_stop) {
                data[i] = ++value;
                half_stop += (remaining / 2);
                remaining = size - half_stop;
            } 
        }

        return data;
    }

    // ex: [0,0,0,0,32,4567,987631,43]
    @Override
    public Integer[] generateTestDataHalfRandom(int size) {
        Integer[] data = new Integer[size];
        int half = size / 2;
        for (int i = 0; i < half; i++)
            data[i] = 0;
        for (int i = half; i < size; i++)
            data[i] = RNG.nextInt(Integer.MAX_VALUE);

        return data;
    }

    // Computes the doubling formula b = log2(t2/t1)
    @Override
    public double computeDoublingFormula(double t1, double t2) {
        if (t1 <= 0) t1 = 1e-9;
        if (t2 <= 0) t2 = 1e-9;
        return Math.log(t2 / t1) / Math.log(2.0);
    }

    // Benchmarks insertion sort using Stopwatch on small and large data.
    @Override
    public double benchmarkInsertionSort(Integer[] small, Integer[] large) {
        // sorting runtime of small array
        Integer[] a1 = small.clone();
        Stopwatch timer1 = new Stopwatch();
        insertionSort(a1);
        double t1 = timer1.elapsedTime();

        // sorting runtime of large array
        Integer[] a2 = large.clone();
        Stopwatch timer2 = new Stopwatch();
        insertionSort(a2);
        double t2 = timer2.elapsedTime();

        return computeDoublingFormula(t1, t2);
    }

    // Benchmarks shellsort using Stopwatch on small and large data.
    @Override
    public double benchmarkShellsort(Integer[] small, Integer[] large) {
        // sorting runtime of small array
        Integer[] a1 = small.clone();
        Stopwatch timer1 = new Stopwatch();
        shellsort(a1);
        double t1 = timer1.elapsedTime();

        // sorting runtime of large array
        Integer[] a2 = large.clone();
        Stopwatch timer2 = new Stopwatch();
        shellsort(a2);
        double t2 = timer2.elapsedTime();

        return computeDoublingFormula(t1, t2);
    }

    // Runs both algorithms on all three data patterns and prints results table.
    @Override
    public void runBenchmarks(int size) {
        int doubleSize = size * 2;

        // generate datasets
        Integer[] binSmall = generateTestDataBinary(size);
        Integer[] binLarge = generateTestDataBinary(doubleSize);

        Integer[] halfSmall = generateTestDataHalves(size);
        Integer[] halfLarge = generateTestDataHalves(doubleSize);

        Integer[] randSmall = generateTestDataHalfRandom(size);
        Integer[] randLarge = generateTestDataHalfRandom(doubleSize);

        // benchmark all
        double binIns = benchmarkInsertionSort(binSmall, binLarge);
        double binShell = benchmarkShellsort(binSmall, binLarge);

        double halfIns = benchmarkInsertionSort(halfSmall, halfLarge);
        double halfShell = benchmarkShellsort(halfSmall, halfLarge);

        double randIns = benchmarkInsertionSort(randSmall, randLarge);
        double randShell = benchmarkShellsort(randSmall, randLarge);

        // output results
        System.out.println("%-8s %10s %10s%n", "", "Insertion", "Shellsort");
        System.out.println("%-8s %10.3f %10.3f%n", "Bin", binIns, binShell);
        System.out.println("%-8s %10.3f %10.3f%n", "Half", halfIns, halfShell);
        System.out.println("%-8s %10.3f %10.3f%n%n", "RanInt", randIns, randShell);
    }

    public static void main(String args[]) {
        BenchmarkTool me = new CompletedBenchmarkTool();
        int size = 4096;
        
        //NOTE: feel free to change size here. all other code must go in the
        //      methods.
        
        me.runBenchmarks(size);
    }
}