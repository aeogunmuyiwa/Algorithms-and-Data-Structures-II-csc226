/* ShortestPaths.java
   CSC 226 - Fall 2018

   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
   java ShortestPaths

   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
   java ShortestPaths file.txt
   where file.txt is replaced by the name of the text file.

   The input consists of a series of graphs in the following format:

   <number of vertices>
   <adjacency matrix row 1>
   ...
   <adjacency matrix row n>

   Entry A[i][j] of the adjacency matrix gives the weight of the edge from
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].

   An input file can contain an unlimited number of graphs; each will be processed separately.

   NOTE: For the purpose of marking, we consider the runtime (time complexity)
         of your implementation to be based only on the work done starting from
	 the ShortestPaths() method. That is, do not not be concerned with the fact that
	 the current main method reads in a file that encodes graphs via an
	 adjacency matrix (which takes time O(n^2) for a graph of n vertices).


   (originally from B. Bird - 08/02/2014)
   (revised by N. Mehta - 10/24/2018)
   Adebayo Ogunmuyiwa
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.*;
import java.io.*;
import java.util.Comparator;

class Edge{
  public int vertex_to ;
  public int vertex_source_from ;
  public int weight;

  public Edge( int vertex_to,int vertex_source_from,  int weight){
    this.vertex_to = vertex_to  ;
    this.vertex_source_from = vertex_source_from ;
    this.weight = weight;
  }
  public int getVertex_to(){
    return vertex_to;
  }
  public int get_vertex_from(){
    return vertex_source_from ;
  }
  public int get_weight(){
    return weight ;
  }

}
//Do not change the name of the ShortestPaths class
public class ShortestPaths{
    public static PriorityQueue<Edge> pq;
    public static boolean []visted ;
    public static int [] distance ;
    public static int [] path ;
    public static ArrayList<ArrayList<Edge>> list_of_edges = new   ArrayList<ArrayList<Edge>> ();

    //TODO: Your code here
    public static int n; // number of vertices

    public static void build_graph(int[][][]adj){
       //ArrayList<ArrayList<Edge>> list_of_edges = new   ArrayList<ArrayList<Edge>> ();
        for(int i = 0; i < adj.length; i++) {
          ArrayList<Edge> e1 = new ArrayList<Edge>();
          for(int j = 0; j < adj[i].length; j++) {
            int vertex_to = adj[i][j][0];
            int vertex_source_from = i;
            int weight = adj[i][j][1];
            Edge new_edge = new Edge (vertex_to,vertex_source_from,weight);
            e1.add(new_edge);
          }
          list_of_edges.add(i,e1);
        }

    }
    static void algorithm ( ArrayList<ArrayList<Edge>> list_of_edges){

      while (!pq.isEmpty()){
        Edge edge1 = pq.poll();
        int value_vetex_from= edge1.get_vertex_from();
        int value_vertex_source_to = edge1.getVertex_to();
        if(visted[value_vertex_source_to]){
          continue;
        }
        if(distance[value_vertex_source_to] > distance [value_vetex_from] + edge1.get_weight()){
          distance[value_vertex_source_to] = distance[value_vetex_from] + edge1.get_weight();
          path[value_vertex_source_to] = value_vetex_from;
        }
        visted[value_vertex_source_to] = true;
        for(Edge i : list_of_edges.get(value_vertex_source_to)){
          if(!visted[i.getVertex_to()]){
            pq.add(i);
          }
        }
      }

    }
    static void distance_set(int source , ArrayList<ArrayList<Edge>> list_of_edges){
      distance = new int [ n];
      for(int i = 0 ; i< distance.length; i ++){
        if ( i == source ) {
          distance[i] = 0 ;
        }
        else {
          distance[i] = Integer.MAX_VALUE;
        }
      }

      path = new int [n];
      for(int i = 0 ; i < path.length; i ++){
        path[i] = ((i == source) ? source : -1);
      }

      pq = new PriorityQueue <Edge>((e, f) -> {
      int x = distance[e.get_vertex_from()] + e.get_weight();
      int y = distance[f.get_vertex_from()] + f.get_weight();

      if(x == y) return 0;
      else if(x < y) return -1;
      else return 1;
      });

      for ( Edge e : list_of_edges.get(source)){
        pq.add(e);
      }
    }

    static void ShortestPaths(int[][][] adj, int source){
      n = adj.length;
      build_graph(adj);
      visted = new boolean[n];
      visted[source ]= true ;
      distance_set(source,list_of_edges);
      algorithm(list_of_edges);

      //TODO: Your code herealgorithm(list_of_edges);
    }

    static void PrintPaths(int source){
      for(int i = 0; i < n; i++) {
            System.out.print("The path from " + source + " to " + i +" is: " + getPath(source, i));
            System.out.println(" and the total distance is : " + distance[i]);
      }
    }
    private static String getPath(int s, int v) {
      String path_1= Integer.toString(v);
      while(v != path[v]) {
        path_1 = path[v] + " --> " + path_1;
        v = path[v];
      }
      return path_1;
    }


    /* main()
       Contains code to test the ShortestPaths function. You may modify the
       testing code if needed, but nothing in this function will be considered
       during marking, and the testing process used for marking will not
       execute any of the code below.
    */
    public static void main(String[] args) throws FileNotFoundException{
	Scanner s;
	if (args.length > 0){
	    //If a file argument was provided on the command line, read from the file
	    try{
		s = new Scanner(new File(args[0]));
	    } catch(java.io.FileNotFoundException e){
		System.out.printf("Unable to open %s\n",args[0]);
		return;
	    }
	    System.out.printf("Reading input values from %s.\n",args[0]);
	}
	else{
	    //Otherwise, read from standard input
	    s = new Scanner(System.in);
	    System.out.printf("Reading input values from stdin.\n");
	}

	int graphNum = 0;
	double totalTimeSeconds = 0;

	//Read graphs until EOF is encountered (or an error occurs)
	while(true){
	    graphNum++;
	    if(graphNum != 1 && !s.hasNextInt())
		break;
	    System.out.printf("Reading graph %d\n",graphNum);
	    int n = s.nextInt();
	    int[][][] adj = new int[n][][];

	    int valuesRead = 0;
	    for (int i = 0; i < n && s.hasNextInt(); i++){
		LinkedList<int[]> edgeList = new LinkedList<int[]>();
		for (int j = 0; j < n && s.hasNextInt(); j++){
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
	    if (valuesRead < n * n){
		System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
		break;
	    }

	    // output the adjacency list representation of the graph
	    for(int i = 0; i < n; i++) {
	    	System.out.print(i + ": ");
	    	for(int j = 0; j < adj[i].length; j++) {
	    	    System.out.print("(" + adj[i][j][0] + ", " + adj[i][j][1] + ") ");
	    	}
	    	System.out.print("\n");
	    }

	    long startTime = System.currentTimeMillis();

	    ShortestPaths(adj, 0);
	    PrintPaths(0);
	    long endTime = System.currentTimeMillis();
	    totalTimeSeconds += (endTime-startTime)/1000.0;

	    //System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
	}
	graphNum--;
	System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
    }
}
