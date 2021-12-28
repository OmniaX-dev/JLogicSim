package gui;

import global.AppInfo;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.ImageIcon;
import utilities.OS;

@SuppressWarnings("serial")
public class AboutForm extends MyForm
{
	private ImageIcon img;
	
	public AboutForm(Container parent)
	{
		super(270, 390, parent, "");
		img = new ImageIcon(getClass().getResource(".." + OS.getSlash() + "logo.png"));
		setUndecorated(true);
		addWindowFocusListener(new WindowFocusListener() {
			public void windowLostFocus(WindowEvent e)
			{
				dispose();
			}
			
			public void windowGainedFocus(WindowEvent e){}
		});
		MyPanel p = new MyPanel() {
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(new Color(0, 60, 0));
				g.setFont(new Font("Arial", Font.BOLD, 20));
				
				g.drawRect(0, 0, getWidth(), getHeight());
				g.drawRect(1, 1, getWidth() - 2, getHeight() - 2);
				g.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
				g.drawRect(3, 3, getWidth() - 6, getHeight() - 6);
				g.drawImage(img.getImage(), 7, 5, 256, 256, null);
				
				g.setColor(new Color(0, 190, 0));
				g.drawString(AppInfo.fullName, 20, 290);
				
				g.setFont(new Font("Arial", Font.BOLD, 12));
				
				g.setColor(new Color(20, 120, 20));
				g.drawString("Developed by: " + AppInfo.developer, 30, 310);
				
				//g.setColor(new Color(0, 140, 198));
				g.drawString("E-mail: " + AppInfo.email, 30, 330);
				
				//g.setColor(new Color(180, 180, 180));
				g.drawString("Version: " + AppInfo.fullVersion + " - build " + AppInfo.build, 30, 350);
				
				//g.setColor(new Color(0, 140, 198));
				//g.drawString("System: " + OS.OS_NAME + " " + OS.OS_VERSION + " " + OS.OS_ARCH, 30, 350);
			}
		};
		p.setGradient(new Color(0, 20, 0), new Color(0, 0, 0));
		setLayout(null);
		p.setBounds(0, 0, getWidth(), getHeight());
		add(p);
		setVisible(true);
	}
	
}
