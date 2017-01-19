/**
*Class:             ToJSONFile.java
*Project:           Node Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    19/01/2016                                              
*Version:           1.0.0                                                      
*                                                                                   
*Purpose:           Basic interface to denote an object can be wrttien to a .json
*					file and can be read from a .json file.
* 
*Update Log:		v1.0.0
*						- null
*/
package io;


//imports
import java.io.IOException;


public interface ToJSONFile 
{
	//write
	public byte[] toJSON();
	
	//read
	public void fromJSON() throws IOException;
}