package utilities;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class std
{
	private static InputStreamReader isr;
	private static BufferedReader br;
	
	
	public static void print(boolean b)
	{
		System.out.print(b);
	}
	
	public static void print(String s)
	{
		System.out.print(s);
	}
	
	public static void print(int i)
	{
		System.out.print(i);
	}
	
	public static void print(char c)
	{
		System.out.print(c);
	}
	
	public static void print(double d)
	{
		System.out.print(d);
	}
	
	public static void print(Object o)
	{
		System.out.print(o.toString());
	}
	
	
	
	public static void println(String s)
	{
		System.out.println(s);
	}
	
	public static void println(int i)
	{
		System.out.println(i);
	}
	
	public static void println(char c)
	{
		System.out.println(c);
	}
	
	public static void println(double d)
	{
		System.out.println(d);
	}
	
	public static void println(Object o)
	{
		System.out.println(o.toString());
	}
	
	public static void println(boolean b)
	{
		System.out.println(b);
	}
	
	public static void println()
	{
		println("");
	}

	
	
	public static String read()
	{
		String s = null;
		try
		{
			isr = new InputStreamReader(System.in);
			br = new BufferedReader(isr);
			s = br.readLine();
		}
		catch(Exception e){}
		return s;
	}
	
	public static int readInt()
	{
		try
		{
			return toint(read());
		}
		catch (Exception e){return 0;}
	}
	
	public static double readDouble()
	{
		try
		{
			return Double.parseDouble(read());
		}
		catch (Exception e){return 0;}
	}
	
	public static char readChar()
	{
		try
		{
			return chr(read());
		}
		catch (Exception e){return ' ';}
	}
	
	public static boolean readBool()
	{
		try
		{
			String b = read();
			b = b.toLowerCase();
			return b.equals("true") || b.equals("vero");
		}
		catch (Exception e){return false;}
	}
	
	
	
	public static String str(int i)
	{
		return String.valueOf(i);
	}
	
	public static String str(char c)
	{
		return String.valueOf(c);
	}
	
	public static String str(double d)
	{
		return String.valueOf(d);
	}
	
	public static String str(boolean b)
	{
		return String.valueOf(b);
	}
	
	
	
	public static char chr(String s)
	{
		return s.charAt(0);
	}
	
	public static char chr(int i)
	{
		return chr(str(i));
	}
	
	
	
	public static int toint(String s)
	{
		return Integer.parseInt(s);
	}
	
	public static int toint(char c)
	{
		return toint(str(c));
	}
	
	public static int toint(boolean b)
	{
		return (b ? 1 : 0);
	}
	
	public static int toint(double d)
	{
		return (int)Math.round(d);
	}
	
	
	public static boolean charToBool(char c)
	{
		return (c == '0' ? false : true);
	}
	
	public static String[] stringTokenizerToArray(StringTokenizer st, boolean trim)
	{
		int conto = st.countTokens();
		String[] tokens = new String[conto];
		int i = 0;
		while(st.hasMoreTokens())
		{
			tokens[i] = st.nextToken();
			if (trim)
				tokens[i] = tokens[i].trim();
			i++;
		}
		return tokens;
	}
	
	public static boolean arrayContains(String[] array, String s)
	{
		for (String str : array)
			if (str != null && str.equals(s))
				return true;
		return false;
	}
	
	public static int arrayLength(String[] array)
	{
		if (array == null)
			return 0;
		int count = 0;
		for (String str : array)
			if (str != null)
				count++;
			else
				break;
		return count;
	}
	
	public static String replaceAll(String src, String what, String with)
	{
		String replaced = src;
		while (replaced.contains(what))
		{
			replaced = replaced.replace(what, with);
		}
		return replaced;
	}
	
	public static boolean moveFile(String f, String d)
	{
	    File file = new File(f);
	    File dir = new File(d);
	    return file.renameTo(new File(dir, file.getName()));
	}
	
	public static boolean deleteDir(File dir) 
	{
		if (dir.isDirectory()) 
		{
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) 
			{
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) 
				{
					return false;
				}
			}
		}
		return dir.delete();
	}
	
	public static String removeNewLine(String s)
	{
		return s.replaceAll("\\r\\n|\\r|\\n", " ");
	}
	
	public static Image getImage(String path)
	{
		return Toolkit.getDefaultToolkit().getImage(path + ".png");
	}
	
	public static Image getImage(String path, String type)
	{
		return Toolkit.getDefaultToolkit().getImage(path + "." + type);
	}
	
	public static Color stringToColor(String str)
	{
		int r = 0, g = 0, b = 0;
		StringTokenizer st = new StringTokenizer(str.trim(), ",");
		r = toint(st.nextToken());
		g = toint(st.nextToken());
		b = toint(st.nextToken());
		return new Color(r, g, b);
	}
}
