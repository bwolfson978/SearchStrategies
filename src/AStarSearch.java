import java.util.*;

/**
 * Created by bwolfson on 9/10/2017.
 */
public class AStarSearch implements ISearchMethod {

    public void printMethodName(){
        System.out.println("A* Search: ");
    }

    @Override
    public Queue searchMethod(Graph g) {
        LinkedList<Node> pathToFinish = new LinkedList<Node>(); //may have to switch the queue

        Node start = g.src;
        //Throw S in here and expand it (must be in a linked list to work)
        LinkedList<Node> initQueue = new LinkedList<Node>(Arrays.asList(start));
        Path firstPath = new Path(initQueue); //need to maintain in path

        //Need a queue of queues (The Queue of all Queues :: used for printing purposes only)
        LinkedList<Path> queueOfQueues = new LinkedList<Path>();
        queueOfQueues.add(firstPath);
        outerloop:
        while(!queueOfQueues.isEmpty()){
            printDistanceStep(queueOfQueues);
            Path currList = queueOfQueues.poll();
            Node curr = currList.pathSoFar.peek();
            PriorityQueue<Node> frontier = g.adjList.get(curr);
            if(currList.getPathSoFar().peekFirst().val == 'G' && currList.getPathSoFar().peekLast().val == 'S'){
                break outerloop;
            }
            Iterator<Node> i = frontier.iterator();
            while(i.hasNext()){
                Node n = i.next();
                if(!currList.pathSoFar.contains(n)){
                    LinkedList<Node> toAdd = new LinkedList<Node>();
                    for(Node m : currList.pathSoFar){
                        toAdd.add(m);
                    }
                    toAdd.addFirst(n);
                    Path pathToInsert = new Path(toAdd, currList.totalDistance + n.d);
                    queueOfQueues = insertByFValue(queueOfQueues, pathToInsert);
                }
            }
        }
        return pathToFinish;
    }


    private LinkedList<Path> insertByFValue(LinkedList<Path> qofqs, Path toAdd){
        if(qofqs.size() == 0){
            qofqs.add(toAdd);
        }
        else {
            float valToInsert = toAdd.totalDistance + toAdd.pathSoFar.peekFirst().h;
            Iterator<Path> i = qofqs.listIterator();
            int idx = 0;
            while (i.hasNext()) {
                Path currPath = i.next();
                LinkedList<Node> currList = currPath.getPathSoFar();
                char toAddFirst = toAdd.getPathSoFar().peekFirst().val;
                char toAddLast = toAdd.getPathSoFar().peekLast().val;
                char currListFirst = currList.peekFirst().val;
                char currListLast = currList.peekLast().val;
                float currVal = currPath.totalDistance + currList.peekFirst().h;
                if(toAddFirst == currListFirst && toAddLast == currListLast && currVal <= valToInsert){
                    // if there is already a path through these nodes with lower f value, don't add this one
                    break;
                }
                if (currVal > valToInsert) {
                    if (idx == 0) {
                        qofqs.addFirst(toAdd);
                        break;
                    } else {
                        qofqs.add(idx, toAdd);
                        break;
                    }
                } else if (currVal == valToInsert) {
                    char currListVal = currList.peekFirst().val;
                    char toAddVal = toAdd.getPathSoFar().peekFirst().val;
                    if (currListVal != toAddVal) {
                        if (currListVal > toAddVal) {
                            if (idx == 0) {
                                qofqs.addFirst(toAdd);
                                break;
                            }
                            else {
                                qofqs.add(idx, toAdd);
                                break;
                            }
                        } else {
                            qofqs.add(idx + 1, toAdd);
                            break;
                        }
                    } else {
                        int currListLength = currList.size();
                        int toAddLength = toAdd.getPathSoFar().size();
                        if (currListLength != toAddLength) {
                            if (toAddLength < currListLength) {
                                if (idx == 0) {
                                    qofqs.addFirst(toAdd);
                                    break;
                                }
                                else {
                                    qofqs.add(idx, toAdd);
                                    break;
                                }
                            } else {
                                qofqs.add(idx + 1, toAdd);
                                break;
                            }
                        } else {
                            // two paths end at same node and are the same length
                            Iterator<Node> currListIter = currList.listIterator();
                            Iterator<Node> toAddIter = toAdd.getPathSoFar().listIterator();
                            while (currListIter.hasNext() && toAddIter.hasNext()) {
                                Node currNode = currListIter.next();
                                Node toAddNode = toAddIter.next();
                                if (!(currNode.val == toAddNode.val)) {
                                    if (toAddNode.val < currNode.val) {
                                        if (idx == 0) {
                                            qofqs.addFirst(toAdd);
                                            break;
                                        } else {
                                            qofqs.add(idx, toAdd);
                                            break;
                                        }
                                    } else {
                                        qofqs.add(idx + 1, toAdd);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                else if(idx == qofqs.size() - 1){
                    qofqs.addLast(toAdd);
                    break;
                }
                idx++;
            }
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
            float hCost = p.getPathSoFar().peekFirst().h;
            System.out.print(p.getTotalDistance() + hCost);

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
