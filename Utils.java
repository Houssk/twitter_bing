import java.util.Vector;

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
 * Methode qui decoupe un texte en blocs de 40 caracteres.
 * Cette methode prend en compte les emplacements des espaces du message 
 * afin de ne pas couper en plein milieu d'un mot.
 * 
 * @param texte   String  C'est le texte a decouper
 *  
 * @return  Vector<String>     Elle renvoie un vector de String. Chaque 
 *                              String est une partie decoupee du texte
 */
    public static Vector<String> decouper(String texte) {
		
       Vector<String> blocs = new Vector<String>();
       
       //on parcourt l'integralite du texte, 40 caracteres par 40 caracteres
       for (int start = 0; start < texte.length(); start += 40)
       {
           // il reste plus de 40 caracteres
           if (start < texte.length() - 40)
           {
                // si l'on ne coupe pas au milieu d'un mot, alors on peut decouper
                if(texte.charAt(start + 39) == ' ')
                {
                    blocs.add(texte.substring(start,start+40));	
                }
                
                // sinon on revient au denier espace avant la coupure
                else
                {
                    int pos = 1;
                    while(texte.charAt(start+39-pos) != ' ')
                    {
                        pos++;
                    }
               	
                    blocs.add(texte.substring(start,start + 40 - pos));
                    start -= pos; // on reprend au niveau de cette coupure
                }
           	
           }
            
            // si il reste moins de 40 caracteres, alors on decoupe 
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
       
       Vector<String> message_decoupe = decouper(message);
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
       
       // oblige de couper le titre sur 40 caracteres, sinon
       // il n'y a pas de line wrapping et on est oblige d'utiliser
       // la scrollbar horizontale pour voir l'integralite du titre
       
       Vector<String> titre_decoupe = decouper(titre);
       for (String s : titre_decoupe)
       {
           element_formate += s + "<br>";
       }
         
        // la source est en vert, la date en gris fonce et italique
        element_formate += "</b><font color=#00A050>" + source + "</font></br><br><i><font color=#6E7170>" + date + "<br></font></i><br></html>";
        
       return element_formate;
       
	}
	
    public static String decoupage_intelligent(String phrase)
    {
        String res = "";
        int start = 0;
        int end   = 1;
        int l = phrase.length();
        
        while (start < (l - 1))
        {
            String sub = phrase.substring(start, end);
            Long n = BingSearch.nombreResultats(sub);
            
            while (end < l)
            {
                end += 1;
                String sub2 = phrase.substring(start, end);
                Long m = BingSearch.nombreResultats(sub2);
                
                if ( m <= (n / 1000) )
                    {
                        res += phrase.substring(start, end-1) + ' ';
                        break;
                    }
                    
                else if (end == l)
                {
                    res += sub2;
                }
                
                n = m;
            }
            
            start = end - 1;
            //end = start + 1;
        
        }
        
        //res += phrase.substring(start);
        return res;
    }
    
    public static void main(String[] args)
    {
        System.out.println(decoupage_intelligent("callofduty"));
        
    }
}
