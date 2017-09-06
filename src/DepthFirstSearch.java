import java.util.*;
/**
 * Created by andrewrot on 9/5/2017.
 */
public class DepthFirstSearch implements ISearchMethod{
    
    @Override
    public void printMethodName() {
        System.out.println("Depth 1st search");
    }


    //search method, traverse the tree using this classes way of searching

    @Override
    public Queue<Node> searchMethod(Graph g) {

        // DFS uses Stack data structure
        Stack stack = new Stack();

        //get start node
        //stack.push(g.src);

        //set start node to visited
        //g.src.visited=true;

        //printStep(stack.peek());

        /*
        while(!stack.empty()) {
            Node node = (Node)stack.peek();

            //find next adjacent node to visit
            Node child = stack.peek().adjList.getFirst();

            if(child != null) {
                child.visited = true;
                //printStep(child);
                stack.push(child);
            }
            else {
                stack.pop();
            //}
        }*/
        // Clear visited property of nodes
        //clearNodes();
        Queue<Node> q = new LinkedList<Node>();
        return q;
    }

    //print each step 
    @Override
    public void printStep(LinkedList<LinkedList<Node>> qoq) {
        System.out.println("X");
    }

    //print path to finish 
    @Override
    public void printPathToFinish(Queue<Node> q) {
        System.out.println("<");
        for(Node n : q)
            System.out.println(n.val);
        System.out.println(">");
    }
}
