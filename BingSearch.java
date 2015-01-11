package tse.fi2.info4.tbek;

import java.util.Iterator;
import java.util.Vector;

import net.billylieurance.azuresearch.*;
import net.billylieurance.azuresearch.AbstractAzureSearchQuery.AZURESEARCH_FORMAT;


/**
 * @author Houssam Karrach
 * @author Ayoub El Fatmi
 * @version 0.1, ecrit le 9 Novembre
 *  
 * Classe faisant le lien avec la bibliotheque azuresearch. Elle permet de 
 * recuperer les news d'une tendance, grace a une methode de 
 * asuresearch
 */
public class BingSearch {
	
    private static final String ACCOUNT_KEY = "oLnzdYxQ+FfGVysn751kjLkO5GPkWzb5yILRH33G908";
    
/**
 * @author Houssam Karrach
 * @author Ayoub El Fatmi
 * @version 0.1, ecrit le 9 Novembre
 * 
 * Cette methode permet de recuperer les articles d'une tendance
 * passee en parametre
 * 
 * @return Vector<String>   Renvoie un tableau de String
 *                          Ces Strings sont les articles tries par date d'une tendance
 */  
    public static Vector<String> recupereNews(String searchParam) {
				
		
		// Codes necessaires a l'authentification lors de la recuperation des articles de Bing news 
		AzureSearchNewsQuery aq = new AzureSearchNewsQuery();
		aq.setAppid(ACCOUNT_KEY);
		
		// on ajoute le Market "France" pour recuperer des articles en français 
		aq.setMarket("fr-FR");
		// on trie les articles par date 
		aq.setSortBy("Date");
		// on fixe le nombre d'article a 10 par page 
		aq.setPerPage(10);
		// on fixe le nombre des pages a 1
		aq.setPage(1);
		// on recupere les articles sous format XML
		aq.setFormat(AZURESEARCH_FORMAT.XML);
		
		// on recherche le mot-cle
		aq.setQuery(searchParam);
		aq.doQuery();
		
		
        // vecteur de sortie. Ce vecteur sera de la forme
        // [ titre article 1, source article 1, date article 1,
        //  titre article 2, source article 2, date article 2, ...]
		Vector<String> lesNews= new Vector<String>();
        
		AzureSearchResultSet<AzureSearchNewsResult> azureSearchResultSet = aq.getQueryResult();
		for (Iterator<AzureSearchNewsResult> iterator = azureSearchResultSet.iterator(); iterator.hasNext();) {
			AzureSearchNewsResult result = (AzureSearchNewsResult) iterator.next();
			// pour chaque resultat de la recherche, on ajoute son 
            // titre, sa source et sa date au vecteur de sortie
			lesNews.add(result.getTitle());
			lesNews.add(result.getSource());
			lesNews.add(result.getDate());
			lesNews.add(result.getUrl());
		}
        
		return lesNews;
	}

/**
 * @author Julien Tissier
 * @version 0.1, ecrit le 16 Novembre
 * 
 * Cette methode indique le nombre de resultats lors d'une recherche
 * sur le mot cle passe en parametre.
 * 
 * @param mot_cle      String  C'est le mot sur lequel on effectue une recherche de news
 * @return Long          Cette methode renvoie un Long, qui est le nombre total de resultats de la recherche
 */
 
    public static Long nombreResultats(String mot_cle)
    {
        // initialisation des parametres necessaires a l'envoi de la requete Bing
        AzureSearchCompositeQuery requete = new AzureSearchCompositeQuery();
        // cle d'authentification
        requete.setAppid("oLnzdYxQ+FfGVysn751kjLkO5GPkWzb5yILRH33G908");
        // on ne souhaite avoir que des news en francais
        requete.setMarket("fr-FR");
        // on indique le mot a chercher
        requete.setQuery(mot_cle);
        // on ne souhaite avoir que des news (pas d'images ou autres)
        requete.setSources(new AbstractAzureSearchQuery.AZURESEARCH_QUERYTYPE[] {AbstractAzureSearchQuery.AZURESEARCH_QUERYTYPE.NEWS});
        
        // on lance la requete
        requete.doQuery();
        AzureSearchResultSet result = requete.getQueryResult();
        
        // on retourne le nombre total de resultats
        return result.getNewsTotal();
    }
    
	/*      T E S T
     * 
     * public static void main(String[] args) {
		
        Vector<String> l = recupereNews("Noel");
        for(String s : l)
        {
            System.out.println(s);
        }
        
        System.out.println("Nombre de resultats : " + nombreResultats("Noel"));
		
	}*/
}
