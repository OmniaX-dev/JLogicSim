package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import core.*;
import utilities.OS;

@SuppressWarnings({ "rawtypes", "unchecked" , "serial"})
public class ComponentBrowser extends MyForm
{
	private String[] folders;
	private String[] subFolders;
	private JList l1;
	private JList l2;
	private JScrollPane sp1;
	private JScrollPane sp2;
	private DefaultListModel dm;
	private DefaultListModel dm2;
	private int count;
	private int count2;
	private int x_start;
	private int y_start;
	private Core core;
	private Core prev;
	private LogicGate preview;
	private JButton add;
	private GridBagConstraints con;
	private GridBagLayout layout;

	public ComponentBrowser(Container parent, Core c)
	{
		super(515, 340, parent, "Component browser");
		layout = new GridBagLayout();
		con = new GridBagConstraints();
		setLayout(layout);
		setBackground(Color.darkGray);
		count = count2 = 0;
		x_start = y_start = 50;
		this.core = c;
		l1 = new MyList();
		l1.setModel(new DefaultListModel());
		l1.addMouseListener(new FolderListHandler());
		dm = (DefaultListModel)l1.getModel();
		loadFolders();
		sp1 = new JScrollPane(l1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		l2 = new MyList();
		l2.setModel(new DefaultListModel());
		l2.addMouseListener(new SubFolderListHandler());
		dm2 = (DefaultListModel)l2.getModel();
		sp2 = new JScrollPane(l2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		prev = new Core() {
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				if (preview != null)
					preview.paint(g);
			}
		};
		prev.setBackground(Color.black);
		
		add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (preview != null)
				{
					String sel = (String)l2.getSelectedValue();
					preview.setX(x_start);
					preview.setY(y_start);
					core.addGate(preview);
					core.repaint();
					x_start += 30;
					y_start += 30;
					preview = new LogicGate(45, 30, sel.substring(0, sel.lastIndexOf(".")), ((String)(l1.getSelectedValue())), core);
					prev.editable = false;
					prev.repaint();
				}
			}
		});
		
		gridbagSetup();
		pack();
		setSize(515, 270);
		
		setVisible(true);
	}
	
	private void gridbagSetup()
	{
		con.gridx = 0;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 2;
		con.weightx = 1.0;
		con.weighty = 1.0;
		con.ipadx = 150;
		con.ipady = 325;
		con.fill = GridBagConstraints.BOTH;
		layout.setConstraints(sp1, con);
		add(sp1);
		
		con.gridx = 1;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 2;
		con.weightx = 1.0;
		con.weighty = 1.0;
		con.ipadx = 150;
		con.ipady = 325;
		con.fill = GridBagConstraints.BOTH;
		layout.setConstraints(sp2, con);
		add(sp2);
		
		con.gridx = 3;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		con.weightx = 0;
		con.weighty = 1.0;
		con.ipadx = 200;
		con.ipady = 260;
		con.fill = GridBagConstraints.BOTH;
		layout.setConstraints(prev, con);
		add(prev);
		
		con.gridx = 3;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		con.weightx = 0;
		con.weighty = 1.0;
		con.ipadx = 200;
		con.ipady = 50;
		con.fill = GridBagConstraints.BOTH;
		layout.setConstraints(add, con);
		add(add);
	}
	
	private void loadFolders()
	{
		File f = new File("components");
		folders = f.list();
		for (String s : folders)
		{
			File ff = new File("components" + OS.getSlash() + s);
			if (ff.isDirectory())
				dm.add(count, s);
		}
	}
	
	private void loadSubFolders(String folder) 
	{
		String path = "components" + OS.getSlash() + folder;
		File f = new File(path);
		subFolders = f.list();
		dm2.clear();
		for (String s : subFolders)
		{
			File ff = new File(path + OS.getSlash() + s);
			if (!ff.isDirectory())
			{
				dm2.add(count2, s);
			}
		}
	}
	
	private class FolderListHandler extends MouseAdapter
	{
		public void mousePressed(MouseEvent e) 
		{
			String sel = (String)l1.getSelectedValue();
			if (sel != null)
			{
				preview = null;
				prev.repaint();
				loadSubFolders(sel);
			}
		}
	}
	
	private class SubFolderListHandler extends MouseAdapter
	{
		public void mousePressed(MouseEvent e) 
		{
			String sel = (String)l2.getSelectedValue();
			if (sel != null)
			{
				preview = new LogicGate(45, 30, sel.substring(0, sel.lastIndexOf(".")), ((String)(l1.getSelectedValue())), core);
				Figure.count--;
				prev.editable = false;
				prev.repaint();
			}
		}
	}

}
