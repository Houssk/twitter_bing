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
	
	
	
	
	public static void main(String[] args)
	{
		HashMap h = createHashmap("FR");
		System.out.println(h.containsKey("television"));
		System.out.println(h.containsKey("arbre"));
		System.out.println(h.containsKey("bientot"));
		System.out.println(h.containsKey("azasasasdfsqfcs"));

	}
}
