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

        LinkedList<Node> queue = new LinkedList<Node>(g.adjList.get(start)); //put the adjacent nodes into out queue

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

            //burn the first value
            queueOfQueues.removeFirst();

            float distanceTraveled = tempFront.getTotalDistance();//might end up needing

            while (i.hasNext()) {
                Node n = i.next();
               
                //Create new/copied objects from old ones
                //LinkedList<Node> tempList = new LinkedList<Node>(tempFront.getPathSoFar());
                Path frontList = new Path(new LinkedList<Node>(tempFront.getPathSoFar()), tempFront.getTotalDistance());
                
                //check to see if the solution has reached the first of list, if so, were good.
                if(frontList.pathSoFar.peekFirst().val == 'G' && frontList.pathSoFar.peekLast().val == 'S')
                    break outerloop; //goal reached!
                
                //Always add unless letter exists in the front queue
                if(!frontList.pathSoFar.contains(n)){
                    //System.out.println("node that matters: "+ n.val);
                    frontList.pathSoFar.addFirst(n); //add new neighbor to front of this list

                    //update this paths distance
                    //Since we are using a priority queue we have to iterate to find the distance to adjacent node X in the queue 
                    Iterator<Node> x = g.adjList.get(n).iterator();
                    while (x.hasNext()) {
                        Node neighborNode = x.next();
                        if(nextNode.val == neighborNode.val)
                            frontList.totalDistance = neighborNode.d + frontList.getTotalDistance();
                    }

                    //re-add this to end of queue of queues
                    //queueOfQueues.add(frontList);// ---old unsorted way
                    queueOfQueues = insertByDistanceTraveled(queueOfQueues, frontList);
                    queue.addFirst(queueOfQueues.peekFirst().getPathSoFar().peekFirst());
                    //old working below
                    //queue.add(n);
                }
            }
            //now we are done with the front list
            //queueOfQueues.removeFirst();
            printDistanceStep(queueOfQueues); 
         }

        System.out.println("goal reached!");

        //Final path from start to finish
        printPathToFinish(queueOfQueues.peekFirst().pathSoFar); //Not required, but i like it

        return pathToFinish;
    }



    private LinkedList<Path> insertByDistanceTraveled(LinkedList<Path> qofqs, Path toAdd){
        boolean added = false;

        //System.out.println("qoq size: "+qofqs.size());
        if(qofqs.size() == 0){
            qofqs.add(toAdd);
        }

        

        else {
            float valToInsert = toAdd.getTotalDistance();
            Iterator<Path> i = qofqs.listIterator();
            int idx = 0;
            //System.out.println("Path to add: "+toAdd.getPathSoFar().peekFirst() + " dist: "+valToInsert);
            while (i.hasNext()) {

                //LinkedList<Node> currList = i.next();
                Path currPath = i.next();
                //System.out.println("currPath: " +currPath.getPathSoFar());

                //if current path length is > than one to insert
                //System.out.println("currPath.getTotalDistance(): "+currPath.getTotalDistance() + " valToInsert: "+valToInsert);
                if (currPath.getTotalDistance() > valToInsert) {
                    //System.out.println("value was smaller");
                    if (idx == 0) {
                        qofqs.addFirst(toAdd);
                        added = true;
                        break;
                    } else {
                        qofqs.add(idx, toAdd);
                        added = true;
                        break;
                    }
                } else if (currPath.getTotalDistance() == valToInsert) {
                    //System.out.println("value was the same");
                    char currListVal = currPath.getPathSoFar().peekFirst().val;

                    char toAddVal = toAdd.getPathSoFar().peekFirst().val;
                    if (currListVal != toAddVal) {
                        if (currListVal > toAddVal) {
                            if (idx == 0) {
                                qofqs.addFirst(toAdd);
                                added = true;
                                break;
                            }
                            else {
                                qofqs.add(idx, toAdd);
                                added = true;
                                break;
                            }
                        } else {
                            //qofqs.add(idx + 1, toAdd);
                            //added = true;
                            //break;
                        }
                    } else {

                        int currListLength = currPath.getPathSoFar().size();
                        int toAddLength = toAdd.getPathSoFar().size();
                        if (currListLength != toAddLength) {
                            if (toAddLength < currListLength) {
                                if (idx == 0) {
                                    qofqs.addFirst(toAdd);
                                    added = true;
                                    break;
                                }
                                else {
                                    qofqs.add(idx, toAdd);
                                    added = true;
                                    break;
                                }
                            } else {
                                qofqs.add(idx + 1, toAdd);
                                added = true;
                                break;
                            }
                        } else {

                            // two paths end at same node and are the same length
                            Iterator<Node> currListIter = currPath.getPathSoFar().listIterator();
                            Iterator<Node> toAddIter = toAdd.getPathSoFar().listIterator();
                            while (currListIter.hasNext() && toAddIter.hasNext()) {
                                Node currNode = currListIter.next();
                                Node toAddNode = toAddIter.next();
                                if (!(currNode.val == toAddNode.val)) {
                                    if (toAddNode.val < currNode.val) {
                                        if (idx == 0) {
                                            qofqs.addFirst(toAdd);
                                            added = true;
                                            break;
                                        } else {
                                            qofqs.add(idx, toAdd);
                                            added = true;
                                            break;
                                        }
                                    } else {
                                        qofqs.add(idx + 1, toAdd);
                                        added = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                }
                //System.out.println("value was larger");
                idx++;
            }
        }
        //System.out.println();

        //got to the end without being added, add to the end
        if(added == false){
            qofqs.addLast(toAdd);
        }

        return qofqs;
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
