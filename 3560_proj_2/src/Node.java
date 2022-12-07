public class Node implements Comparable<Node>{
    protected String _id;
    protected long _creationTime;
    
    @Override
    public int compareTo(Node node){
        return _id.compareTo(node._id);
    }

    @Override
    public String toString(){
        return _id;
    }
}
