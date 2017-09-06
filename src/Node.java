import java.*;
/**
 * Created by bwolfson on 9/4/2017.
 */
public class Node implements Comparable {
    char val;
    float d;
    float h;

    public Node(char val){
        this.val = val;
    }

    public Node(char val, float d){
        this.val = val;
        this.d = d;
    }

    public Node(char val, float d, float h){
        this.val = val;
        this.d = d;
        this.h = h;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Node)) {
            return false;
        }

        Node n = (Node) o;

        return n.val == val;
    }

    //Idea from effective Java : Item 9
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + val;
        return result;
    }

    @Override
    public int compareTo(Object o){
        Node compare = (Node) o;
        if (this.val == compare.val) 
            return 0;
        else if (this.val < compare.val) 
            return -1;
        else 
            return 1;

        
    }
}
