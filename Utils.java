package tse.fi2.info4.tbek;


import java.util.Vector;
import java.util.HashMap;
import java.lang.Character; 

/**
 * @author Julien Tissier
 * @author Houssam Karrach
 * @author Nader Ben Abdeljelil
 * @version 0.1, ecrit le 16 Novembre
 * 
 * Classe contenant de nombreuses methodes qui sont utilisees dans
 * d'autres classes. Ces methodes sont d'utilites diverses : decouper 
 * un texte, formater un titre...
 * 
 */
 public class Utils
{
/**
 * @author Nader Ben Abdeljelil
 * @author Houssam Karrach
 * @version 0.1, ecrit le 1 Novembre 2014
 * 
 * Methode qui decoupe un texte en blocs de 60 caracteres.
 * Cette methode prend en compte les emplacements des espaces du message 
 * afin de ne pas couper en plein milieu d'un mot.
 * 
 * @param texte   String  C'est le texte a decouper
 *  
 * @return  Vector<String>     Elle renvoie un vector de String. Chaque 
 *                              String est une partie decoupee du texte
 */
	 public static Vector<String> decouper(String texte,int n) {
			
	       Vector<String> blocs = new Vector<String>();
	       
	       //on parcourt l'integralite du texte, n caracteres par n caracteres
	       for (int start = 0; start < texte.length(); start += n)
	       {
	           // il reste plus de 60 caracteres
	           if (start < texte.length() - n)
	           {
				   
	                // si l'on ne coupe pas au milieu d'un mot, alors on peut decouper
	                if(texte.charAt(start + (n-1)) == ' ')
	                {
	                    blocs.add(texte.substring(start,start+n));	
	                }
	                
	                // sinon on revient au dernier espace avant la coupure
	                else
	                {
	                    int pos = 1;
	                    while(texte.charAt(start+(n-1)-pos) != ' ')
	                    {
	                        pos++;
	                    }
	               	
	                    blocs.add(texte.substring(start,start + n - pos));
	                    start -= pos; // on reprend au niveau de cette coupure
	                }
	           	
	           }
	            
	            // si il reste moins de n caracteres, alors on decoupe 
	            // jusqu'a la fin du message
	           else
	           {
				  
	              blocs.add(texte.substring(start));
	           }
	         }
	       
	       return blocs;
		}
    
