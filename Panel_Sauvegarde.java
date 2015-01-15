package tse.fi2.info4.tbek;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.Desktop;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


public class Panel_Sauvegarde extends JPanel{

	public Panel_Sauvegarde() throws SQLException, IOException {
		super();
        initComponents();
    }
	JFrame f = new JFrame("");
    JPanel p1 = this;
	JPanel global_panel = new JPanel();
	JPanel bande_sup = new JPanel();
	GroupLayout bande_supLayout = new GroupLayout(bande_sup);
	JButton deconnexion = new JButton();
	JLabel jLabel1 = new JLabel();
	JLabel jLabel2 = new JLabel();
	
	private void initComponents() throws SQLException, IOException {
	   System.out.println(Panel_acc.id_user);
		
		final Vector<String> lesPages = GestionBD.afficher_page(Panel_acc.id_user);
		global_panel.setLayout(new BorderLayout());
		bande_sup.setLayout(bande_supLayout);
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
            	p1.removeAll();
            	f.setTitle("projet tbek");
            	add(p);
            	validate();
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
		JLabel titre = new JLabel("Liste des pages sauvegardees");
		titre_panel.add(titre);
		
		JScrollPane selection_fichier = new JScrollPane();
        final DefaultListModel listeModel_fichier = new DefaultListModel();
		final JList liste_fichier = new JList(listeModel_fichier);
		JPanel button_panel = new JPanel();
		JButton supprimer = new JButton("Supprimer");
		supprimer.setPreferredSize(new Dimension(150, 30));
		button_panel.add(supprimer);
		selection_fichier.setViewportView(liste_fichier);
		selection_fichier.setPreferredSize(new Dimension(300,260));
		int size= lesPages.size();
		String element_page= new String();
		for(int i=0;i<size;i=i+5){
			 
			
				String title = lesPages.get(i);
				title=title.replace('_', ' ');
				String source = lesPages.get(i+3);
				String date = lesPages.get(i+4);
				String newDate=date.substring(0, 10)+" à "+ date.substring(11, 19);
				// Utilise le html pour rendre les tweets presentables. 
				element_page=Utils.formatePage(title,source,newDate);
				
			
		   listeModel_fichier.addElement(element_page);
		}
		
		 ActionListener supprimer_pdf = new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
//	            	final JOptionPane optionPane = new JOptionPane("Voulez vous supprimer ce fichier ? ",
//	            	    		 "Suppression",JOptionPane.WARNING_MESSAGE,JOptionPane.OK_CANCEL_OPTION);
			            	Object[] options = {"Oui",
			                        "Non"};
			            	int opt = JOptionPane.showOptionDialog(null,
			            			"Voulez vous supprimer ce fichier ? ",
			            			"Suppression", JOptionPane.YES_NO_CANCEL_OPTION,
					        JOptionPane.QUESTION_MESSAGE,
					        null,
					        options,
					        options[1]);

	            	       System.out.println(opt);	
	            	     // Recupere l'indice de l'item selectionne pour avoir la tendance
	            	    if(opt==0)
	            	     {
							int index = (int) liste_fichier.getSelectedIndex();
							String page= lesPages.get(index*5);
							String id_page=lesPages.get(index*5+2);
							Integer id_page1;
							Integer.parseInt(id_page);
							File file = new File("tbek/"+page);
							if (file.exists()) {
					            file.delete();
					            try {
									GestionBD.delete(id_page);
									JPanel new_p= new Panel_Sauvegarde();
									if(Panel_acc.id_user==1)
									{
										Interface_admin.onglet2.removeAll();
										new_p.setPreferredSize(new Dimension(1000, 300));
										Interface_admin.onglet2.add(new_p);
										Interface_admin.onglet2.validate();
									}
									else{
										Interface_utilisateur.onglet2.removeAll();
										new_p.setPreferredSize(new Dimension(1000, 300));
										Interface_utilisateur.onglet2.add(new_p);
										Interface_utilisateur.onglet2.validate();
									}
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
					            System.out.println("fichier supprimé");
					            
					        }
					
							else System.out.println("Fichier introuvable");
							
	            }
	            }
	        };
			
		 supprimer.addActionListener(supprimer_pdf);
		
//		for (String page : lesPages)
//        {
//            listeModel_fichier.addElement(page);
//        }
		
        // Placement des differents widgets 
		global_panel.add(titre_panel, BorderLayout.NORTH);	
		global_panel.add(selection_fichier, BorderLayout.CENTER);
		global_panel.add(button_panel, BorderLayout.SOUTH);
		
		p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
	    p1.add(bande_sup);
	    p1.add(global_panel);
		MouseListener mouseListener = new MouseAdapter() 
        {
		    public void mouseClicked(MouseEvent e) 
            {
		        if (e.getClickCount() == 2)
		        	
                {
		        	int index = (int) liste_fichier.getSelectedIndex();
		        	String url = new String();
		        	url=lesPages.get(index*5+1);
		        	try {
		        		Desktop.getDesktop().open(new File(url));
						//Desktop.getDesktop().browse(java.net.URI.create(url));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    
                }
            }
		};
		liste_fichier.addMouseListener(mouseListener);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

	        public void run() {
	        	JFrame f= new JFrame();
	        	Panel_Sauvegarde p = null;
				try {
					p = new Panel_Sauvegarde();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	        	f.setVisible(true);	
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.add(p);
				f.setSize(1000, 300);
				f.setLocationRelativeTo(null);
	        	
	        }
	    });
	}
}