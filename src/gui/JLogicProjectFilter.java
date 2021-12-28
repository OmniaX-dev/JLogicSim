package gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class JLogicProjectFilter extends FileFilter
{
	public boolean accept(File f)
	{
		if (f.isDirectory())
			return false;
		String s = f.getName();
		return s.toLowerCase().endsWith(".jl");
	}
	
	public String getDescription()
	{
		return "JLogicSim project file (*.jl)";
	}
}
