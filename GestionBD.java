package tse.fi2.info4.tbek;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;


/**
 * @author Houssam Karrach
 * @author Ayoub el fatmi
 * @version 0.1, ecrit le 2 Janvier
 * 
 * Classe qui permet de sauvegarder et lire des pages sous format pdf
 * dans une base de donnee de type sqlite3
 * @param myConn 	Connection 	 permet d'établir une connexion avec bd mysql
 * @param mySTmt	PreparedStatement pour preparer une requete 
 * @param input     File 	fichier en entrée
 * @param input1   	File  	fichier en entrée
 * @param stmt 		Statement  Preparer une requele en mode select
 * @param myRs  	ResultSet  Resultat d'une requete sql
 */

public class GestionBD
{

	static Connection myConn = null;
	static PreparedStatement myStmt = null;
	static byte[] input = null;
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
	
	public static Connection connexion_bd() {
		
				try {
					Class.forName("org.sqlite.JDBC");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					myConn = DriverManager.getConnection("jdbc:sqlite:projet_tbek.db");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	    return myConn;
		
	}
	/**
	 * @author Houssam Karrach
	 * @author Ayoub el fatmi
	 * @version 0.1, ecrit le 28 Decembre 2014
	 * 
	 * Methode pour fermer la base de donnée
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
	/**
	 * @author Houssam Karrach
	 * @author Ayoub el fatmi
	 * @version 0.1, ecrit le 11 janvier 2014
	 * 
	 * Methode pour supprimer une page à partir de son id 
	 * 
	 * 
	 */
	
	public static void delete(String id_page) throws SQLException{
	    myConn=connexion_bd();
	      stmt=myConn.createStatement();
	      String sql = " Delete from page where id_page = "+id_page;                 
	      stmt.close();
	      stmt.executeUpdate(sql);
	      myConn.close();
	    System.out.println("Fichier supprimé de la bd");
	  
	
	        
	}
	/**
	 * @author Houssam Karrach
	 * @author Ayoub el fatmi
	 * @version 0.1, ecrit le 11 janvier 2014
	 * 
	 * Methode pour creer les tables Utilisateur et page
	 * il permet aussi de creer l'administrateur de la bd bob
	 * si celui n'existe pas
	 * 
	 * 
	 */
	public static void createTables() throws SQLException{
		     
	          myConn=connexion_bd();
		      stmt=myConn.createStatement();
		      String sql = "CREATE TABLE IF NOT EXISTS Utilisateur  " +
		                   "(ID INTEGER PRIMARY KEY      AUTOINCREMENT," +
		                   " username           TEXT    NOT NULL, " + 
		                   " password           TEXT    NOT NULL, " +
		                   " date_inscription            DATETIME     NOT NULL, " + 
		                   " date_connexion        DATETIME     NOT NULL )" ; 
		                   
		      stmt.executeUpdate(sql);
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM Utilisateur where id=1;" );
		      if(!rs.next()) {
		    	  sql= "INSERT INTO Utilisateur (id, username, password, date_inscription, date_connexion)"+" VALUES(" +
		    	       		"1, 'bob', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
		    	  stmt.executeUpdate(sql);
		      }
		      else{
		    	  System.out.println("Administrateur existant");
		      }
		      sql = "CREATE TABLE IF NOT EXISTS page  " +
	                   "(id_page INTEGER PRIMARY KEY      AUTOINCREMENT," +
	                   " titre           TEXT    NOT NULL, " + 
	                   " contenu           BLOB    NOT NULL, " +
	                   " id_user            INTEGER     DEFAULT NULL, " + 
	                   "FOREIGN KEY(id_user) REFERENCES Utilisateur(id))" ; 
		      
		      stmt.executeUpdate(sql);
		      rs.close();
		      stmt.close();
		      myConn.close();
		    

		    System.out.println("Table created successfully");
		  
		
	}
	
	/**
	 * @author Houssam Karrach
	 * @author Ayoub el fatmi
	 * @version 0.1, ecrit le 11 janvier 2014
	 * 
	 * Methode pour convertir un fichier en binaire
	 * necessaire pour stocker le fichier dans la bd
	 * 
	 * @return     byte[] 
	 */
	public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
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
	static void inserer_page(int id_user,String titre) throws SQLException, IOException{
		Connection myConn = connexion_bd();
		String sql = "insert into  page (titre,contenu,id_user) values(?,?,?)";
		myStmt = myConn.prepareStatement(sql);
		File theFile = new File("tbek/"+titre);
		input = getBytesFromFile(theFile);
		myStmt.setString(1,titre);
		myStmt.setBytes(2, input);
		//myStmt.setBlob(2, input);
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
	static Vector<String >afficher_page(int id_user) throws SQLException, IOException{
		Connection myConn= connexion_bd();
		stmt = myConn.createStatement();
		Vector<String> mesLiens= new Vector<String>();
		String sql="select id_page,titre,contenu from page where id_user="+id_user;
		myRs = stmt.executeQuery(sql);
		
		// 3. Set up a handle to the file
		
           int i=0;
		while (myRs.next()) {
			
			Integer id_page=myRs.getInt("id_page");
			String id_page1=id_page.toString();
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
			mesLiens.add(id_page1);
			i++;
		}
		close(myConn, stmt);
		System.out.println("\nCompleted successfully!");
		return mesLiens;
	}
	/**
	 * @author Houssam Karrach
	 * @author Ayoub el fatmi
	 * @version 0.1, ecrit le 28 Decembre 2014
	 * 
	 * Methode pour fermer la base de donnée
	 * 
	 * 
	 */
	public static void closebd(Connection myConn, Statement myStmt,ResultSet myRes)
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
	

	/*
	 * 
	 * 
	 */
	

	public static void main( String args[] ) throws SQLException, IOException
  {
	 
//		createTables();
//		 String title= "test.pdf";
//			inserer_page(1,title);
//			Vector<String> mesLiens=afficher_page(1);
//			for (String string : mesLiens) {
//				System.out.println(string);
//			}
		

	
  }
}