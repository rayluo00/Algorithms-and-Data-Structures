/*
 * Weiming Raymond Luo
 * CSCI 405 : Analysis of Algorithms II
 *
 * Graph Cycle Homework Program
 * 
 * This program is to determine if the input data from a text file contains a
 * valid cycle. The runtime for the algorithm is, Theta(V + E). If there isn't
 * a valid cycle in the graph, the program will state no possible cycles.
 *
 */

import java.util.*;
import java.io.*;

public class GraphCycle {

    /************************************************************************************************ 
     * Print the graph.
     */ 
    public static void printCycle (ArrayList<GraphData> graph) {
        for (int i = 0; i < graph.size(); i++) {
            System.out.println("Node : "+graph.get(i).head+" | Edges : "+graph.get(i).edges);
        }
        System.out.println("\n============================================================");
    }

    /************************************************************************************************ 
     * Determine where to start when finding a new cycle in the graph.
     */
    public static int findStartingNode (ArrayList<GraphData> graph, ArrayList<Integer> finalCycle) {
        int maxOutDegreeNode = 0;
        
        for (int i = 0; i < graph.size(); i++) {
            if ((graph.get(i).outDegree > graph.get(maxOutDegreeNode).outDegree) && finalCycle.contains(i)) {
                maxOutDegreeNode = i;
            }
        }

        return maxOutDegreeNode;
    }

    /************************************************************************************************ 
     * Check if there are any more edges that weren't checked after the previous
     * cycle check.
     */
    public static boolean hasMoreCycles (ArrayList<GraphData> graph) {
        for (int i = 0; i < graph.size(); i++) {
            if (!graph.get(i).edges.isEmpty())
                return true;
        }

        return false;
    }

    /************************************************************************************************
     * Perform the cycle of the graph by starting at node 0, which determines the next
     * node to take based on the out degree. There will be a check at the end when
     * there is no node to take but still has some paths to take. This check will
     * determine any non-cyclic graphs.
     */
    public static void performCycle (ArrayList<GraphData> graph, ArrayList<Integer> tempCycle, ArrayList<Integer> finalCycle) {
        int currNode = 0;
        int maxOutDegreeNode = 0;

        //printCycle(graph);

        if (!graph.get(0).edges.isEmpty()) {
            currNode = 0;
        } else {
            currNode = findStartingNode(graph, finalCycle);
        }
        
        tempCycle.add(currNode);
        
        while (!graph.get(currNode).edges.isEmpty()) {
            maxOutDegreeNode = graph.get(currNode).edges.peek();
            for (int i : graph.get(currNode).edges) {
                if (graph.get(i).outDegree > graph.get(maxOutDegreeNode).outDegree) {
                    maxOutDegreeNode = i;
                }
            }

            graph.get(currNode).edges.removeFirstOccurrence(maxOutDegreeNode);
            graph.get(currNode).outDegree--;
            currNode = maxOutDegreeNode;
            
            tempCycle.add(currNode);
        }

        //printCycle(graph);
    }

    /************************************************************************************************ 
     * Combine the current graph to get a complete cycle after finding a new cycle.
     */
    public static ArrayList<Integer> combineGraphs (ArrayList<Integer> tempCycle, ArrayList<Integer> finalCycle) {
        ArrayList<Integer> updatedCycle = new ArrayList<Integer>();
        int src  = tempCycle.get(0);
        
        if (!finalCycle.contains(src)) {
            for (int i = 0; i < tempCycle.size(); i++) {
                updatedCycle.add(tempCycle.get(i));
            }
        } else {
            for (int i = 0; i < finalCycle.size(); i++) {
                if (finalCycle.get(i) == src) {
                    for (int j = 0; j < tempCycle.size(); j++) {
                        updatedCycle.add(tempCycle.get(j));
                    }
                    src = -1;
                } else {
                    updatedCycle.add(finalCycle.get(i));
                }
            }
        }

        return updatedCycle;
    }

    /*************************************************************************************************
     * Reads the data from the text file and sets up the graph of GraphData objects
     * into an ArrayList.
     */
    public static void main (String[] args) {
        BufferedReader fileReader = null;
        File inputFile = new File(args[1]);
        String currentVertices;
        int printCount = 0;
        ArrayList<GraphData> graph = new ArrayList<GraphData>();
        ArrayList<Integer> finalCycle = new ArrayList<Integer>();
        ArrayList<Integer> tempCycle = new ArrayList<Integer>();
        
        try {
            fileReader = new BufferedReader(new FileReader (inputFile));
            while ((currentVertices = fileReader.readLine()) != null) {
                GraphData data = new GraphData();
                data.initGraphData();
                
                for (String point : (currentVertices.trim()).split("\\s+")) {
                    if (data.head == -1) {
                        if (point.equals(""))
                            break;
                        data.setHead(Integer.parseInt(point));
                    } else {
                        data.setEdges(Integer.parseInt(point));
                        data.outDegree++;
                    }
                }
                
                graph.add(data);
            }

            while (hasMoreCycles(graph)) {
                performCycle(graph, tempCycle, finalCycle);
                finalCycle = combineGraphs(tempCycle, finalCycle);
                tempCycle.clear();
                //tempCycle.add(1111);
            }

            System.out.println("============================================================\nCycle : ");
            for (int i : finalCycle) {
                System.out.print(i+" ");
                printCount++;

                if (printCount > 19) {
                    System.out.println("");
                    printCount = 0;
                }
            }
            System.out.println("\n============================================================");
            
        } catch (FileNotFoundException e) {
            System.out.println("error : file not found");
        } catch (IOException e) {
            System.out.println("error : input/output error");
        } catch (NumberFormatException e) {
            System.out.println("error : parsed a non-integer");
            e.printStackTrace(System.out);
        }
    }
}
