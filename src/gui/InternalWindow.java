package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class InternalWindow extends JPanel
{
	private MyPanel topBar;
	private MoveHandler mh;
	private MyButton close;
	
	public InternalWindow(int x, int y, int w, int h)
	{
		setLayout(null);
		setBounds(x, y, w, h);
		setBackground(new Color(0, 30, 0));
		topBar = new MyPanel();
		topBar.setGradient(new Color(100, 100, 100), new Color(20, 20, 20));
		topBar.setBounds(0, 0, w, 20);
		topBar.setBackground(Color.blue);
		topBar.setLayout(null);
		add(topBar);
		mh = new MoveHandler(this, topBar);
		topBar.addMouseListener(mh);
		topBar.addMouseMotionListener(mh);
		
		close = new MyButton("X");
		close.setGradient(new Color(100, 100, 100), new Color(20, 20, 20));
		close.setBounds(w - 20, 0, 20, 20);
		close.setTextColor(Color.red);
		topBar.add(close);
	}
	
	private class MoveHandler extends MouseAdapter
	{
		private Point pt;
		private JPanel movingPanel;
		private JPanel topBar;
		private int xx;
		private int yy;
		
		public MoveHandler(JPanel p, JPanel topBar)
		{
			movingPanel = p;
			this.topBar = topBar;
		}
		
		public void mousePressed(MouseEvent e)
		{
			xx = e.getX();
			yy = e.getY();
		}
		
		public void mouseDragged(MouseEvent e)
		{
			pt = SwingUtilities.convertPoint(topBar, e.getX(), e.getY(), movingPanel.getParent());
			movingPanel.setLocation(pt.x - xx, pt.y - yy);
		}
	}
}
