package tse.fi2.info4.tbek;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import tse.fi2.info4.tbek.Aide;

public class Interface_utilisateur extends JFrame{
	
	public Interface_utilisateur() {
		super();
        try {
			initComponents();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	// Creation de la fenetre ainsi que de tous les elements la composant		
				final JFrame f = new JFrame("");
			    JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);
			    JPanel pannel = new JPanel();
			    static JPanel onglet2 = new JPanel();
			    JPanel onglet3 = new JPanel();
				JPanel global_panel = new JPanel();
				JPanel bande_sup = new JPanel();
				JPanel p1 = new JPanel();
				GroupLayout bande_supLayout = new GroupLayout(bande_sup);
				JButton deconnexion = new JButton();
				JLabel jLabel1 = new JLabel();
				JLabel jLabel2 = new JLabel();

	private void initComponents() throws SQLException, Exception {


	        
	        // Recuperation des tendances actuelles
			final Vector<String> lesTendances = Tendance.recupereTendance();
			final Vector lesTweets = new Vector();
			final Vector lesNews = new Vector();
			
			global_panel.setLayout(new BorderLayout());
			bande_sup.setLayout(bande_supLayout);
	        // Pour chaque tendances, recuperation de 10 tweets relatifs
	        for (String hashtag : lesTendances)
	        {
	            // some_tweets est un vector de 10 tweets, donc lesTweets
	            // est un vector de vector
	            Vector<String> some_tweets = TweetUtilisateurs.recupereTweets(hashtag);
	            lesTweets.add(some_tweets);
	        }
	        // Pour chaque tendances, recuperation des news relatifs
	        for (String hashtag : lesTendances)
	        {
	        	// some_news est un vector de news 
	            Vector<String> some_news = BingSearch.recupereNews(hashtag);
	            lesNews.add(some_news);
	        }    
				
			
			
			
			bande_sup.setBackground(new Color(64,153,255));

			jLabel1.setText("Bienvenue");
	        jLabel2.setText(Panel_acc.nom);
	        deconnexion.setBackground(new Color(64,153,255));
	        deconnexion.setFont(new Font("Tahoma", 0, 10));
	        deconnexion.setForeground(new Color(255, 255, 255));
	        deconnexion.setText("Deconnexion");
	        deconnexion.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	JPanel p = new Panel_acc(f);
	            	pannel.removeAll();
	            	f.setTitle("projet tbek");
	            	//f.setSize(1010, 400);
	            	f.add(p);
	            	f.validate();
	            }
	        });
			
	        bande_supLayout.setHorizontalGroup(
	        		bande_supLayout.createParallelGroup(Alignment.LEADING)
		            .addGroup(bande_supLayout.createSequentialGroup()
	        		.addContainerGap(353, Short.MAX_VALUE)
	                .addGroup(bande_supLayout.createParallelGroup(Alignment.LEADING)
	                    .addGroup(Alignment.TRAILING, bande_supLayout.createSequentialGroup()
	                        .addComponent(jLabel1)
	                        .addGap(18, 18, 18)
	                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
	                        .addGap(113, 113, 113))
	                    .addGroup(Alignment.TRAILING, bande_supLayout.createSequentialGroup()
	                        .addComponent(deconnexion)
	                        .addGap(27, 27, 27))))
	        );
	        bande_supLayout.setVerticalGroup(
	        		bande_supLayout.createParallelGroup(Alignment.LEADING)
	            .addGroup(bande_supLayout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(bande_supLayout.createParallelGroup(Alignment.LEADING)
	                    .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel1))
	                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(deconnexion)
	                .addContainerGap())
	        );
			
			

			JPanel titre_panel = new JPanel();
			JLabel titre = new JLabel("Liste des tendances");
			titre_panel.add(titre);

			JPanel button_panel = new JPanel();
			JButton actualiser = new JButton("Actualiser");
			actualiser.setPreferredSize(new Dimension(150, 30));
			button_panel.add(actualiser);
			JButton recherche_dict = new JButton("Recherche avancee 2");
			recherche_dict.setPreferredSize(new Dimension(210, 30));
			JButton recherche_maj= new JButton("Recherche avancee 1");
			recherche_maj.setPreferredSize(new Dimension(210, 30));
			JButton sauvegarder = new JButton("Sauvegarder");
			sauvegarder.setPreferredSize(new Dimension(150, 30));
			button_panel.add(recherche_maj);
			button_panel.add(recherche_dict);
			button_panel.add(sauvegarder);
			
	        
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
			selection_tendances.setPreferredSize(new Dimension(200,260));
	        
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
	        selection_tweets.setPreferredSize(new Dimension(400,260));
	        
	        JScrollPane selection_news = new JScrollPane();
	        final DefaultListModel listeModel_news = new DefaultListModel();
	        final JList liste_news = new JList(listeModel_news);
	        selection_news.setViewportView(liste_news);
	        selection_news.setPreferredSize(new Dimension(400,260));
	        
			
	        
	        
			// A chaque fois que l'on clique sur une tendance, il faut 
	        // mettre a jour les 2 listes (centre et a droite) , c'est-a-dire la vider et
	        // les remplir avec les tweets et les news de la tendance selectionnee
			MouseListener mouseListener = new MouseAdapter() 
	        {
			    public void mouseClicked(MouseEvent e) 
	            {
			        if (e.getClickCount() == 1) 
	                {
	                    // enleve tous les elements de la liste tweets+news
	                    listeModel_tweets.removeAllElements();
	                    listeModel_news.removeAllElements();
	                    
	                    // Recupere l'indice de l'item selectionne pour avoir les tweets et news corespondants
	                    int index = (int) liste_tendances.getSelectedIndex();
	                    
	                    // On va parcourir le vector des tweets concernant 
	                    // la tendance selectionnee
	                    int size = ((Vector<String>) lesTweets.get(index)).size();
	                    
	                    for (int i = 0; i < size; i = i+2)
	                    {
	                        String auteur = ((Vector<String>) lesTweets.get(index)).get(i);
	                        String message = ((Vector<String>) lesTweets.get(index)).get(i+1);
	                        String element_formate = Utils.formateTweet(message,auteur);
	                            
	                        // ajoute l'auteur ainsi que son message a la liste des tweets
	                        listeModel_tweets.addElement(element_formate);

	                    }
	                    // On va parcourir le vector des tweets concernant 
	                    // la tendance selectionnee
	                    int size2 = ((Vector<String>) lesNews.get(index)).size();
	                    String element_news= new String();
	                   if(((Vector<String>) lesNews.get(index)).size()!=0){
	                    for (int i = 0; i < size2; i = i+4)
	                    {
	                        String title = ((Vector<String>) lesNews.get(index)).get(i);
	                        String source = ((Vector<String>) lesNews.get(index)).get(i+1);
	                        String date = ((Vector<String>) lesNews.get(index)).get(i+2);
	                        String newDate=date.substring(0, 10)+" à "+ date.substring(11, 19);
	                       // Utilise le html pour rendre les tweets presentables. 
	                        element_news=Utils.formateNews(title,source,newDate);
	                        // ajoute le titre,source et date a la liste des news
	                        listeModel_news.addElement(element_news);

	                    }
	                   }
	                   else {
	                	   element_news=" Il n'y a pas de news relatives à  "+ lesTendances.get(index);
	                	   listeModel_news.addElement(element_news);
	                   }
	                    }
	                    
	              }
			};
			liste_tendances.addMouseListener(mouseListener);
	        
	        
	        // A chaque fois que l'on clique sur le bouton Actualiser, on 
	        // doit verifier s'il y a des nouvelles tendances. Si c'est le
	        // cas, on met à jour la liste des tendances, la liste des 
	        // tweets et news pour chaque nouvelle tendance, et les affichages de 
	        // ces deux listes
	        ActionListener actualisation = new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                // On recupere les nouvelles tendances
	                Vector<String> nouvelles_tendances = Tendance.recupereTendance();
	                
	                // Variable permettant de savoir s'il y a des nouvelles 
	                // tendances
	                boolean est_different = false;
	                
	                // On parcourt les deux vectors pour voir s'il y a des differences
	                for (int i = 0; i < nouvelles_tendances.size(); i++)
	                {
	                    // Si l'une des tendances est differente
	                    if (!(nouvelles_tendances.get(i).equals(lesTendances.get(i))))
	                    {
	                        // on supprime les tendances
	                        lesTendances.clear();
	                        
	                        // on supprime l'ensemble des tweets, puisqu'il faut updater
	                        lesTweets.clear();
	                        
	                        //on supprime l'ensemble des news
	                        lesNews.clear();
	                        
	                        // on vide la liste des anciennes tendances et on la remplit a nouveau
	                        listeModel_tendances.removeAllElements();
	                        for (String tendance : nouvelles_tendances)
	                        {
	                            lesTendances.add(tendance);
	                            // recherche 10 tweets relatifs a la nouvelle tendance
	                            Vector<String> some_tweets = TweetUtilisateurs.recupereTweets(tendance);
	                            lesTweets.add(some_tweets);
	                            // recherche les news relatifs a la nouvelle tendance
	                            Vector<String> some_news = BingSearch.recupereNews(tendance);
	                            lesNews.add(some_news);
	                            listeModel_tendances.addElement(tendance);
	                        }
	                        
	                        // on indique qu'il y a eu un changement
	                        est_different = true;
	                        
	                        // sort de la boucle for. ca ne sert a rien de 
	                        // poursuivre, on a tout updater
	                        break; 
	                    }
	                }
	                
	                // si apres avoir parcouru les deux vectors, aucune difference
	                // n'a ete trouvee, alors on previent l'utilisateur via une 
	                // boite de dialogue qu'il n'y a pas de nouvelles tendances
	                if (!est_different)
	                {
	                    JOptionPane.showMessageDialog(f, "Il n'y a pas de nouvelles tendances.");
	                }
	            }
	        };
	        ActionListener recherche_avancee_maj = new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
							// Recupere l'indice de l'item selectionne pour avoir la tendance
							int index = (int) liste_tendances.getSelectedIndex();
							String hashtag = lesTendances.get(index);
							
							listeModel_news.removeAllElements();
	                        ((Vector<String>) lesNews.get(index)).clear();
	                        
	                        
							
							String correct_hashtag = Utils.decouperMaj(hashtag);
							System.out.println(correct_hashtag);
							Vector<String> some_news = BingSearch.recupereNews(correct_hashtag);
	                        for (String news : some_news)
	                        {
								//System.out.println(news);
								((Vector<String>) lesNews.get(index)).add(news);
							}
	                        
	                        // On va parcourir le vector des news  de la tendance selectionnee
							int size = ((Vector<String>) lesNews.get(index)).size();
							String element_news= new String();
							
							if (((Vector<String>) lesNews.get(index)).size() != 0)
							{
								for (int i = 0; i < size; i = i+4)
								{
									String title = ((Vector<String>) lesNews.get(index)).get(i);
									String source = ((Vector<String>) lesNews.get(index)).get(i+1);
									String date = ((Vector<String>) lesNews.get(index)).get(i+2);
									String newDate=date.substring(0, 10)+" à "+ date.substring(11, 19);
									// Utilise le html pour rendre les tweets presentables. 
									element_news=Utils.formateNews(title,source,newDate);
									//System.out.println(element_news);
									// ajoute le titre,source et date a la liste des news
									listeModel_news.addElement(element_news);
								}
							}
							else 
							{
								element_news=" Il n'y a pas de news relatives à cette tendance";
								element_news=Utils.formateTweet("Il n'y a pas de news relatives à "+lesTendances.get(index) + " meme avec la recherche avancee 1.","");
								listeModel_news.addElement(element_news);
							}
	                        
	            }           
			};
			
			 ActionListener recherche_avancee_dict = new ActionListener()
		        {
		            public void actionPerformed(ActionEvent e)
		            {
								// Recupere l'indice de l'item selectionne pour avoir la tendance
								int index = (int) liste_tendances.getSelectedIndex();
								String hashtag = lesTendances.get(index);
								
								listeModel_news.removeAllElements();
		                        ((Vector<String>) lesNews.get(index)).clear();
		                        
		                        
								
								String correct_hashtag = Utils.decoupage_dictionnaire(Utils.removeHashtag(hashtag));
								System.out.println(correct_hashtag);
								Vector<String> some_news = BingSearch.recupereNews(correct_hashtag);
		                        for (String news : some_news)
		                        {
									//System.out.println(news);
									((Vector<String>) lesNews.get(index)).add(news);
								}
		                        
		                        // On va parcourir le vector des news  de la tendance selectionnee
								int size = ((Vector<String>) lesNews.get(index)).size();
								String element_news= new String();
								
								if (((Vector<String>) lesNews.get(index)).size() != 0)
								{
									for (int i = 0; i < size; i = i+4)
									{
										String title = ((Vector<String>) lesNews.get(index)).get(i);
										String source = ((Vector<String>) lesNews.get(index)).get(i+1);
										String date = ((Vector<String>) lesNews.get(index)).get(i+2);
										String newDate=date.substring(0, 10)+" à "+ date.substring(11, 19);
										// Utilise le html pour rendre les tweets presentables. 
										element_news=Utils.formateNews(title,source,newDate);
										//System.out.println(element_news);
										// ajoute le titre,source et date a la liste des news
										listeModel_news.addElement(element_news);
									}
								}
								else 
								{
									element_news=" Il n'y a pas de news relatives à cette tendance";
									element_news=Utils.formateTweet("Il n'y a pas de news relatives a  "+lesTendances.get(index) + " meme avec le recherche avancee 2.","");
									listeModel_news.addElement(element_news);
								}
		                        
		            }           
				};
			MouseListener doubleclicNews = new MouseAdapter() 
        {
		    public void mouseClicked(MouseEvent e) 
            {
		        if (e.getClickCount() == 2) 
                {
                    
                    int index_tendance = (int) liste_tendances.getSelectedIndex();
                    int index_news = (int) liste_news.getSelectedIndex();
                   
					Vector<String> lesNewsDeLaTendance = ((Vector<String>) lesNews.get(index_tendance));
					
                    String url = new String();
                    url = lesNewsDeLaTendance.get(index_news*4 + 3); //recupere l'url en quatrieme case du vecteur
                    
					System.out.println(url);
					
					try
					{
						//System.out.println(java.net.URI.create(url));
						Desktop.getDesktop().browse(java.net.URI.create(url));
					}
					catch (IOException except)
					{
						except.printStackTrace();
					}
                }
                    
              }
		};
		
		ActionListener sauvegarde_article = new ActionListener()
		{
				
			public void actionPerformed(ActionEvent e)
			{
				// Recupere l'indice de l'item selectionne pour avoir la tendance
				
				int index_tendance = (int) liste_tendances.getSelectedIndex();
                int index_news = (int) liste_news.getSelectedIndex();
               
				Vector<String> lesNewsDeLaTendance = ((Vector<String>) lesNews.get(index_tendance));
				
                String url = new String();
                url = lesNewsDeLaTendance.get(index_news*4 + 3); //recupere l'url en quatrieme case du vecteur
                System.out.println(url);
                final String url1= url;
                String fileName = new String();
                String source = new String();
                String date = new String();
                fileName = lesNewsDeLaTendance.get(index_news*4); //recupe le titre
                source = lesNewsDeLaTendance.get(index_news*4+1);
                date = lesNewsDeLaTendance.get(index_news*4+2);
                fileName=fileName.replace(' ', '_');
                fileName=fileName.replace('?', '_');
                System.out.println(fileName);
                final String title= fileName;
                final String date1= date;
                final String source1= source;
                f.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                JOptionPane.showMessageDialog(null," Sauvegarde en cours ... ","Sauvegarde en cours",JOptionPane.INFORMATION_MESSAGE);
                Thread t = new Thread() {
            		public void run() {
            			try {
            				String titre=PDFConvertisseur.sauvergardeArticle(url1, title);
            				GestionBD.inserer_page(Panel_acc.id_user,titre ,source1,date1);
            		} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 // On recupere les nouvelles tendances
					catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                
				onglet2.removeAll();
				try {
					JPanel new_p= new Panel_Sauvegarde();
					new_p.setPreferredSize(new Dimension(1000, 300));
					onglet2.add(new_p);
					onglet2.validate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null,"Article sauvegardé avec succès ","Sauvegarde",JOptionPane.INFORMATION_MESSAGE);
            		}
        		};
        		t.start();
        		f.setCursor(Cursor.getDefaultCursor());
            }
		};
			sauvegarder.addActionListener(sauvegarde_article);
			liste_news.addMouseListener(doubleclicNews);	
			recherche_maj.addActionListener(recherche_avancee_maj); 
			recherche_dict.addActionListener(recherche_avancee_dict);
	        actualiser.addActionListener(actualisation);
	                        
	                        


	        // Placement des differents widgets dans la fenetre
			global_panel.add(titre_panel, BorderLayout.NORTH);	
			global_panel.add(button_panel, BorderLayout.SOUTH);
			global_panel.add(selection_tendances, BorderLayout.WEST);	
			global_panel.add(selection_tweets, BorderLayout.CENTER);
			global_panel.add(selection_news, BorderLayout.EAST);
			
			onglets.setPreferredSize(new Dimension(1000, 350));
			p1.setPreferredSize(new Dimension(1000, 300));
		    p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
		    p1.add(bande_sup);
		    p1.add(global_panel);
		    
		    onglets.addTab("Trends", p1);
		    
		    onglet2.setPreferredSize(new Dimension(1000, 350));
		    JPanel sauvegarde =new Panel_Sauvegarde();
		    sauvegarde.setPreferredSize(new Dimension(1000, 300));
		    onglet2.add(sauvegarde);
		    onglets.addTab("Sauvegarde", onglet2);
		    JPanel p_aide =new Aide();
		    p_aide.setPreferredSize(new Dimension(1000, 350));
		    onglet3.add(p_aide);
		    onglets.addTab("Aide", onglet3);
		    onglets.setOpaque(true);
		    pannel.add(onglets);
		    f.setSize(1010, 400);
		    f.getContentPane().add(pannel);
		    pack();
		    f.setTitle("Bienvenue  "+Panel_acc.nom+" sur votre session");
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setVisible(true);	
			f.setLocationRelativeTo(null);
		} 

	 public static void main(String[] args) {
		 EventQueue.invokeLater(new Runnable() {

		        public void run() {
		        	Interface_utilisateur admin =new Interface_utilisateur(); 
		        }
		    });
	    }

	}

