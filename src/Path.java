import java.*;
import java.util.*;
/**
 * Created by andrewrot on 9/10/2017.
 */



//may add heuristic to this class later for informed search algorithms
public class Path {
    
    LinkedList<Node> pathSoFar;
    float totalDistance; //total distance traveled 

    public Path(){
        this.pathSoFar = new LinkedList<Node>();
        this.totalDistance = 0;
    }

    //initialize with first node of path
    public Path(LinkedList<Node> n){
        this.pathSoFar = new LinkedList<Node>();
        pathSoFar = n;
        this.totalDistance = 0;
    }

    public Path(LinkedList<Node> n, float d){
        this.pathSoFar = n;
        this.totalDistance = d;
    }

    public LinkedList<Node> getPathSoFar(){
        return this.pathSoFar;
    }

    public float getTotalDistance(){
        return this.totalDistance;
    }



}
