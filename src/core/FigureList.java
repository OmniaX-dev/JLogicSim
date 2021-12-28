package core;

import global.Global;
import java.awt.Graphics;
import java.util.StringTokenizer;
import java.util.Vector;

import utilities.TextFileStream;
import utilities.std;

public class FigureList
{
	private Vector<Figure> figure;
	private boolean multiple_selection;

	public FigureList()
	{
		figure = new Vector<Figure>(1, 1);
		multiple_selection = false;
	}

	public void add(Figure f)
	{
		figure.add(f);
	}

	public void clear()
	{
		figure.clear();
	}

	public void remove(Figure f)
	{
		int i = figure.indexOf(f);
		if (i > -1)
		{
			remove(i);
		}
	}

	public void remove(int i)
	{
		try
		{
			if (i >= 0 && i < figure.size())
			{
				int tmp = figure.get(i).id;
				figure.set(i, null);
				figure.remove(i);
				Figure.remove();
				for (Figure f : figure)
					if (f.id > tmp)
						f.id--;
			}

		}
		catch (Exception e){}
	}

	public void removeSelected()
	{
		remove(getSelected());
	}

	public void set(int i, Figure f)
	{
		if (i >= 0 && i < figure.size())
			figure.set(i, f);
	}

	public Figure get(int i)
	{
		return (i >= 0 && i < figure.size() ? figure.get(i) : null);
	}

	public boolean contains(Figure f)
	{
		for (Figure ff : figure)
			if (ff.equals(f))
				return true;
		return false;
	}

	public boolean isSomeSelected()
	{
		for (Figure f : figure)
		{
			if (f.isSelected())
				return true;
		}
		return false;
	}
	
	public boolean isMultipleSelected()
	{
		int count = 0;
		for (Figure f : figure)
		{
			count += (f.isSelected() ? 1 : 0);
			if (count > 1)
			{
				multiple_selection = true;
				return true;
			}
		}
		multiple_selection = false;
		return false;
	}

	public Figure getMouseOver(int mx, int my)
	{
		for (Figure f : figure)
		{
			if (f instanceof LogicGate)
			{
				if (((LogicGate)f).isMouseOver(mx, my))
					return f;
			}
			else
			{
				if (((BaseFigure)f).isMouseOver(mx, my))
					return f;
			}
		}
		return null;
	}

	public void deselectAll()
	{
		for (Figure f : figure)
		{
			f.setSelected(false);
		}
	}

	public void bringOnTop(Figure fig)
	{
		for (Figure f : figure)
		{
			if (f.isOntop())
			{
				f.setOntop(false);
				break;
			}
		}
		int i = figure.indexOf(fig);
		figure.add(0, figure.get(i));
		figure.remove(i + 1);
	}

	public void repaintAll(Graphics g)
	{
		for (int i = figure.size() - 1; i >= 0 ; i--)
		{
			figure.get(i).paint(g);
		}
	}

	public int length()
	{
		return figure.size();
	}

	public Figure getSelected()
	{
		if (isSomeSelected())
		{
			for (Figure f : figure)
			{
				if (f.isSelected())
					return f;
			}
		}
		return null;
	}
	
	public Vector<Figure> getMultipleSelection()
	{
		if (!isMultipleSelected())
			return null;
		Vector<Figure> selection = new Vector<Figure>();
		for (Figure f : figure)
		{
			if (f.isSelected())
				selection.add(f);
		}
		return selection;
	}
	
	public boolean selectionContains(Figure f)
	{
		for (Figure ff : getMultipleSelection())
		{
			if (ff.getID() == f.getID())
				return true;
		}
		return false;
	}

	public void save(String fname, FigureList wires)
	{
		String file = "";
		if (length() <= 0)
		{
			file = "empty";
			file = Global.TextToHex(file);
			TextFileStream f = new TextFileStream(fname);
			if (f.exists())
				f.deleteFile();
			f.createFile();
			f.addLine(file);
			f.apply();
			return;
		}
		for (Figure f : figure)
		{
			if (f instanceof LogicGate)
			{
				LogicGate l = (LogicGate)f;
				file += l.name + "%" + l.basePath + "%" + l.getX() + "%" + l.getY() +"%" + l.sid;
				if (l.isClock())
				{
					file += "%1%";
					file += l.clock_speed;
				}
				else if (l.isInput())
				{
					file += "%2%";
					file += (l.lastStatus ? "1" : "0");
				}
				else if (l.isSwitch())
				{
					file += "%3%";
					file += (l._switch ? "1" : "0");
				}
				else
				{
					file += "%0";
				}
				file += "&";
			}
		}
		file += "@@$";
		for (Figure f : wires.figure)
		{
			if (f instanceof Wire)
			{
				Wire w = (Wire)f;
				if (w.input != null && w.output != null)
					file += w.input.sid + "$" + w.output.sid + "$" + w.in_node + "$" + w.out_node + "£";
			}
		}
		file = Global.TextToHex(file);
		TextFileStream f = new TextFileStream(fname);
		if (f.exists())
			f.deleteFile();
		f.createFile();
		f.addLine(file);
		f.apply();
	}
	
	public LogicGate findBySid(String sid)
	{
		for (Figure f : figure)
			if (f instanceof LogicGate)
				if (((LogicGate) f).sid.equals(sid))
					return (LogicGate)f;
		return null;
	}

	public void load(String fname, Core c, FigureList wires)
	{
		clear();
		wires.clear();
		TextFileStream f = new TextFileStream(fname);
		String file = f.getLines();
		file = Global.HexToText(file);
		if (file.equals("empty"))
		{
			return;
		}
		StringTokenizer st = new StringTokenizer(file, "&");
		while (st.hasMoreTokens())
		{
			try
			{
				String token = st.nextToken();
				if (!token.startsWith("@@$"))
				{
					StringTokenizer st2 = new StringTokenizer(token, "%");
					String name = st2.nextToken();
					String basePath = st2.nextToken();
					int x = std.toint(st2.nextToken());
					int y = std.toint(st2.nextToken());
					String sid = st2.nextToken();
					String type = st2.nextToken();
					LogicGate g = new LogicGate(x, y, name, basePath, c);
					if (type.equals("1"))
					{
						int speed = std.toint(st2.nextToken());
						g.clock_speed = speed;
					}
					else if (type.equals("2"))
					{
						boolean status = (st2.nextToken().equals("1") ? true : false);
						g.lastStatus = status;
						g.process();
					}
					else if (type.equals("3"))
					{
						boolean status = (st2.nextToken().equals("1") ? true : false);
						g._switch = status;
					}
					g.sid = sid;
					add(g);
				}
				else
				{
					StringTokenizer st2 = new StringTokenizer(token, "£");
					while (st2.hasMoreTokens())
					{
						String t = st2.nextToken();
						StringTokenizer st3 = new StringTokenizer(t, "$");
						if (t.startsWith("@@$"))
							st3.nextToken();
						String i_sid = st3.nextToken();
						String o_sid = st3.nextToken();
						int i_n = std.toint(st3.nextToken());
						int o_n = std.toint(st3.nextToken());
						LogicGate in = findBySid(i_sid);
						LogicGate out = findBySid(o_sid);
						Wire w = new Wire(in, out, i_n, o_n);
						wires.add(w);
					}
				}
			}
			catch (Exception e){}
		}
	}
}
