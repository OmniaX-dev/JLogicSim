package global;

import utilities.TextFileStream;
import utilities.std;

public class AppInfo
{
	public static final String fullName = "JLogicSim 0.9 - Alpha 5";
	public static final String name = "JLogicSim";
	public static final String version = "0.9.0";
	public static final String status = "Preview";
	public static final String fullVersion = "0.9.0-alpha.5";
	
	public static final String developer = "Syl4r93";
	public static final String email = "syl4r39@gmail.com";
	
	private static int build_n = 0;
	public static String build = "";
	
	public static void loadBuildNumber()
	{
		TextFileStream file = new TextFileStream("build");
		if (file.exists() && build_n == 0)
		{
			build_n = std.toint(file.getLine(0).trim()) + 1;
			file.clear();
			file.addLine(std.str(build_n));
			file.apply();
			build = std.str(build_n);
		}
		else
			build = std.str(build_n);
	}
}
