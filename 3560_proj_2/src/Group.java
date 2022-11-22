import java.util.*;
public class Group extends Node{
	public Map<String, Node> children;
    public Group(String id){
        children = new HashMap<String, Node>();
        super._id = id;

    }

    public void addChild(Node node){
        children.put(node._id, node);
    }

    @Override
    public String toString(){
        return children.toString();
    }
	
}
