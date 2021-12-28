package gui.toolbar;

import utilities.OS;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class ToolSeparator extends ToolButton
{
	public ToolSeparator()
	{
		super("");
		img = new ImageIcon(getClass().getResource(".." + OS.getSlash() + ".." + OS.getSlash() + "icons" + OS.getSlash() + "sep.png"));
		removeMouseListener(getMouseListeners()[0]);
		setIcon(img);
	}
}