    public static Vector<String> decouperTitrePage(String texte) {
		
        Vector<String> blocs = new Vector<String>();
        
        //on parcourt l'integralite du texte, 150 caracteres par 150 caracteres
        for (int start = 0; start < texte.length(); start += 150)
        {
            // il reste plus de 60 caracteres
            if (start < texte.length() - 150)
            {
 			   
                 // si l'on ne coupe pas au milieu d'un mot, alors on peut decouper
                 if(texte.charAt(start + 149) == ' ')
                 {
                     blocs.add(texte.substring(start,start+150));	
                 }
                 
                 // sinon on revient au dernier espace avant la coupure
                 else
                 {
                     int pos = 1;
                     while(texte.charAt(start+149-pos) != ' ')
                     {
                         pos++;
                     }
                	
                     blocs.add(texte.substring(start,start + 150 - pos));
                     start -= pos; // on reprend au niveau de cette coupure
                 }
            	
            }
             
             // si il reste moins de 60 caracteres, alors on decoupe 
             // jusqu'a la fin du message
            else
            {
 			  
               blocs.add(texte.substring(start));
            }
          }
        
        return blocs;
 	}
    
    
/**
 * @author Nader Ben Abdeljelil
 * @author Houssam Karrach
 * @version 0.2, ecrit le 16 Novembre 2014
 * 
 * Methode qui formate un tweet au format html, afin de le rendre
 * plus presentable dans l'interface graphique
 * 
 * @param message   String  C'est le contenu du tweet
 * @param auteur    String  C'est l'auteur du tweet
 * 
 * @return  String      Elle renvoie un message au format html,  qui 
 *                      permet de visualiser de maniere correcte le
 *                      tweet
 */
    public static String formateTweet(String message,String auteur) {
		
        // Utilise le html pour rendre le tweets presentable. 
        String element_formate = "<html><i><font color=blue>"+ auteur +"</i><br>";
       
       // oblige de couper les messages sur 40 caracteres, sinon
       // il n'y a pas de line wrapping et on est oblige d'utiliser
       // la scrollbar horizontale pour voir l'integralite du message
       
       Vector<String> message_decoupe = decouper(message,48);
       for (String s : message_decoupe)
       {
           element_formate += s + "<br>";
       }
       
            
       element_formate += "<br></font></html>";
       return element_formate;
	}
  
/**
     * @author Nader Ben Abdeljelil
     * @author Houssam Karrach
     * @version 0.1, ecrit le 11 Novembre 2014
     * 
     * Methode qui formate une news au format html, afin de la rendre 
     * plus presentable dans l'interface graphique
     * 
     * @param title   String  C'est le titre de l'article
     * @param source    String  C'est la source de l'article
     * @param date   String  C'est la date de l'article
     * 
     * @return  String      Elle renvoie un message au format html,  qui 
     *                      permet de visualiser de maniere correcte la 
     *                      news
     */
    
public static String formateNews(String titre, String source, String date) {
		
        // Utilise le html pour rendre la news presentable. Le titre sera en gras 
        String element_formate = "<html><b>";
       
       // oblige de couper le titre sur 60 caracteres, sinon
       // il n'y a pas de line wrapping et on est oblige d'utiliser
       // la scrollbar horizontale pour voir l'integralite du titre
       
       Vector<String> titre_decoupe = decouper(titre,48);
       for (String s : titre_decoupe)
       {
           element_formate += s + "<br>";
       }
         
        // la source est en vert, la date en gris fonce et italique
        element_formate += "</b><font color=#00A050>" + source + "</font></br><br><i><font color=#6E7170>" + date + "<br></font></i><br></html>";
        
       return element_formate;
       
	}
public static String formatePage(String titre, String source, String date) {
	
    // Utilise le html pour rendre la news presentable. Le titre sera en gras 
    String element_formate = "<html><b>";
   
   // oblige de couper le titre sur 60 caracteres, sinon
   // il n'y a pas de line wrapping et on est oblige d'utiliser
   // la scrollbar horizontale pour voir l'integralite du titre
   
   Vector<String> titre_decoupe = decouperTitrePage(titre);
   for (String s : titre_decoupe)
   {
       element_formate += s + "<br>";
   }
     
    // la source est en vert, la date en gris fonce et italique
    element_formate += "</b><font color=#00A050>" + source + "</font></br><br><i><font color=#6E7170>" + date + "<br></font></i><br></html>";
    
   return element_formate;
   
}

/**
 * @author Nader Ben Abdeljelil
 * @version 0.1, ecrit le 10 Janvier 2015
 * 
 * Methode qui formate une reponse au format html, afin de la rendre 
 * plus presentable dans l'interface graphique
 * 
 * @param message   String  C'est le message a decouper
 *  
 * @return  String      Elle renvoie un message au format html,  qui 
 *                      permet de visualiser de maniere correcte la 
 *                      reponse de la question
 */

public static String formateReponse(String message) {
	
    // Utilise le html pour rendre la reponse presentable. 
    String element_formate = "<html><b>";
   
   // oblige de couper les messages sur 75 caracteres, sinon
   // il n'y a pas de line wrapping et on est oblige d'utiliser
   // la scrollbar horizontale pour voir l'integralite du message
   
   Vector<String> message_decoupe = decouper(message,55);
   for (String s : message_decoupe)
   {
       element_formate += s + "<br>";
   }
   
        
   element_formate += "<br></font></html>";
   return element_formate;
}

/**
 * @author Julien Tissier
 * @version 0.1, ecrit le 18 Novembre 2014
 * 
 * Methode qui place des espaces au bon endroit dans une phrase, grace 
 * au moteur de recherche Bing News
 * Par exemple, elle transforme "callofduty" en "call of duty"
 * 
 * @param phrase   String  C'est la phrase a laquelle on doit ajouter des espaces
 *  
 * @return  String     Elle renvoie un String qui est le decoupage de maniere
 *                      intelligente de la phrase.
 */	
    public static String decoupage_intelligent(String phrase)
    {
        // ceci est notre variable de sortie
        String res = "";
        
        /* on va parcourir notre phrase de la gauche vers la droite,
         * lettre par lettre. On commence en selectionnant seulement
         * une lettre. cela donne un premier sous-mot (sub)
         */
        int start = 0;
        int end   = 1;
        int l = phrase.length();
        
        while (start < (l - 1))
        {
            /* On compte le nombre de resultats Bing News (n)
             * si on effectue une recherche sur cette lettre.
             */
            String sub = phrase.substring(start, end);
            Long n = BingSearch.nombreResultats(sub);
            
            while (end < l)
            {
                // On ajoute ensuite une lettre a ce sous-mot. Ce qui forme
                // un deuxieme sous-mot (sub2)
                end += 1;
                String sub2 = phrase.substring(start, end);
                
                
                /* On compte a nouveau le nombre de resultats Bing News (m)
                 * de ce nouveau sous-mot. Deux cas sont possibles :
                 * 
                 *  - soit notre premier sous-mot (sub) etait un mot entier
                 *  (c'est-a-dire qu'il ne manquait aucune lettre). Dans ce cas
                 *  la lettre ajoutee ruine ce premier sous-mot, et le rend
                 *  insensé, ce qui veut dire que le nombre de resultat (m) est tres inferieur
                 *  a n (m <= n / 1000). On sait donc que l'on a ajoute une lettre 
                 *  en trop, et qu'il ne fallait pas ajouter cette lettre.
                 * 
                 *  - soit soit notre premier sous-mot (sub) etait un mot incomplet
                 *  (c'est-a-dire qu'il manquait une/des lettres). Dans ce cas
                 *  la lettre ajoutee ameliore ce premier sous-mot, et ameliore 
                 *  son sens, ce qui veut que le nombre de resultat (m) est environ
                 *  egal a n ( n / 1000 < m ). On sait donc que la lettre ajoutee 
                 *  est bonne, donc on en ajoute une autre
                 */
                
                Long m = BingSearch.nombreResultats(sub2);
                System.out.println(sub + ": " + n + "\n" + sub2 + ": " + m + "\n");
                if ( m <= (n / 1000) )
                    {
                        // dans ce cas, on coupe car on sait que l'on a
                        // ajoute un lettre en trop
                        res += phrase.substring(start, end-1) + ' ';
                        break;
                    }
                  
                  // si on a atteint la fin de la phrase, alors on ne peut plus
                  // ajouter de lettres, donc on coupe  
                else if (end == l)
                {
                    res += sub2;
                }
                
                // Pour l'ajout de lettre suivante, il faut prendre en compte 
                // le nombre de resultats (m) du nouveau sous-mot (sub2) comme reference
                n = m;
            }
            
            // une fois la coupure effectuee, on reprend juste apres la 
            // coupure
            start = end - 1;
        }
    
    return res;
    }
    
