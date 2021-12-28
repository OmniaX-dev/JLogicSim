package gui.toolbar;

import gui.MyPanel;
import java.util.Vector;

@SuppressWarnings("serial")
public class ToolBar extends MyPanel
{
	private Vector<ToolButton> buttons;
	
	public ToolBar()
	{
		buttons = new Vector<ToolButton>(1, 1);
		setLayout(null);
	}
	
	public void refreshButtons()
	{
		int x = 0;
		for (ToolButton b : buttons)
		{
			if (b instanceof ToolSeparator)
			{
				b.setBounds(x, 0, 16, 40);
				add(b);
				x += 16;
			}
			else
			{
				b.setBounds(x, 0, 40, 40);
				add(b);
				x += 40;
			}
		}
		repaint();
	}
	
	public void addSeparator()
	{
		addButton(new ToolSeparator());
	}
	
	public void addButton(ToolButton b)
	{
		buttons.add(b);
		refreshButtons();
	}
}
