import java.util.*;

/**
 * Created by andrewrot on 9/10/2017.
 */
public class HillClimbingWithoutBT implements ISearchMethod {

    @Override
    public void printMethodName() {
        System.out.println("Hill Climbing (without backtracking)");
        System.out.println();
        System.out.println("Expanded     Queue");
    }


    public LinkedList<Node> searchMethod(Graph g){
        Node start = g.src;
        LinkedList<Node> pathToFinish = new LinkedList<Node>();
        pathToFinish.add(start);

        LinkedList<Node> initQueue = new LinkedList<Node>(Arrays.asList(start));

        LinkedList<LinkedList<Node>> queueOfQueues = new LinkedList<>(Arrays.asList(initQueue));


        outerloop:
        while (!queueOfQueues.isEmpty()) {



            printStep(queueOfQueues);
            LinkedList<Node> currList = queueOfQueues.poll();
            pathToFinish = currList; //m
            Node curr = currList.peek();
            PriorityQueue<Node> frontier = g.adjList.get(curr);
            if(currList.peekFirst().val == 'G' && currList.peekLast().val == 'S'){
                break outerloop;
            }
            Iterator<Node> i = frontier.iterator();

            //hold the children in here (only sort going into this qofqs)
            LinkedList<LinkedList<Node>> childrenQueues = new LinkedList<LinkedList<Node>>();

            while (i.hasNext()) {
                Node n = i.next();
                if (!currList.contains(n)) {
                    LinkedList<Node> toAdd = new LinkedList<>();
                    // deep copy list for addition into q of qs
                    for(Node m : currList){
                        toAdd.add(m);
                    }
                    toAdd.addFirst(n);

                    queueOfQueues = insertByHeuristic(queueOfQueues, toAdd);
                }
            }

            LinkedList<Node> bestChild = queueOfQueues.pollFirst();

            if(curr.h > bestChild.peekFirst().h){
                //continue, update queue of queues
                queueOfQueues.clear();
                queueOfQueues.addFirst(bestChild);
            } else {
                //otherwise, we've found the local max
                // System.out.println("goal reached!");
                pathToFinish = bestChild;
                queueOfQueues.clear();
            }


            

            
        }
        //System.out.println("goal reached!");
        return pathToFinish;
    }

    private LinkedList<LinkedList<Node>> insertByHeuristic(LinkedList<LinkedList<Node>> qofqs, LinkedList<Node> toAdd){
        if(qofqs.size() == 0){
            qofqs.add(toAdd);
        }
        else {
            float valToInsert = toAdd.peekFirst().h;
            Iterator<LinkedList<Node>> i = qofqs.listIterator();
            int idx = 0;
            while (i.hasNext()) {
                LinkedList<Node> currList = i.next();
                if (currList.peekFirst().h > valToInsert) {
                    if (idx == 0) {
                        qofqs.addFirst(toAdd);
                        break;
                    } else {
                        qofqs.add(idx, toAdd);
                        break;
                    }
                } else if (currList.peekFirst().h == valToInsert) {
                    char currListVal = currList.peekFirst().val;
                    char toAddVal = toAdd.peekFirst().val;
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
                        int toAddLength = toAdd.size();
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
                            Iterator<Node> toAddIter = toAdd.listIterator();
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
                idx++;
            }
        }
        return qofqs;
    }

    //print each step
    @Override
    public void printStep(LinkedList<LinkedList<Node>> qoq) {

        //print out expanded node (this is the first value in first list)
        System.out.print("   " + qoq.peekFirst().peekFirst().val + "         ");

        //Print out queues
        System.out.print("[");
        for (LinkedList<Node> q : qoq) {
            System.out.print(q.peekFirst().h);
            System.out.print("<");
            for (Node n : (LinkedList<Node>) q) {
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
        for (Node n : q)
            System.out.print(n.val + " ");
        System.out.println(">");
    }

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
}
