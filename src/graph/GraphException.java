/**
*Class:             GraphException.java
*Project:           Node Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    28/12/2016                                              
*Version:           1.0.0                                                      
*                                                                                   
*Purpose:           Generic exception
* 
*Update Log:		v1.0.0
*						- null
*/
package graph;

public class GraphException extends Exception
{
	//declaring local instance variables
	public final boolean criticalFlag;
	public final String msgTitle;
	
	
	//constructor for non-critical errors (added to keep previous code working)
	public GraphException(String error, String title)
	{
		this(error, title, false);
	}
	
	
	//generic constructor
	public GraphException(String error, String title, boolean critical)
	{
		super(error);
		msgTitle = title;
		criticalFlag = critical;
	}
	
	
	@Override
	//return a string representation (almost exclusively used for debug)
	public String toString()
	{
		return ("GraphException: " + msgTitle + ", " + this.getMessage() + " (critical=" + criticalFlag + ")");
	}
}
