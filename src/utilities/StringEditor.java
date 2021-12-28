package utilities;

import java.util.StringTokenizer;

public class StringEditor
{
	public static String removeNewLine(String s)
	{
		return s.replaceAll("\\r\\n|\\r|\\n", " ");
	}
	
	public static boolean arrayContains(String[] array, String s)
	{
		for (String str : array)
			if (str.equals(s))
				return true;
		return false;
	}
	
	public static String replaceAll(String src, String what, String with)
	{
		String replaced = src;
		while (replaced != null && replaced.contains(what))
		{
			replaced = replaced.replace(what, with);
		}
		return replaced;
	}
	
	public static String[] stringTokenizerToArray(StringTokenizer st, boolean trim)
	{
		int conto = st.countTokens();
		String[] tokens = new String[conto];
		int i = 0;
		while(st.hasMoreTokens())
		{
			tokens[i] = st.nextToken();
			if (trim)
				tokens[i] = tokens[i].trim();
			i++;
		}
		return tokens;
	}

	public static String getBetween(String src, char c1, char c2)
	{
		String bet = src;
		int i = 0;
		for (char c : bet.toCharArray())
		{
			if (c == c1)
			{
				bet = bet.substring(i + 1);
				break;
			}
			i++;
		}
		for (i = bet.length() - 1; i > 0; i--)
		{
			if (bet.charAt(i) == c2)
			{
				bet = bet.substring(0, i);
				break;
			}
		}
		return bet.trim();
	}
	
	public static String getBetweenFirst(String src, char c1, char c2)
	{
		String bet = src;
		int i = 0;
		for (char c : bet.toCharArray())
		{
			if (c == c1)
			{
				bet = bet.substring(i + 1);
				break;
			}
			i++;
		}
		for (i = 0; i < bet.length(); i++)
		{
			if (bet.charAt(i) == c2)
			{
				bet = bet.substring(0, i);
				break;
			}
		}
		return bet.trim();
	}

	public static String deleteBetween(String src, char c1, char c2)
	{
		String bet = "";
		int i = 0;
		for (char c : src.toCharArray())
		{
			if (c == c1)
			{
				break;
			}
			bet += c;
			i++;
		}
		for (i = src.length() - 1; i > 0; i--)
		{
			if (src.charAt(i) == c2)
			{
				break;
			}
		}
		bet += src.substring(i + 1);
		return bet.trim();
	}
	
	public static String deleteBetweenFirst(String src, char c1, char c2)
	{
		String bet = "";
		int i = 0;
		for (char c : src.toCharArray())
		{
			if (c == c1)
			{
				break;
			}
			bet += c;
			i++;
		}
		for (i = 0; i < src.length(); i++)
		{
			if (src.charAt(i) == c2)
			{
				break;
			}
		}
		for (int j = i + 1; j < src.length(); j++)
			bet += src.charAt(j);
		return bet.trim();
	}

}
