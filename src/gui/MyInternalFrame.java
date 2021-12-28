package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class MyInternalFrame extends MyPanel
{
	protected MyButton close = new MyButton("x");
	protected boolean moveable;
	private MouseHandler mh;
	
	public MyInternalFrame(int x, int y, int w, int h)
	{
		mh = new MouseHandler(this);
		setMoveable(true);
		setBounds(x, y, w, h);
		setGradient(new Color(0, 60, 0), Color.black);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});
		close.addMouseListener(new CloseButtoMouseHandler());
		close.addMouseMotionListener(new CloseButtoMouseHandler());
		close.setGradient(Color.red.darker(), Color.red.darker());
		close.setBackground(Color.red);
		close.setTextColor(Color.white);
		close.setBounds(getWidth() - 35, 5, 30, 30);
		add(close);
	}
	
	protected void setMoveable(boolean m)
	{
		if (m && !moveable)
		{
			addMouseListener(mh);
			addMouseMotionListener(mh);
			moveable = true;
		}
		else if (moveable)
		{
			removeMouseListener(mh);
			removeMouseMotionListener(mh);
		}
	}
	
	private class CloseButtoMouseHandler extends MouseAdapter
	{
		public void mouseEntered(MouseEvent e)
		{
			close.setGradient(new Color(90, 0, 0), new Color(90, 0, 0));
		}
		
		public void mouseExited(MouseEvent e)
		{
			close.setGradient(Color.red.darker(), Color.red.darker());
		}
		
		public void mousePressed(MouseEvent e)
		{
			close.setGradient(new Color(50, 0, 0), new Color(50, 0, 0));
		}
	}
	
	private class MouseHandler extends MouseAdapter
	{
		private Point pt;
		private JPanel movingPanel;
		private int xx;
		private int yy;
		
		public MouseHandler(JPanel p)
		{
			movingPanel = p;
		}
		
		public void mousePressed(MouseEvent e)
		{
			xx = e.getX();
			yy = e.getY();
		}
		
		public void mouseDragged(MouseEvent e)
		{
			pt = SwingUtilities.convertPoint(movingPanel, e.getX(), e.getY(), movingPanel.getParent());
			movingPanel.setLocation(pt.x - xx, pt.y - yy);
		}
	}
}
