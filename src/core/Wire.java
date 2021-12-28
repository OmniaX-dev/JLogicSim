package core;

import global.Colors;

import java.awt.Graphics;

public class Wire extends BaseFigure
{
	public LogicGate input;
	public LogicGate output;
	public int in_node;
	public int out_node;
	private int x2;
	private int y2;
	private boolean status;
	private boolean deleting;

	public Wire(LogicGate in, LogicGate out, int in_n, int out_n)
	{
		super(0, 0, 0, 0);
		input = in;
		output = out;
		in_node = in_n;
		out_node = out_n;
		input.wires.add(this);s
		output.input_occu[out_node] = true;
		deleting = false;
		process();
	}
	
	public boolean isDeleting()
	{
		return deleting;
	}
	
	public void setDeleting(boolean del)
	{
		deleting = del;
	}
	
	public void process()
	{
		if (input == null || output == null)
			return;
		status = input.output_v[in_node];
		output.input_v[out_node] = status;
		output.process();
	}
	
	public boolean intersects(int x3, int y3, int x4, int y4)
	{
		return (((x3 < (x + (Math.abs(x2 - x) / 2))) && (y3 < y) && (y4 > y) && (x3 >= x) && (x3 <= x2)) || ((x3 >= (x + (Math.abs(x2 - x) / 2))) && (y3 < y2) && (y4 > y2) && (x3 >= x) && (x3 <= x2)));
	}
	
	public void paint(Graphics g)
	{
		if (input == null || output == null)
			return;
		x = input.x_outputs[in_node];
		y = input.y_outputs[in_node];
		x2 = output.x_inputs[out_node];
		y2 = output.y_inputs[out_node];
		
		drawLink(x, y, x2, y2, g, (isDeleting() ? Colors.wireDeletingColor : (status ? Colors.oneColor : Colors.zeroColor)));
	}

}
