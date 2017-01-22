/**
*Class:             Network.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    17/01/2017                                              
*Version:           1.0.3                                                      
*                                                                                   
*Purpose:           Controller for a collection of nodes which can be linked to n amount of other nodes.
*					Nodes are all doubly linked.
*					Network supports orphaned Nodes (unlinked to anything else in network)
*
* 
*Update Log:		v1.0.3
*						- methods for changing a current nodes value added (both directly and via ID hash)
*						- toJSON(...) method implemented
*					v1.0.2
*						- accessor for nodes returns Collection<Node> instead of Collection<Object> now
*					v1.0.1
*						- remove(...) method changed to fix error during removing
*						  a node that was linked to itself
*					v1.0.0
*						- renamed to Network.java
*						- toString() rewritten
*						- delinkAll(...) methods added to make orphan nodes
*						- remove(...) method bug fixed, now calls delinkAll(...)
*						- tested and is A-Okay for use :)
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



//import packages
import io.json.*;

//import external libraries
import java.util.HashMap;
import java.util.LinkedList;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;


public class Network implements ToJSONFile
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
	
	
	//return Collection of all Nodes
	public Collection<Node> getNodes()
	{
		return idMap.values();
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
	
	
	//change the value of a node, based on if
	public void changeNode(int id, String newValue) throws NetworkException
	{
		changeNode(idMap.get(id), newValue);
	}
	
	
	//change the value of a node in the Network
	public void changeNode(Node node, String newValue) throws NetworkException
	{
		if(node != null)
		{
			node.setValue(newValue);
		}
		else
		{
			throw new NetworkException("Node not found", "Node Error");
		}
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
			int iMax;
			
			//remove pointers to this node from all other nodes
			iMax = inLink.size();
			for(int i=0; i < iMax; i++)
			{
				Node prevNode = inLink.getFirst();
				prevNode.delink(node);
			}
			//remove pointers to other nodes from this node
			iMax = outLink.size();
			for (int i=0; i < iMax; i++)
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
				string += "Key: " + i + " || Value: <" + idMap.get(i).toString() + ">\n";
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

	
	//convert the instance nto a .json format, no initial tab offset
	public JsonFile toJSON()
	{
		return toJSON(null);
	}
	
	
	@Override
	//convert the instance into a .json format
	public JsonFile toJSON(String baseOffset)
	{
		//Set up controller and prime data block
		JsonFile file = new JsonFile(baseOffset);
		file.newBlock();
		
		//save dispatch
		JsonFile dispatchJson = dispatch.toJSON(file.getNetOffset());
		file.addField("dispatch", dispatchJson);
		
		//save basic idMap
		file.addField("idMap", "");
		file.newBlock();
		
		//iterate through all keys
		file.addField("pairings", "");
		file.newBlock();
		Set<Integer> keySet = idMap.keySet();
		for (int key : keySet)
		{
			String nodeValue = idMap.get(key).getValue();
			file.addPairing(key, nodeValue);
		}
		file.endBlock();
		
		//save link details, node
		Collection<Node> allNodes = idMap.values();
		//iterate through all nodes
		for (Node node : allNodes)
		{
			//initialize blank JsonFile for node
			JsonFile nodeJson = new JsonFile(file.getNetOffset());
			nodeJson.newBlock();
			
			//add inLink block
			nodeJson.addField("inLinks", "");
			nodeJson.newBlock();
			for(Node n: node.getInLinks())
			{
				nodeJson.add(n.getId()+"\n");
			}
			nodeJson.endBlock();
			
			//add outlink block
			nodeJson.addField("outLinks", "");
			nodeJson.newBlock();
			for (Node n: node.getOutLinks())
			{
				nodeJson.add(n.getId()+"\n");
			}
			nodeJson.endBlock();
			
			//end prime block and add to main JsonFile
			nodeJson.endBlock();
			String fieldName = "node " + node.getId();
			file.addField(fieldName, nodeJson);
		}
		
		//end idMap block
		file.endBlock();
		
		//end prime data block and return
		file.endBlock();
		return file;
	}


	@Override
	//built Network object from .json
	public void fromJSON(String JsonFile) throws JsonException 
	{
		//declaring method variables
		int line = 0;
		
		//make sure this instance is in its default state
		this.clear();
		
		//split at newlines, remove all tabs, remove spaces
		JsonFile = JsonFile.replaceAll("\t", "");
		JsonFile = JsonFile.replaceAll(" ", "");
		String[] fileLine = JsonFile.split("\n");
		
		//make sure there is a starting block
		if (!fileLine[line].equals("{"))
		{
			throw new JsonException("No starting block", JsonException.ERR_FORMAT);
		}
		line++;
		
		//check for dispatch
		if (fileLine[line].equals("\"dispatch\":{"))
		{
			line++;
			String dispatchJson = "{\n";
			int block = 0;
			
			//isolate dispatch json
			while (! (fileLine[line].equals("}") && block == 0) )
			{
				if(fileLine[line].contains("{"))
				{
					block++;
				}
				else if (fileLine[line].contains("}"))
				{
					block--;
				}
				dispatchJson += fileLine[line] + "\n";
				line++;
			}
			dispatchJson += fileLine[line] +"\n";
			line++;
			
			//build dispatch
			this.dispatch.fromJSON(dispatchJson);
		}
		else
		{
			throw new JsonException("dispatch object not found", JsonException.ERR_BAD_FIELD);
		}
		
		
		//check idMap block
		if(fileLine[line].equals("\"idMap\":{"))
		{
			line++;
			//check pairings block
			if(fileLine[line].equals("\"pairings\":{"))
			{
				line++;
				
				//get all pairings
				while(!fileLine[line].equals("}"))
				{
					//remove quotes, split at ","
					if (!fileLine[line].contains("\"")) throw new JsonException("Invalid format for pairing value -- must be String", JsonException.ERR_BAD_VALUE);
					String[] pair = fileLine[line].replaceAll("\"", "").split(",");
					
					//make sure there are only 2 pairs
					if (pair.length == 2)
					{
						try
						{
							//parse info
							int id = Integer.parseInt(pair[0]);
							String value = pair[1];
							
							//create node with id and add
							Node node = new Node(value, id);
							this.idMap.put(id, node);
						}
						catch (NumberFormatException e)
						{
							throw new JsonException("Pairing information id invalid -- must be of type integer", JsonException.ERR_BAD_VALUE);
						}
					}
					else
					{
						throw new JsonException("Pairing information for idMap invalid", JsonException.ERR_FORMAT);
					}
					
					line++;
				}
			}
			else
			{
				throw new JsonException("Pairing information for idMap not found", JsonException.ERR_BAD_FIELD);
			}
		}
		else
		{
			throw new JsonException("idMap object not found", JsonException.ERR_BAD_FIELD);
		}
		line++;
		
		
		//check for node link blocks until file end
		int block = 2;		//we need one } to exit the idMap block and one } to end the file block
		while (block > 0)
		{
			//check for starting sub-block denoting which node
			if(fileLine[line].contains("{"))
			{
				block++;
				
				int currentNode;
				
				//isolate node id and convert to int
				String filteredLine = fileLine[line].replaceAll("\"node", "");
				filteredLine = filteredLine.replaceAll("\":{", "");
				try
				{
					currentNode = Integer.parseInt(filteredLine);
				}
				catch (NumberFormatException e)
				{
					throw new JsonException("Node id invalid -- must be of type integer", JsonException.ERR_BAD_VALUE);
				}
				
				//TODO check and set inLinks/outLinks
			}
		}
	}


	@Override
	//build Network object from .json
	public void fromJSON(byte[] rawBytes) throws JsonException 
	{
		/* TODO there must be a better way to do this
		 * using char array so as i dont need to make a new string each char add (each string is immutable)
		 */

		//cast each byte as a char
		char[] brokenString = new char[rawBytes.length];
		for(int i=0; i < rawBytes.length; i++)
		{
			brokenString[i] = (char)rawBytes[i];
		}
		
		//generate object from json file, now in String form
		fromJSON(new String(brokenString));
		
	}
}



