import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.HashMap;

/**
 * @author Julien Tissier
 * @version 0.1, ecrit le 30 Novembre 2014
 * 
 * Classe permettant de creer et d'utiliser un dictionnaire.
 * On peut choisir la langue du dictionnaire.
 * 
 */
 
public class Dictionnaire
{
	
	/**
	 * @author Julien Tissier
	 * @version 0.1, ecrit le 30 Novembre 2014
	 * 
	 * Methode creant un dictionnaire de mots sous la forme d'une table 
	 * de hachage. Il faut bien evidemment que les mots soient stockes
	 * dans des fichiers txt. Chaque fichier txt correspond à l'ensemble
	 * des mots d'une langue.
	 * 
	 * @param langue 	String	On specifie la langue désirée : FR ou EN
	 * 
	 * @return HashMap<Key, Value>	Elle renvoie une hashmap contenant 
	 * 						l'ensemble des mots de la langue choisie. 
	 * 						La key de chaque element est le mot en minuscule
	 * 						tandis que la value est le mot normal.
	 * 						Par exemple, <Key, Value> = <"paris", "Paris">
	 */
	 
	public static HashMap createHashmap(String langue)
	{
		// on initialise les variables selon une condition, donc on doit les declarer avant 
		String filename = null;
		int size = 1;
			
			/* Selon la langue choisie, le fichier txt utilisé n'est pas le même.
			 * Pour eviter d'avoir à elargir la table de hachage lors du
			 * remplissage, il est preferable de connaitre le nombre d'éléments 
			 * que l'on va inserer.
			 */
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
		BufferedReader buff = null; // buffer pour lire le fichier .txt
		
		try
		{
			String line;
			buff = new BufferedReader(new FileReader(filename));
			
			// on parcourt le fichier ligne par ligne. Chaque ligne 
			// represente un mot.
			while ((line = buff.readLine() ) != null)
			{
				// insertion dans la table de hachage avec la key en minuscule
				hmap.put(line.toLowerCase(), line);
			}
		}
		
		catch (IOException e) // recupere les erreurs d'ouverture de fichier
		{
			e.printStackTrace();
		}
		
		return hmap;
	}
	
	/**
	 * @author Julien Tissier
	 * @version 0.1, ecrit le 30 Novembre 2014
	 * 
	 * Methode indiquant si un mot se trouve dans le dictionnaire.
	 * 
	 * @param s		String	C'est le mot qu'on recherche dans le dictionnaire
	 * @param hmap	HashMap	Le dictionnaire dans lequel on cherche
	 * 
	 * @return 		true -> le mot est dans le dictionnaire
	 * 				false -> le mot n'est pas dans le dictionnaire
	 */
	 
	public static boolean isAWord(String s, HashMap hmap)
	{
		return (hmap.containsKey(s));
	}
	
	/**
	 * @author Julien Tissier
	 * @version 0.1, ecrit le 30 Novembre 2014
	 * 
	 * Methode indiquant si on obtient un mot valable en ajoutant une lettre
	 * au mot passé en paramètre.
	 * 
	 * @param s		String		Le mot auquel on ajoute une lettre
	 * @param letter	String	La lettre ajoutee
	 * @param hmap 		HashMap	Le dictionnaire de recherche
	 * 
	 * @return 		true -> le mot nouveau est dans le dictionnaire
	 * 				false -> le mot nouveau n'est pas dans le dictionnaire
	 * 
	 * @deprecated use {@link findLongerWord()} instead.
	 */
	 
	@Deprecated
	public static boolean isLongerWord(String s, String letter, HashMap hmap)
	{
		String word = s + letter;
		return isAWord(word, hmap);
	}
	
	/**
	 * @author Julien Tissier
	 * @version 0.1, ecrit le 30 Novembre 2014
	 * 
	 * Methode trouvant le mot le plus long qu'il est possible de faire
	 * en ajoutant des lettres au mot passé en paramètre.
	 * 
	 * @param s		String		Le mot auquel on ajoute des lettres
	 * @param letters	String	L'ensemble des lettres qu'on peut ajouter
	 * @param hmap 		HashMap	Le dictionnaire de recherche
	 */
	 
	public static String findLongerWord(String word, String letters, HashMap hmap)
	{
		
		String tmp = word;// tmp est le mot auquel on va ajouter les lettres
		String res = word;// res est le mot valable le plus long
		
		// on parcourt l'ensemble des lettres, pour les ajouter une à une
		for(int i = 0; i < letters.length(); i++)
		{
			tmp += letters.charAt(i); //on ajoute la lettre
			
			if (isAWord(tmp, hmap)) //si le mot formé est valable..
			{
				//.. alors il est plus long que word, donc potentiellement le plus long
				res = tmp;
			}
		}
		
		return res;
	}
	
	/**
	 * @author Julien Tissier
	 * @version 0.1, ecrit le 30 Novembre 2014
	 * 
	 * Methode qui decoupe une String selon les mots qui la composent.
	 * Par exemple, transforme "bientotnoel" en "bientot noel".
	 * 
	 * @param s 	String	La string à découper.
	 * @param hmap	HashMap	Le dictionnaire sur lequel on se base pour decouper.
	 * 
	 * @return String	La string de départ correctement découpée
	 */
	 
	public static String decoupe(String s, HashMap hmap)
	{
		
		String res = ""; // variable de sortie
		int start  = 0; // on commence a decouper a partir de la premiere lettre
		int end    = 1;
	
		while (end < s.length()) //jusqu'à atteindre la fin de s
		{
			if (end == (s.length() - 1)) // dans ce cas, on ne peut pas effectuer de decoupage supplementaire
			{
				res += s.substring(start); // on ajoute l'ensemble des caractères restant à res
				break;
			}
				
			else 
			{
				/* on decoupe s en 2 parties :
				 * 	- la premiere est le debut d'un mot
				 * 	- la seconde est l'ensemble des lettres restantes
				 */
				String sub = s.substring(start, end);
				String letters = s.substring(end);
				
				/* On cherche le plus grand mot qu'il est possible de 
				 * creer, et on l'ajoute à res
				 */
				String word = findLongerWord(sub, letters, hmap);
				res += word + " ";
				
				// on reprend le decoupage au niveau de ce mot trouvé	
				start = start + word.length();
				end = start + 1;
			}		
		}
			
		return res;
	
	}
		
	/*		T E S T
	 * 
	
	public static void main(String[] args)
	{
		HashMap h = createHashmap("FR");
		String s1 = "ceciestuntestdedecoupage";
		System.out.println(decoupe(s1, h));

	}
	*/
}
