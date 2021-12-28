package gui;

import global.Global;
import gui.toolbar.*;
import javax.swing.*;
import utilities.OS;
import core.Core;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Gui extends JFrame
{
	private GridBagLayout layout;
	private GridBagConstraints c;
	private ToolBar toolbar;
	public Core core;
	private Gui instance;
	private JFileChooser save;
	
	public Gui(String titolo, int w, int h, boolean visible)
	{
		//------------------------------------------------------------------------------------------------------
		super(titolo);
		instance = this;
		Global.g = this;
		setSize(w, h);
		setLocation(100, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layout = new GridBagLayout();
		c = new GridBagConstraints();
		setLayout(layout);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		Image icon = Toolkit.getDefaultToolkit().getImage("logo.png");
		setIconImage(icon);
		//------------------------------------------------------------------------------------------------------
		
		
		addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e)
			{
				core.clear();
			}
			
			public void windowClosed(WindowEvent e){}
		});
		
		setupLayout();
		setupToolbar();
		
		setVisible(visible);
	}
	
	public void saveDialog(boolean showDialog)
	{
		if (!showDialog && !core.saved)
			showDialog = true;
		save = new JFileChooser();
		//save.setAcceptAllFileFilterUsed(false);
		save.addChoosableFileFilter(new JLogicProjectFilter());
		if (showDialog && save.showSaveDialog(instance) == JFileChooser.APPROVE_OPTION)
		{
			String file = save.getSelectedFile().getPath();
			if (!file.toLowerCase().endsWith(".jl"))
				file += ".jl";
			core.save(file);
		}
		else if (!showDialog)
		{
			core.save(core.filePath);
		}
	}
	
	public void openDialog()
	{
		save = new JFileChooser();
		//save.setAcceptAllFileFilterUsed(false);
		save.addChoosableFileFilter(new JLogicProjectFilter());
		if (save.showOpenDialog(instance) == JFileChooser.APPROVE_OPTION)
		{
			String file = save.getSelectedFile().getPath();
			core.load(file);
		}
	}
	
	private void setupLayout()
	{
		toolbar = new ToolBar();
		toolbar.setGradient(new Color(30, 30, 30), new Color(20, 20, 20));
		
		core = new Core();
		core.g = this;
		core.setLayout(null);
		Global.c = core;
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0;
		c.ipadx = getWidth();
		c.ipady = 40;
		c.fill = GridBagConstraints.BOTH;
		layout.setConstraints(toolbar, c);
		add(toolbar);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = getWidth();
		c.ipady = getHeight();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		layout.setConstraints(core, c);
		add(core);
	}
	
	private void setupToolbar()
	{
		ToolButton n = new ToolButton("new");
		n.setToolTipText("Create a new project");
		ToolButton o = new ToolButton("open");
		o.setToolTipText("Open an existing project");
		ToolButton s = new ToolButton("save");
		s.setToolTipText("Save the current project");
		ToolButton sa = new ToolButton("saveas");
		sa.setToolTipText("Save the current project as...");
		ToolButton add = new ToolButton("add");
		add.setToolTipText("Add a component");
		ToolButton about = new ToolButton("about");
		about.setToolTipText("Program information");
		ToolButton del = new ToolButton("delete");
		del.setToolTipText("Delete selected component");
		ToolButton session = new ToolButton("session");
		session.setToolTipText("Load the last session");
		ToolButton create = new ToolButton("create");
		create.setToolTipText("Create a custom component");
		ToolButton settings = new ToolButton("settings");
		settings.setToolTipText("Program settings");
		
		about.addToolButtonListener(new ToolButtonListener() {
			public void toolButtonClicked(MouseEvent e)
			{
				Global.showAbout();
			}
		});
		add.addToolButtonListener(new ToolButtonListener() {
			public void toolButtonClicked(MouseEvent e)
			{
				Global.showComponentBrowser();
			}
		});
		del.addToolButtonListener(new ToolButtonListener() {
			public void toolButtonClicked(MouseEvent e)
			{
				core.deleteGate();
			}
		});
		session.addToolButtonListener(new ToolButtonListener() {
			public void toolButtonClicked(MouseEvent e)
			{
				core.load("saves" + OS.getSlash() + "session.jl");
				core.filePath = "";
				core.saved = false;
				core.reprocessWires();
			}
		});
		n.addToolButtonListener(new ToolButtonListener() {
			public void toolButtonClicked(MouseEvent e)
			{
				core.clear();
			}
		});
		sa.addToolButtonListener(new ToolButtonListener() {
			public void toolButtonClicked(MouseEvent e)
			{
				saveDialog(true);
			}
		});
		o.addToolButtonListener(new ToolButtonListener() {
			public void toolButtonClicked(MouseEvent e)
			{
				openDialog();
			}
		});
		s.addToolButtonListener(new ToolButtonListener() {
			public void toolButtonClicked(MouseEvent e)
			{
				saveDialog(false);
			}
		});
		create.addToolButtonListener(new ToolButtonListener() {
			public void toolButtonClicked(MouseEvent e)
			{
				Global.showComponentCreator();
			}
		});
		settings.addToolButtonListener(new ToolButtonListener() {
			public void toolButtonClicked(MouseEvent e)
			{
				Global.showSettings();
			}
		});
		
		toolbar.addButton(n);
		toolbar.addButton(o);
		toolbar.addButton(session);
		toolbar.addButton(s);
		toolbar.addButton(sa);
		toolbar.addSeparator();
		toolbar.addButton(del);
		toolbar.addSeparator();
		toolbar.addButton(add);
		toolbar.addButton(create);
		toolbar.addSeparator();
		toolbar.addButton(settings);
		toolbar.addSeparator();
		toolbar.addButton(about);
	}
}
