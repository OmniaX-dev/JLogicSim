package scripting;

import utilities.Conversions;

public class FunctionGenerator
{
	public static String[] fillStandard(int vars)
	{
		int rows = (int)(Math.pow(2, vars));
		String[] array = new String[rows];
		for (int i = 0; i < rows; i++)
		{
			String n = Conversions.DecToBin(i);
			for (int j = n.length(); j < vars; j++)
				n = "0" + n;
			array[i] = n;
		}
		return array;
	}
	
	public static String getFunction(int vars, boolean[] outs)
	{
		String[] standard = fillStandard(vars);
		String f = "";
		int rows = (int)(Math.pow(2, vars));
		for (int i = 0; i < rows; i++)
		{
			if (outs[i])
			{
				f += getPartOfFunction(standard[i]) + " + ";
			}
		}
		f = f.trim();
		if (f.endsWith("+"))
			f = f.substring(0, f.length() - 2);
		return f;
	}
	
	private static String getPartOfFunction(String part)
	{
		String f = "";
		for (int i = 0; i < part.trim().length(); i++)
		{
			if (part.charAt(i) == '0')
				f += "!" + (char)(i + 65);
			else if (part.charAt(i) == '1')
				f += (char)(i + 65);
		}
		return f;
	}
}
