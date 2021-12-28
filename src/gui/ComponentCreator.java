package gui;

import scripting.ComponentFileWriter;
import scripting.FunctionGenerator;
import utilities.MsgBox;
import utilities.StringEditor;
import utilities.TextFileStream;
import utilities.std;
import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;
import javax.swing.*;

@SuppressWarnings({ "rawtypes", "unchecked" , "serial"})
public class ComponentCreator extends MyForm
{
	private int vars, outs;
	private DefaultListModel m;
	private JList l;
	private boolean[][] values;
	private StatusViewer[] outputs;
	private int previuous_sel;
	private JLabel lblIn;
	private JButton done;
	private JButton tt_from_file;
	private String name;
	public String in_names;
	public String out_names;

	public ComponentCreator(Container parent, int vars, int outs, String componentName)
	{
		super(505, 350, parent, "Custom components creator");
		setLayout(null);
		this.outs = outs;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBackground(Color.black);
		getContentPane().setBackground(Color.darkGray);
		setResizable(false);
		
		//setAlwaysOnTop(false);
		
		name = componentName;
		
		lblIn = new JLabel("Input combination:");
		lblIn.setBounds(5, 0, 150, 25);
		lblIn.setForeground(Color.white);
		add(lblIn);
		
		done = new JButton("Create");
		done.setBounds(250, 285, 255, 30);
		done.addActionListener(new Create(this));
		add(done);
		
		tt_from_file = new JButton("Truth Table from file");
		tt_from_file.setBounds(250, 255, 255, 30);
		tt_from_file.addActionListener(new TTFromFile(this));
		add(tt_from_file);
		
		
		values = new boolean[outs][(int)(Math.pow(2, vars))];
		outputs = new StatusViewer[outs];
		l = new JList();
		m = new DefaultListModel();
		l.setModel(m);
		
		l.setBounds(0, 25, 240, 290);
		l.setFont(new Font("Arial", Font.BOLD, 20));
		l.setForeground(Color.green);
		l.setBackground(Color.black);
		l.setSelectionBackground(Color.green.darker());
		l.setSelectionForeground(Color.white);
		l.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane sp = new JScrollPane(l, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setBounds(5, 25, 240, 290);
		
		this.vars = vars;
		generateStandard();
		add(sp);
		l.setSelectedIndex(0);
		l.addMouseListener(new MouseHandler());
		repaint();
		
		setVisible(true);
	}
	
	private class TTFromFile implements ActionListener
	{
		private ComponentCreator instance;
		
		public TTFromFile(ComponentCreator instance)
		{
			this.instance = instance;
		}
		
		public void actionPerformed(ActionEvent arg0)
		{
			JFileChooser save = new JFileChooser();
			//save.setAcceptAllFileFilterUsed(false);
			save.addChoosableFileFilter(new JLogicProjectFilter());
			if (save.showOpenDialog(instance) == JFileChooser.APPROVE_OPTION)
			{
				String file = save.getSelectedFile().getPath();
				TextFileStream fs = new TextFileStream(file);
				String tt = fs.getLines();
				StringTokenizer st = new StringTokenizer(tt, "\n");
				
				String[] arr = StringEditor.stringTokenizerToArray(st, true);
				int cols = (int)(Math.pow(2, vars));
				if (arr.length != cols)
				{
					instance.setVisible(false);
					instance.dispose();
					MsgBox.show("Number of lines does not\nmatch number of combinations");
					return;
				}
				String col = "";
				for (int i = 0; i < cols; i++)
				{
					col = arr[i];
					if (col.length() != outs)
					{
						instance.setVisible(false);
						instance.dispose();
						MsgBox.show("Number of digits on line " + i + " does not\nmatch number of outputs");
						return;
					}
					for (int j = 0; j < outs; j++)
					{
						char c = col.charAt(j);
						if (c != '0' && c != '1')
						{
							instance.setVisible(false);
							instance.dispose();
							MsgBox.show("unknown symbol on line " + i);
							return;
						}
						values[j][i] = col.charAt(j) == '1';
					}
				}
			
				String f = "";
				for (int i = 0; i < outs; i++)
					f += "o" + (i + 1) + " = " + FunctionGenerator.getFunction(vars, values[i]) + ";";
				ComponentFileWriter.createComponentFile(name, f, vars, outs, in_names, out_names);
				
				instance.setVisible(false);
				instance.dispose();
			}
		}
	}
	
	private class Create implements ActionListener
	{
		private ComponentCreator instance;
		
		public Create(ComponentCreator instance)
		{
			this.instance = instance;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			previuous_sel = l.getSelectedIndex();
			l.setSelectedIndex(0);
			resetStatus(previuous_sel);
			String f = "";
			for (int i = 0; i < outs; i++)
				f += "o" + (i + 1) + " = " + FunctionGenerator.getFunction(vars, values[i]) + ";";
			ComponentFileWriter.createComponentFile(name, f, vars, outs, in_names, out_names);
			
			instance.setVisible(false);
			instance.dispose();
		}
	}
	
	private void generateStandard()
	{
		String[] st = FunctionGenerator.fillStandard(vars);
		for (int i = 0; i < Math.pow(2, vars); i++)
		{
			String s = st[i].trim().toUpperCase();
			m.add(i, s);
		}
	}
	
	public void drawOutputs(int outs)
	{
		String[] names;
		StringTokenizer st = new StringTokenizer(out_names, ",");
		names = std.stringTokenizerToArray(st, true);
		int r = 0, c = 0;
		for (int i = 0; i < outs; i++)
		{
			outputs[i] = new StatusViewer(false);
			if (i < names.length)
			{
				JLabel lab = new JLabel(names[i]);
				lab.setFont(new Font("Arial", Font.PLAIN, 12));
				lab.setForeground(Color.white);
				lab.setBounds(255 + (c * 35) + 5, (r * 25 * 2) + 5, 30, 20);
				add(lab);
			}
			outputs[i].setCoords(255 + (c * 35), (r * 50 + 20) + 5);
			add(outputs[i]);
			c++;
			if ((i + 1) % 7 == 0)
			{
				r += 1;
				c = 0;
			}
		}
	}
	
	public void resetStatus(int row)
	{
		int var = l.getSelectedIndex();
		for (int i = 0; i < outs; i++)
		{
			StatusViewer s = outputs[i];
			values[i][row] = s.status;
			outputs[i].status = values[i][var];
			outputs[i].repaint();
		}
		repaint();
	}
	
	private class MouseHandler extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			if (l.getSelectedIndex() >= 0)
			{
				resetStatus(previuous_sel);
				previuous_sel = l.getSelectedIndex();
			}
		}
	}

}
