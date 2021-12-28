package run;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import global.AppInfo;
import global.Booleans;
import gui.Gui;

public class Main 
{
	public static Gui g;
	
	public static void main(String[] args) 
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e){}
		UIManager.put("ToolTip.foreground", new ColorUIResource(Color.white));
	    UIManager.put("ToolTip.background", new ColorUIResource(Color.black));
	    UIManager.put("ToolTip.font", new Font("Arial", Font.PLAIN, 15));
	    UIManager.put("ToolTip.border", BorderFactory.createLineBorder(new Color(0, 128, 0)));
		g = new Gui(AppInfo.fullName, 1024, 768, true);
		AppInfo.loadBuildNumber();
		if (args.length > 0)
		{
			g.core.load(args[0].trim());
		}
		Booleans.load();
	}
}
