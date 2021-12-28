package utilities;

public class Conversions
{
    public static String StringReverse(String src)
    {
        String rev = "";
        int i = src.length() - 1;
        while (i >= 0)
        {
            rev += src.charAt(i);
            i -= 1;
        }
        return rev;
    }

    public static int Potenza(int Base, int esponente)
    {
        if (esponente > 0)
        {
            return Base * Potenza(Base, esponente - 1);
        }
        else
        {
            return 1;
        }
    }

    public static String chrToStr(char c)
    {
    	String tmp = "";
    	tmp += c;
    	return tmp;
    }
    
    public static int BinToDec(String bin)
    {
        int n = 0, f = 0, i = bin.length() - 1;
        for (char c : bin.toCharArray())
        {
            n = Integer.parseInt(chrToStr(c));
            f += n * Potenza(2, i);
            i -= 1;
        }
        return f;
    }

    public static String DecToBin(int dec)
    {
        int q = dec, r = 0;
        String res = "";
        while (q > 0)
        {
            r = q % 2;
            q /= 2;
            res += Integer.toString(r);
        }
        return StringReverse(res);
    }

    public static String HexToBin(String hex)
    {
        String sf = "";
        for (char c : hex.toCharArray())
        {
            if (c == '0')
            {
                sf += "0000";
            }
            else if (c == '1')
            {
                sf += "0001";
            }
            else if (c == '2')
            {
                sf += "0010";
            }
            else if (c == '3')
            {
                sf += "0011";
            }
            else if (c == '4')
            {
                sf += "0100";
            }
            else if (c == '5')
            {
                sf += "0101";
            }
            else if (c == '6')
            {
                sf += "0110";
            }
            else if (c == '7')
            {
                sf += "0111";
            }
            else if (c == '8')
            {
                sf += "1000";
            }
            else if (c == '9')
            {
                sf += "1001";
            }
            else if (c == 'a' || c == 'A')
            {
                sf += "1010";
            }
            else if (c == 'b' || c == 'B')
            {
                sf += "1011";
            }
            else if (c == 'c' || c == 'C')
            {
                sf += "1100";
            }
            else if (c == 'd' || c == 'D')
            {
                sf += "1101";
            }
            else if (c == 'e' || c == 'E')
            {
                sf += "1110";
            }
            else if (c == 'f' || c == 'F')
            {
                sf += "1111";
            }
        }
        return sf;
    }

    public static String BinToHex(String bin)
    {
        String sf = "", b = "";
        int e = 0, i = 0;
        while (e != 1)
        {
            if (bin.length() > 3)
            {
                b = bin.substring(bin.length() - 4);
                bin = bin.substring(0, bin.length() - 4);
            }
            if (b.equals("0000"))
            {
                sf += "0";
            }
            else if (b.equals("0001"))
            {
                sf += "1";
            }
            else if (b.equals("0010"))
            {
                sf += "2";
            }
            else if (b.equals("0011"))
            {
                sf += "3";
            }
            else if (b.equals("0100"))
            {
                sf += "4";
            }
            else if (b.equals("0101"))
            {
                sf += "5";
            }
            else if (b.equals("0110"))
            {
                sf += "6";
            }
            else if (b.equals("0111"))
            {
                sf += "7";
            }
            else if (b.equals("1000"))
            {
                sf += "8";
            }
            else if (b.equals("1001"))
            {
                sf += "9";
            }
            else if (b.equals("1010"))
            {
                sf += "A";
            }
            else if (b.equals("1011"))
            {
                sf += "B";
            }
            else if (b.equals("1100"))
            {
                sf += "C";
            }
            else if (b.equals("1101"))
            {
                sf += "D";
            }
            else if (b.equals("1110"))
            {
                sf += "E";
            }
            else if (b.equals("1111"))
            {
                sf += "F";
            }
            if (bin.length() < 4 && bin.length() > 1)
            {
                bin = StringReverse(bin);
                i = 0;
                while (i <= (4 - bin.length()) + 1)
                {
                    bin += "0";
                    i += 1;
                }
                bin = StringReverse(bin);
                b = bin;
                bin = "";
            }
            else if (bin.length() <= 0)
            {
                e = 1;
            }
        }
        return StringReverse(sf);
    }

