package core;

import global.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.Timer;
import scripting.GateScriptCompiler;
import utilities.*;

public class LogicGate extends BaseFigure
{
	public String name;
	public String fileName;
	public String label;
	public String basePath;
	public String sid;
	private int inputs;
	public int outputs;
	private final int interval = 20;
	private final int input_length = 20;
	private final int input_radius = 10;
	public Color color;
	public boolean[] input_v;
	public boolean[] output_v;
	private String[] in_names;
	private String[] out_names;
	private Timer clock;
	public int clock_speed;
	private Core parent;
	private boolean clock_running;
	public boolean _switch;
	public int[] x_inputs;
	public int[] y_inputs;
	public int[] x_outputs;
	public int[] y_outputs;
	public Vector<Wire> wires;
	public boolean[] input_occu;
	public boolean del;
	public boolean[] o_link;
	public boolean[] o_hover;
	public boolean[] i_hover;
	private int space_number;
	private boolean moreInputs;
	public boolean lastStatus;
	private int hh;
	private int t;

	public LogicGate(int x, int y, String name, String path, Core parent)
	{
		super(x, y, 0, 0);
		label = "";
		sid = Global.randomString(40);
		lastStatus = false;
		this.parent = parent;
		this.name = name;
		basePath = path;
		setPath(path);
		name = name.toUpperCase();
		this.name = name;
		int in = GateScriptCompiler.getInputNumber(fileName);
		input_v = new boolean[in];
		inputs = in;
		outputs = GateScriptCompiler.getOutputNumber(fileName);
		input_v = new boolean[in];
		color = Colors.gateColor;
		in_names = GateScriptCompiler.getInputNames(fileName);
		out_names = GateScriptCompiler.getOutputNames(fileName);
		x_inputs = new int[inputs];
		y_inputs = new int[inputs];
		x_outputs = new int[outputs];
		y_outputs = new int[outputs];
		input_occu = new boolean[inputs];
		o_hover = new boolean[outputs];
		i_hover = new boolean[inputs];
		o_link = new boolean[outputs];
		moreInputs = (inputs + space_number) >= outputs;
		if (moreInputs)
		{
			hh = interval * (inputs + space_number + 1);
			t = (hh - (interval * (outputs + 1))) / 2;
		}
		else
		{
			hh = interval * (outputs + 1);
			t = (hh - (interval * (inputs + 1))) / 2;
		}
		wires = new Vector<Wire>(1, 1);
		setImage(name);
		process();
		if (isClock())
		{
			clock_speed = 500;
			startClock();
			stopClock();
		}
	}
	
	public void setPath(String path)
	{
		fileName = "components" + OS.getSlash() + path + OS.getSlash() + name + ".jlg";
	}
	
	public void reprocessWires()
	{
		parent.reprocessWires();
	}
	
	public void process()
	{
		if (inputs > 0)
			output_v = GateScriptCompiler.execute(fileName, input_v);
		else
		{
			output_v = new boolean[outputs];
			output_v[0] = lastStatus;
		}
		if (isSwitch() && !_switch)
			output_v[0] = false;
		parent.repaint();
	}
	
	public boolean isDisplay()
	{
		return name.trim().toLowerCase().startsWith("display");
	}
	
	public boolean isGreenLed()
	{
		return name.trim().toLowerCase().startsWith("led-green");
	}
	
	public boolean isRedLed()
	{
		return name.trim().toLowerCase().startsWith("led-red");
	}
	
	public boolean isBlueLed()
	{
		return name.trim().toLowerCase().startsWith("led-blue");
	}
	
	public boolean isLed()
	{
		return isGreenLed() || isBlueLed() || isRedLed();
	}
	
	public boolean isInput()
	{
		return name.trim().toLowerCase().startsWith("input");
	}
	
	public boolean isClock()
	{
		return name.trim().toLowerCase().startsWith("clock");
	}
	
	public boolean isSwitch()
	{
		return name.trim().toLowerCase().startsWith("switch");
	}
	
	public boolean isButton()
	{
		return name.trim().toLowerCase().startsWith("button");
	}
	
