/*
 * Weiming Raymond Luo
 * CSCI 405 : Analysis of Algorithms II
 *
 * Graph Cycle Homework Program
 * 
 * This program is used as an object that holds the information of the nodes 
 * along with an LinkedList of the edges.
 *
 */

import java.util.*;

public class GraphData {
    public int head;
    public int outDegree;
    public LinkedList<Integer> edges;
    
    public void initGraphData () {
        this.head = -1;
        this.outDegree = 0;
        this.edges = new LinkedList<Integer>();
    }

    public void setHead (int headData) {
        this.head = headData;
    }

    public void setEdges (int edgeData) {
        this.edges.add(edgeData);
    }
}
