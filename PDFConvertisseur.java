import it.grabz.grabzit.*;

/**
 * @author Julien Tissier
 * @version 0.1, ecrit le 17 Decembre 2014
 * 
 * Classe faisant le lien avec la bibliothèque GrabzIt 
 * et permettant d'enregistrer une page web au format PDF
 */

public class PDFConvertisseur
{
	/**
	 * @author Julien Tissier
	 * @version 0.1, ecrit le 17 Decembre 2014
	 * 
	 * Methode enregistrant une page web au format PDF.
	 * 
	 * @param url 	String 		L'adresse de la page web a telecharger
	 * @param filename 	String	Le nom du fichier pdf de sortie
	 * 
	 */
	 
	public static void sauvergardeArticle(String url, String fileName)
	{
		try
		{
			//connexion au serveur qui nous convertira la page web en 
			//fichier PDF. Acces restreint, besoin des codes 
			//d'authentification
			GrabzItClient grabzIt = new GrabzItClient("YjNmNjRlMTQ3NTc2NDg1OTg3NmI3M2ExYTQxNGQ5MzM=", "Pz8yKkMYNFR7Zz9QPyQUJD8/Pz8SPyYFMT9qXz8/Pz8=");
			
			// on indique l'url à sauvegarder
			grabzIt.SetPDFOptions(url);
			
			// on indique le nom sous lequel on veut sauvegarder
			grabzIt.SaveTo(fileName);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
		
		
		
		
		
		public static void main(String[] args)
	{
		sauvergardeArticle("http://raysohn.com/posts/decoding-floats.html","float.pdf");
		
	}
}
