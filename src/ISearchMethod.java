import java.util.*;

/**
 * Created by andrewrot on 9/5/2017.
 */
public interface ISearchMethod {
    public void printMethodName();
    public LinkedList<Node> searchMethod(Graph g);
    public void printStep(LinkedList<LinkedList<Node>> qoq);

    public void printDistanceStep(LinkedList<Path> qoq); //this is for when we need to keep track of distance traveled

    public void printPathToFinish(Queue<Node> q);
}
