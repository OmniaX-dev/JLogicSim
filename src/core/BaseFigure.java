package core;

import java.awt.*;
import java.io.File;
import utilities.*;



public class BaseFigure extends Figure
{
	protected Image img;
	
	public BaseFigure(int x, int y, int w, int h)
	{
		super(x, y, w, h);
		id = count;
		count++;
		createBorder(x, y, w, h);
	}
	
	public void setImage(String name)
	{
		try
		{
			File f = new File("img" + OS.getSlash() + name.substring(0, (name.contains("-") ? name.indexOf("-") : name.length())).toLowerCase() + ".png");
			if (f.exists())
				img = std.getImage("img" + OS.getSlash() + name.substring(0, (name.contains("-") ? name.indexOf("-") : name.length())).toLowerCase());
			else
				img = std.getImage("img" + OS.getSlash() + "generic");
		}
		catch (Exception e)
		{
			img = std.getImage("img" + OS.getSlash() + "generic");
		}
	}
	
	public void createBorder(int x, int y, int w, int h)
	{
		con = new Border(relx(-10), rely(-10), w + 20, h + 20, borderCol);
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.green);
		Font f = g.getFont();
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.fill3DRect(x, y, w, h, true);
		g.setColor(Color.black);
		g.drawString(std.str(id), relx(2), rely(15));
		drawBorder(g);
		g.setFont(f);
	}
}
