/* MST.java
   CSC 226 - Fall 2018
   Problem Set 2 - Template for Minimum Spanning Tree algorithm

   The assignment is to implement the mst() method below, using Kruskal's algorithm
   equipped with the Weighted Quick-Union version of Union-Find. The mst() method computes
   a minimum spanning tree of the provided graph and returns the total weight
   of the tree. To receive full marks, the implementation must run in time O(m log m)
   on a graph with n vertices and m edges.

   This template includes some testing code to help verify the implementation.
   Input graphs can be provided with standard input or read from a file.

   To provide test inputs with standard input, run the program with
       java MST
   To terminate the input, use Ctrl-D (which signals EOF).

   To read test inputs from a file (e.g. graphs.txt), run the program with
       java MST graphs.txt

   The input format for both methods is the same. Input consists
   of a series of graphs in the following format:

       <number of vertices>
       <adjacency matrix row 1>
       ...
       <adjacency matrix row n>

   For example, a path on 3 vertices where one edge has weight 1 and the other
   edge has weight 2 would be represented by the following

   3
   0 1 0
   1 0 2
   0 2 0

   An input file can contain an unlimited number of graphs; each will be processed separately.

   NOTE: For the purpose of marking, we consider the runtime (time complexity)
         of your implementation to be based only on the work done starting from
   the mst() method. That is, do not not be concerned with the fact that
   the current main method reads in a file that encodes graphs via an
   adjacency matrix (which takes time O(n^2) for a graph of n vertices).

   (originally from B. Bird - 03/11/2012)
   (revised by N. Mehta - 10/9/2018)
   Addebayo Ogunmuiwa
*/

import java.util.Scanner;
import java.io.File;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.*;
import java.io.*;
class uf {


  public static void find (int n ,int [] parent , int []componentSize){

        for(int i = 0; i < parent.length; i++) {
            parent[i] = i;
            componentSize[i] = 0;
        }

  }
  public static int find(int v, int [] parent) {
        ArrayList<Integer> path = new ArrayList<Integer>();
        while(v != parent[v]) {
            path.add(v);
            v = parent[v];
        }
        for(int p : path) {
            parent[p] = v;
        }
        return v;
    }
  public static boolean connected(int u, int v, int [] parent) {
        return uf.find(u, parent) == uf.find(v, parent);
  }
   public static void union(int u, int v, int [] size, int [] parent) {
        int parentV = uf.find(v, parent), parentU = uf.find(u, parent);
        if(size[parentU] < size[parentV]) {
            parent[parentU] = parentV;
            size[parentV] += size[parentU];
        } else {
            parent[parentV] = parentU;
            size[parentU] += size[parentV];
        }
    }

}

public class MST {



  public static int total_weight(ArrayList<int[]> list_of_edges,int []parent,int []componentSize, int totalWeight,int n ){
      int m = 0;
        for(int [] i : list_of_edges) {
            if(!(uf.connected(i[0], i[1], parent))) {
                uf.union(i[0], i[1], componentSize, parent);
                totalWeight += i[2];
                m++;
            }

            if(m >= n-1) break;
        }
        return totalWeight;

    }
  static int mst(int[][][] adj) {
      int n = adj.length;



      int totalWeight = 0;
      /* ... Your code here ... */


      int [] parent = new int[n];
      int [] componentSize = new int [n];

      uf.find(n,parent,componentSize);

      ArrayList<int[]> list_of_edges = new ArrayList<int[]>();
      int weight, v;
      int [] edge ;
      for(int i = 0; i < adj.length; i++) {
        for(int j = 0; j < adj[i].length; j++) {
            v = adj[i][j][0];
            weight = adj[i][j][1];


            if(weight == 0) {
                break;
            } else if(v < i) {
                continue;
            }

            edge = new int[3];
            edge[0] = i;
            edge[1] = v;
            edge[2] = weight;
            list_of_edges.add(edge);


        }
      }
      list_of_edges.sort( (int [] a, int [] b) -> (a[2] - b[2]) );
      return totalWeight = total_weight(list_of_edges,parent,componentSize,totalWeight,n);

    }


    public static void main(String[] args) {
  /* Code to test your implementation */
  /* You may modify this, but nothing in this function will be marked */

  int graphNum = 0;
  Scanner s;

  if (args.length > 0) {
      //If a file argument was provided on the command line, read from the file
      try {
    s = new Scanner(new File(args[0]));
      }
      catch(java.io.FileNotFoundException e) {
    System.out.printf("Unable to open %s\n",args[0]);
    return;
      }
      System.out.printf("Reading input values from %s.\n",args[0]);
  }
  else {
      //Otherwise, read from standard input
      s = new Scanner(System.in);
      System.out.printf("Reading input values from stdin.\n");
  }

  //Read graphs until EOF is encountered (or an error occurs)
  while(true) {
      graphNum++;
      if(!s.hasNextInt()) {
    break;
      }
      System.out.printf("Reading graph %d\n",graphNum);
      int n = s.nextInt();

      int[][][] adj = new int[n][][];




      int valuesRead = 0;
      for (int i = 0; i < n && s.hasNextInt(); i++) {
    LinkedList<int[]> edgeList = new LinkedList<int[]>();
    for (int j = 0; j < n && s.hasNextInt(); j++) {
        int weight = s.nextInt();
        if(weight > 0) {
      edgeList.add(new int[]{j, weight});
        }
        valuesRead++;
    }
    adj[i] = new int[edgeList.size()][2];
    Iterator it = edgeList.iterator();
    for(int k = 0; k < edgeList.size(); k++) {
        adj[i][k] = (int[]) it.next();
    }
      }
      if (valuesRead < n * n) {
    System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
    break;
      }

      // // output the adjacency list representation of the graph
      // for(int i = 0; i < n; i++) {
      //  System.out.print(i + ": ");
      //  for(int j = 0; j < adj[i].length; j++) {
      //      System.out.print("(" + adj[i][j][0] + ", " + adj[i][j][1] + ") ");
      //  }
      //  System.out.print("\n");
      // }

      int totalWeight = mst(adj);
      System.out.printf("Graph %d: Total weight of MST is %d\n",graphNum,totalWeight);


  }
    }


}
