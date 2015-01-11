package tse.fi2.info4.tbek;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
/**
 * 
 *   On n'utilise plus cette clase 
 * 
 * 
 */



public class Connexionbd {
	static Connection con = null;
	static Statement s = null;
	static ResultSet resultat=null;
	
	public static ResultSet lecturebd() {
	    
    	s=connexion_bd( );	
    	try {
    		//con.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
    		s = con.createStatement();
    		resultat=s.executeQuery("SELECT *  FROM Utilisateur;");
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		/*finally {
    			connexionbd.fermeture_connexion(con,s , resultat);
    		}*/
    	
    		return resultat;	    		
    	}

	public static Statement connexion_bd( ){
		
		try{
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:projet_tbek.db");
			s = con.createStatement(); 
			
			}
		catch (Exception ex) {

    		System.out.println(ex.getMessage());
    		ex.printStackTrace(); }
		return s;
	}
	
	
	public static void fermeture_connexion(Connection con,Statement s ,ResultSet resultat){
		//"Fermeture de l'objet ResultSet."
        if ( resultat != null ) {
            try {
                resultat.close();
            } catch ( SQLException ignore ) {
            }
        }
        // "Fermeture de l'objet Statement." 
        if ( s != null ) {
            try {
                s.close();
            } catch ( SQLException ignore ) {
            }
        }
        // "Fermeture de l'objet Connection." 
        if ( con != null ) {
            try {
                con.close();
            } catch ( SQLException ignore ) {
            }
        }
    }
	
}
