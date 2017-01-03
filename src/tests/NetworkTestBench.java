/**
*Class:             NetworkTestBench.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    02/01/2017                                              
*Version:           1.0.0                                                     
*                                                                                   
*Purpose:           Test the network.java class
* 
* 
*Update Log:		v1.0.0
*						- null
*/
package tests;


//import local packages
import network.*;


public class NetworkTestBench 
{	
	public static String preHash,preNetwork;
	
	
	public static void saveState(Network network)
	{
		preHash = network.toStringHashmap();
		preNetwork = network.toString();
	}
	
	
	public static void printResult(Network network)
	{
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ network.toStringHashmap());
		System.out.println("Network PRE:\n" + preNetwork + "Network POST:\n" + network.toString() + "\n\n");
	}
	
	
	public static void main(String[] args) 
	{
		//working instance of network
		Network network = new Network();
		final String DIV = "\n==================================================================================================================";

		
		//test indirect add
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .add(String) ..." + DIV);
		saveState(network);
		System.out.println("Adding five new values to empty network...");
		try
		{
			network.add("Zero ");
			network.add("One  ");
			network.add("Two  ");
			network.add("Three");
			network.add("Four ");
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
			System.exit(0);
		}
		printResult(network);
		
		
		//test indirect remove
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .remove(int) ..." + DIV);
		System.out.println("Removing all but id 2...");
		saveState(network);
		try
		{
			network.remove(0);
			network.remove(4);
			network.remove(3);
			network.remove(1);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
			System.exit(0);
		}
		printResult(network);
		
		
		//test indirect remove of nonexistent node
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .remove(int) ..." + DIV);
		saveState(network);
		System.out.println("Removing node id 3 (invalid id)...");
		try
		{
			network.remove(3);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		printResult(network);
		
		
		//test clearing network
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .clear() ..." + DIV);
		saveState(network);
		System.out.println("Clearing network...");
		network.clear();
		printResult(network);
		
		
		//test indirect link
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .link(int, int) ..." + DIV);
		saveState(network);
		System.out.println("Adding initial nodes...");
		try
		{
			network.add("Zero ");
			network.add("One  ");
			network.add("Two  ");
			network.add("Three");
			network.add("Four ");
			network.add("Five ");
			network.add("Six  ");
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
			System.exit(0);
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ network.toStringHashmap());
		System.out.println("Network PRE:\n" + preNetwork + "Network POST:\n" + network.toString() + "\n");
		System.out.println("Linking nodes...");
		saveState(network);
		try
		{	
			network.link(1, 2);
			
			network.link(2, 3);
			network.link(2, 4);
			
			network.link(3, 5);
			network.link(3, 1);
			
			network.link(4, 5);
			
			network.link(5, 2);
			
			network.link(6, 4);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
			System.exit(0);
		}
		printResult(network);
		
		
		//test linking an existing link
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .link(int, int) ..." + DIV);
		saveState(network);
		System.out.println("Linking node6 to node4 (already linked)...");
		try
		{
			network.link(6, 4);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		printResult(network);
		
		
		//test linking a node to a non-existing node
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .link(int, int) ..." + DIV);
		saveState(network);
		System.out.println("Linking node2 to node7 (node7 DNE)...");
		try
		{
			network.link(2, 7);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		printResult(network);
		
		
		//test linking two nodes that do not exist
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .link(int, int) ..." + DIV);
		saveState(network);
		System.out.println("Linking node7 to node10 (node7 DNE, node10 DNE)...");
		try
		{
			network.link(7, 10);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		printResult(network);
		
		
		//test delinking a node that is linked
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .delink(int, int) ..." + DIV);
		saveState(network);
		System.out.println("Delinking node4 from node6...\n");
		try
		{
			network.delink(6, 4);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		printResult(network);
		
		
		//test delinking a node that is linked
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .delink(int, int) ..." + DIV);
		saveState(network);
		System.out.println("Delinking node5 from node3...\n");
		try
		{
			network.delink(3, 5);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		printResult(network);
		
		
		//test delinking a node that is not linked
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .delink(int, int) ..." + DIV);
		saveState(network);
		System.out.println("Delinking node3 from node1... (node1 is not linked to node3)\n");
		try
		{
			network.delink(1, 3);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		printResult(network);
		
		
		//test delinking a node that DNE
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .delink(int, int) ..." + DIV);
		saveState(network);
		System.out.println("Delinking node10 from node2... (node10 DNE)\n");
		try
		{
			network.delink(2, 10);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		printResult(network);
		
		
		//test delink ALL links from a node
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .delinkAll(int) ..." + DIV);
		System.out.println("Clearing working Network instance...");
		network.clear();
		System.out.println("Adding initial nodes...");
		try
		{
			network.add("Zero ");
			network.add("One  ");
			network.add("Two  ");
			network.add("Three");
			network.add("Four ");
			network.add("Five ");
			network.add("Six  ");
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
			System.exit(0);
		}
		System.out.println("Linking nodes...");
		try
		{	
			network.link(1, 2);
			
			network.link(2, 3);
			network.link(2, 4);
			
			network.link(3, 5);
			network.link(3, 1);
			
			network.link(4, 5);
			
			network.link(5, 2);
			
			network.link(6, 4);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
			System.exit(0);
		}
		System.out.println("Delinking all nodes from node 4...\n");
		saveState(network);
		try
		{
			network.delinkAll(4);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
			System.exit(0);
		}
		printResult(network);
		
		
		//test delinking all for a invalid node
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .remove(int) ..." + DIV);
		System.out.println("Removing node 7 (DNE)...");
		saveState(network);
		try
		{
			network.delinkAll(7);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		printResult(network);
		
		
		
		//test removing a node
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .remove(int) ..." + DIV);
		System.out.println("Removing orphan nodes...");
		try
		{
			network.remove(0);
			network.remove(4);
			network.remove(6);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
			System.exit(0);
		}
		saveState(network);
		System.out.println("Removing node 3...");
		try
		{
			network.remove(3);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
			System.exit(0);
		}
		printResult(network);
		
		
		//test removing a node that DNE
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .remove(int) ..." + DIV);
		System.out.println("Removing node 3 (DNE)...");
		saveState(network);
		try
		{
			network.remove(3);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		printResult(network);
	}
}
