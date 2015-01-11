package tse.fi2.info4.tbek;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * @author Julien Tissier
 * @author Nader Ben Abdeljelil
 * @author Houssam Karrach
 * @version 0.1, ecrit le 1 Novembre 2014
 * 
 * Classe representant l'interface graphique. L'ensemble des widgets (bouton, liste)
 * de l'interface graphique sont declares dans cette classe. Les actions
 * listener sont aussi declares dans cette classe.
 * */

public class Interface_acc extends JFrame{  
/**
 * @author Julien Tissier
 * @author Nader Ben Abdeljelil
 * @version 0.1, ecrit le 1 Novembre 2014
 * 
 * Methode principal qui cree la fenetre d'affichage ainsi que tous les
 * widgets la composant.
 * @throws SQLException 
 */    
   
	public Interface_acc() throws SQLException {
		super();
		GestionBD.createTables();
		File theDir= new File("tbek");
		if(!theDir.exists()){
			try{
				theDir.mkdir();
				
			}
			catch(SecurityException se){
				se.printStackTrace();
				
			}
		}
		JPanel p=new Panel_acc(f);
		f.setVisible(true);	
 		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		f.add(p);
 		f.setSize(1000, 300);
 		f.setLocationRelativeTo(null);
		
       // initComponents();
    }
	

	
	// Creation de la fenetre ainsi que de tous les elements la composant		
			final JFrame f = new JFrame("projet tbek");
			

public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {

        public void run() {
        	try {
				Interface_acc f =new Interface_acc();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
        	
        }
    });
}
}
