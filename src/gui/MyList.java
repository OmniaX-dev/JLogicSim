package gui;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings({ "serial", "rawtypes" })
public class MyList extends JList
{
	public MyList()
	{
		super();
		setFont(new Font("Arial", Font.BOLD, 15));
		setForeground(Color.green);
		setBackground(Color.black);
		setSelectionBackground(Color.green.darker());
		setSelectionForeground(Color.white);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
}
