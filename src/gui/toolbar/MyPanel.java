package gui.toolbar;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class MyPanel extends JPanel
{
	private Color startColor;
	private Color endColor;
	private GradientPaint back;
	
	public MyPanel()
	{
		
	}
	
	public void setGradient(Color start, Color end)
	{
		startColor = start;
		endColor = end;
		repaint();
	}
	
	public void paintComponent(Graphics gfx)
	{
		Graphics2D g = (Graphics2D)gfx;
		back = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
		g.setPaint(back);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
