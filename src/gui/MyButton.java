package gui;

import java.awt.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class MyButton extends JButton
{
	private Color textColor;
	private Color startColor;
	private Color endColor;
	private GradientPaint normal;
	private GradientPaint hover;
	private GradientPaint clicked;
	
	public MyButton(String label)
	{
		setBorder(null);
		setText(label);
		textColor = Color.white;
		startColor = Color.blue;
		endColor = Color.blue.darker().darker();
	}
	
	public void setTextColor(Color c)
	{
		textColor = c;
		repaint();
	}
	
	public Color getTextColor()
	{
		return textColor;
	}
	
	public void setGradient(Color start, Color end)
	{
		startColor = start;
		endColor = end;
		repaint();
	}
	
	public Color getGradientColor1()
	{
		return startColor;
	}
	
	public Color getGradientColor2()
	{
		return endColor;
	}
	
	public void paintComponent(Graphics gfx)
	{
		Color tmptxt = new Color(textColor.getRGB());
		Graphics2D g = (Graphics2D)gfx;
		FontMetrics fm = g.getFontMetrics(getFont());
		int w = fm.stringWidth(getText());
		normal = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
		hover = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor.brighter());
		clicked = new GradientPaint(0, 0, endColor, 0, getHeight(), startColor);
		ButtonModel model = getModel();	
		if (!model.isRollover())
		{
			g.setPaint(normal);
			tmptxt = new Color(textColor.getRGB());
		}
		else
		{
			g.setPaint(hover);
			tmptxt = new Color(textColor.getRGB()).brighter();
		}
		if (model.isPressed())
		{
			g.setPaint(clicked);
			tmptxt = new Color(textColor.getRGB()).darker();
		}
		g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
		g.setColor(tmptxt);
		g.drawString(getText(), (getWidth() / 2) - (w / 2), (getHeight() / 2) + (fm.getHeight() / 3));
	}
}
