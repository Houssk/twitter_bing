
import java.lang.Character; 
import java.util.Iterator;


public class Test {
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
		String resultat1="";
		// la fonction tendance.split("(?<=[a-z])(?=[A-Z])") permet de 
		// chercher des lettres minuscules après une lettre majuscule
		// pour composer un mot
		// la fonction ignore s'il y a 2 majuscules qui se suivent
		
		for (String mot: tendance.split("(?<=[a-z])(?=[A-Z])")) { 
		      int count=0;
		      // le cas ou 2/ plusieurs majuscules se suivent
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
		return resultat;
	}
 public static void main(String[] args) {
	
	 System.out.println(decouperMaj("###HoussamKarrachCeciEstUNTestDeLaTendanceMTVStar"));
}
}
