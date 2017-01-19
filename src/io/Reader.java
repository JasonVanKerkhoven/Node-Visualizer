/**
*Class:             Reader.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    18/01/2016                                              
*Version:           1.0.0                                         
*                                                                                   
*Purpose:           Reads the raw Byte array from a file on disk.
*					Loads said Bytes into memory.
*
*					Modified from TFTPReader v1.0.0, written 26/09/2016 for TFTP Project
* 
* 
*Update Log			v1.1.0
*						- minor modifications to move TFTPReader over to generic Reader
*						  (TFTPReader would divide file into segments of 512B)
*					v1.0.0
*						- null
*/
package io;

//imports
import java.io.*;
import java.util.*;


public class Reader 
{
	//declaring local instance variables
	byte[] rawBytes;

	
	//generic constructor
	public Reader()
	{
	}

	
	//read a file into an array of bytes
	public void read(String file) throws FileNotFoundException, IOException  
	{
		//declaring local variables
		int b = 0;
		int i = 0;

		//load buffer with data
		BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
	
		//run until buffer is empty
		while( (b=input.read()) != -1)
		{
			input.read
		}

		//close buffer
		input.close();
	}
	
	/*
	//test function please ignore
	public static void main(String args[])
	{
		TFTPReader reader = new TFTPReader();
		int n = 0 ;
		byte[] arr ;
		
		try
		{
			reader.readAndSplit("DatagramsOutForHarambe.txt");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Packets: " + reader.arrNum());
		while ( !(reader.isEmpty()) )
		{
			n++;
			arr = reader.pop();
			System.out.println("Packet #" + n + ": " + arr.length + " Bytes");
			for(int i=0; i<arr.length; i++)
			{
				System.out.println("  " + (char)arr[i] + " | " + arr[i]);
			}
		}
	}
	*/
	
}
