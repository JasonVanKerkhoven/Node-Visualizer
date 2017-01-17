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


public class ListAndStringDialogStateWrapper<Etype>
{ 
	//data to wrap
	public final int closeMode;
	public final String string;
	public final Etype element;
	
	
	//wrapper constructor
	public ListAndStringDialogStateWrapper(int closeMode, String string, Etype element)
	{
		this.closeMode = closeMode;
		this.string = string;
		this.element = element;
	}
}
