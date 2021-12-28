package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import utilities.MsgBox;
import utilities.std;

@SuppressWarnings("serial")
public class ComponentCreatorInfo extends MyForm
{
	private int ins;
	private int outs;
	private MyTextField txtName;
	private MyTextField txtInputs;
	private MyTextField txtOutputs;	
	private MyTextField txtInNames;
	private MyTextField txtOutNames;
	
	private JLabel lblInputs;
	private JLabel lblOutputs;
	private JLabel lblName;
	private JLabel lblInNames;
	private JLabel lblOutNames;
	
	private JButton ok;
	private Container parent;

	public ComponentCreatorInfo(Container p)
	{
		super(335, 220, p, "Add info");
		setLayout(null);
		this.parent = p;
		
		lblInputs = new JLabel(" Input number:");
		lblInputs.setBounds(5, 5, 130, 25);
		lblInputs.setForeground(Color.white);
		add(lblInputs, 0, 0);
		
		txtInputs = new MyTextField();
		txtInputs.setBounds(130, 5, 195, 25);
		txtInputs.setHorizontalAlignment(JTextField.CENTER);
		add(txtInputs);
		
		lblOutputs = new JLabel(" Output number:");
		lblOutputs.setBounds(5, 35, 130, 25);
		lblOutputs.setForeground(Color.white);
		add(lblOutputs, 0, 0);
		
		txtOutputs = new MyTextField();
		txtOutputs.setBounds(130, 35, 195, 25);
		txtOutputs.setHorizontalAlignment(JTextField.CENTER);
		add(txtOutputs);
		
		lblName = new JLabel(" Name:");
		lblName.setBounds(5, 65, 130, 25);
		lblName.setForeground(Color.white);
		add(lblName, 0, 0);
		
		txtName = new MyTextField();
		txtName.setBounds(130, 65, 195, 25);
		txtName.setHorizontalAlignment(JTextField.CENTER);
		add(txtName);
		
		lblInNames = new JLabel(" Input names:");
		lblInNames.setBounds(5, 95, 130, 25);
		lblInNames.setForeground(Color.white);
		add(lblInNames, 0, 0);
		
		txtInNames = new MyTextField();
		txtInNames.setBounds(130, 95, 195, 25);
		txtInNames.setHorizontalAlignment(JTextField.CENTER);
		add(txtInNames);
		
		lblOutNames = new JLabel(" Output names:");
		lblOutNames.setBounds(5, 125, 130, 25);
		lblOutNames.setForeground(Color.white);
		add(lblOutNames, 0, 0);
		
		txtOutNames = new MyTextField();
		txtOutNames.setBounds(130, 125, 195, 25);
		txtOutNames.setHorizontalAlignment(JTextField.CENTER);
		add(txtOutNames);
		
		ok = new JButton("OK");
		ok.setBounds(5, 155, 320, 25);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String in_names = txtInNames.getText();
				String out_names = txtOutNames.getText();
				String name = txtName.getText();
				if (name.trim().equals(""))
				{
					setVisible(false);
					MsgBox.show("Please, insert a valid name!");
					setVisible(true);
					return;
				}
				String s_ins = txtInputs.getText();
				try
				{
					ins = std.toint(s_ins);
				}
				catch (Exception e2)
				{
					setVisible(false);
					MsgBox.show("Please, insert a valid input number!");
					setVisible(true);
					return;
				}
				String s_outs = txtOutputs.getText();
				try
				{
					outs = std.toint(s_outs);
				}
				catch (Exception e2)
				{
					setVisible(false);
					MsgBox.show("Please, insert a valid output number!");
					setVisible(true);
					return;
				}
				ComponentCreator c = new ComponentCreator(parent, ins, outs, name);
				c.in_names = in_names;
				c.out_names = out_names;
				c.drawOutputs(outs);
				c.resetStatus(0);
				dispose();
			}
		});
		add(ok);
		
		setVisible(true);
	}
	
	public void clear()
	{
		txtInNames.setText("");
		txtInputs.setText("");
		txtName.setText("");
		txtOutNames.setText("");
		txtOutputs.setText("");
	}
}
