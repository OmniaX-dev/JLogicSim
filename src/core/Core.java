package core;

import global.Booleans;
import global.Colors;
import gui.Gui;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import utilities.OS;
import utilities.std;


@SuppressWarnings("serial")
public class Core extends JPanel
{
	private FigureList figure;
	private FigureList wires;
	private MouseHandler mh;
	private Core instance;
	private LogicGate g1, g2;
	private Timer t;
	private Timer reprocess;
	private boolean rem, rem2;
	private int _mx;
	private int _my;
	private int _mx2;
	private int _my2;
	public boolean saved;
	public String filePath;
	private int count_rep;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private Cursor l_cur;
	private Cursor d_cur;
	
	private int box_sel_start_x;
	private int box_sel_start_y;
	private boolean box_sel;

	private int current_mx;
	private int current_my;
	private int box_sel_x;
	private int box_sel_y;
	private int box_sel_w;
	private int box_sel_h;
	
	private int grid_offset_x;
	private int grid_offset_y;
	
	public boolean editable;
	public Gui g;
	
	//HANDLERS
	private KeyboardHandler kh;
	
	//CONSTANTS
	private final int grid_size = 15;
	
	public Core()
	{
		t = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				garbageCollector();
			}
		});
		count_rep = 0;
		reprocess = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (count_rep == 100)
				{
					reprocess.stop();
					count_rep = 0;
					return;
				}
				count_rep += 1;
				for (int i = 0; i < wires.length(); i++)
				{
					Wire w = (Wire)wires.get(i);
					w.process();
				}
				for (int i = 0; i < figure.length(); i++)
				{
					LogicGate g = (LogicGate)figure.get(i);
					g.process();
				}
			}
		});
		instance = this;
		saved = false;
		editable = true;
		filePath = "";
		mh = new MouseHandler();
		kh = new KeyboardHandler();
		figure = new FigureList();
		wires = new FigureList();
		addMouseListener(mh);
		addKeyListener(kh);
		addMouseMotionListener(mh);
		addMouseWheelListener(mh);
		setBackground(Colors.backgroundColor);
		setFocusable(true);
		g1 = g2 = null;
		t.start();
		l_cur = toolkit.createCustomCursor(toolkit.getImage("img" + OS.getSlash() + "link_cursor.png"), new Point(15, 15), "link");
		d_cur = toolkit.createCustomCursor(toolkit.getImage("img" + OS.getSlash() + "del_cursor.png"), new Point(15, 15), "link");
		
		box_sel_start_x = box_sel_start_y = 0;
		box_sel = false;
		current_mx = current_my = 0;
		
		grid_offset_x = grid_offset_y = 0;
	}
	
	public void deleteGate()
	{
		if (!figure.isSomeSelected())
			return;
		LogicGate g = ((LogicGate)figure.getSelected());
		for (int i = 0; i < wires.length(); i++)
		{
			Wire w = (Wire)wires.get(i);
			if (!(w.output.equals(g)) && !(w.input.equals(g)))
				continue;
			if (w.output != null)
			{
				w.output.input_v[w.out_node] = false;
				w.output.process();
			}
			w.input = null;
			w.output = null;
			w.process();
		}
		g.delete();
		figure.removeSelected();
		garbageCollector();
		reprocessWires();
		repaint();
	}
	
	private void garbageCollector()
	{
		for (int i = 0; i < wires.length(); i++)
		{
			Figure f = wires.get(i);
			if (f instanceof Wire)
			{
				Wire w = ((Wire)f);
				if (w.input == null || w.output == null || w.output.del)
				{
					wires.remove(w);
					repaint();
				}
			}
		}
	}
	
	public void reprocessWires()
	{
		reprocess.start();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (Booleans.grid)
			drawGrid(g);
		figure.repaintAll(g);
		wires.repaintAll(g);
		
		if (box_sel)
		{
			int width = current_mx - box_sel_start_x;
			int height = current_my - box_sel_start_y;
			int start_x = box_sel_start_x;
			int start_y = box_sel_start_y;
			if (width < 0)
			{
				width *= -1;
				start_x = current_mx;
			}
			if (height < 0)
			{
				height *= -1;
				start_y = current_my;
			}
			box_sel_x = start_x;
			box_sel_y = start_y;
			box_sel_w = width;
			box_sel_h = height;
			Color tmp = g.getColor();
			g.setColor(Colors.boxSelectionColor);
			g.fillRect(start_x, start_y, width, height);
			g.setColor(Colors.boxSelectionBorderColor);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setStroke(new BasicStroke(2));
			g2d.drawRect(start_x, start_y, width, height);
			g2d.setStroke(new BasicStroke(1));
			g2d.setColor(tmp);
		}
		
		if (Booleans.debug && figure.isSomeSelected())
		{
			g.drawString(std.str(Figure.count), 10, 25);
			g.drawString(std.str(figure.getSelected().id), 10, 50);
			if (figure.getSelected() instanceof LogicGate)
				g.drawString(((LogicGate)figure.getSelected()).sid, 10, 75);
		}
	}
	
	private void drawDelete(Graphics g, int mx, int my, int mx2, int my2)
	{
		if (rem2)
		{
			Graphics2D g2d = (Graphics2D)g;
			g2d.setStroke(new BasicStroke(3));
			g2d.setColor(Colors.deleteColor);
			g2d.drawLine(mx, my, mx2, my2);
			g2d.setStroke(new BasicStroke(1));
		}
	}
	
	private void drawGrid(Graphics g)
	{
		g.setColor(Colors.gridColor);
		for (int i = 0; i <= getHeight(); i += grid_size)
		{
			g.drawLine(0 + grid_offset_x, i + grid_offset_y, getWidth(), i);
			if (i % (grid_size * 5) == 0)
				g.drawLine(0 + grid_offset_x, i + 1 + grid_offset_y, getWidth(), i + 1);
		}
		for (int i = 0; i <= getWidth(); i += grid_size)
		{
			g.drawLine(i + grid_offset_x, 0 + grid_offset_y, i, getHeight());
			if (i % (grid_size * 5) == 0)
				g.drawLine(i + 1 + grid_offset_x, 0 + grid_offset_y, i + 1, getHeight());
		}
		drawDelete(g, _mx, _my, _mx2, _my2);
	}
	
	private void Switch()
	{
		LogicGate g = ((LogicGate)figure.getSelected());
		g.lastStatus = !g.lastStatus;
		g.process();
		reprocessWires();
	}
	
	public void button()
	{
		LogicGate g = ((LogicGate)figure.getSelected());
		g.lastStatus = !g.lastStatus;
		g.process();
		reprocessWires();
	}
	
	public void addGate(LogicGate g)
	{
		figure.deselectAll();
		g.setSelected(true);
		figure.add(g);
		figure.bringOnTop(g);
	}
	
	public void save(String path)
	{
		figure.save(path, wires);
		filePath = path;
		saved = true;
	}
	
	public void load(String path)
	{
		figure.load(path, instance, wires);
		filePath = path;
		saved = true;
		repaint();
	}
	
	public void clear()
	{
		if (figure.length() > 0)
			save("saves" + OS.getSlash() + "session.jl");
		Figure.count = 0;
		figure.clear();
		wires.clear();
		filePath = "";
		saved = false;
		g1 = g2 = null;
		repaint();
	}
	
	private class MouseHandler extends MouseAdapter
	{
		private final int left = MouseEvent.BUTTON1;
		private final int right = MouseEvent.BUTTON3;
		private final int center = MouseEvent.BUTTON2;
		private int xx;
		private int yy;
		private boolean pan;
		private int speed = 500;
		private int on;
		private LogicGate tmpg;
		private int button;
		private boolean move;
		private int ou;
		private int inp;
		private LogicGate tmpl;
		private int mx;
		private int my;
		
		public void mousePressed(MouseEvent e)
		{
			if (!editable)
				return;
			mx = e.getX();
			my = e.getY();
			int b = e.getButton();
			int nclick = e.getClickCount();
			Figure orig = figure.getMouseOver(mx, my);
			BaseFigure f = (BaseFigure)orig;
			LogicGate ff = null;
			if (b == left)
				move = true;
			else
				move = false;
			if (f != null)
			{
				if (figure.isMultipleSelected() && !figure.selectionContains(orig))
					figure.deselectAll();
				if (b != center)
				{
					int o = -1, i = -1;
					if (b == left)
					{
						try
						{
							ff = (LogicGate)f;
							o = ff.isMouseOverOutPut(mx, my);
							i = ff.isMouseOverInput(mx, my);
						}
						catch (Exception e2)
						{
							o = i = -1;
							if (g1 != null)
								g1.setLinkOutput(-1);
							g1 = g2 = null;
						}
					}
					if (g1 == null && o >= 0 && b == left)
					{
						g1 = ff;
						g1.setLinkOutput(o);
						figure.deselectAll();
						g1.setSelected(true);
						figure.bringOnTop(g1);
						xx = mx - g1.getX();
						yy = my - g1.getY();
						on = o;
					}
					else if (g1 != null && g2 == null && i >= 0 && b == left)
					{
						g2 = ff;
						if (g1.equals(g2))
						{
							g1.setLinkOutput(-1);
							g1 = g2 = null;
							return;
						}
						try
						{
							for (int j = 0; j < wires.length(); j++)
							{
								Wire tmp = (Wire)wires.get(j);
								if (tmp.output != null && tmp.output.equals(g2) && i == tmp.out_node)
								{
									g1.setLinkOutput(-1);
									g1 = g2 = null;
									return;
								}
							}
						}
						catch (Exception e2)
						{
							e2.printStackTrace();
						}
						Wire w = new Wire(g1, g2, on, i);
						if (!wires.contains(w))
							wires.add(w);
						g1.setLinkOutput(-1);
						figure.deselectAll();
						g2.setSelected(true);
						figure.bringOnTop(g2);
						xx = mx - g2.getX();
						yy = my - g2.getY();
						g1 = g2 = null;
						reprocessWires();
						repaint();
					}
					else if (figure.isMultipleSelected() && button == left)
					{
					}
					else if (b == right)
					{
						if (g1 != null)
							g1.setLinkOutput(-1);
						g1 = g2 = null;
						figure.deselectAll();
						f.setSelected(true);
						figure.bringOnTop(f);
						xx = mx - f.getX();
						yy = my - f.getY();
						if (f instanceof LogicGate)
						{
							LogicGate gate = (LogicGate)f;
							JPopupMenu gateMenu = new JPopupMenu();
							JMenuItem delete = new JMenuItem("Delete");
							delete.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e)
								{
									deleteGate();
								}
							});
							ou = ((LogicGate) f).isMouseOverOutPut(mx, my);
							inp = ((LogicGate) f).isMouseOverInput(mx, my);
							tmpl = (LogicGate) f;
							if (ou > -1)
							{
								JMenuItem deleteLink = new JMenuItem("Delete link");
								deleteLink.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e)
									{
										for (int i = 0; i < wires.length(); i++)
										{
											Wire w = (Wire)wires.get(i);
											if (w.input.equals(tmpl) && w.in_node == ou)
											{
												if (w.output != null)
												{
													w.output.input_v[w.out_node] = false;
													w.output.process();
												}
												w.input = null;
												w.output = null;
											}
											w.process();
										}
									}
								});
								gateMenu.add(deleteLink);
							}
							else if (inp > -1)
							{
								JMenuItem deleteLink = new JMenuItem("Delete link");
								deleteLink.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e)
									{
										for (int i = 0; i < wires.length(); i++)
										{
											Wire w = (Wire)wires.get(i);
											if (w.output.equals(tmpl) && w.out_node == inp)
											{
												if (w.output != null)
												{
													w.output.input_v[w.out_node] = false;
													w.output.process();
												}
												w.input = null;
												w.output = null;
											}
											w.process();
										}
									}
								});
								gateMenu.add(deleteLink);
							}
							gateMenu.add(delete);
							if (gate.isClock())
							{
								JMenuItem start = new JMenuItem("Start/Stop");
								start.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e)
									{
										((LogicGate)figure.getSelected()).startClock();
									}
								});
								gateMenu.add(start);
							}
							else if (gate.isInput())
							{
								JMenuItem status = new JMenuItem("Change status");
								status.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e)
									{
										Switch();
									}
								});
								gateMenu.add(status);
							}
							else if (gate.isSwitch())
							{
								JMenuItem sw = new JMenuItem("Switch");
								sw.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e)
									{
										((LogicGate)figure.getSelected()).Switch();
									}
								});
								gateMenu.add(sw);
							}
							
							gateMenu.show(e.getComponent(), mx, my);
						}
					}
					else
					{
						if (g1 != null)
							g1.setLinkOutput(-1);
						g1 = g2 = null;
						figure.deselectAll();
						f.setSelected(true);
						figure.bringOnTop(f);
						xx = mx - f.getX();
						yy = my - f.getY();
						if (figure.getSelected() != null && ((LogicGate)(figure.getSelected())).isButton())
						{
							button();
						}
						if (nclick == 2)
						{
							if (figure.getSelected() != null && ((LogicGate)(figure.getSelected())).isInput())
							{
								Switch();
							}
							else if (figure.getSelected() != null && ((LogicGate)(figure.getSelected())).isClock())
							{
								((LogicGate)figure.getSelected()).startClock();
							}
							else if (figure.getSelected() != null && ((LogicGate)(figure.getSelected())).isSwitch())
							{
								((LogicGate)figure.getSelected()).Switch();
							}
						}
					}
				}
			}
			else if (b != center)
			{
				if (g1 != null)
					g1.setLinkOutput(-1);
				g1 = g2 = null;
				figure.deselectAll();
				if (b == right)
				{
					box_sel_start_x = e.getX();
					box_sel_start_y = e.getY();
					current_mx = e.getX();
					current_my = e.getY();
					box_sel = true;
				}
				else if (b == left && Booleans.cut)
				{
					button = b;
					rem = true;
					_mx = mx;
					_my = my;
				}
			}
			if (b == center)
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				pan = true;
			}
			repaint();
		}
		
		public void mouseMoved(MouseEvent e)
		{
			if (!editable)
				return;
			int mx = e.getX(), my = e.getY();
			BaseFigure f = (BaseFigure)figure.getMouseOver(mx, my);
			if (f == null || !(f instanceof LogicGate))
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if (tmpg != null)
				{
					tmpg.setHoverInput(-1);
					tmpg.setHoverOutput(-1);
					tmpg = null;
				}
				return;
			}
			LogicGate g = (LogicGate)f;
			tmpg = g;
			int i = g.isMouseOverInput(mx, my);
			int o = g.isMouseOverOutPut(mx, my);
			if (i >= 0)
			{
				g.setHoverOutput(-1);
				g.setHoverInput(i);
				setCursor(l_cur);
			}
			else if (o >= 0)
			{
				g.setHoverInput(-1);
				g.setHoverOutput(o);
				setCursor(l_cur);
			}
			else
			{
				g.setHoverInput(-1);
				g.setHoverOutput(-1);
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
		
		public void mouseReleased(MouseEvent e)
		{
			if (!editable)
				return;
			if (e.getButton() == left)
			{
				int mx2 = mx, my2 = e.getY();
				if (my2 < my)
				{
					int tmp = my2;
					my2 = my;
					my = tmp;
				}
				Figure ff = figure.getSelected();
				if (ff != null && ff instanceof LogicGate)
				{
					LogicGate gg = (LogicGate)ff;
					if (gg.isButton())
						button();
				}
				if (rem)
				{
					for (int i = 0; i < wires.length(); i++)
					{
						Figure f = wires.get(i);
						if (f instanceof Wire)
						{
							Wire w = ((Wire)f);
							if (w.intersects(mx, my, mx2, my2))
							{
								if (w.output != null)
								{
									w.output.input_v[w.out_node] = false;
									w.output.process();
								}
								w.input = null;
								w.output = null;
							}
						}
					}
					rem = rem2 = false;
					reprocessWires();
				}
			}
			else if (e.getButton() == center)
			{
				pan = false;
			}
			else if (e.getButton() == right)
			{
				Rectangle box_sel_r = new Rectangle(box_sel_x, box_sel_y, box_sel_w, box_sel_h);
				Rectangle tmp = null;
				BaseFigure ff = null;
				for (int k = 0; k < figure.length(); k++)
				{
					ff = (BaseFigure)figure.get(k);
					tmp = ff.getRectangle();
					if (box_sel_r.contains(tmp) || box_sel_r.intersects(tmp))
						ff.setSelected(true);
				}
				figure.isMultipleSelected();
				box_sel = false;
			}
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			repaint();
		}
		
		public void mouseDragged(MouseEvent e)
		{
			if (!editable)
				return;
			if (rem && button == left)
			{
				rem2 = true;
				_mx2 = e.getX();
				_my2 = e.getY();
				setCursor(d_cur);
				for (int i = 0; i < wires.length(); i++)
				{
					Figure f = wires.get(i);
					if (f instanceof Wire)
					{
						Wire w = ((Wire)f);
						if (w.intersects(mx, my, _mx2, _my2))
							w.setDeleting(true);
						else
							w.setDeleting(false);
					}
				}
			}
			else if (!pan && figure.isMultipleSelected() && move)
			{
				Vector<Figure> selected = figure.getMultipleSelection();
				int xxx = 0, yyy = 0;
				for (Figure f : selected)
				{
					xxx = e.getX() - mx - f.getX();
					yyy = e.getY() - my - f.getY();	
					f.setX(xxx);
					f.setY(yyy);
				}
				//System.out.println(xxx + "," + yyy);
				setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			}
			else if (!pan && figure.isSomeSelected() && move)
			{
				BaseFigure f = (BaseFigure)figure.getSelected();
				int xxx = e.getX() - xx;
				int yyy = e.getY() - yy;			
				f.setX(xxx);
				f.setY(yyy);
				setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			}
			else if (pan)
			{
				for (int i = 0; i < figure.length(); i++)
				{
					Figure f = figure.get(i);
					f.setX(f.getX() + e.getX() - mx);
					f.setY(f.getY() + e.getY() - my);
				}
				mx = e.getX();
				my = e.getY();
				grid_offset_x = e.getX() - mx;
				grid_offset_y = e.getY() - my;
			}
			else if (box_sel)
			{
				current_mx = e.getX();
				current_my = e.getY();
			}
			repaint();
		}
		
		public void mouseWheelMoved(MouseWheelEvent e)
		{
			if (!editable)
				return;
			Figure f = figure.getMouseOver(e.getX(), e.getY());
			if (f == null)
				return;
			LogicGate g = (LogicGate)f;
			if (!g.isClock())
				return;
			if (e.getWheelRotation() > 0)
			{
				if (speed < 10000)
					speed += 25;
			}
			else
			{
				if (speed > 25)
					speed -= 25;
			}
			g.stopClock();
			g.clock_speed = speed;
			repaint();
		}
	}
	
	private class KeyboardHandler extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			if (!editable)
				return;
			int k = e.getKeyCode();
			if (k == KeyEvent.VK_DELETE)
			{
				if (!(figure.getSelected() instanceof LogicGate))
					return;
				deleteGate();
			}
			else if (k == KeyEvent.VK_ENTER)
			{
				if (figure.getSelected() != null && ((LogicGate)(figure.getSelected())).isInput())
				{
					Switch();
				}
				else if (figure.getSelected() != null && ((LogicGate)(figure.getSelected())).isClock())
				{
					((LogicGate)figure.getSelected()).startClock();
				}
				else if (figure.getSelected() != null && ((LogicGate)(figure.getSelected())).isSwitch())
				{
					((LogicGate)figure.getSelected()).Switch();
				}
			}
			repaint();
		}
		
		public void keyReleased(KeyEvent e)
		{
			
		}
		
		public void keyTyped(KeyEvent e)
		{
			
		}
	}
}
