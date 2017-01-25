/**
*Class:             Writter.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    23/01/2017                                              
*Version:           1.0.0                                         
*                                                                                   
*Purpose:           Saves a string to a file.
*					Modified from TFTPWriter v1.0.0, written 26/09/2016 for TFTP Project
* 
* 
*Update Log			v1.0.0
*						- structured as completely static class
*/
package io;


//imports
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public abstract class Writter extends BasicIO
{
	//write a string to a particular location on the disk
	public static void write(String data, File file) throws IOException
	{
		Writter.write(data.getBytes(), file);
	}
	
	
	//write a string to the disk, get location
	public static void write(String data) throws IOException 
	{
		Writter.write(data.getBytes());
	}
	
	
	//write raw bytes to the disk
	public static void write(byte[] data) throws IOException 
	{
		//get directory path and write to
		File file = Writter.getFile("Please select a file");
		Writter.write(data, file);
	}
	
	
	//write to a file on the disk
	public static void write(byte[] data, File file) throws IOException
	{
		//check for valid file
		if(file != null)
		{
			//prep datastream for output
			FileOutputStream output = new FileOutputStream(file,false);
			
			//write
			output.write(data);
			
			//close stream
			output.close();
		}
	}
}
