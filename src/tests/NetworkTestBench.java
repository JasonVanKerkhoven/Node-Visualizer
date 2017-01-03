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
	public static void main(String[] args) 
	{
		//working instance of network
		Network network = new Network();
		final String DIV = "\n==================================================================================================================";
		String preHash, prenetwork;
		
		//test indirect add
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .add(String) ..." + DIV);
		preHash = network.toStringHashmap();
		prenetwork = network.toString();
		System.out.println("Adding five new values to empty network...");
		try
		{
			network.add("Zero");
			network.add("One");
			network.add("Two");
			network.add("Three");
			network.add("Four");
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
			System.exit(0);
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ network.toStringHashmap());
		System.out.println("network PRE:\n" + prenetwork + "network POST:\n" + network.toString() + "\n\n");
		
		
		//test indirect remove
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .remove(int) ..." + DIV);
		System.out.println("Removing all but id 2...");
		preHash = network.toStringHashmap();
		prenetwork = network.toString();
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
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ network.toStringHashmap());
		System.out.println("network PRE:\n" + prenetwork + "network POST:\n" + network.toString() + "\n\n");
		
		
		//test indirect remove of nonexistent node
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .remove(int) ..." + DIV);
		preHash = network.toStringHashmap();
		prenetwork = network.toString();
		System.out.println("Removing node id 3 (invalid id)...");
		try
		{
			network.remove(3);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ network.toStringHashmap());
		System.out.println("network PRE:\n" + prenetwork + "network POST:\n" + network.toString() + "\n\n");
		
		
		//test clearing network
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .clear() ..." + DIV);
		preHash = network.toStringHashmap();
		prenetwork = network.toString();
		System.out.println("Clearing network...");
		network.clear();
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ network.toStringHashmap());
		System.out.println("network PRE:\n" + prenetwork + "network POST:\n" + network.toString() + "\n\n");
		
		
		//test indirect link
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .link(int, int) ..." + DIV);
		preHash = network.toStringHashmap();
		prenetwork = network.toString();
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
		System.out.println("network PRE:\n" + prenetwork + "network POST:\n" + network.toString() + "\n");
		System.out.println("Linking nodes...");
		preHash = network.toStringHashmap();
		prenetwork = network.toString();
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
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ network.toStringHashmap());
		System.out.println("network PRE:\n" + prenetwork + "network POST:\n" + network.toString() + "\n\n");
		
		
		//test linking an existing link
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .link(int, int) ..." + DIV);
		preHash = network.toStringHashmap();
		prenetwork = network.toString();
		System.out.println("Linking node6 to node4 (already linked)...");
		try
		{
			network.link(6, 4);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ network.toStringHashmap());
		System.out.println("network PRE:\n" + prenetwork + "network POST:\n" + network.toString() + "\n\n");
		
		
		//test linking a node to a non-existing node
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .link(int, int) ..." + DIV);
		preHash = network.toStringHashmap();
		prenetwork = network.toString();
		System.out.println("Linking node2 to node7 (node7 DNE)...");
		try
		{
			network.link(2, 7);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ network.toStringHashmap());
		System.out.println("network PRE:\n" + prenetwork + "network POST:\n" + network.toString() + "\n\n");
		
		
		//test linking two nodes that do not exist
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .link(int, int) ..." + DIV);
		preHash = network.toStringHashmap();
		prenetwork = network.toString();
		System.out.println("Linking node7 to node10 (node7 DNE, node10 DNE)...");
		try
		{
			network.link(7, 10);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ network.toStringHashmap());
		System.out.println("network PRE:\n" + prenetwork + "network POST:\n" + network.toString() + "\n\n");
		
		
		//test delinking a node that is linked
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .delink(int, int) ..." + DIV);
		preHash = network.toStringHashmap();
		prenetwork = network.toString();
		System.out.println("Delinking node4 from node6...\n");
		try
		{
			network.delink(6, 4);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ network.toStringHashmap());
		System.out.println("network PRE:\n" + prenetwork + "network POST:\n" + network.toString() + "\n\n");
		
		
		//test delinking a node that is not linked
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .delink(int, int) ..." + DIV);
		preHash = network.toStringHashmap();
		prenetwork = network.toString();
		System.out.println("Delinking node3 from node1... (node1 is not linked to node3)\n");
		try
		{
			network.delink(1, 3);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ network.toStringHashmap());
		System.out.println("network PRE:\n" + prenetwork + "network POST:\n" + network.toString() + "\n\n");
		
		
		//test delinking a node that DNE
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .delink(int, int) ..." + DIV);
		preHash = network.toStringHashmap();
		prenetwork = network.toString();
		System.out.println("Delinking node10 from node2... (node10 DNE)\n");
		try
		{
			network.delink(2, 10);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ network.toStringHashmap());
		System.out.println("network PRE:\n" + prenetwork + "network POST:\n" + network.toString() + "\n\n");
		
		
		//test remove a linked node
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Testing .remove(int) ..." + DIV);
		preHash = network.toStringHashmap();
		prenetwork = network.toString();
		System.out.println("Deleting node2...\n");
		try
		{
			network.remove(2);
		}
		catch (NetworkException e)
		{
			System.out.println(e.toString());
		}
		System.out.println("Hashmap PRE:\n" + preHash + "Hashmap POST:\n"+ network.toStringHashmap());
		System.out.println("network PRE:\n" + prenetwork + "network POST:\n" + network.toString() + "\n\n");
	}
}
