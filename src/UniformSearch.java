import java.util.*;
import java.*;
/**
 * Created by andrewrot on 9/10/2017.
 */

//Queue is sorted by least cost so far first - need extra value to hold the distance traveled
public class UniformSearch implements ISearchMethod{
    


    public UniformSearch(){

    }

    public void printMethodName() {
        System.out.println("Uniform Search");
        System.out.println();
        System.out.println("Expanded     Queue");
    }


    //search method, traverse the tree using this classes way of searching
    @Override
    public Queue<Node> searchMethod(Graph g) {

        Node start = g.src;

        //hold the path to the goal node
        LinkedList<Node> pathToFinish = new LinkedList<Node>(); //may have to switch the queue
        //pathToFinish.add(start);

        //put the adjacent nodes into out queue
        Queue<Node> queue = new LinkedList<Node>(g.adjList.get(start));

        //Throw S in here and expand it (must be in a linked list to work)
        LinkedList<Node> initQueue = new LinkedList<Node>(Arrays.asList(start));
        Path firstPath = new Path(initQueue); //need to maintain in path

        //Need a queue of queues (The Queue of all Queues :: used for printing purposes only)
        LinkedList<Path> queueOfQueues = new LinkedList<Path>();
        queueOfQueues.add(firstPath);

        //print first step and then remove it
        printDistanceStep(queueOfQueues);
        queueOfQueues.removeFirst();

        //Maintain 'threading' of queues -> need new queue for every neighbor of S
        for (Node n : queue) {
            LinkedList<Node> newQueue = new LinkedList<Node>();

            newQueue.add(n);
            newQueue.add(start); 

            float dt = 0;
            //GRAB DISTANCE FOR THESE TOO MOFOS
             Iterator<Node> x = g.adjList.get(n).iterator();
            while (x.hasNext()) {
               Node neighborNode = x.next();
               //find the node pointing back to the start to get directed distance
                if(neighborNode.val == start.val){ 
                    dt = neighborNode.d;
                }
            }

            Path nextPath = new Path(newQueue, dt);
            queueOfQueues.add(nextPath);
        }

        //print out current step
        printDistanceStep(queueOfQueues);

        
        outerloop: while(queue.peek() != null) {
            Node nextNode = queue.poll(); //dequeue the head and hold it here (poll = dequeue)
            //System.out.println("Expanded node: "+ nextNode.val);

            Iterator<Node> i = g.adjList.get(nextNode).iterator(); //get nextNode's neighbors
            

            //store the front of the queue. New nodes will add to this previous head.
            Path tempFront = new Path(queueOfQueues.peekFirst().getPathSoFar(), queueOfQueues.peekFirst().getTotalDistance());

            float distanceTraveled = tempFront.getTotalDistance();//might end up needing
            //System.out.println("Distance traveled for this path: "+ distanceTraveled);

            while (i.hasNext()) {
                Node n = i.next();

                //System.out.println("neighbor.val: "+ n.val);
                //********* Print related code ********
               
                //Create new/copied objects from old ones
                LinkedList<Node> tempList = new LinkedList<Node>(tempFront.getPathSoFar());
                

                Path frontList = new Path(tempList);
                
                //check to see if the solution has reached the first of list, if so, were good.
                if(frontList.pathSoFar.peekFirst().val == 'G' && frontList.pathSoFar.peekLast().val == 'S')
                    break outerloop; //goal reached!
                

                //Always add unless letter exists in the front queue
                if(!frontList.pathSoFar.contains(n)){
                    //System.out.println("node that matters: "+ n.val);
                    frontList.pathSoFar.addFirst(n); //add new neighbor to front of this list

                    //update this paths distance

                    //Since we are using a priority queue we have to iterate to find the 
                    //distance to adjacent node X in the queue grrrrr
                    Iterator<Node> x = g.adjList.get(n).iterator();
                    while (x.hasNext()) {

                        Node neighborNode = x.next();
                        //System.out.println("adj: "+neighborNode.val);
                        if(nextNode.val == neighborNode.val){
                            //System.out.println("value added!");
                            frontList.totalDistance = neighborNode.d + distanceTraveled;
                        }
                    }

                    //re-add this to end of queue of queues
                    queueOfQueues.add(frontList);
                    //pathToFinish.add(n);
                    queue.add(n);
                }else{
                    //System.out.println("neighbor doesnt matter: "+n.val);
                }
                //*************************************

            }
            //***** Print related code *******
            //now we are done with the front list
            queueOfQueues.removeFirst();
            printDistanceStep(queueOfQueues); 
         }

        System.out.println("goal reached!");

        //Final path from start to finish
        printPathToFinish(queueOfQueues.peekFirst().pathSoFar); //Not required, but i like it

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

    //This print adds distance to the print step - also requires a list of paths (which hold distance traveled)
    @Override
    public void printDistanceStep(LinkedList<Path> qoq){
        //print out expanded node (this is the first value in first list)
        System.out.print("   " + qoq.peekFirst().getPathSoFar().peekFirst().val + "         ");

        //Print out queues
        System.out.print("[");
        for( Path p : qoq){

            //get this paths distance and print it first
            System.out.print(p.getTotalDistance());

            System.out.print("<");
            for(Node n : (LinkedList<Node>)p.getPathSoFar()){
                System.out.print(n.val);
            }
            System.out.print("> ");

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
        System.out.println();
    }



}
