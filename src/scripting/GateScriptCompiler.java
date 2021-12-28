package scripting;

import global.Global;
import java.util.StringTokenizer;
import utilities.TextFileStream;
import utilities.std;

public class GateScriptCompiler
{
	private static TextFileStream file;
	
	public static int getInputNumber(String fileName)
	{
		file = new TextFileStream(fileName);
		String lines = file.getLines();
		lines = Global.HexToText(lines);
		StringTokenizer st = new StringTokenizer(lines, ";");
		String token = "", tmp = "";
		int ins = 0;
		while (st.hasMoreTokens())
		{
			token = st.nextToken().trim();
			if (token.toLowerCase().startsWith("#inputs"))
			{
				tmp = token.substring(token.indexOf("=") + 1).trim();
				try
				{
					ins = std.toint(tmp);
				}
				catch (Exception e)
				{
					ins = 2;
				}
				break;
			}
		}
		return ins;
	}
	
	public static int getOutputNumber(String fileName)
	{
		file = new TextFileStream(fileName);
		String lines = file.getLines();
		lines = Global.HexToText(lines);
		StringTokenizer st = new StringTokenizer(lines, ";");
		String token = "", tmp = "";
		int outs = 0;
		while (st.hasMoreTokens())
		{
			token = st.nextToken().trim();
			tmp = token.substring(token.indexOf("=") + 1).trim();
			if (token.toLowerCase().startsWith("#outputs"))
			{
				tmp = token.substring(token.indexOf("=") + 1).trim();
				try
				{
					outs = std.toint(tmp);
				}
				catch (Exception e)
				{
					outs = 1;
				}
				break;
			}
		}
		return outs;
	}
	
	public static String[] getInputNames(String fileName)
	{
		String[] names = new String[getInputNumber(fileName)];
		file = new TextFileStream(fileName);
		String lines = file.getLines();
		lines = Global.HexToText(lines);
		StringTokenizer st = new StringTokenizer(lines, ";");
		String token = "", tmp = "";
		while (st.hasMoreTokens())
		{
			token = st.nextToken();
			token = token.trim();
			if (token.toLowerCase().startsWith("#input_names"))
			{
				tmp = token.substring(token.indexOf("(") + 1, token.lastIndexOf(")")).trim();
				if (tmp.trim().equals(""))
				{
					for (int i = 0; i < getInputNumber(fileName); i++)
						names[i] = " ";
					return names;
				}
				st = new StringTokenizer(tmp, ",");
				int i = 0;
				while (st.hasMoreTokens())
				{
					names[i] = st.nextToken().trim();
					i++;
				}
				return names;
			}
		}
		return null;
	}
	
	public static String[] getOutputNames(String fileName)
	{
		String[] names = new String[getOutputNumber(fileName)];
		file = new TextFileStream(fileName);
		String lines = file.getLines();
		lines = Global.HexToText(lines);
		StringTokenizer st = new StringTokenizer(lines, ";");
		String token = "", tmp = "";
		while (st.hasMoreTokens())
		{
			token = st.nextToken();
			token = token.trim();
			if (token.toLowerCase().startsWith("#output_names"))
			{
				tmp = token.substring(token.indexOf("(") + 1, token.lastIndexOf(")")).trim();
				if (tmp.trim().equals(""))
				{
					for (int i = 0; i < getOutputNumber(fileName); i++)
						names[i] = " ";
					return names;
				}
				st = new StringTokenizer(tmp, ",");
				int i = 0;
				while (st.hasMoreTokens())
				{
					names[i] = st.nextToken().trim();
					i++;
				}
				return names;
			}
		}
		return null;
	}
	
	public static boolean[] execute(String fileName, boolean[] inputs)
	{
		int o = getOutputNumber(fileName);
		boolean[] outputs = new boolean[o];
		file = new TextFileStream(fileName);
		String lines = file.getLines();
		lines = Global.HexToText(lines);
		StringTokenizer st = new StringTokenizer(lines, ";");
		String token = "", tmp = "";
		while (st.hasMoreTokens())
		{
			token = st.nextToken().trim().toLowerCase();
			if (token.startsWith("#") || token.trim().equals(""))
				continue;
			String out = token.substring(0, token.indexOf("=")).trim();
			int out_n = 0;
			try
			{
				out_n = std.toint(out.substring(1));
			}
			catch (Exception e)
			{
				return null;
			}
			out_n--;
			tmp = token.substring(token.indexOf("=") + 1).trim();
			StringTokenizer st2 = new StringTokenizer(tmp, "+");
			boolean first = true;
			while (st2.hasMoreTokens())
			{
				String and = st2.nextToken().trim();
				if (first)
				{
					outputs[out_n] = solveAND(and, inputs);
					first = false;
				}
				else
					outputs[out_n] = outputs[out_n] || solveAND(and, inputs);
			}
		}
		return outputs;
	}
	
	private static boolean solveAND(String and, boolean[] inputs)
	{
		and = and.trim().toUpperCase();
		boolean neg = false, f = false, first = true, tmp = false;
		for (int i = 0; i < and.length(); i++)
		{
			char c = and.charAt(i);
			if (c == '!')
			{
				neg = true;
				continue;
			}
			int index = ((int)(c)) - 65;
			tmp = inputs[index];
			if (neg)
				tmp = !tmp;
			if (first)
			{
				f = tmp;
				first = false;
			}
			else
				f = (f && tmp);
			neg = false;
		}
		return f;
	}
}
