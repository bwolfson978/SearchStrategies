import java.util.*;
import java.*;

/**
 * Created by andrewrot on 9/5/2017.
 */
public class DepthFirstSearch implements ISearchMethod{
    
        LinkedList<Node> visitedList; //anything in this list, the search has already looked at


    public DepthFirstSearch(){
        visitedList = new LinkedList<Node>();
    }


    @Override
    public void printMethodName() {
        System.out.println("Depth 1st search");
    }


    //search method, traverse the tree using this classes way of searching
    @Override
    public Queue<Node> searchMethod(Graph g) {
        Node start = g.src;
        visitedList.add(start);

        //hold the path to the goal node
        LinkedList<Node> pathToFinish = new LinkedList<Node>(); //may have to switch the queue
        pathToFinish.add(start);
        //put the adjacent nodes into out queue
        LinkedList<Node> queue = new LinkedList<Node>(g.adjList.get(start));

        //Throw S in here and expand it (must be in a linked list to work)
        LinkedList<Node> initQueue = new LinkedList<Node>(Arrays.asList(start));

        //Need a queue of queues (The Queue of all Queues :: used for printing purposes only)
        LinkedList<LinkedList<Node>> queueOfQueues = new LinkedList<LinkedList<Node>>(Arrays.asList(initQueue));


        //print first step and then remove it
        printStep(queueOfQueues);
        queueOfQueues.removeFirst();

        //Maintain 'threading' of queues -> need new queue for every neighbor of S
        for (Node n : queue) {
            LinkedList<Node> newQueue = new LinkedList<Node>();
            newQueue.add(n);
            newQueue.add(start); 
            queueOfQueues.add(newQueue);
        }

        //print out current step
        printStep(queueOfQueues);

        //------------------- Everything after the first step -------------------
        outerloop: while(queue.peek() != null) {
            Node nextNode = queue.poll(); //dequeue the head and hold it here (poll = dequeue)
         
            Iterator<Node> i = g.adjList.get(nextNode).iterator(); //get nextNode's neighbors
            
            int counter = 0;

            //store the front of the queue. New nodes will add to this previous head.
            LinkedList<Node> tempFront = new LinkedList<Node>(queueOfQueues.poll());

            //check to see if the solution has reached the first of list, if so, were good.
            if(tempFront.peekFirst().val == 'G' && tempFront.peekLast().val == 'S'){
                pathToFinish = tempFront; //update the path to finish
                break outerloop; //goal reached!
            }

            while (i.hasNext()) {
                
                Node n = i.next();

                //for each of it's neighbors, make a new list - starting from the previous queue head
                LinkedList<Node> frontList =  new LinkedList<Node>(tempFront);
                
                //Always add unless letter exists in the front queue - no redundancies/cycles
                if(!tempFront.contains(n)){
                    frontList.addFirst(n); //add new neighbor to front of this list

                    //add this to the queue where the current count is at
                    queueOfQueues.add(counter, frontList); 
                    
                    pathToFinish.add(n);
                    queue.add(counter, n);//also important to update the search queue in correct order

                    counter++;
                }
            
            }
            //***** Print related code *******
            //now we are done with the front list
            printStep(queueOfQueues);             
         }

        System.out.println("goal reached!");

        //Final path from start to finish
        printPathToFinish(pathToFinish); //Not required, but i like it

        //Not finished implementing
        return pathToFinish;
    }

    //print each step 
    @Override
    public void printStep(LinkedList<LinkedList<Node>> qoq ) {

        //print out expanded node (this is the first value in first list)
        System.out.print("   " + qoq.peekFirst().peekFirst().val + "         ");

        //Print out queues
        System.out.print("[");
        for( LinkedList<Node> q : qoq){

            System.out.print("<");
            for(Node n : (LinkedList<Node>)q){
                System.out.print(n.val);
            }
            System.out.print(">");

        }
        System.out.println("]");
    }

    public void printDistanceStep(LinkedList<Path> qoq){}

    //print path to finish 
    @Override
    public void printPathToFinish(Queue<Node> q) {
        System.out.print("Final Path: <");
        for(Node n : q)
            System.out.print(n.val + " ");
        System.out.println(">");
        System.out.println();
    }
    
}
