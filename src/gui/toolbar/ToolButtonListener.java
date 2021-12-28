package gui.toolbar;

import java.awt.event.*;

public abstract class ToolButtonListener extends MouseAdapter
{
	public abstract void toolButtonClicked(MouseEvent e);
	public int done = 0;
	
	public void mousePressed(MouseEvent e)
	{
		toolButtonClicked(e);
	}
}
