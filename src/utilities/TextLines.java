package utilities;

import java.util.Vector;



public class TextLines
{
	private Vector<String> lines;

	public TextLines()
	{
		lines = new Vector<String>(1, 1);
	}

	public void addLine(String line)
	{
		lines.add(line);
	}

	public boolean addLine(int index, String line)
	{
		try
		{
			lines.add(index, line);
			return true;
		}
		catch (Exception e){return false;}
	}

	public void removeLine(int index)
	{
		try
		{
			lines.remove(index);
		}
		catch (Exception e){}
	}

	public boolean removeLine(String line)
	{
		return lines.remove(line);
	}

	public void clear()
	{
		lines.clear();
	}

	public String getLine(int index)
	{
		try
		{
			return lines.get(index);
		}
		catch (Exception e){return null;}
	}

	public String getLines(boolean newLineChar)
	{
		String nl = (newLineChar) ? "\n" : "";
		String allLines = "";
		String tmp = "";
		int i = 0;
		while ((tmp = getLine(i)) != null)
		{
			allLines += tmp + nl;
			i++;
		}
		return allLines;
	}

	public boolean contains(String line)
	{
		for (String s : lines)
		{
			if (s.equals(line))
				return true;
		}
		return false;
	}

	public int length()
	{
		return lines.size();
	}
}