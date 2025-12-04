package edu.ser222.m04_02;

/**
 * (basic description of the program or class)
 *
 * Completion time: (estimation of hours spent on this program)
 *
 * @author Pin-Yang Wang, Acuna, Buckner
 * @version 1.3
 */

//Note: not all of these packages may be needed.
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;

public class CompletedMain implements KanjiMain {

    //Do not add any member variables to this class.

    @Override
    public HashMap<Integer, String> loadKanji(String filename, EditableDiGraph graph) {
        HashMap<Integer, String> kanjiMap = new HashMap<Integer, String>();
        BufferedReader reader = null;

        try {
            // UTF-8 for kanji file, handle possible BOM
            reader = new BufferedReader(
                        new InputStreamReader(
                            new FileInputStream(new File(filename)), "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) {
                    continue;
                }

                // remove BOM if present
                if (line.length() > 0 && line.charAt(0) == '\uFEFF') {
                    line = line.substring(1);
                }

                // skip comments
                if (line.startsWith("#")) {
                    continue;
                }

                // format: id<TAB>kanji
                String[] parts = line.split("\\t");
                if (parts.length < 2) {
                    continue;
                }

                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String kanji = parts[1].trim();

                    kanjiMap.put(id, kanji);
                    graph.addVertex(id);   // add node to graph
                } catch (NumberFormatException e) {
                    // skip malformed lines
                }
            }
        } catch (IOException e) {
            // for this assignment, printing stack trace is fine
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try { reader.close(); } catch (IOException e) { /* ignore */ }
            }
        }

        return kanjiMap;
    }

    @Override
    public void loadDataComponents(String filename, EditableDiGraph graph) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(new File(filename)));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) {
                    continue;
                }

                // skip comments
                if (line.startsWith("#")) {
                    continue;
                }

                // format: src<TAB>dst
                String[] parts = line.split("\\t");
                if (parts.length < 2) {
                    continue;
                }

                try {
                    int src = Integer.parseInt(parts[0].trim());
                    int dst = Integer.parseInt(parts[1].trim());

                    graph.addEdge(src, dst);
                } catch (NumberFormatException e) {
                    // skip malformed lines
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try { reader.close(); } catch (IOException e) { /* ignore */ }
            }
        }
    }

    @Override
    public String buildOrderString(EditableDiGraph graph, TopologicalSort topSort, HashMap<Integer, String> kanjiMap) {
        // 1. Build "original" line: original kanji order from the map
        StringBuilder original = new StringBuilder();
        for (Integer id : kanjiMap.keySet()) {
            String ch = kanjiMap.get(id);
            if (ch != null) {
                original.append(ch);
            }
        }

        // 2. Build "sorted" line using topological order
        StringBuilder sorted = new StringBuilder();
        Iterable<Integer> order = topSort.order();

        if (order != null) {
            for (Integer id : order) {
                String ch = kanjiMap.get(id);
                if (ch != null) {
                    sorted.append(ch);
                }
            }
        }

        // 3. Assemble the four-line output
        StringBuilder out = new StringBuilder();
        out.append("Original:\n");
        out.append(original.toString());
        out.append("\nSorted:\n");
        out.append(sorted.toString());

        return out.toString();
    }

    public static void main(String[] args) {
        /***************************************************************************
         * START - CORE DRIVER LOGIC, DO NOT MODIFY                                *
         **************************************************************************/
        String FILENAME_KANJI = "data-kanji.txt";
        String FILENAME_COMPONENTS = "data-components.txt";

        KanjiMain driver = new CompletedMain();
        EditableDiGraph graph = new BetterDiGraph();

        HashMap<Integer, String> kanjiMap = driver.loadKanji(FILENAME_KANJI, graph);
        driver.loadDataComponents(FILENAME_COMPONENTS, graph);

        TopologicalSort intuitive = new IntuitiveTopological(graph);

        System.out.println(driver.buildOrderString(graph, intuitive, kanjiMap));

        /***************************************************************************
         * END - CORE DRIVER LOGIC, DO NOT MODIFY                                  *
         **************************************************************************/
    }
}
