import java.util.*;

/**
 * Created by bwolfson on 9/10/2017.
 */
public class IterativeDeepeningSearch implements ISearchMethod {
    LinkedList<Node> visitedList;
    int d; // depth of search-tree this search will cover

    public IterativeDeepeningSearch() {
        visitedList = new LinkedList<Node>();
    }

    public void printMethodName() {
        System.out.println("Iterative Deepening Search");
    }

    public Queue<Node> searchMethod(Graph g) {
        LinkedList<Node> pathToFinish = new LinkedList<Node>();

        int depth = 0;
        outerloop:
        while(true){
            Node start = g.src;
            visitedList.add(start);

            pathToFinish.add(start);

            Queue<Node> queue = new LinkedList<Node>(g.adjList.get(start));

            LinkedList<Node> initQueue = new LinkedList<Node>(Arrays.asList(start));

            LinkedList<LinkedList<Node>> queueOfQueues = new LinkedList<>(Arrays.asList(initQueue));

            //printStep(queueOfQueues);
            //queueOfQueues.removeFirst();
            System.out.println("Depth: " + depth);

            while (!queueOfQueues.isEmpty()) {
                printStep(queueOfQueues);
                LinkedList<Node> currList = queueOfQueues.poll();
                Node curr = currList.peek();
                PriorityQueue<Node> frontier = g.adjList.get(curr);
                if (currList.peekFirst().val == 'G' && currList.peekLast().val == 'S') {
                    break outerloop;
                }
                Iterator<Node> i = frontier.iterator();
                Stack<LinkedList<Node>> newQueues = new Stack<LinkedList<Node>>();
                while (i.hasNext()) {
                    Node n = i.next();
                    if (!currList.contains(n)) {
                        LinkedList<Node> toAdd = new LinkedList<>();
                        // deep copy list for addition into q of qs
                        for (Node m : currList) {
                            toAdd.add(m);
                        }
                        toAdd.addFirst(n);
                        if (currList.size() < depth + 1) {
                            // use stack to reverse the pq ordering
                            newQueues.push(toAdd);
                        }
                    }
                }
                while (!newQueues.isEmpty()) {
                    queueOfQueues.addFirst(newQueues.pop());
                }
            }
            depth++;
        }
        return pathToFinish;
    }

    //print each step
    @Override
    public void printStep(LinkedList<LinkedList<Node>> qoq) {

        //print out expanded node (this is the first value in first list)
        System.out.print("   " + qoq.peekFirst().peekFirst().val + "         ");

        //Print out queues
        System.out.print("[");
        for (LinkedList<Node> q : qoq) {

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