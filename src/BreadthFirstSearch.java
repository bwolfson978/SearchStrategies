import java.util.*;
import java.*;
/**
 * Created by andrewrot on 9/5/2017.
 */
public class BreadthFirstSearch implements ISearchMethod{
    
    LinkedList<Node> visitedList; //anything in this list, the search has already looked at

    public BreadthFirstSearch(){
        visitedList = new LinkedList<Node>();
    }

    public void printMethodName() {
        System.out.println("Breadth 1st search");
        System.out.println();
        System.out.println("Expanded     Queue");
    }


    //search method, traverse the tree using this classes way of searching
    @Override
    public Queue<Node> searchMethod(Graph g) {

        Node start = g.src;
        visitedList.add(start);

        //print first node expanded & it's queue
        System.out.println("   " + start.val + "         "+"[<" + start.val +">]");


        //add check to see if src and goal are the same node -> if so return 'goal reached'

        //hold the path to the goal node
        LinkedList<Node> pathToFinish = new LinkedList<Node>(); //may have to switch the queue
        pathToFinish.add(start);

        //put the adjacent nodes into out queue
        Queue<Node> queue = new LinkedList<Node>(g.adjList.get(start));
        
        //Print the next queues
        /*for (Node n : queue) {
            System.out.println("insert: " + n.val);
        }*/

         while(queue.peek() != null) {
            Node nextNode = queue.poll(); //dequeue the head and hold it here (poll = dequeue)
            
            //print next nodes information
            //System.out.println(nextNode.val + "");

            Iterator<Node> i = g.adjList.get(nextNode).listIterator(); //get nextNode's neighbors
            
            while (i.hasNext()) {
                Node n = i.next();
                if (!visitedList.contains(n)) {
                    visitedList.add(n); //now the node is visited
                    pathToFinish.add(n);
                    queue.add(n);
                }
            }
         }

        //not real path to finish
        printPathToFinish(pathToFinish);


        //Not finished implementing
     
        return pathToFinish;
    }

    //print each step 
    @Override
    public void printStep() {
        System.out.println("X");
    }

    //print path to finish 
    @Override
    public void printPathToFinish(Queue<Node> q) {
        System.out.print("<");
        for(Node n : q)
            System.out.print(n.val + " ");
        System.out.println(">");
    }

}
