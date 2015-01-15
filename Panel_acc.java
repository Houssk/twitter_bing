package tse.fi2.info4.tbek;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import tse.fi2.info4.tbek.*;


public class Panel_acc extends JPanel{  
/**
 * @author Julien Tissier
 * @author Nader Ben Abdeljelil
 * @version 0.1, ecrit le 15 Decembre 2014
 * 
 * Methode principal qui cree la panel d'accueil ainsi que tous les
 * widgets la composant.
 */    
   
	public Panel_acc(final JFrame f) {
		super();
		this.f=f;
        initComponents();
    }
	
	static // Creation de la fenetre ainsi que de tous les elements la composant		
			JFrame f = new JFrame("projet tbek");
			JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);
			JPanel pannel = this;
			JPanel onglet2 = new JPanel();
			JPanel global_panel = new JPanel();
			JPanel p1 =new JPanel();
			
			JPanel identification = new JPanel();
			GroupLayout identificationLayout = new GroupLayout(identification);
			
			JButton New = new JButton();
			JButton connexion = new JButton();
			JTextField username = new JTextField();
			JPasswordField password = new JPasswordField();
			JLabel jLabel1 = new JLabel();
			JLabel jLabel2 = new JLabel();
			Connection con = null;
    		Statement s = null;
    		ResultSet resultat=null;
    		static String nom="user";
    		ResultSet resultat1=null;
    		static int id_user=0;
    		
			
	private String initComponents() {
        
        // Recuperation des tendances actuelles
		final Vector<String> lesTendances = Tendance.recupereTendance();
		identification.setLayout(identificationLayout);
		global_panel.setLayout(new BorderLayout());
        identification.setBackground(new Color(64,153,255));

        New.setBackground(new Color(64,153,255));
        New.setFont(new Font("Tahoma", 0, 10));
        New.setForeground(new Color(255, 255, 255));
        New.setText("New");
        New.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	if(!username.getText().isEmpty()&&!password.getText().isEmpty()){
            		
            		try{
            			s=Connexionbd.connexion_bd( );
            			resultat=s.executeQuery("SELECT id FROM Utilisateur WHERE username ='"+username.getText()+"';");
            			while(resultat.next()){
            				id_user=resultat.getInt("id");
            			}
            			if(resultat.next())
            			{   
            				JOptionPane.showMessageDialog(null,"nom d'utilisateur non disponible");
                    		username.setText("");
                    		password.setText("");	
            			}
            			else{
            				int statut = s.executeUpdate( "INSERT INTO Utilisateur (username, password, date_inscription,date_connexion)"
            						+ " VALUES ('"+username.getText()+"', '"+password.getText()+"', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);" );
            				
            				nom=username.getText();
            				s=Connexionbd.connexion_bd( );
                			resultat=s.executeQuery("SELECT id FROM Utilisateur WHERE username ='"+username.getText()+"';");
                			while(resultat.next()){
                				id_user=resultat.getInt("id");
                			}
            				FrameWelcome t= new FrameWelcome();
                			t.go(160);
                			f.setVisible(false);
           					System.out.println("connexion à l'interface utili  "+nom);
            			}
            		}
            		catch (Exception ex) {

                		System.out.println(ex.getMessage());
                		ex.printStackTrace(); }
            		finally {
            			Connexionbd.fermeture_connexion(con,s , resultat);
                    }
            	}
            	else{
            		JOptionPane.showMessageDialog(null,"inserer un login et mot de passe svp");
            		username.setText("");
            		password.setText("");
            		}
            }
        });


        connexion.setBackground(new Color(64,153,255));
        connexion.setFont(new Font("Tahoma", 0, 10));
        connexion.setForeground(new Color(255, 255, 255));
        connexion.setText("Connexion");
        


        jLabel1.setText("Username");

        jLabel2.setText("Password");
		
		 identificationLayout.setHorizontalGroup(
				 identificationLayout.createParallelGroup(Alignment.LEADING)
		            .addGroup(Alignment.TRAILING, identificationLayout.createSequentialGroup()
		                .addContainerGap(290, Short.MAX_VALUE)
		                .addGroup(identificationLayout.createParallelGroup(Alignment.LEADING)
		                    .addComponent(username, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
		                    .addComponent(jLabel1))
		                .addGap(18, 18, 18)
		                .addGroup(identificationLayout.createParallelGroup(Alignment.LEADING)
		                    .addGroup(identificationLayout.createSequentialGroup()
		                        .addComponent(password, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
		                        .addGap(14, 14, 14)
		                        .addComponent(connexion)
		                        .addPreferredGap(ComponentPlacement.RELATED)
		                        .addComponent(New))
		                    .addComponent(jLabel2))
		                .addContainerGap())
		        );
		 	identificationLayout.setVerticalGroup(
		 			identificationLayout.createParallelGroup(Alignment.LEADING)
		            .addGroup(Alignment.TRAILING, identificationLayout.createSequentialGroup()
		                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                .addGroup(identificationLayout.createParallelGroup(Alignment.BASELINE)
		                    .addComponent(jLabel1)
		                    .addComponent(jLabel2))
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addGroup(identificationLayout.createParallelGroup(Alignment.BASELINE)
		                    .addComponent(New)
		                    .addComponent(connexion)
		                    .addComponent(password, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                    .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		        );
		
		

		JPanel titre_panel = new JPanel();
		JLabel titre = new JLabel("Liste des tendances");
		titre_panel.add(titre);

		JPanel button_panel = new JPanel();
		JButton actualiser = new JButton("Actualiser");
		actualiser.setPreferredSize(new Dimension(100, 30));
		button_panel.add(actualiser);
		
        
        // trois listes avec srollbar : 
        //  -->  une a gauche pour les tendances
        //  -->  une au centre pour les tweets
		//  -->  une a droite pour les news
        
		JScrollPane selection_tendances = new JScrollPane();
        // La liste a gauche doit pouvoir etre modifiable. Ce n'est pas
        // possible avec une JListe, donc on utilise un listModel
        final DefaultListModel listeModel_tendances = new DefaultListModel();
		final JList liste_tendances = new JList(listeModel_tendances);
		selection_tendances.setViewportView(liste_tendances);
		selection_tendances.setPreferredSize(new Dimension(300,260));
        
        // remplit la liste avec les tendances
        for (String tendance : lesTendances)
        {
            listeModel_tendances.addElement(tendance);
        }
		
        
        JScrollPane selection_tweets = new JScrollPane();
		// La liste a droite doit pouvoir etre modifiable. Ce n'est pas
        // possible avec une JListe, donc on utilise un listModel
        final DefaultListModel listeModel_tweets = new DefaultListModel();
        final JList liste_tweets = new JList(listeModel_tweets);
        selection_tweets.setViewportView(liste_tweets);
        selection_tweets.setPreferredSize(new Dimension(350,260));
        
        JScrollPane selection_news = new JScrollPane();
        final DefaultListModel listeModel_news = new DefaultListModel();
        final JList liste_news = new JList(listeModel_news);
        selection_news.setViewportView(liste_news);
        selection_news.setPreferredSize(new Dimension(350,260));
        

		// A chaque fois que l'on clique sur une tendance, un message 
        listeModel_tweets.addElement("Il faut se connecter pour voir les Tweets");
        listeModel_news.addElement("Il faut se connecter pour voir les News");
      
            	
           actualiser.addActionListener(actualisation);
          // connexion.addActionListener(authentification);
           connexion.addActionListener(new ActionListener() {
        	   public void actionPerformed(ActionEvent e)
               {
					if(!username.getText().isEmpty()&&!password.getText().isEmpty()){
            		
            		s=Connexionbd.connexion_bd( );
            		try{
            			resultat1=s.executeQuery("Select id from Utilisateur WHERE username ='"+username.getText()+"';");
                        while(resultat1.next()){
                        	id_user=resultat1.getInt(1);
                        	System.out.println(id_user);
                        }
                        resultat1.close();
            			resultat=s.executeQuery("SELECT username ,password FROM Utilisateur WHERE username ='"+username.getText()+"'AND password ='"+password.getText()+"' ;");
            			
            			if(resultat.next())
            			{
            					nom = resultat.getString("username");
            					System.out.println(nom);
            				//if(user.equals("bob")){
            					FrameWelcome t= new FrameWelcome();
                				t.go(160);
                				f.setVisible(false);
           						
           						System.out.println("connexion à l'interface   "+nom);
           						//Interface_admin ad =new Interface_admin();
        					//}
//        					else {
//        						FrameWelcome t= new FrameWelcome();
//                				t.go(160);
//                				f.setVisible(false);
//           						nom=username.getText();
//           						System.out.println("connexion à l'interface utili  "+nom);
//            				}
							int statut = s.executeUpdate( "UPDATE Utilisateur SET date_connexion= CURRENT_TIMESTAMP WHERE username='"+username.getText()+"';" );
                			
            			}
            			 else{
        					 JOptionPane.showMessageDialog(null,"login ou mot de passe incorrect");
        					 username.setText("");
        					 password.setText("");
        				 }

            		}
            		catch (Exception ex) {

                		System.out.println(ex.getMessage());
                		ex.printStackTrace(); }
            		finally {
            			Connexionbd.fermeture_connexion(con,s , resultat);
            		
                    }
            	}
            	else{
					 JOptionPane.showMessageDialog(null,"inserer votre login et mot de passe svp");
					 username.setText("");
					password.setText("");
				 }
               }
           });
           
     // Placement des differents widgets dans la fenetre
     		global_panel.add(titre_panel, BorderLayout.NORTH);	
     		global_panel.add(button_panel, BorderLayout.SOUTH);
     		global_panel.add(selection_tendances, BorderLayout.WEST);	
     		global_panel.add(selection_tweets, BorderLayout.CENTER);
     		global_panel.add(selection_news, BorderLayout.EAST);
     		
     		onglets.setPreferredSize(new Dimension(1000, 300));
     		p1.setPreferredSize(new Dimension(1000, 300));
     	    p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
     	    p1.add(identification);
     	    p1.add(global_panel);
     	    
     	    onglets.addTab("Trends", p1);
		    
		    onglet2.setPreferredSize(new Dimension(1000, 350));
		    JPanel p_aide =new Aide();
		    p_aide.setPreferredSize(new Dimension(1000, 300));
		    onglet2.add(p_aide);
		    onglets.addTab("Aide", onglet2);
		    onglets.setOpaque(true);
		    pannel.add(onglets);
     	    
     	   return nom;
	}

        // A chaque fois que l'on clique sur le bouton Actualiser,un message
        //"veuillez connecter pour pouvoir actualiser la liste des tendances"
        ActionListener actualisation = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	JOptionPane.showMessageDialog(null,"Veuillez vous connecter pour avoir le droit d'actualiser la liste des tendances");
            }
            
        };

/*public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {

        public void run() {
J			Panel p =new Panel_acc(f); 
        	f.setVisible(true);	
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.add(p);
			f.setSize(1000, 300);
			f.setLocationRelativeTo(null);
        	
        }
    });
}*/
}


