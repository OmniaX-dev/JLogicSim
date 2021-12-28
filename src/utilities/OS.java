package utilities;

public class OS 
{
	public static final String OS_NAME = System.getProperty("os.name");
	public static final String OS_ARCH = System.getProperty("os.arch");
	public static final String OS_VERSION = System.getProperty("os.version");
	public static final String USER_NAME = System.getProperty("user.name");
	
	
	public static String getSlash()
	{
		if (System.getProperty("os.name").equalsIgnoreCase("Linux"))
		{
			return "/";
		}
		else if (System.getProperty("os.name").indexOf("indows") > 0)
		{
			return "\\";
		}
		return null;
	}
}
