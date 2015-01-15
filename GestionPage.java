package tse.fi2.info4.tbek;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;



/**
 * 
 *   On n'utilise plus cette classe 
 * 
 * 
 */


/**
 * @author Houssam Karrach
 * @author Ayoub el fatmi
 * @version 0.1, ecrit le 17 décembre
 * 
 * Classe qui permet de sauvegarder et lire des pages sous format pdf
 * dans une base de donnée
 * @param myConn 	Connection 	 permet d'établir une connexion avec bd mysql
 * @param mySTmt	PreparedStatement pour preparer une requete 
 * @param input     File 	fichier en entrée
 * @param input1   	File  	fichier en entrée
 * @param stmt 		Statement  Preparer une requele en mode select
 * @param myRs  	ResultSet  Resultat d'une requete sql
 */
public class GestionPage {
	
	
	static Connection myConn = null;
	static PreparedStatement myStmt = null;
	static FileInputStream input = null;
	static Statement stmt = null;
	static ResultSet myRs = null;
	static InputStream input1 = null;
	static FileOutputStream output = null;
	
	/**
	 * @author Houssam Karrach
	 * @author Ayoub el fatmi
	 * @version 0.1, ecrit le 17 Decembre 2014
	 * 
	 * Methode pour établir la connexion avec une base de donnée
	 * mysql
	 * 
	 @return myConn Connection
	 * 
	 */
	// Fonction pour établir la connexion avec la base de donnée
	static Connection connexion() throws SQLException{
		
		myConn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/projet_tbek", "root", "POPLOL");
		return myConn;
	}
	/**
	 * @author Houssam Karrach
	 * @author Ayoub el fatmi
	 * @version 0.1, ecrit le 17 Decembre 2014
	 * 
	 * Methode pour inserer un fichier pdf dans la base de donnée
	 * 
	 * 
	 */
	// Foction qui permet de sauvegarder une page pdf dans la table page 
	static void inserer_page(int id_user,String titre) throws SQLException, FileNotFoundException{
		Connection myConn = connexion();
		String sql = "insert into  page (titre,contenu,id_user) values(?,?,?)";
		myStmt = myConn.prepareStatement(sql);
		File theFile = new File("tbek/"+titre);
		input = new FileInputStream(theFile);
		myStmt.setString(1,titre);
		myStmt.setBinaryStream(2, input);
		myStmt.setInt(3, id_user);
		System.out.println("Reading input file: " + theFile.getAbsolutePath());
		
		// 4. Execute statement
		//System.out.println(sql);
		
		myStmt.executeUpdate();
		
		System.out.println("\nCompleted successfully!");
		close(myConn, myStmt);
		
	}
	/**
	 * @author Houssam Karrach
	 * @author Ayoub el fatmi
	 * @version 0.1, ecrit le 17 Decembre 2014
	 * 
	 * Methode pour affcher le titre ainsi que le lien absolu du fichier pdf
	 * 
	 @return mesLiens  	Vector<String>  composé des titres et liens absolus
	 * 
	 */
	// Fonction qui permet de lister les pages d'un utilisateur 
	static Vector<String >afficher_page(int id_user) throws SQLException, IOException{
		Connection myConn= connexion();
		stmt = myConn.createStatement();
		Vector<String> mesLiens= new Vector<String>();
		String sql="select titre,contenu from page where id_user="+id_user;
		myRs = stmt.executeQuery(sql);
		
		// 3. Set up a handle to the file
		
           int i=0;
		while (myRs.next()) {
			String titre= myRs.getString("titre");
			File theFile = new File("tbek/"+titre);
			output = new FileOutputStream(theFile);

			input1 = myRs.getBinaryStream("contenu");	
			byte[] buffer = new byte[1024];
      		while (input1.read(buffer) > 0) {
				output.write(buffer);
 			}
      		mesLiens.add(titre);
			String url =theFile.getAbsolutePath();
			mesLiens.add(url);
			i++;
		}
		close(myConn, stmt);
		System.out.println("\nCompleted successfully!");
		return mesLiens;
	}
	/**
	 * @author Houssam Karrach
	 * @author Ayoub el fatmi
	 * @version 0.1, ecrit le 17 Decembre 2014
	 * 
	 * Methode pour fermer la connexion avec une base de donnée
	 *
	 * 
	 */
	private static void close(Connection myConn, Statement myStmt)
			throws SQLException {

		if (myStmt != null) {
			myStmt.close();
		}
		
		if (myConn != null) {
			myConn.close();
		}
	}
	private static void closebd(Connection myConn, Statement myStmt,ResultSet myRes)
			throws SQLException {

		if (myStmt != null) {
			myStmt.close();
		}
		
		if (myConn != null) {
			myConn.close();
		}
		if (myRes != null) {
			myRes.close();
		}
	}
	public static void main(String[] args) throws Exception {

//		   	Test 
//		
//		    String title=PDFConvertisseur.sauvergardeArticle("http://www.melty.fr/downton-abbey-saison-5-episode-de-noel-une-video-promo-devoilee-a363782.html", "kkk.pdf");
//			inserer_page(2,title);
//			Vector<String> mesLiens=afficher_page(2);
//			for (String string : mesLiens) {
//				System.out.println(string);
//			}
//			
		
	}

	

}