    /**
     * @author Julien Tissier
     * @version 0.1, ecrit le 30 Novembre
     * 
     * Methode supprimant le # d'une tendance, s'il existe.
     * 
     * @param s 	String	Tendance dont on veut supprimer le #
     * @return String		Tendance sans le #
     */
     
    public static String removeHashtag(String s)
    {
		return (s.charAt(0) == '#') ? s.substring(1) : s;
	}
	
    /**
     * @author Julien Tissier
     * @version 0.1, ecrit le 30 Novembre 2014
     * 
     * Methode qui decoupe une string selon les mots qui la composent.
     * Elle utilise à la fois le dictionnaire anglais et le dictionnaire
     * francais pour trouver le meilleur decoupage.
     * 
     * @param s		String	C'est la String à découper.
     * 
     * @return String	Renvoie une string où les mots sont séparés par des espaces
     */
     
    public static String decoupage_dictionnaire(String s)
    {
		HashMap fr = Dictionnaire.createHashmap("FR");
		HashMap en = Dictionnaire.createHashmap("EN");
		
		String decoupage_fr = Dictionnaire.advancedDecoupe(s, fr);
		String decoupage_en = Dictionnaire.advancedDecoupe(s, en);
		
		double nb_fr = Dictionnaire.nombreMoyenLettres(decoupage_fr);
		double nb_en = Dictionnaire.nombreMoyenLettres(decoupage_en);
		
		return (nb_fr > nb_en) ? decoupage_fr : decoupage_en;
	}
		
   
    /**
	 * @author Houssam Karrach
	 * @author Ayoub el fatmi
	 * @version 0.1, ecrit le 29 Decembre 2014
	 * 
	 * Méthode qui decoupe une tendance s'il est composée de plusieurs
	 * mots qui commencent par une majuscule
	 * @param tendance   String  C'est le contenu de la tendance
	 * 
	 * @return  String      Elle renvoie une chaine de caractère
	 *                      
	 */
	
	public static String decouperMaj(String tendance)
	{
		for(int i=0;i<tendance.length();i++){
			if(tendance.charAt(i)=='#')
			tendance=tendance.substring(i+1);
		}
		String resultat="";
		String dernier= tendance.substring(tendance.length()-1);
		System.out.println(dernier);
		String new_tendance=tendance.substring(0, tendance.length()-1);
		System.out.println(new_tendance);
		// la fonction tendance.split("(?<=[a-z])(?=[A-Z])") permet de 
		// chercher des lettres minuscules après une lettre majuscule
		// pour composer un mot
		// la fonction ignore s'il y a 2 majuscules qui se suivent
		
		for (String mot: new_tendance.split("(?<=[a-z])(?=[A-Z])")) { 
		      int count=0;
		      // le cas ou 2 ou plusieurs majuscules se suivent
		      for(int i=1; i<mot.length();i++){
		    	  if(Character.isUpperCase(mot.charAt(i)))
		    		 count++;
		      }
		      if(count!=0)
		      {
		    	  mot=mot.substring(0, count)+" "+mot.substring(count);
		      }
		      resultat=resultat+mot+" ";
		 }
		resultat=resultat.substring(0, resultat.length()-1);
		resultat=resultat+dernier;
		return resultat;
	}
	
    public static void main(String[] args)
    {
        	
//		System.out.println(decoupage_dictionnaire("traineauLDLC"));
//		System.out.println(decoupage_dictionnaire("joyeuxnoel"));
//		System.out.println(decoupage_dictionnaire("merryChristmas"));
//        System.out.println(removeHashtag("#lyon"));
//        System.out.println(removeHashtag("paris"));
        System.out.println(decouperMaj("###HoussamKarrachCeciEstUNTestDeLa15TendanceMTVStarKlkoOp"));
    }
}

