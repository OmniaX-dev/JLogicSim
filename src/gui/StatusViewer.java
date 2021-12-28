package gui;

import global.Colors;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatusViewer extends JPanel
{
	public boolean status;
	private int x;
	private int y;
	public boolean enabled;
	
	public StatusViewer(boolean status)
	{
		this.status = status;
		setBackground(Color.black);
		addMouseListener(new MouseHandler());
		enabled = true;
	}
	
	public void setCoords(int x, int y)
	{
		this.x = x;
		this.y = y;
		setBounds(x, y, 30, 20);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.setColor(status ? Colors.oneColor : Colors.zeroColor);
		g.drawRect(x, y, 30, 20);
		FontMetrics fm = g.getFontMetrics(g.getFont());
		String s = (status ? "1" : "0");
		int fh = fm.getHeight();
		int fw = fm.stringWidth(s);
		g.drawString(s, 15 - (fw / 2), 10 + (fh / 2) - 5);
	}
	
	private class MouseHandler extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			int nclick = e.getClickCount();
			if (nclick == 2 && enabled)
			{
				status = !status;
				repaint();
			}
		}
	}
}
