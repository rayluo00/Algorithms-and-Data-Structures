/*
 * Weiming Raymond Luo
 * W#01130104
 * CSCI 405 : Analysis of Algorithms II
 * Homework 3 - Edmonds-Karp Algorithm
 *
 * Program computes the max flow problem using Edmonds-Karp algorithm, which
 * uses the BFS search from source to sink. Display the paths taken along with
 * the status of the graph. Show the max flow, min cut, and min cut capacity.
 *
 */

import java.util.*;
import java.math.*;
import java.io.*;

public class EdmondsKarp {
    private static int sinkVal = 0;
    private static int maxFlowTotal = 0;
    private static ArrayList<FlowData> graph;

    /************************************** EDMONDS-KARP *************************************
     * Performed the Edmonds-Karp algorithm by running a BFS search to find path from source
     * to sink. Update the graph's flow and print the path taken along with the initial state
     * of the graph.
     */
    public static int performBFS (ArrayList<Integer> sCut, ArrayList<Integer> tCut) {
        int node;
        int curr;
        int next;
        int edgeCap;
        int nextFlow;
        int sink = -1;
        int source = 0;
        int maxFlowUsed = Integer.MAX_VALUE;
        int graphLens = graph.size();
        int[] pred = new int[graphLens];
        ArrayList<FlowData> tempGraph = graph;
        Queue<Integer> queue = new LinkedList<Integer>();

        for (int i = 0; i < graphLens; i++) {
            System.out.println("HEAD : "+graph.get(i).node+" | EDGES : "+graph.get(i).edges);
        }
        System.out.println();
        
        for (int i = 0; i < graphLens; i++) {
            pred[i] = -1;
        }
        
        queue.add(source);

        while (queue.peek() != null) {
            node = queue.poll();

            if (sink < node)
                sink = node;

            for (int d : graph.get(node).edges.keySet()) {
                if (pred[d] == -1 && d != source && graph.get(node).edges.containsKey(d)) {
                    pred[d] = node;
                    queue.add(d);
                }
            }
        }

        if (sink != sinkVal)
            return -1;

        int pathNode = sink;
        Stack<Integer> pathStack = new Stack<Integer>();
        Stack<Integer> flowUpdateStack = new Stack<Integer>();
        while (pathNode != -1) {
            flowUpdateStack.push(pathNode);
            pathStack.push(pathNode);
            pathNode = pred[pathNode];
        }

        System.out.print("BFS PATH : ");
        while (pathStack.peek() != null) {
            curr = pathStack.pop();
            System.out.print(curr+" | ");
            
            if (curr == sink)
                break;
            
            next = pathStack.pop();
            
            nextFlow = graph.get(curr).edges.get(next);
            maxFlowUsed = Math.min(maxFlowUsed, nextFlow);
            pathStack.push(next);
        }
        System.out.println("\n");

        while (flowUpdateStack.peek() != null) {
            curr = flowUpdateStack.pop();
            
            if (curr == sink)
                break;
            
            next = flowUpdateStack.pop();
            nextFlow = (graph.get(curr).edges.get(next)-maxFlowUsed);
            if (nextFlow != 0)
                graph.get(curr).edges.put(next, nextFlow);
            else
                graph.get(curr).edges.remove(next);
            flowUpdateStack.push(next);
        }

        maxFlowTotal += maxFlowUsed;
        return sink;
    }

    /************************************** QUICKSORT *************************************
     * Peforms a quicksort of the graph to sort the position of the nodes to easily access
     * the FlowData in the ArrayList.
     */
    public static int partition (int low, int high) {
        int pivot = graph.get(high).node;
        int i = (low-1);

        for (int j = low; j <= high-1; j++) {
            if (graph.get(j).node <= pivot) {
                i++;
                FlowData temp = graph.get(i);
                graph.set(i, graph.get(j));
                graph.set(j, temp);
            }
        }

        FlowData temp = graph.get(i+1);
        graph.set((i+1), graph.get(high));
        graph.set(high, temp);

        return (i+1);
    }

