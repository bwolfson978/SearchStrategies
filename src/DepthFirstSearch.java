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

        
        outerloop: while(queue.peek() != null) {
            Node nextNode = queue.poll(); //dequeue the head and hold it here (poll = dequeue)
         
            Iterator<Node> i = g.adjList.get(nextNode).iterator(); //get nextNode's neighbors
            

            while (i.hasNext()) {
                Node n = i.next();

                //********* Print related code ********
                //for each of it's neighbors, append it to the front of the first list of lists, and then re-add it to the end
                LinkedList<Node> frontList = new LinkedList<Node>(queueOfQueues.peekFirst());
                
                //check to see if the solution has reached the first of list, if so, were good.
                if(frontList.peekFirst().val == 'G' && frontList.peekLast().val == 'S')
                    break outerloop; //goal reached!
                
                //Always add unless letter exists in the front queue
                if(!frontList.contains(n)){
                    frontList.addFirst(n); //add new neighbor to front of this list
                    System.out.println("FrontList: "+frontList);
                    //re-add this to end of queue of queues
//queueOfQueues.add(frontList);
                    //decide where to add this to the queue
                    //Iterator<Node> x = queueOfQueues.peekFirst().listIterator();
                    int counter = 0;
                     //while (x.hasNext()) {
                        //System.out.println("b");
                    //System.out.println("queueOfQueues.length: "+queueOfQueues.size());
                    //System.out.println("queueOfQueues.firstlength: "+queueOfQueues.peek().size());

                    for (LinkedList<Node> l : queueOfQueues){
                        /*System.out.println("X");
                        System.out.println("l.length: "+l.size());
                         System.out.println("l: "+l.peek().val);
                         System.out.println("frontList: "+frontList.peek().val );*/
                        
                        if(frontList.peek().val >= l.peek().val){
                            System.out.println("Insert: "+frontList + " at index: "+ counter);
                            queueOfQueues.add(counter, frontList); //null line?
                        }
                        counter++;
                        //x.next();*/
                     }
                    

                    pathToFinish.add(n);
                    queue.add(n);
                }
                //*************************************

                //Should be able to remove visitedList...
                if (!visitedList.contains(n)) {
                    visitedList.add(n); //now the node is visited
                    //pathToFinish.add(n); <-- This stuff is how BFS is supposed to work
                    //queue.add(n);        <--
                }
            }
            //***** Print related code *******
            //now we are done with the front list
            queueOfQueues.removeFirst();
            printStep(queueOfQueues); 
         }

        System.out.println("goal reached!");

        //Final path from start to finish
        printPathToFinish(queueOfQueues.peekFirst()); //Not required, but i like it

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

    //print path to finish 
    @Override
    public void printPathToFinish(Queue<Node> q) {
        System.out.print("Final Path: <");
        for(Node n : q)
            System.out.print(n.val + " ");
        System.out.println(">");
    }
}
