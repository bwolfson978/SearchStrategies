import java.util.*;

/**
 * Created by andrewrot on 9/5/2017.
 */
public interface ISearchMethod {
    public void printMethodName();
    public Queue searchMethod(Graph g);
    public void printStep(LinkedList<LinkedList<Node>> qoq);
    public void printPathToFinish(Queue<Node> q);
}
