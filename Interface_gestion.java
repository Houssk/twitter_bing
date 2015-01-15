package tse.fi2.info4.tbek;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.TableModel;




public class Interface_gestion extends JFrame {  
	static Connection con = null;
	static Statement s = null;
	static ResultSet resultat=null;
	//final JFrame f =new JFrame("Gestion des Utilisateurs");
	static JTable tableau = new JTable ();
	JPanel global_panel = new JPanel();
	JScrollPane selection_tendances = new JScrollPane(tableau);
	public Interface_gestion() {
        initComponents();
    }
	
	private void initComponents()  {
	
  
		// Creation de la fenetre ainsi que de tous les elements la composant		
		final JFrame f = new JFrame("Gestion des Utilisateurs");
		global_panel.setLayout(new BorderLayout());

		JPanel titre_panel = new JPanel();
		JLabel titre = new JLabel("Gestion des Utilisateurs");
		titre_panel.add(titre);
		JPanel ajouter = new JPanel();
		ajouter.setPreferredSize(new Dimension(240,70));
		JPanel supp = new JPanel();
		supp.setPreferredSize(new Dimension(240,70));
		JButton add = new JButton("Ajouter");
		ajouter.setPreferredSize(new Dimension(50, 30));
		JButton mod = new JButton("Modifier");
		ajouter.setPreferredSize(new Dimension(50, 30));
		JButton delete = new JButton("Supprimer");
		delete.setPreferredSize(new Dimension(50, 30));
		final JTextField username =new JTextField();
		username.setPreferredSize(new Dimension(80, 30));
		final JTextField password =new JTextField();
		password.setPreferredSize(new Dimension(80, 30));
		final JTextField id =new JTextField();
		JLabel ajou = new JLabel("Ajouter , Modifier ou Supprimer");
		JLabel supprime = new JLabel("Supprimer");
		JPanel button_panel = new JPanel();
		JButton retour = new JButton("Retour");
		retour.setPreferredSize(new Dimension(100, 30));
		button_panel.add(retour);
		
		
		JPanel b1 = new JPanel();
	    //On d�finit le layout en lui indiquant qu'il travaillera en ligne
	    b1.setLayout(new BoxLayout(b1, BoxLayout.LINE_AXIS));
	    b1.add(ajou);

	    JPanel b2 = new JPanel();
	    //Idem pour cette ligne
	    b2.setLayout(new BoxLayout(b2, BoxLayout.LINE_AXIS));
	    b2.add(new JLabel("iden_user"));
	    b2.add(id);

	    JPanel b3 = new JPanel();
	    //Idem pour cette ligne
	    b3.setLayout(new BoxLayout(b3, BoxLayout.LINE_AXIS));
	    b3.add(new JLabel("Username"));
	    b3.add(username);
	    
	    JPanel b4 = new JPanel();
	    //Idem pour cette ligne
	    b4.setLayout(new BoxLayout(b4, BoxLayout.LINE_AXIS));
	    b4.add(new JLabel("Password"));
	    b4.add(password);
		
		ajouter.setLayout(new BoxLayout(ajouter, BoxLayout.PAGE_AXIS));
		ajouter.add(b1);
 	    ajouter.add(b2);
 	    ajouter.add(b3);
 	    ajouter.add(b4);
		
 	   JPanel s1 = new JPanel();
	    //On d�finit le layout en lui indiquant qu'il travaillera en ligne
	    s1.setLayout(new BoxLayout(s1, BoxLayout.LINE_AXIS));
	    s1.add(supprime);

	    JPanel s2 = new JPanel();
	    //Idem pour cette ligne
	    s2.setLayout(new BoxLayout(s2, BoxLayout.LINE_AXIS));
	    s2.add(new JLabel("       "));
	    JPanel s3 = new JPanel();
	    //Idem pour cette ligne
	    s3.setLayout(new BoxLayout(s3, BoxLayout.LINE_AXIS));
	    s3.add(add);
	    s3.add(mod);
	    //s3.add(delete);
	   
	    JPanel s4 = new JPanel();
	    //Idem pour cette ligne
	    s4.setLayout(new BoxLayout(s4, BoxLayout.LINE_AXIS));
	    s4.add(delete);
	    
 	    supp.setLayout(new BoxLayout(supp, BoxLayout.PAGE_AXIS));
		supp.add(s1);
	    supp.add(s2);
	    supp.add(s3);
	    supp.add(s4);

//	    ResultSet rs =Connexionbd.lecturebd();
//	    final MyTableModel rtm = new MyTableModel( rs );
//	    tableau.setModel(rtm);
	    try {
			tableau=MyTableModel.table();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    selection_tendances = new JScrollPane(tableau);
	    selection_tendances.setPreferredSize(new Dimension(500,260));

       ActionListener retour_appli= new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {    
            	f.setVisible(false);
            	//Interface_admin ad =new Interface_admin();
            }
        };
	    
	    MouseListener mouseListener = new MouseAdapter() 
        {
		    public void mouseClicked(MouseEvent e) 
            {
		        if (e.getClickCount() == 1) 
                {
//		              try {
//					
//					    JScrollPane selection_tendances = new JScrollPane(MyTableModel.actualisation());
//					    selection_tendances.setPreferredSize(new Dimension(500,260));
//					    tableau.validate();
//					
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//		        	
		        	
		        	int ligneSelectionne = tableau.getSelectedRow();
		        	//on r�cup�re la valeur  de la ligne s�lectionn�
		        	String s=tableau.getValueAt(ligneSelectionne, 0).toString();
		        	id.setText(s);
		        	username.setText((String) tableau.getValueAt(ligneSelectionne, 1));
		        	password.setText((String) tableau.getValueAt(ligneSelectionne, 2));
		        	id.setEditable(false);
		        	
                }
            
            }
      	};
		        
      	tableau.addMouseListener(mouseListener);  
  
        ActionListener suppression = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	if(!username.getText().isEmpty()&&!id.getText().isEmpty()){
            		
            		try{
            			con = GestionBD.connexion_bd();
            			s=con.createStatement();
            			resultat=s.executeQuery("SELECT id FROM Utilisateur WHERE username ='"+username.getText()+"'AND id ='"+id.getText()+"';");
            			if(resultat.next())
            			{
            				String identi = resultat.getString("id");
            				if(identi.equals(id.getText())){
            					int statut = s.executeUpdate( "DELETE FROM Utilisateur WHERE id ='"+id.getText()+"';" );
                				
                				id.setText("");
                				username.setText("");
                        		password.setText("");
                        		
            				}
            				else{
            					JOptionPane.showMessageDialog(null," identifiant et nom d'utilisateur non conforme.");
            					id.setText("");
            					username.setText("");
                        		password.setText("");
            				}
            				
            			}
            			else{
            				JOptionPane.showMessageDialog(null," identifiant ou nom d'utilisateur non reconnu.");
            				id.setText("");
            				username.setText("");
                    		password.setText("");	
            			}
            		}
            		catch (Exception ex) {

                		System.out.println(ex.getMessage());
                		ex.printStackTrace(); }
            		finally {
            			try {
            				resultat.close();
            				s.close();
							con.close();
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
            			
                    }
            		   
            		try {
            			//selection_tendances.removeAll();
            			actualisation();
					  
				       
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	}
            	else{
            		JOptionPane.showMessageDialog(null,"inserer identifiant ou nom d'utilisateur svp");
            		id.setText("");
            		username.setText("");
            		password.setText("");
            		}
            	
            }
            
        };
        
        
        ActionListener modification = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	if(!username.getText().isEmpty()&&!password.getText().isEmpty()){
            		
            		try{
            			con = GestionBD.connexion_bd();
            			s=con.createStatement();
            			resultat=s.executeQuery("SELECT id FROM Utilisateur WHERE username ='"+username.getText()+"';");
            			if(resultat.next())
            			{
            				int statut = s.executeUpdate( "UPDATE Utilisateur SET password ='"+password.getText()+"' WHERE username='"+username.getText()+"';" );
            				//int statut1 = s.executeUpdate( "commit;");	
            				id.setText("");
            				username.setText("");
                    		password.setText("");
            			}
            			else{
            				JOptionPane.showMessageDialog(null,"nom d'utilisateur non existant.\n ");
            				id.setText("");
            				username.setText("");
                    		password.setText("");	
            			}
            		}
            		catch (Exception ex) {

                		System.out.println(ex.getMessage());
                		ex.printStackTrace(); } 
            		

            		finally {
            			
            			try {
            				resultat.close();
            				s.close();
							con.close();
							
	            			
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                    }
            		try {
            			actualisation();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	}
            	else{
            		JOptionPane.showMessageDialog(null,"inserer login et mot de passe svp");
            		id.setText("");
            		username.setText("");
            		password.setText("");
            		}
            }

        };
        
        
        ActionListener ajouter_uti = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	if(!username.getText().isEmpty()&&!password.getText().isEmpty()){

            		try{
            			con = GestionBD.connexion_bd();
            			s=con.createStatement();
            			resultat=s.executeQuery("SELECT id FROM Utilisateur WHERE username ='"+username.getText()+"';");
            			if(resultat.next())
            			{
            				JOptionPane.showMessageDialog(null,"nom d'utilisateur non disponible");
            				id.setText("");
            				username.setText("");
                    		password.setText("");	
            			}
            			else{
            				int statut = s.executeUpdate( "INSERT INTO Utilisateur (username, password, date_inscription,date_connexion)"
            						+ " VALUES ('"+username.getText()+"', '"+password.getText()+"', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);" );
            				//int statut1 = s.executeUpdate( "commit;");
            				id.setText("");
            				username.setText("");
                    		password.setText("");
            			}
            		}
            		catch (Exception ex) {

                		System.out.println(ex.getMessage());
                		ex.printStackTrace(); }
            		finally {
                		//"Fermeture connexion"
            			try {
            				resultat.close();
            				s.close();
							con.close();
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
            			//Connexionbd.fermeture_connexion(con,s , resultat);
                    }
            		try {
            			actualisation();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	}
            	else{
            		JOptionPane.showMessageDialog(null,"inserer login et mot de passe svp");
            		id.setText("");
            		username.setText("");
            		password.setText("");
            		}
            }
        };
        delete.addActionListener(suppression);   	
        mod.addActionListener(modification);
        retour.addActionListener(retour_appli);
        add.addActionListener(ajouter_uti);
        global_panel.add(titre_panel, BorderLayout.NORTH);	
		global_panel.add(button_panel, BorderLayout.SOUTH);
		global_panel.add(selection_tendances, BorderLayout.WEST);	
		global_panel.add(ajouter, BorderLayout.CENTER);
		global_panel.add(supp, BorderLayout.EAST);

		f.setSize(980, 220);
		f.add(global_panel);
		pack();
		f.setVisible(true);	
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);

       

    }
	
	public void actualisation() throws SQLException {
   	    //try{
    	ResultSet new_rs =MyTableModel.lecturebd();
	    final MyTableModel new_rtm = new MyTableModel( new_rs );	    
	    tableau.setModel(new_rtm);
	    //}
   	    
	   // finally {
		//	connexionbd.fermeture_connexion(con,s , resultat);
       // }

}
	 public static void main(String[] args) {
		 EventQueue.invokeLater(new Runnable() {

		        public void run() {
		        	Interface_gestion admin =new Interface_gestion(); 
		        }
		    });
	 }

}