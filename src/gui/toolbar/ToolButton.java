package gui.toolbar;

import java.awt.event.*;
import javax.swing.*;
import utilities.OS;

@SuppressWarnings("serial")
public class ToolButton extends JLabel
{
	protected ImageIcon img;
	private String name;
	
	public ToolButton(String imageName)
	{
		MouseHandler mh = new MouseHandler();
		name = imageName;
		setImage(imageName);
		setIcon(img);
		addMouseListener(mh);
	}
	
	public void setToolTipText(String text)
	{
		super.setToolTipText(text);
	}
	
	public void setImage(String name)
	{
		try
		{
			img = new ImageIcon(getClass().getResource(".." + OS.getSlash() + ".." + OS.getSlash() + "icons" + OS.getSlash()  + name + ".png"));
		}
		catch (Exception e){}
	}
	
	public void addToolButtonListener(ToolButtonListener a)
	{
		addMouseListener(a);
		addMouseMotionListener(a);
	}
	
	private class MouseHandler extends MouseAdapter
	{		
		public void mouseEntered(MouseEvent e)
		{
			setImage(name + "_s");
			setIcon(img);
		}
		
		public void mouseExited(MouseEvent e)
		{
			setImage(name);
			setIcon(img);
		}
		
		public void mousePressed(MouseEvent e)
		{
			setImage(name + "_c");
			setIcon(img);
		}
		
		public void mouseReleased(MouseEvent e)
		{
			if (e.getX() > getWidth() || e.getY() > getHeight() || e.getX() < 0 || e.getY() < 0)
			{
				setImage(name);
				setIcon(img);
			}
			else
			{
				setImage(name + "_s");
				setIcon(img);
			}
		}
	}
}
