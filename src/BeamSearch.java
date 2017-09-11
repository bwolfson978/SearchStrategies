import java.util.*;

/**
 * Created by bwolfson on 9/10/2017.
 */
public class BeamSearch implements ISearchMethod {

    int w;

    public BeamSearch(int w){
        this.w = w;
    }

    @Override
    public void printMethodName() {
        System.out.println("Beam Search");
    }

    public Queue<Node> searchMethod(Graph g){
        Node start = g.src;
        LinkedList<Node> pathToFinish = new LinkedList<Node>();
        pathToFinish.add(start);

        LinkedList<Node> initQueue = new LinkedList<Node>(Arrays.asList(start));

        Path firstPath = new Path(initQueue);
        LinkedList<Path> queueOfQueues = new LinkedList<>();
        queueOfQueues.add(firstPath);
        //printStep(queueOfQueues);
        //queueOfQueues.removeFirst();

        outerloop:
        while (!queueOfQueues.isEmpty()) {
            printDistanceStep(queueOfQueues);
            boolean overLimit = queueOfQueues.size() > w ? true : false;
            LinkedList<Node> currList = queueOfQueues.poll().getPathSoFar();
            Node curr = currList.peek();
            PriorityQueue<Node> frontier = g.adjList.get(curr);
            if(currList.peekFirst().val == 'G' && currList.peekLast().val == 'S'){
                break outerloop;
            }
            Iterator<Node> i = frontier.iterator();
            while (i.hasNext()) {
                Node n = i.next();
                if (!currList.contains(n)) {
                    LinkedList<Node> toAdd = new LinkedList<>();
                    // deep copy list for addition into q of qs
                    for(Node m : currList){
                        toAdd.add(m);
                    }
                    toAdd.addFirst(n);
                    Path p = new Path(toAdd, 0);
                    queueOfQueues.add(p);
                }
            }
            if(overLimit){
                LinkedList<Path> survivors = new LinkedList<Path>();
                survivors.add(queueOfQueues.get(0));
                for(int j = 1; j < w; j++){
                    Path keeper = getAndRemoveBest(queueOfQueues);
                    survivors.add(keeper);
                }
                queueOfQueues = new LinkedList<Path>();
                for(int k = 0; k < survivors.size(); k++){
                    queueOfQueues.add(survivors.get(k));
                }
            }
        }

        return pathToFinish;
    }

    Path getAndRemoveBest(LinkedList<Path> qofqs){
        float min = Integer.MAX_VALUE;
        for(int i = 1; i < qofqs.size(); i++){
            if(qofqs.get(i).getPathSoFar().peekFirst().h < min){
                min  = qofqs.get(i).getPathSoFar().peekFirst().h;
            }
        }
        // remove the first value with this h
        for(int i = 0; i < qofqs.size(); i++){
            if(qofqs.get(i).getPathSoFar().peekFirst().h == min){
                return qofqs.remove(i);
            }
        }
        return null;
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
            System.out.print(p.getPathSoFar().peekFirst().h);

            System.out.print("<");
            for(Node n : (LinkedList<Node>)p.getPathSoFar()){
                System.out.print(n.val);
            }
            System.out.print("> ");

        }
        System.out.println("]");
    }
}