	public void startClock()
	{
		if (clock_running)
		{
			stopClock();
			return;
		}
		if (!isClock() || parent == null)
			return;
		clock_running = true;
		clock = new Timer(clock_speed, new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				lastStatus = !lastStatus;
				process();
				reprocessWires();
			}
		});
		clock.start();
	}
	
	public void setHoverInput(int i)
	{
		if (i >= inputs)
			return;
		for (int j = 0; j < inputs; j++)
		{
			i_hover[j] = false;
		}
		if (i != -1)
			i_hover[i] = true;
		parent.repaint();
	}
	
	public void setHoverOutput(int o)
	{
		if (o >= outputs)
			return;
		for (int j = 0; j < outputs; j++)
		{
			o_hover[j] = false;
		}
		if (o != -1)
			o_hover[o] = true;
		parent.repaint();
	}
	
	public void setLinkOutput(int o)
	{
		if (o >= outputs)
			return;
		for (int j = 0; j < outputs; j++)
		{
			o_link[j] = false;
		}
		if (o != -1)
			o_link[o] = true;
		parent.repaint();
	}
	
	public void stopClock()
	{
		clock.stop();
		clock_running = false;
		output_v[0] = false;
		reprocessWires();
	}
	
	public void Switch()
	{
		_switch = !_switch;
		process();
		reprocessWires();
		parent.repaint();
	}
	
	public int isMouseOverInput(int mx, int my)
	{
		for (int i = 0; i < inputs; i++)
		{
			if (mx >= x_inputs[i] - (input_radius / 2) && mx <= x_inputs[i] + (input_radius / 2) && my >= y_inputs[i] - (input_radius / 2) && my <= y_inputs[i] + (input_radius / 2))
				return i;
		}
		return -1;
	}
	
	public int isMouseOverOutPut(int mx, int my)
	{
		for (int i = 0; i < outputs; i++)
		{
			if (mx >= x_outputs[i] - (input_radius / 2) && mx <= x_outputs[i] + (input_radius / 2) && my >= y_outputs[i] - (input_radius / 2) && my <= y_outputs[i] + (input_radius / 2))
				return i;
		}
		return -1;
	}
	
	public boolean isMouseOver(int mx, int my)
	{
		return super.isMouseOver(mx, my) || isMouseOverInput(mx, my) >= 0 || isMouseOverOutPut(mx, my) >= 0;
	}
	
	public void delete()
	{
		for (int i = 0; i < input_v.length; i++)
		{
			input_v[i] = false;
		}
		process();
		for (int i = 0; i < wires.size(); i++)
		{
			Wire w = wires.get(i);
			w.input = w.output = null;
		}
		del = true;
		parent.repaint();
	}
	
	public void paint(Graphics g)
	{
		int w = 0;
		FontMetrics fm = g.getFontMetrics(g.getFont());
		int fh = fm.getHeight();
		int fw = fm.stringWidth(name);
		w = 140;
		int spaces = 0;
		if (isDisplay())
		{
			img = null;
			int xx = relx(w - 110);
			int yy = rely(((interval * (inputs + 1)) - 130) / 2);
			g.setColor(Colors.displayBackgroundColor);
			g.fillRect(xx, yy, 100, 130);
			g.setColor(Colors.displayBorderColor);
			g.drawRect(xx, yy, 100, 130);
			g.setColor((input_v[0] ? Colors.onDisplayColor : Colors.offDisplayColor));
			g.fillRoundRect(xx + 30, yy + 10, 40, 10, 10, 10);
			
			
			g.setColor((input_v[6] ? Colors.onDisplayColor : Colors.offDisplayColor));
			g.fillRoundRect(xx + 30, yy + 60, 40, 10, 10, 10);
			
			
			g.setColor((input_v[3] ? Colors.onDisplayColor : Colors.offDisplayColor));
			g.fillRoundRect(xx + 30, yy + 110, 40, 10, 10, 10);
			
			
			g.setColor((input_v[5] ? Colors.onDisplayColor : Colors.offDisplayColor));
			g.fillRoundRect(xx + 20, yy + 20, 10, 40, 10, 10);
			
			
			g.setColor((input_v[1] ? Colors.onDisplayColor : Colors.offDisplayColor));
			g.fillRoundRect(xx + 70, yy + 20, 10, 40, 10, 10);
			
			
			g.setColor((input_v[4] ? Colors.onDisplayColor : Colors.offDisplayColor));
			g.fillRoundRect(xx + 20, yy + 70, 10, 40, 10, 10);
			
			
			g.setColor((input_v[2] ? Colors.onDisplayColor : Colors.offDisplayColor));
			g.fillRoundRect(xx + 70, yy + 70, 10, 40, 10, 10);
			
			
			g.setColor((input_v[7] ? Colors.onDisplayColor : Colors.offDisplayColor));
			g.fillRoundRect(xx + 80, yy + 110, 10, 10, 10, 10);
			
			
		}
		else if (isGreenLed())
		{
			img = null;
			w = 40;
			this.w = w;
			g.setColor((input_v[0] ? Color.green : new Color(0, 32, 0)));
			g.fillOval(relx(5), rely(5), 30, 30);
		}
		else if (isRedLed())
		{
			img = null;
			w = 40;
			this.w = w;
			g.setColor((input_v[0] ? Color.red : new Color(32, 0, 0)));
			g.fillOval(relx(5), rely(5), 30, 30);
		}
		else if (isBlueLed())
		{
			img = null;
			w = 40;
			this.w = w;
			g.setColor((input_v[0] ? Color.blue : new Color(0, 0, 32)));
			g.fillOval(relx(5), rely(5), 30, 30);
		}
		else if (isInput())
		{
			img = null;
			w = 40;
			this.w = w;
			Font f = g.getFont();
			Font nf = new Font("Arial", Font.BOLD, 18);
			g.setColor((output_v[0] ? Colors.oneColor : Colors.zeroColor));
			g.setFont(nf);
			int sw = fm.stringWidth((output_v[0] ? "ON" : "OFF"));
			g.drawString((output_v[0] ? "ON" : "OFF"), relx((w / 2) - (sw / 2) - 6), rely(27));
			g.setFont(f);
		}
		else if (isButton())
		{
			img = null;
			w = 40;
			this.w = w;
			g.setColor((output_v[0] ? Colors.oneColor : Colors.zeroColor));
			g.fillOval(relx(4), rely(4), this.w - 8, this.h - 8);
		}
		else if (isClock())
		{
			w = 40;
			this.w = w;
			g.drawImage(img, relx((w / 2) - (40 / 2)), rely(0), 40, 40, null);
		}
		else if (isSwitch())
		{
			w = 60;
			img = null;
			this.w = w;
			g.setColor(input_v[0] ? Colors.oneColor : Colors.zeroColor);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setStroke(new BasicStroke(2));
			g2d.drawLine(relx(0), rely(20), relx(20), rely(20));
			if (_switch)
			{
				g2d.drawLine(relx(20), rely(20), relx(40), rely(20));
			}
			else
			{
				g2d.drawLine(relx(20), rely(20), relx(40), rely(5));
				g2d.setColor(Colors.zeroColor);
			}
			g2d.drawLine(relx(40), rely(20), relx(60), rely(20));	
			g2d.setStroke(new BasicStroke(1));
		}
		this.w = w;
		this.h = hh;
		if (moreInputs)
		{
			for (int i = 1; i <= inputs; i++)
			{
				g.setColor((input_v[i - 1] ? Colors.oneColor : Colors.zeroColor));
				g.drawLine(x - input_length, rely((i + spaces) * interval), x, rely((i + spaces) * interval));
				g.fillOval(x - input_length - (input_radius / 2), rely((i + spaces) * interval - (input_radius / 2)), input_radius, input_radius);
				if (i_hover[i - 1])
				{
					g.setColor(Colors.hoverBorderColor);
					g.drawRect(x - input_length - (input_radius / 2), rely((i + spaces) * interval - (input_radius / 2)), input_radius, input_radius);
				}
				x_inputs[i - 1] = x - input_length;
				y_inputs[i - 1] = rely((i + spaces) * interval);
				String n = in_names[i - 1];
				g.setColor(Colors.textColor);
				g.drawString((n != null ? n : ""), x + 5, rely(((i + spaces) * interval) + (input_radius / 2)));
			}
			for (int i = 1; i <= outputs; i++)
			{
				g.setColor((output_v[i - 1] ? Colors.oneColor : Colors.zeroColor));
				g.drawLine(relx(w), rely((i * interval)) + t, relx(w + input_length), rely((i * interval) + t));
				g.fillOval(relx(w + input_length - (input_radius / 2)), rely(((i * interval) + t) - (input_radius / 2)), input_radius, input_radius);
				if (o_hover[i - 1] || o_link[i - 1])
				{
					g.setColor((o_link[i - 1] ? Colors.linkModeBorderColor : Colors.hoverBorderColor));
					g.drawRect(relx(w + input_length - (input_radius / 2)), rely(((i * interval) + t) - (input_radius / 2)), input_radius, input_radius);
				}
				x_outputs[i - 1] = relx(w + input_length);
				y_outputs[i - 1] = rely((i * interval) + t);
				String n = out_names[i - 1];
				int nl = (n != null ? fm.stringWidth(n) : 0);
				g.setColor(Colors.textColor);
				g.drawString((n != null ? n : ""), relx(w - nl - 5), rely((i * interval) + 5 + t));
			}
		}
		else
		{
			for (int i = 1; i <= inputs; i++)
			{
				g.setColor((input_v[i - 1] ? Colors.oneColor : Colors.zeroColor));
				g.drawLine(x - input_length, rely((i + spaces) * interval) + t, x, rely((i + spaces) * interval) + t);
				g.fillOval(x - input_length - (input_radius / 2), rely(((i + spaces) * interval) + t - (input_radius / 2)), input_radius, input_radius);
				if (i_hover[i - 1])
				{
					g.setColor(Colors.hoverBorderColor);
					g.drawRect(x - input_length - (input_radius / 2), rely(((i + spaces) * interval) + t - (input_radius / 2)), input_radius, input_radius);
				}
				x_inputs[i - 1] = x - input_length;
				y_inputs[i - 1] = rely((i + spaces) * interval) + t;
				String n = in_names[i - 1];
				g.setColor(Colors.textColor);
				g.drawString((n != null ? n : ""), x + 5, rely(((i + spaces) * interval) + (input_radius / 2)) + t);
			}
			for (int i = 1; i <= outputs; i++)
			{
				g.setColor((output_v[i - 1] ? Colors.oneColor : Colors.zeroColor));
				g.drawLine(relx(w), rely(i * interval), relx(w + input_length), rely(i * interval));
				g.fillOval(relx(w + input_length - (input_radius / 2)), rely((i * interval) - (input_radius / 2)), input_radius, input_radius);
				if (o_hover[i - 1] || o_link[i - 1])
				{
					g.setColor((o_link[i - 1] ? Colors.linkModeBorderColor : Colors.hoverBorderColor));
					g.drawRect(relx(w + input_length - (input_radius / 2)), rely((i * interval) - (input_radius / 2)), input_radius, input_radius);
				}
				x_outputs[i - 1] = relx(w + input_length);
				y_outputs[i - 1] = rely(i * interval);
				String n = out_names[i - 1];
				int nl = (n != null ? fm.stringWidth(n) : 0);
				g.setColor(Colors.textColor);
				g.drawString((n != null ? n : ""), relx(w - nl - 5), rely((i * interval) + 5));
			}
		}
		g.setColor(new Color(0, 255, 0, 15));
		g.fillRoundRect(x, y, w, h, 20, 20);
		g.setColor(color);
		g.drawRoundRect(x, y, w, h, 20, 20);
		createBorder(x, y, w, h);
		drawBorder(g);
		if (img != null && !isClock())
			g.drawImage(img, relx((w / 2) - (80 / 2)), rely((h / 2) - (40 / 2)), 80, 40, null);
		g.setColor(Colors.textColor);
		g.drawString(name, relx((w / 2) - (fw / 2)), rely(-fh / 2));
		if (isClock())
			label = std.str(clock_speed);
		if (!label.trim().equals("")) 
		{
			fw = fm.stringWidth(label);
			g.drawString(label, relx((w / 2) - (fw / 2)), rely(h + 15));
		}
	}

}