    public static int HexToDec(String hex)
    {
        int n = 0, f = 0, i = hex.length() - 1;
        for (char c : hex.toCharArray())
        {
            n = dec_tab(c);
            f += n * Potenza(16, i);
            i -= 1;
        }
        return f;
    }

    public static String DecToHex(int dec)
    {
        int q = dec, r = 0;
        String res = "";
        while (q > 0)
        {
            r = q % 16;
            q /= 16;
            res += hex_tab(r);
        }
        if (res.length() % 2 != 0)
        	res += "0";
        return StringReverse(res);
    }

    private static char hex_tab(int n)
    {
        if (n > 15)
        {
            return 'Z';
        }
        else if (n == 10)
        {
            return 'A';
        }
        else if (n == 11)
        {
            return 'B';
        }
        else if (n == 12)
        {
            return 'C';
        }
        else if (n == 13)
        {
            return 'D';
        }
        else if (n == 14)
        {
            return 'E';
        }
        else if (n == 15)
        {
            return 'F';
        }
        else
        {
            return (char)(n + 48);
        }
    }

    private static int dec_tab(char n)
    {
        n = Upper(n);
        if (n > 'F')
        {
            return -1;
        }
        else if (n == 'A')
        {
            return 10;
        }
        else if (n == 'B')
        {
            return 11;
        }
        else if (n == 'C')
        {
            return 12;
        }
        else if (n == 'D')
        {
            return 13;
        }
        else if (n == 'E')
        {
            return 14;
        }
        else if (n == 'F')
        {
            return 15;
        }
        else
        {
            return Integer.parseInt(chrToStr(n));
        }
    }

    private static char Upper(char c)
    {
        if (c > 96 && c < 123)
        {
            return (char)((int)c - 32);
        }
        else
        {
            return c;
        }
    }

    public static String OctToBin(String oct)
    {
        String sf = "";
        for (char c : oct.toCharArray())
        {
            if (c == '0')
            {
                sf += "000";
            }
            else if (c == '1')
            {
                sf += "001";
            }
            else if (c == '2')
            {
                sf += "010";
            }
            else if (c == '3')
            {
                sf += "011";
            }
            else if (c == '4')
            {
                sf += "100";
            }
            else if (c == '5')
            {
                sf += "101";
            }
            else if (c == '6')
            {
                sf += "110";
            }
            else if (c == '7')
            {
                sf += "111";
            }
        }
        return sf;
    }

    public static String BinToOct(String bin)
    {
        String sf = "", b = "";
        int e = 0, i = 0;
        while (e != 1)
        {
            if (bin.length() > 2)
            {
                b = bin.substring(bin.length() - 3);
                bin = bin.substring(0, bin.length() - 3);
            }
            if (b.equals("000"))
            {
                sf += "0";
            }
            else if (b.equals("001"))
            {
                sf += "1";
            }
            else if (b.equals("010"))
            {
                sf += "2";
            }
            else if (b.equals("011"))
            {
                sf += "3";
            }
            else if (b.equals("100"))
            {
                sf += "4";
            }
            else if (b.equals("101"))
            {
                sf += "5";
            }
            else if (b.equals("110"))
            {
                sf += "6";
            }
            else if (b.equals("111"))
            {
                sf += "7";
            }
            if (bin.length() < 3 && bin.length() > 0)
            {
                bin = StringReverse(bin);
                i = 0;
                while (i <= (3 - bin.length()) + 1)
                {
                    bin += "0";
                    i += 1;
                }
                bin = StringReverse(bin);
                b = bin;
                bin = "";
            }
            else if (bin.length() <= 0)
            {
                e = 1;
            }
        }
        return StringReverse(sf);
    }

    public static int OctToDec(String oct)
    {
        int n = 0, f = 0, i = oct.length() - 1;
        for (char c : oct.toCharArray())
        {
            n = Integer.parseInt(chrToStr(c));
            f += n * Potenza(8, i);
            i -= 1;
        }
        return f;
    }

    public static String DecToOct(int dec)
    {
        int q = dec, r = 0;
        String res = "";
        while (q > 0)
        {
            r = q % 8;
            q /= 8;
            res += Integer.toString(r);
        }
        return StringReverse(res);
    }

    public static String HexToOct(String hex)
    {
        return DecToOct(HexToDec(hex));
    }

    public static String OctToHex(String oct)
    {
        return DecToHex(OctToDec(oct));
    }
}