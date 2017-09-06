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
        //method.searchMethod(g);

       //return new Queue();
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
    }
}
