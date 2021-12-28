package gui;

import global.Booleans;
import global.Global;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import utilities.std;

@SuppressWarnings("serial")
public class Settings extends MyForm
{
	private JCheckBox debug;
	private JCheckBox grid;
	private JCheckBox cut;
	private JButton save;
	private Component parent;
	
	public Settings(Container p)
	{
		super(150, 150, p, "Settings");
		parent = p;
		Booleans.settings = true;
		 setVisible(true);
		 debug = new JCheckBox("Debug");
		 grid = new JCheckBox("Grid");
		 cut = new JCheckBox("Cut");
		 
		 save = new JButton("Save");
		 
		 debug.setSelected(Booleans.debug);
		 grid.setSelected(Booleans.grid);
		 cut.setSelected(Booleans.cut);
		 
		 debug.setBounds(5, 5, 100, 25);
		 debug.setOpaque(false);
		 debug.setForeground(Color.white);
		 grid.setBounds(5, 30, 100, 25);
		 grid.setOpaque(false);
		 grid.setForeground(Color.white);
		 cut.setBounds(5, 55, 100, 25);
		 cut.setOpaque(false);
		 cut.setForeground(Color.white);
		 
		 save.setBounds(5, 85, 135, 25);
		 save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				Global.sets.editProperty("debug", std.str(debug.isSelected()));
				Global.sets.editProperty("grid", std.str(grid.isSelected()));
				Global.sets.editProperty("cut", std.str(cut.isSelected()));
				Global.sets.apply();
				Booleans.load();
				Booleans.settings = false;
				parent.repaint();
				dispose();
			}
		});
		 
		 add(debug);
		 add(grid);
		 add(cut);
		 
		 add(save);
		 
		 addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent arg0)
			{
				Booleans.settings = false;
			}
		});
	}

}
