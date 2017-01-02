/**
*Class:             GraphTestBench.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    02/01/2017                                              
*Version:           1.0.0                                                     
*                                                                                   
*Purpose:           Test the Graph.java class
* 
* 
*Update Log:		v1.0.0
*						- null
*/
package tests;


//import local packages
import graph.Graph;
import graph.GraphException;


public class GraphTestBench 
{	
	public static void main(String[] args) 
	{
		//working instance of Graph
		Graph graph = new Graph();
		final String DIV = "\n==================================================================================================================";
		String preHash, preGraph;
		
		//test indirect add
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .add(String) ..." + DIV);
		preHash = graph.toStringHashmap();
		preGraph = graph.toString();
		System.out.println("Adding five new values to empty graph...");
		try
		{
			graph.add("Zero");
			graph.add("One");
			graph.add("Two");
			graph.add("Three");
			graph.add("Four");
		}
		catch (GraphException e)
		{
			System.out.println(e.toString());
			System.exit(0);
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ graph.toStringHashmap());
		System.out.println("Graph PRE:\n" + preGraph + "Graph POST:\n" + graph.toString() + "\n\n");
		
		
		//test indirect remove
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .remove(int) ..." + DIV);
		System.out.println("Removing all but id 2...");
		preHash = graph.toStringHashmap();
		preGraph = graph.toString();
		try
		{
			graph.remove(0);
			graph.remove(4);
			graph.remove(3);
			graph.remove(1);
		}
		catch (GraphException e)
		{
			System.out.println(e.toString());
			System.exit(0);
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ graph.toStringHashmap());
		System.out.println("Graph PRE:\n" + preGraph + "Graph POST:\n" + graph.toString() + "\n\n");
		
		
		//test indirect remove of nonexistent node
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .remove(int) ..." + DIV);
		preHash = graph.toStringHashmap();
		preGraph = graph.toString();
		System.out.println("Removing node id 3 (invalid id)...");
		try
		{
			graph.remove(3);
		}
		catch (GraphException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ graph.toStringHashmap());
		System.out.println("Graph PRE:\n" + preGraph + "Graph POST:\n" + graph.toString() + "\n\n");
		
		
		//test clearing graph
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .clear() ..." + DIV);
		preHash = graph.toStringHashmap();
		preGraph = graph.toString();
		System.out.println("Clearing graph...");
		graph.clear();
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ graph.toStringHashmap());
		System.out.println("Graph PRE:\n" + preGraph + "Graph POST:\n" + graph.toString() + "\n\n");
		
		
		//test indirect link
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .link(int, int) ..." + DIV);
		preHash = graph.toStringHashmap();
		preGraph = graph.toString();
		System.out.println("Adding initial nodes...");
		try
		{
			graph.add("Zero ");
			graph.add("One  ");
			graph.add("Two  ");
			graph.add("Three");
			graph.add("Four ");
			graph.add("Five ");
			graph.add("Six  ");
		}
		catch (GraphException e)
		{
			System.out.println(e.toString());
			System.exit(0);
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ graph.toStringHashmap());
		System.out.println("Graph PRE:\n" + preGraph + "Graph POST:\n" + graph.toString() + "\n");
		System.out.println("Linking nodes...");
		preHash = graph.toStringHashmap();
		preGraph = graph.toString();
		try
		{	
			graph.link(1, 2);
			
			graph.link(2, 3);
			graph.link(2, 4);
			
			graph.link(3, 5);
			graph.link(3, 1);
			
			graph.link(4, 5);
			
			graph.link(5, 2);
			
			graph.link(6, 4);
		}
		catch (GraphException e)
		{
			System.out.println(e.toString());
			System.exit(0);
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ graph.toStringHashmap());
		System.out.println("Graph PRE:\n" + preGraph + "Graph POST:\n" + graph.toString() + "\n\n");
		
		
		//test linking an existing link
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .link(int, int) ..." + DIV);
		preHash = graph.toStringHashmap();
		preGraph = graph.toString();
		System.out.println("Linking node6 to node4 (already linked)...");
		try
		{
			graph.link(6, 4);
		}
		catch (GraphException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ graph.toStringHashmap());
		System.out.println("Graph PRE:\n" + preGraph + "Graph POST:\n" + graph.toString() + "\n\n");
		
		
		//test linking a node to a non-existing node
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .link(int, int) ..." + DIV);
		preHash = graph.toStringHashmap();
		preGraph = graph.toString();
		System.out.println("Linking node2 to node7 (node7 DNE)...");
		try
		{
			graph.link(2, 7);
		}
		catch (GraphException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ graph.toStringHashmap());
		System.out.println("Graph PRE:\n" + preGraph + "Graph POST:\n" + graph.toString() + "\n\n");
		
		
		//test linking two nodes that do not exist
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .link(int, int) ..." + DIV);
		preHash = graph.toStringHashmap();
		preGraph = graph.toString();
		System.out.println("Linking node7 to node10 (node7 DNE, node10 DNE)...");
		try
		{
			graph.link(7, 10);
		}
		catch (GraphException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ graph.toStringHashmap());
		System.out.println("Graph PRE:\n" + preGraph + "Graph POST:\n" + graph.toString() + "\n\n");
		
		
		//test delinking a node that is linked
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .delink(int, int) ..." + DIV);
		preHash = graph.toStringHashmap();
		preGraph = graph.toString();
		System.out.println("Delinking node4 from node6...\n");
		try
		{
			graph.delink(6, 4);
		}
		catch (GraphException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ graph.toStringHashmap());
		System.out.println("Graph PRE:\n" + preGraph + "Graph POST:\n" + graph.toString() + "\n\n");
		
		
		//test delinking a node that is not linked
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .delink(int, int) ..." + DIV);
		preHash = graph.toStringHashmap();
		preGraph = graph.toString();
		System.out.println("Delinking node3 from node1... (node1 is not linked to node3)\n");
		try
		{
			graph.delink(1, 3);
		}
		catch (GraphException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ graph.toStringHashmap());
		System.out.println("Graph PRE:\n" + preGraph + "Graph POST:\n" + graph.toString() + "\n\n");
		
		
		//test delinking a node that DNE
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .delink(int, int) ..." + DIV);
		preHash = graph.toStringHashmap();
		preGraph = graph.toString();
		System.out.println("Delinking node10 from node2... (node10 DNE)\n");
		try
		{
			graph.delink(2, 10);
		}
		catch (GraphException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ graph.toStringHashmap());
		System.out.println("Graph PRE:\n" + preGraph + "Graph POST:\n" + graph.toString() + "\n\n");
		
		
		//test remove a linked node
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .remove(int) ..." + DIV);
		preHash = graph.toStringHashmap();
		preGraph = graph.toString();
		System.out.println("Deleting node2...\n");
		try
		{
			graph.remove(2);
		}
		catch (GraphException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ graph.toStringHashmap());
		System.out.println("Graph PRE:\n" + preGraph + "Graph POST:\n" + graph.toString() + "\n\n");
	}
}
