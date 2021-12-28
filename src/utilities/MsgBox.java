package utilities;

import javax.swing.JOptionPane;

public class MsgBox 
{
	private static void msgbox(String s)
	{
		JOptionPane.showMessageDialog(null, s);
	}
	
	public static void show(String s)
	{
		msgbox(s);
	}
	
	public static void show(char c)
	{
		String s = "";
		s += c;
		msgbox(s);
	}
	
	public static void show(int i)
	{
		try
		{
			msgbox(Integer.toString(i));
		}
		catch (Exception ex)
		{
			msgbox("Il numero inserito non è valido!");
		}
	}
	
	public static void show(double d)
	{
		try
		{
			msgbox(Double.toString(d));
		}
		catch (Exception ex)
		{
			msgbox("Il numero inserito non è valido!");
		}
	}
	
	public static void show(float f)
	{
		try
		{
			msgbox(Float.toString(f));
		}
		catch (Exception ex)
		{
			msgbox("Il numero inserito non è valido!");
		}
	}
	
	public static void show(boolean b)
	{
		if (b)
			msgbox("True");
		else
			msgbox("False");
	}
}
