package global;

import java.util.Random;
import utilities.Conversions;
import utilities.Settings;
import utilities.std;
import core.Core;
import gui.AboutForm;
import gui.ComponentBrowser;
import gui.ComponentCreatorInfo;
import gui.Gui;

@SuppressWarnings("unused")
public class Global
{
	private static ComponentCreatorInfo cc;
	public static Gui g;
	public static Core c;
	private static AboutForm af;
	private static ComponentBrowser cb;
	private static gui.Settings settings;
	public static Settings sets = new Settings("settings");
	
	public static void showAbout()
	{
		af = new AboutForm(g);
	}
	
	public static void showSettings()
	{
		if (!Booleans.settings)
			settings = new gui.Settings(g);
	}
	
	public static void showComponentCreator()
	{
		if (cc == null)
			cc = new ComponentCreatorInfo(g);
		else
		{
			cc.clear();
			cc.setVisible(true);
		}
	}
	
	public static void showComponentBrowser()
	{
		if (cb == null)
			cb = new ComponentBrowser(g, c);
		else
			cb.setVisible(true);
	}
	
	public static String TextToHex(String text)
	{
		String hex = "";
		for (char c : text.toCharArray())
		{
			int tmp = (int)c;
			hex += Conversions.DecToHex(tmp);
		}
		return hex;
	}
	
	public static String HexToText(String hex)
	{
		String text = "";
		for (int i = 0; i < hex.length() - 1; i += 2)
		{
			String h = std.str(hex.charAt(i)) + std.str(hex.charAt(i + 1));
			int tmp = Conversions.HexToDec(h);
			text += (char)tmp;
		}
		return text;
	}
	
	private static int[] rand = new int[62];
	
	private static void fillRand()
	{
		for (int i = 48; i < 58; i++)
			rand[i - 48] = i;
		for (int i = 65; i < 91; i++)
			rand[i - 65 + 10] = i;
		for (int i = 97; i < 123; i++)
			rand[i - 97 + 36] = i;
	}
	
	public static String randomString(int len)
	{
		fillRand();
		String id = "";
		Random rnd = new Random();
		try
		{
			Thread.sleep(10);
		}
		catch (InterruptedException e){}
		Random tmp = new Random();
		for (int i = 0; i < len; i++)
		{
			id += (char)rand[rnd.nextInt(62)];
		}
		return id;
	}
}
