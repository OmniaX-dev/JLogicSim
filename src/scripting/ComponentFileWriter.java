package scripting;

import global.Global;
import java.util.StringTokenizer;
import utilities.OS;
import utilities.TextFileStream;

public class ComponentFileWriter
{
	public static void createComponentFile(String fileName, String functions, int in_n, int out_n, String in_names, String out_names)
	{
		String script = "#inputs = " + in_n + ";\n";
		script += "#outputs = " + out_n + ";\n";
		script += "#input_names = (" + in_names + ");\n";
		script += "#output_names = (" + out_names + ");\n";
		
		StringTokenizer st = new StringTokenizer(functions, ";");
		while (st.hasMoreTokens())
		{
			String token = st.nextToken().trim().toUpperCase();
			script += token + ";\n";
		}
		
		TextFileStream file = new TextFileStream("components" + OS.getSlash() + "CUSTOM" + OS.getSlash() + fileName + ".jlg");
		if (file.exists())
			file.deleteFile();
		file.createFile();
		script = Global.TextToHex(script);
		file.addLine(script);
		file.apply();
	}
}
