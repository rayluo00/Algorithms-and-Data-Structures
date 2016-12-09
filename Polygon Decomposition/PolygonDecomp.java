/*
 * Weiming Raymond Luo
 * CSCI 405 - Analysis of Algorithms II
 * Homework 4 - Polygon Decomposition 
 *
 * This program find the minimum perimeter of the polygon decomposition
 * by creating triangular substructures. Outputs the chords used for the
 * decomposition and the resulting minimum perimeter.
 */

import java.util.*;
import java.io.*;
import java.math.*;

public class PolygonDecomp {

    public static HashMap<Integer, List<Integer>> chordMap = new HashMap<Integer, List<Integer>>(); 
    
    /*****************************************************************************
     * Calculate the distance between two points of a triangle.
     */
    public static double calculateDistance (PolygonData node1, PolygonData node2) {
        double xDistance = (node2.x - node1.x);
        double yDistance = (node2.y - node1.y);

        xDistance *= xDistance;
        yDistance *= yDistance;

        return Math.sqrt(xDistance + yDistance);
    }

    /******************************************************************************
     * Get the total perimeter of the triangle formed with the given three points. 
     */
    public static double calculatePerimeter (ArrayList<PolygonData> graph, int point1, int point2, int point3) {
        PolygonData triangleP1 = graph.get(point1);
        PolygonData triangleP2 = graph.get(point2);
        PolygonData triangleP3 = graph.get(point3);        

        return (calculateDistance(triangleP1, triangleP2) +
                calculateDistance(triangleP2, triangleP3) +
                calculateDistance(triangleP3, triangleP1));
    }

    /******************************************************************************
     * Get the possible chords of the polygon triangulation. Remove chords that are
     * i+1 == chords[i][j] and chords[i][j]+1 == j to remove chords that has two
     * points in the triangle that are next to each other. 
     */
    public static void buildChordsMap (int[][] chords, int i, int j) {
        if (j-i < 2)
            return;

        buildChordsMap(chords,i,chords[i][j]); 

        List<Integer> ls;
        if (i+1 != chords[i][j]) {
            if (!chordMap.containsKey(i)) {
                ls = new ArrayList<Integer>();
                ls.add(chords[i][j]);
                chordMap.put(i, ls);
            } else {
                ls = chordMap.get(i);
                ls.add(chords[i][j]);
                chordMap.put(i, ls);
            }
        }

        if (chords[i][j]+1 != j) {
            if (!chordMap.containsKey(chords[i][j])) {
                ls = new ArrayList<Integer>();
                ls.add(j);
                chordMap.put(chords[i][j], ls);
            } else {
                ls = chordMap.get(chords[i][j]);
                ls.add(j);
                chordMap.put(chords[i][j], ls);
            }
        }
        
        buildChordsMap(chords,chords[i][j],j);
    }

    /*******************************************************************************
     * Find the minimum total perimeter of the polygon decomposition into triangles.
     */
    public static double minPolyPerimeter (ArrayList<PolygonData> graph, int trianglePoints) {
        int j;
        int currentPoint = 0;
        double perimeter;
        int[][] chords = new int[trianglePoints][trianglePoints];
        double[][] table = new double[trianglePoints][trianglePoints];
        
        if (trianglePoints < 3)
            return 0;

        while (currentPoint < trianglePoints) {
            j = currentPoint;
            for (int i = 0; j < trianglePoints; i++) {
                if (j < i+2)
                    table[i][j] = 0.00;
                else {
                    table[i][j] = Double.POSITIVE_INFINITY;

                    for (int k = i+1; k < j; k++) {
                        perimeter = table[i][k] + table[k][j] + calculatePerimeter(graph, i, j, k);

                        if (perimeter < table[i][j]) {
                            chords[i][j] = k;
                            table[i][j] = perimeter;
                        }
                    }
                }
                j++;
            }
            currentPoint++;
        }

        buildChordsMap(chords, 0, trianglePoints-1);
        
        return table[0][trianglePoints-1];
    }
    
    /***************************************************************************
     * Initiate data and create the graph given the points.
     */
    public static void main (String[] args) {
        int index = 0;
        double result;
        String inputCoordinates;
        File inputFile = new File(args[1]);
        BufferedReader fileReader = null;
        ArrayList<PolygonData> graph = new ArrayList<PolygonData>();

        try {
            fileReader = new BufferedReader(new FileReader (inputFile));

            while ((inputCoordinates = fileReader.readLine()) != null) {
                PolygonData data = new PolygonData();
                data.initPolygonData();
                data.setNode(index);

                for (String node : (inputCoordinates.trim()).split("\\s+")) {
                    if (node.equals(""))
                        break;

                    if (data.x == Double.NEGATIVE_INFINITY)
                        data.setX(Double.parseDouble(node));
                    else
                        data.setY(Double.parseDouble(node));
                }

                graph.add(data);
                index++;
            }

            result = minPolyPerimeter(graph, (graph.size()));
            System.out.println("==================== CHORDS ====================");

            for (int i : chordMap.keySet()) {
                for (int j : chordMap.get(i)) {
                    System.out.println(i+", "+j);
                }
            }
            
            System.out.println("==============================================");
            System.out.println("Minimal sum of triangle perimeters : "+result);

        } catch (FileNotFoundException e) {
            System.out.println("error : file not found");
        } catch (IOException e) {
            System.out.println("error : input/output error");
        } catch (NumberFormatException e) {
            System.out.println("error : parse a non-integer");
            e.printStackTrace(System.out);
        }
    }
}
