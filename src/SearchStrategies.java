import java.io.*;
import java.util.*;

/**
 * Created by bwolfson on 9/4/2017.
 */
public class SearchStrategies {

    private Graph graph;

    public SearchStrategies(){
        graph = new Graph();
    }

    private void readFile(File fin) throws IOException {
        FileInputStream fis = new FileInputStream(fin);

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            String[] vals = line.split(" ");
            if(vals.length == 3){
                char fromVal = vals[0].charAt(0);
                char toVal = vals[1].charAt(0);
                float d = Float.parseFloat(vals[2]);
                graph.addEdge(fromVal, toVal, d);
                // add the reverse direction as well
                graph.addEdge(toVal, fromVal, d);
                if(fromVal == 'S' || toVal == 'S'){
                    graph.src = new Node(fromVal, 0);
                }
                if(toVal == 'G'){
                    graph.goal = new Node(toVal, d);
                }
                if(fromVal == 'G'){
                    graph.goal = new Node(fromVal);
                }
            }
            else if(vals.length == 2){
                char val = vals[0].charAt(0);
                float h = Float.parseFloat(vals[1]);
                graph.addHeuristic(val, h);
            }
        }

        br.close();
    }

    public void General_Search(Graph g, ISearchMethod method){
        //print name of the method
        method.printMethodName();

        //call the search method
        method.searchMethod(g);


        //if( method.searchMethod(g).isEmpty()){
            //failed
        //}
        //else{
            //found a path to goal

        //}
       //return new Queue();

        ///***** DONT SUBMIT without checking General_Search instructions on asignment*****
    }

    public static void main(String[] args){
        System.out.println("hello");
        SearchStrategies s = new SearchStrategies();
        // read file into adjacency list representation
        File dir = new File(".");
        try {
            File graph = new File(dir.getCanonicalPath() + File.separator + "graph.txt");
            s.readFile(graph);
        }
        catch(IOException exc){
            System.out.println("exception caught:" + exc.getMessage());
        }

        //Begin searches...

        //Depth 1st search
        try {
            DepthFirstSearch dfs = new DepthFirstSearch();
            s.General_Search(s.graph, dfs);
        }
        catch(Exception exc){
            System.out.println("exception caught in dfs:" + exc.getMessage());
        }

        //Breadth 1st search
        try {
            BreadthFirstSearch bfs = new BreadthFirstSearch();
            s.General_Search(s.graph, bfs);
        }
        catch(Exception exc){
            System.out.println("exception caught in bfs:" + exc.getMessage());
        }

        //Depth-Limited Search
        try {
            DepthLimitedSearch dls = new DepthLimitedSearch(2);
            s.General_Search(s.graph, dls);
        }
        catch(Exception e){
            System.out.println("exc caught in depth-limited: " + e.getMessage());
        }

        // iterative deepening search
        try {
            IterativeDeepeningSearch ids = new IterativeDeepeningSearch();
            s.General_Search(s.graph, ids);
        }
        catch(Exception e){
            System.out.println("exc caught in iterative deepening search: " + e.getMessage());
        }



        //Uniform Search
        try {
            UniformSearch us = new UniformSearch();
            s.General_Search(s.graph, us);
        }
        catch(Exception e){
            System.out.println("exception caught in us:" + e.getMessage());
        }

        // greedy search
        try {
            GreedySearch gs = new GreedySearch();
            s.General_Search(s.graph, gs);
        }
        catch(Exception e){
            System.out.println("exception caught in gs: " + e.getMessage());
        }

        // A* Search
        try{
            AStarSearch as = new AStarSearch();
            s.General_Search(s.graph, as);
        }
        catch(Exception e){
            System.out.println("exception caught in as: " + e.getMessage());
        }

        // Hill Climbing without BT Search
        try{
            HillClimbingWithoutBT hc = new HillClimbingWithoutBT();
            s.General_Search(s.graph, hc);
        }
        catch(Exception e){
            System.out.println("exception caught in hc: " + e.getMessage());
        }

        // Beam Search
        try{
            BeamSearch bs = new BeamSearch(2);
            s.General_Search(s.graph, bs);
        }
        catch(Exception e){
            System.out.println("exception caught in as: " + e.getMessage());
        }

          // Hill Climbing  w bt Search
        try{
            HillClimbing hc2 = new HillClimbing();
            s.General_Search(s.graph, hc2);
        }
        catch(Exception e){
            System.out.println("exception caught in hc: " + e.getMessage());
        }

    }
}
