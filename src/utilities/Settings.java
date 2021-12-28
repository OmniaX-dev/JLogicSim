package utilities;

import java.awt.Color;
import java.util.StringTokenizer;

public class Settings
{
	private String lines;
	private String[] properties;
	private TextFileStream f;
	
	public Settings(String path)
	{
		f = new TextFileStream(path);
		lines = f.getLines();
		StringTokenizer st = new StringTokenizer(lines, ";");
		properties = std.stringTokenizerToArray(st, true);
	}
	
	public String getProperty(String name)
	{
		name = name.trim();
		for (int i = 0; i < properties.length; i++)
		{
			String n = "", v = "", tmp = properties[i];
			n = tmp.substring(0, tmp.indexOf("=")).trim();
			v = tmp.substring(tmp.indexOf("=") + 1, tmp.length()).trim();
			if (n.equals(name))
				return v;
		}
		return null;
	}
	
	public int getInt(String name)
	{
		if (getProperty(name) != null)
			return std.toint(getProperty(name));
		return 0;
	}
	
	public boolean getBoolean(String name)
	{
		if (getProperty(name) != null)
			return (getProperty(name).toLowerCase().equals("true") ? true : false);
		return false;
	}
	
	public Color getColor(String name)
	{
		if (getProperty(name) != null)
			return std.stringToColor(getProperty(name));
		return null;
	}
	
	public void editProperty(String name, String value)
	{
		name = name.trim();
		for (int i = 0; i < properties.length; i++)
		{
			String n = "", tmp = properties[i];
			n = tmp.substring(0, tmp.indexOf("=")).trim();
			if (n.equals(name))
			{
				properties[i] = n + " = " + value;
				return;
			}
		}
	}
	
	public void apply()
	{
		f.clear();
		for (int i = 0; i < properties.length; i++)
			if (!properties[i].trim().equals(""))
				f.addLine(properties[i] + ";");
		f.apply();
	}
}
