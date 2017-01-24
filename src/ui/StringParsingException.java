/**
*Class:             StringParsingException.java
*Project:           Node Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    27/12/2016                                              
*Version:           1.0.0                                                      
*                                                                                   
*Purpose:           Generic exception
* 
*Update Log:		v1.0.0
*						- null
*/
package ui;

@SuppressWarnings("serial")
public class StringParsingException extends Exception
{
	public StringParsingException(String msg)
	{
		super(msg);
	}

}
