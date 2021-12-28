package core;
import global.Colors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public abstract class Figure
{
	public static int count = 0;
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected int id;
	protected boolean selected;
	protected boolean ontop;
	protected Border con;
	protected Color borderCol;
	
	
	public Figure(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		borderCol = Colors.selectionColor;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void setWidth(int w)
	{
		this.w = w;
	}
	
	public void setHeight(int h)
	{
		this.h = h;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return w;
	}
	
	public int getHeight()
	{
		return h;
	}
	
	public int getID()
	{
		return id;
	}
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	public void setSelected(boolean s)
	{
		selected = s;
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public static void remove()
	{
		count--;
	}
	
	public Rectangle getRectangle()
	{
		return new Rectangle(x, y, w, h);
	}
	
	protected void drawLink(int x1, int y1, int x2, int y2, Graphics g, Color c)
	{
		Color temp = g.getColor();
		g.setColor(c);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(2));
		int x3 = 0, y3 = 0, x4 = 0, y4 = 0;
		if (x2 < x1)
		{
			x3 = x2;
			y3 = rely((y2 - y1) / 2);
			x4 = x1;
			y4 = y3;
			int tmp = x3;
			x3 = x4;
			x4 = tmp;
		}
		else
		{
			x3 = relx((x2 - x1) / 2);
			y3 = y1;
			x4 = x3;
			y4 = y2;
		}
		g2d.drawLine(x1, y1, x3, y3);
		g2d.drawLine(x3, y3, x4, y4);
		g2d.drawLine(x4, y4, x2, y2);
		g2d.setColor(temp);
		g2d.setStroke(new BasicStroke(1));
	}
	
	public boolean isMouseOver(int mx, int my)
	{
		return (mx >= getX() && mx <= getX() + getWidth() && my >= getY() && my <= getY() + getHeight());
	}
	
	public boolean isOntop()
	{
		return ontop;
	}
	
	public void setOntop(boolean top)
	{
		ontop = top;
	}
	
	public void setBorderColor(Color c)
	{
		borderCol = c;
	}
	
	public int relx(int x)
	{
		return this.x + x;
	}
	
	public int rely(int y)
	{
		return this.y + y;
	}
	
	public abstract void paint(Graphics g);
	
	public void drawBorder(Graphics g)
	{
		con.setX(relx(-10));
		con.setY(rely(-10));
		if (selected)
			con.paint(g);
	}
}
