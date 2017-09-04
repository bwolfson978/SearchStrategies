import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by bwolfson on 9/4/2017.
 */
public class Graph {
    Node src;
    Node goal;
    HashMap<Node, LinkedList<Node>> adjList;

    public Graph(){
        this.adjList = new HashMap<>();
    }

    public void addEdge(char fromVal, char toVal, float distance){
        Node from = new Node(fromVal);
        Node to = new Node(toVal, distance);
        LinkedList<Node> neighbors = adjList.containsKey(from) ? adjList.get(from) : new LinkedList<>();
        neighbors.add(to);
        adjList.put(from, neighbors);
    }

    public void addHeuristic(char val, float h){
        //find possible key
        for(Node key: adjList.keySet()){
            if(key.val == val){
                key.h = h;
                if(val == 'S'){
                    src.h = h;
                }
            }
            LinkedList<Node> neighbors = adjList.get(key);
            for(Node n : neighbors){
                if(n.val == val){
                    n.h = h;
                    adjList.put(key, neighbors);
                    if(val == 'S'){
                        src.h = h;
                    }
                }
            }
        }
        //find possible values

    }


}
