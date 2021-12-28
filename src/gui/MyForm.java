package gui;

import java.awt.Color;
import java.awt.Container;
import javax.swing.*;

@SuppressWarnings("serial")
public class MyForm extends JDialog
{
	public MyForm(int w, int h, Container parent, String title)
	{
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds((parent.getWidth() / 2) - (w / 2) + parent.getX(), (parent.getHeight() / 2) - (h / 2) + parent.getY(), w, h);
		setTitle(title);
		setResizable(false);
		getContentPane().setBackground(Color.darkGray);
	}
}
