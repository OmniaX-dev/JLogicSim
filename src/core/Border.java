package core;
import java.awt.*;


public class Border extends Figure
{	
	private Color col;
	
	public Border(int w, int h, int x, int y, Color c)
	{
		super(w, h, x, y);
		col = c;
	}
	
	public void setColor(Color c)
	{
		col = c;
	}
	
	public void paint(Graphics g)
	{
		int x = getX();
		int y = getY();
		int _x = 0, _y = 0, x_ = 0, y_ = 0;
		int w = getWidth();
		int h = getHeight();
		int len = 15;
		
		Color cc = g.getColor();
		g.setColor(col);
		
		_x = x;
		_y = y;
		x_ = x;
		y_ = y + len;
		g.drawLine(_x, _y, x_, y_);
		
		_x = x;
		_y = y;
		x_ = x + len;
		y_ = y;
		g.drawLine(_x, _y, x_, y_);
		
		_x = x;
		_y = y + h;
		x_ = x;
		y_ = (y + h) - len;
		g.drawLine(_x, _y, x_, y_);
		
		_x = x;
		_y = y + h;
		x_ = x + len;
		y_ = y + h;
		g.drawLine(_x, _y, x_, y_);
		
		_x = x + w;
		_y = y;
		x_ = (x + w) - len;
		y_ = y;
		g.drawLine(_x, _y, x_, y_);
		
		_x = x + w;
		_y = y;
		x_ = x + w;
		y_ = y + len;
		g.drawLine(_x, _y, x_, y_);
		
		_x = x + w;
		_y = y + h;
		x_ =  x + w;
		y_ = (y + h) - len;
		g.drawLine(_x, _y, x_, y_);
		
		_x = x + w;
		_y = y + h;
		x_ = (x + w) - len;
		y_ = y + h;
		g.drawLine(_x, _y, x_, y_);
		
		g.setColor(cc);
	}

}
