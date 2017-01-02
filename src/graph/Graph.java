/**
*Class:             Graph.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    01/01/2017                                              
*Version:           0.4.0                                                      
*                                                                                   
*Purpose:           Built due to complexities with delinking nodes.
*					Internally support linking n amount of nodes to a node.
*					Additionally supports delinking a node from ALL nodes.
* 
* 
*Update Log:		v0.4.0
*						- 
*					v0.3.0
*						- indirect add, remove, link, and delink modified to use 
*						  a common intermediate method
*						- direct add(...) method removed
*						- toString() method fixed
*						- exception messages that are actually human-readable added
*					v0.2.0
*						- toString() method added
*						- clear() method added
*					v0.1.0
*						- addNode(...) method logic added
*						- removeNode(...) method logic added
*						- linkNode(...) method logic added
*						- delinkNode(...) method logic added
* 
*/

package graph;


//import external libraries
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;


public class Graph 
{
	//declaring local instance variables
	IdDispatcher dispatch;
	HashMap<Node, LinkedList<Node>> map;
	
	
	//generic constructor
	public Graph()
	{
		dispatch = new IdDispatcher();
		map = new HashMap<Node, LinkedList<Node>>();
	}
	
	
	//clear graph
	public void clear()
	{
		dispatch.reset();
		map.clear();
	}
	
	
	//return the node associated to an id value, return null if node not found
	private Node idToNode(int id)
	{
		Set<Node> keys = map.keySet();
		for (Node node : keys)
		{
			//node found
			if (node.getId() == id)
			{
				return node;
			}
		}
		
		//node does not exist in key set
		return null;
	}
	
	
	//create new node from a value, add to graph
	public void add(String nodeValue) throws GraphException
	{
		map.put	( new Node(nodeValue, dispatch.getNextID()),
				  new LinkedList<Node>()
				);
	}
	
	
	//find and remove a node from the graph based on ID
	public void remove(int id) throws GraphException
	{
		removeNode(idToNode(id));
		return;
	}
	
	
	//remove a node from the graph, delink it from all other nodes
	public void removeNode(Node node) throws GraphException
	{
		LinkedList<Node> delinkMapping = map.get(node);

		//check node is in map
		if (delinkMapping != null)
		{
			//remove all links to the node
			for (int i = 0; i < delinkMapping.size(); i++)
			{
				try
				{
					Node n = delinkMapping.get(i);
					//delink node from 
					n.delink(node);
					
				}
				catch (GraphException e)
				{
					//should never get here
				}
			}
			
			//remove node from map
			delinkMapping.clear();			//TODO line should not be necessary, check API for HashMap
			map.remove(node);
		}
		else
		{
			throw new GraphException("Node not found", "Node Error") ;
		}
	}
	
	
	//find and link two nodes, based on id
	public void link(int start, int end) throws GraphException
	{
		linkNode(idToNode(start), idToNode(end));
	}
	
	
	//link a node in graph to another pre-existing node
	public void linkNode(Node start, Node end) throws GraphException
	{
		LinkedList<Node> startMapping = map.get(start);
		LinkedList<Node> endMapping = map.get(end);
		
		//check to confirm Node start is in map
		if (startMapping != null && endMapping != null)
		{
			//link end to start, add start to mapping for key = end
			start.link(end);
			endMapping.add(start);
		}
		else
		{
			throw new GraphException("Node not found", "Linking Error") ;
		}
	}
	
	
	//find and delink two nodes, based on id
	public void delink(int start, int end) throws GraphException
	{
		delinkNode(idToNode(start), idToNode(end));
	}
	
	
	//delink a single path
	public void delinkNode(Node start, Node end) throws GraphException
	{
		//check to confirm Node start is in map
		LinkedList<Node> startMapping = map.get(start);
		LinkedList<Node> endMapping = map.get(end);
		
		//check to confirm Node start is in map
		if (startMapping != null && endMapping != null)
		{
			//delink end from start, remove pointer to start from end mapping
			start.delink(end);
			endMapping.remove(start);
		}
		else
		{
			throw new GraphException("Node not found", "Linking Error") ;
		}
	}
	
	
	//return string representation of the hashmap used
	//(used almost 100% for debugging)
	public String toStringHashmap()
	{
		Set<Node> keys = map.keySet();
		
		if (keys.size() > 0)
		{
			String string = "";
			for (Node node : keys)
			{
				string += "Key: <" + node.toStringNoLinks() + "> || " + "Mapping: {";
				LinkedList<Node> mapping = map.get(node);
				for (int i=0; i < mapping.size(); i++)
				{
					if(i != 0)
					{
						string += ", ";
					}
					string +=  "<" + mapping.get(i).toStringNoLinks() + ">";
				}
				string += "}\n";
			}
			return string;
		}
		else
		{
			return "empty";
		}
	}
	
	
	@Override
	//returns a string of all nodes in graph
	public String toString()
	{
		Set<Node> keys = map.keySet();
		String returnable = "";
		if(keys.size() > 0)
		{
			for(Node node : keys)
			{
				returnable += node.toString() + "\n";
			}
		}
		else
		{
			returnable = "No nodes\n";
		}
		return returnable;
	}
}
