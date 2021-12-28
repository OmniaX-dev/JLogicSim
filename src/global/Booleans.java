package global;

public class Booleans
{
	public static boolean debug;
	public static boolean grid;
	public static boolean cut;
	
	public static boolean settings = false;
	
	public static void load()
	{
		debug = Global.sets.getBoolean("debug");
		grid = Global.sets.getBoolean("grid");
		cut = Global.sets.getBoolean("cut");
	}
}
