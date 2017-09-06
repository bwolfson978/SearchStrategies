import java.util.*;
/**
 * Created by andrewrot on 9/5/2017.
 */
public class BreadthFirstSearch implements ISearchMethod{
    
    LinkedList<Node> visitedList; //anything in this list, the search has already looked at

    public BreadthFirstSearch(){
        visitedList = new LinkedList();
    }

    public void printMethodName() {
        System.out.println("Breadth 1st search");
    }


    //search method, traverse the tree using this classes way of searching
    @Override
    public Queue<Node> searchMethod(Graph g) {

        Node start = g.src;
        visitedList.add(start);

        //hold the path to the goal node
        LinkedList pathToFinish = new LinkedList();
        pathToFinish.add(start);

        //hold the adjacent nodes of the root
        LinkedList adjacentNodes = g.adjList.get(start);///Lets convert the hashmap to be node, queue (not list)
        Queue<Node> queue = new PriorityQueue<Node>();
        //otherwise we need to convert the list to the queue


        //Not finished implementing
     
        Queue<Node> q = new LinkedList<Node>();
        return q;
    }

    //print each step 
    @Override
    public void printStep() {
        System.out.println("X");
    }
}
