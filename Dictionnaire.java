package tse.fi2.info4.tbek;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
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
	 * @version 0.1, ecrit le 14 Decembre 2014
	 * 
	 * Methode trouvant tous les mots qui sont un sous-mot du mot passé 
	 * en paramètre. Par exemple, pour le mot information, les sous-mots
	 * sont : in, informa, informat, information
	 * 
	 * @param s		String	C'est le mot dont on cherche les sous-mots
	 * @param hmap	HashMap	Le dictionnaire dans lequel on cherche
	 * 
	 * @return 	Vector<String> L'ensemble des sous-mots trouvés
	 */
	public static Vector<String> findAllWords(String word, HashMap hmap)
	{
		Vector<String> res = new Vector<String>();
		
		// on parcout les lettres du mot une a une
		for (int i = 0; i < word.length(); i++)
		{
			// on cree le sous-mot commencant a la lettre 0 et se 
			// terminant a la lettre i
			String sub = word.substring(0, i+1);
			//System.out.println(sub);
			// on regarde si ce mot est valide
			if (isAWord(sub, hmap))
			{
				res.add(sub);
			}
			
		}
			
		return res;
	}
	
	/**
	 * @author Julien Tissier
	 * @version 0.1, ecrit le 14 Decembre 2014
	 * 
	 * Methode comptant le nombre moyen de lettres par mot.
	 * 
	 * @param mot		String	La phrase dans laquelle on calcule le
	 * 							nombre moyen de mots
	 * 
	 * @return 	double	Nombre moyen de lettres par mot
	 */
	public static double nombreMoyenLettres(String mot)
	{
		//on separe les mots grace aux espaces
		String[] split = mot.split("\\s+");
		double nbChar = 0;
		double nbWord = 0;
		// nombre moyen de lettres = (nombre total de lettres) / (nombre de mots)
		for (String s : split)
		{
			nbChar += s.length();
			nbWord += 1;
		}
		
		return nbChar/nbWord;
	}
	
	/**
	 * @author Julien Tissier
	 * @version 0.1, ecrit le 14 Decembre 2014
	 * 
	 * Methode indiquant quel decoupage est le plus approprie. L'algorithme 
	 * choisit celui qui a le plus grand nombre moyen de lettres par mot
	 * 
	 * @param Vector<String> decoupages	L'ensemble des decoupages a tester
	 * 
	 * @return 	String 	Le decoupage ayant le meilleur ratio nombredeLettres / nombreDeMots
	 */
	public static String meilleurDecoupage(Vector<String> decoupages)
	{
		//int lowestNumberSpaces = 100;
		double averageLetters = 0;
		String meilleur = "";
		
		// on parcourt tous les decoupages, et on calcule pour chacun le 
		// nombre moyen de lettres par mot
		for (String s : decoupages)
		{
			//System.out.println(s);
			/*if ( (s.length() - s.replace(" ", "").length()) <= lowestNumberSpaces )
			{
				lowestNumberSpaces = s.length() - s.replace(" ", "").length();
				meilleur = s;
			}*/
			if ( nombreMoyenLettres(s) >= averageLetters)
			{
				averageLetters = nombreMoyenLettres(s);
				meilleur = s; // on ne retient que le meilleur
			}
		}
		
		return meilleur;
	}
	
	/**
	 * @author Julien Tissier
	 * @version 0.1, ecrit le 14 Decembre 2014
	 * 
	 * Methode decoupant une tendance a partir d'un dictionnaire.
	 * Methode recursive
	 * 
	 * @param phrase		String	C'est la tendance que l'on veut decouper
	 * @param hmap	HashMap	Le dictionnaire avec lequel on decoupe
	 * 
	 * @return 	String 	La tendance decoupee
	 */
	public static String advancedDecoupe(String phrase, HashMap hmap)
	{
		String s = phrase.toLowerCase();
		

		if (s.length() == 0)
		{
				return s;
		}
		/* si la tendance a decouper n'est pas la chaine vide. 
		* On cherche tous les sous-mots de la tendances. Pour chacun de
		* ces sous-mots, on cherche le meilleur decoupage parmi les 
		* lettres restantes. Cela forme un decoupage possible.
		* On renvoie le meilleur decoupage parmi ces decoupages formes
		* a partir des sous-mots.
		*/
		else
		{
			Vector<String> tmp = new Vector<String>();
			for (String sub : findAllWords(s, hmap))
			{
				//System.out.println("les mots " + findAllWords(s, hmap));
				//System.out.println(sub);
				int l = sub.length();
				tmp.add(sub + " " + advancedDecoupe(s.substring(l), hmap));
				//System.out.println(tmp);
				
			}
			
			return meilleurDecoupage(tmp);
		}	
		
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
	 
	public static String decoupe(String phrase, HashMap hmap)
	{
		String s = phrase.toLowerCase();		
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
	 */ 
	
	public static void main(String[] args)
	{
		HashMap h = createHashmap("FR");
		HashMap en = createHashmap("EN");
		/*Vector<String> t = new Vector<String>();
		t.add(" l a allez ly o n");
		t.add("alle z lyon");
		t.add("allez lyon");
		t.add("allezl yon");*/
		
		String s1 = "EnjoyChristmas";
		String s2 = "PeopleWhoMadeMy2014";
		String s3 = "MUNLIV";
		String s4 = "OGCNASSE";
		String s5 = "NoelAvecLeFarfadet";
		//System.out.println(findAllWords(s1, h));
		//System.out.println(meilleurDecoupage(t));
		System.out.println(advancedDecoupe(s1, en));
		System.out.println(advancedDecoupe(s2, en));
		System.out.println(advancedDecoupe(s3, h));
		System.out.println(advancedDecoupe(s4, h));
		System.out.println(advancedDecoupe(s5, h));

	}
	/**/
}
