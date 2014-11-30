import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.HashMap;

public class Dictionnaire
{
	
	
	public static HashMap createHashmap(String langue)
	{
		
		String filename = null;
		int size = 1;
		
			if (langue.equals("FR"))
			{
				filename = "dict_FR.txt";
				size = 22740;
			}
		
			else if (langue.equals("EN"))
			{
				filename = "dict_EN.txt";
				size = 58110;
			}
		
		HashMap hmap = new HashMap(size);
		BufferedReader buff = null;
		
		try
		{
			String line;
			buff = new BufferedReader(new FileReader(filename));
			
			while ((line = buff.readLine() ) != null)
			{
				hmap.put(line.toLowerCase(), line);
			}
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return hmap;
		
	}
	
	public static boolean isAWord(String s, HashMap hmap)
	{
		return (hmap.containsKey(s));
	}
	
	public static boolean isLongerWord(String s, String letter, HashMap hmap)
	{
		String word = s + letter;
		return isAWord(word, hmap);
	}
	
	public static String decoupe(String s, HashMap hmap)
	{
		
			String res = "";
			int start  = 0;
			int end    = 1;
			
			while (end < s.length())
			{
				if (end == (s.length() - 1))// on ne pourra pas faire de decoupage supplementaire, on est à la fin
				{
					res += s.substring(start);
					break;
				}
				
				String sub = s.substring(start, end);

				if (isAWord(sub, hmap))
				{
					String letter = s.substring(end, end+1);
					
					
					if (isLongerWord(sub, letter, hmap))
					{
						end += 1;
						break;
					}
					
					else//si on ne peut pas faire de mot plus long en ajoutant une lettre, alors on decoupe
					{
						res += sub + " ";
						start = end;
						end = start + 1;
					}
				}
				
				else// on n'a pas de mot, donc on ajoute une lettre
				{
					end += 1;
				}		
			}
			
			return res;
		
	}
		
	
	
	public static void main(String[] args)
	{
		HashMap h = createHashmap("FR");
		String s1 = "bientotnoel";
		System.out.println(decoupe(s1, h));

	}
}