    public static void quicksort (int low, int high) {
        int point;
        
        if (low < high) {
            point =  partition(low, high);
            quicksort(low, point-1);
            quicksort(point+1, high);
        }
    }

    /************************************** MAIN/INIT *************************************
     * Read from the text file and create the graph using the FlowData object. Continue to
     * search graph using BFS until all paths from source to sink is completed. Obatin the
     * min cut of the source and sink using the residual graph. 
     */
    public static void main (String[] args) {
        int flow = -1;
        int edgeNode = -1;
        int linebreak = 0;
        int sinkRetVal;
        int currentNode;
        int totalGraphNodes;
        String vertex;
        File inputFile = new File(args[1]);
        Queue<Integer> minCutQueue = new LinkedList<Integer>();
        BufferedReader fileReader = null;
        ArrayList<FlowData> tempGraph = null; 
        ArrayList<Integer> sCut = new ArrayList<Integer>();
        ArrayList<Integer> tCut = new ArrayList<Integer>();
        graph = new ArrayList<FlowData>();
        System.out.println("--------------------------------");

        try {
            fileReader = new BufferedReader(new FileReader (inputFile));
            totalGraphNodes = Integer.parseInt(fileReader.readLine());
            sinkVal = totalGraphNodes - 1;

            while ((vertex = fileReader.readLine()) != null) {
                FlowData data = new FlowData();
                data.initFlowData();

                for (String node : (vertex.trim()).split("\\s+")) {
                    if (node.equals(""))
                        break;
                    
                    if (data.node == -1) {
                        data.setNode(Integer.parseInt(node));
                    } else {
                        if (edgeNode == -1)
                            edgeNode = Integer.parseInt(node);
                        else if (flow == -1)
                            flow = Integer.parseInt(node);
                        
                        if (edgeNode != -1 && flow != -1) {
                            data.addEdge(edgeNode, flow);
                            edgeNode = -1;
                            flow = -1;
                        }
                    }
                }
                
                graph.add(data);
            }
           
            if (sinkVal == graph.size()) {
                FlowData sink = new FlowData();
                sink.initFlowData();
                sink.node = sinkVal;
                graph.add(sink);
            }
            
            quicksort(0, (graph.size()-1));

            sinkRetVal = sinkVal;
            while (sinkRetVal == sinkVal) {
                sinkRetVal = performBFS(sCut, tCut);
            }

            minCutQueue.add(0);
            sCut.add(0);

            while (minCutQueue.peek() != null) {
                currentNode = minCutQueue.poll();

                for (Iterator<HashMap.Entry<Integer, Integer>> mapIter = graph.get(currentNode).edges.entrySet().iterator(); mapIter.hasNext();) {
                    HashMap.Entry<Integer, Integer> key = mapIter.next();

                    if (!sCut.contains(key.getKey())) {
                        sCut.add(key.getKey());
                        minCutQueue.add(key.getKey());
                    }

                    mapIter.remove();
                }
            }

            for (FlowData data : graph) {
                if (!sCut.contains(data.node) && !tCut.contains(data.node))
                    tCut.add(data.node);

                if (graph.get(data.node).edges.size() > 1) {
                    for (int key : graph.get(data.node).edges.keySet()) {
                        if (!sCut.contains(key) && !tCut.contains(key))
                            tCut.add(key);
                    }
                }
            }

            System.out.println("\nMAX FLOW : "+maxFlowTotal);
            System.out.print("S MIN CUT : ");
            for (int x : sCut) {
                linebreak++;
                System.out.print(x+" | ");

                if (linebreak == 10) {
                    System.out.print("\n            ");
                    linebreak = 0;
                }
            }
            System.out.println();

            System.out.print("T MIN CUT : ");
            linebreak = 0;
            for (int x : tCut) {
                linebreak++;
                System.out.print(x+" | ");

                if (linebreak == 10) {
                    System.out.print("\n            ");
                    linebreak = 0;
                }
            }
            System.out.println("\nMIN CUT CAPACITY : "+maxFlowTotal);
            
            System.out.println("--------------------------------");
            
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
