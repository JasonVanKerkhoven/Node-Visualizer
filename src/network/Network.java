/**
*Class:             Network.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    02/01/2017                                              
*Version:           0.4.0                                                      
*                                                                                   
*Purpose:           Controller for a collection of nodes which can be linked to n amount of other nodes.
*					Nodes are all doubly linked.
*					Network supports orphaned Nodes (unlinked to anything else in network)
*
* 
*Update Log:		v0.4.0
*						- renamed to Network.java
*						- toString() rewritten
*						- delinkAll(...) methods added to make orphan nodes
*						- remove(...) method bug fixed, now calls delinkAll(...)
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
*						- linkNode(...) method logic added
*						- delinkNode(...) method logic added
*						- remove(...) method added for unlinking and removing a node
* 
*/
package network;



//import external libraries
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Set;


public class Network 
{
	//declaring local instance variables
	IdDispatcher dispatch;
	HashMap<Integer, Node> idMap;
	
	
	//generic constructor
	public Network()
	{
		dispatch = new IdDispatcher();
		idMap = new HashMap<Integer, Node>();
	}
	
	
	//clear graph
	public void clear()
	{
		dispatch.reset();
		idMap.clear();
	}
	
	
	//create new node from a value, add to graph
	public void add(String nodeValue) throws NetworkException
	{
		int id = dispatch.getNextID();
		idMap.put(id, new Node(nodeValue, id));
	}
	
	
	//find and remove a node from the graph based on ID
	public void remove(int id) throws NetworkException
	{
		remove(idMap.get(id));
		return;
	}
	
	
	//remove a node from the graph, delink it from all other nodes
	public void remove(Node node) throws NetworkException
	{
		//make node an orphan
		delinkAll(node);
		//remove node from hashmap, retire ID
		int id = node.getId();
		idMap.remove(id);
		dispatch.retireID(id);
		
		
	}
	
	
	//find and link two nodes, based on id
	public void link(int start, int end) throws NetworkException
	{
		link(idMap.get(start), idMap.get(end));
	}
	
	
	//link a node in graph to another pre-existing node
	public void link(Node start, Node end) throws NetworkException
	{
		if(start != null && end != null)
		{
			start.link(end);
		}
		else
		{
			throw new NetworkException("Node not found", "Linking Error");
		}
	}
	
	
	//find and delink two nodes, based on id
	public void delink(int start, int end) throws NetworkException
	{
		delink(idMap.get(start), idMap.get(end));
	}
	
	
	//delink a single path
	public void delink(Node start, Node end) throws NetworkException
	{
		if (start != null && end != null)
		{
			start.delink(end);
		}
		else
		{
			throw new NetworkException("Node not found", "Linking Error");
		}
	}
	
	
	//find and make the node an orphan, based on id
	public void delinkAll(int id) throws NetworkException
	{
		delinkAll(idMap.get(id));
	}
	
	
	//Make a node an orphan (remove all links from and to itself)
	public void delinkAll(Node node) throws NetworkException
	{
		if (node != null)
		{
			//get all in/out links
			LinkedList<Node> inLink = node.getInLinks();
			LinkedList<Node> outLink = node.getOutLinks();
			int iPrev = inLink.size();
			int iPost = outLink.size();
			
			//remove pointers to this node from all other nodes
			for(int i=0; i < iPrev; i++)
			{
				Node prevNode = inLink.getFirst();
				prevNode.delink(node);
			}
			//remove pointers to other nodes from this node
			for (int i=0; i < iPost; i++)
			{
				Node postNode = outLink.getFirst();
				node.delink(postNode);
			}
		}
		else
		{
			throw new NetworkException("Node not found", "Linking Error");
		}
	}
	
	
	//return string representation of the hashmap used
	//(used almost 100% for debugging)
	public String toStringHashmap()
	{
		String string = "";
		Set<Integer> keys = idMap.keySet();
		
		if (keys.size() > 0)
		{
			for (int i : keys)
			{
				string += "Key: " + i + " || Value: <" + idMap.get(i).toStringSimple() + ">\n";
			}
		}
		else
		{
			string = "Empty\n";
		}
		
		return string;
	}
	
	
	@Override
	//returns a string of all nodes in network
	public String toString()
	{
		Collection<Node> values = idMap.values();
		String string = "";
		if(values.size() > 0)
		{
			for(Node node : values)
			{
				string += node.toString() + "\n";
			}
		}
		else
		{
			string = "No nodes\n";
		}
		return string;
	}
}



