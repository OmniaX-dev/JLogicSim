package gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MyTextField extends JTextField
{
	public MyTextField()
	{
		super();
		setForeground(Color.green);
		setBackground(Color.black);
		setFont(new Font("Arial", Font.PLAIN, 14));
		setCaretColor(Color.red);
	}
}
