/*
 * Weiming Raymond Luo
 * W#01130104
 * CSCI 405 : Analysis of Algorithms II
 * Homework 3 - Edmonds-Karp Algorithm
 *
 * Class to store data of every node in the graph.
 *
 */

import java.util.*;

public class FlowData {
    public int node;
    public int status;
    public int maxFlow;
    public int currFlow;
    public HashMap<Integer, Integer> edges;

    public void initFlowData () {
        this.node = -1;
        this.status = 0;
        this.maxFlow = -1;
        this.currFlow = -1;
        this.edges = new HashMap<Integer, Integer>();
    }

    public void setNode (int inputNode) {
        this.node = inputNode;
    }

    public void setStatus (int inputStatus) {
        this.status = inputStatus;
    }

    public void setMaxFlow (int inputMaxFlow) {
        this.maxFlow = inputMaxFlow;
    }

    public void setCurrFlow (int inputCurrFlow) {
        this.currFlow = inputCurrFlow;
    }

    public void addEdge (int inputEdge, int inputFlow) {
        this.edges.put(inputEdge, inputFlow);
    }
}
