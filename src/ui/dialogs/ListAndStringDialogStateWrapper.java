/**
*Class:             ListAndStringDialogStateWrapper.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    17/01/2017                                              
*Version:           1.0.0                                                     
*                                                                                   
*Purpose:           Basic wrapper.
*					Git forgive me for the unwieldy file name
* 
* 
*Update Log:		v1.0.0
*						- null
*/
package ui.dialogs;


public class ListAndStringDialogStateWrapper
{ 
	//data to wrap
	public final int closeMode;
	public final String string;
	public final Object element;
	
	
	//wrapper constructor
	public ListAndStringDialogStateWrapper(int closeMode, String string, Object element)
	{
		this.closeMode = closeMode;
		this.string = string;
		this.element = element;
	}
	
	
	@Override
	//print all contexts
	public String toString()
	{
		String s = ("closeMode: " + closeMode + " || string: ");
		
		if(string != null)
		{
			if(string.length() > 0)
			{
				s += string;
			}
			else
			{
				s += "<NULL>";
			}
		}
		else
		{
			s += "<NULL>";
		}
		
		if (element != null)
		{
			s += " || element: " + element.toString();
		}
		else
		{
			s += " || element: <NULL>";
		}
		
		return s;
	}
}
