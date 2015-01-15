package tse.fi2.info4.tbek;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
public class Aide extends JPanel{

    public Aide() {
    	super();
        initComponents();
        
    }

 // Variables declaration 
    JFrame f = new JFrame("");
    JPanel p1 = this;
	JPanel global_panel = new JPanel();
	JPanel bande_sup = new JPanel();
	//GroupLayout bande_supLayout = new GroupLayout(bande_sup);
	JLabel jLabel1 = new JLabel();
    
    private void initComponents() {
    	
    	Vector<String> questions=new Vector();
    	final Vector<String> reponses=new Vector();
    
    	global_panel.setLayout(new BorderLayout());
    	bande_sup.setLayout(new BorderLayout());
		//bande_sup.setLayout(bande_supLayout);
		bande_sup.setBackground(new Color(64,153,255));

		jLabel1.setText("            Bienvenue sur Aide et Support TBEK");
		bande_sup.add(jLabel1);

		JPanel titre_panel = new JPanel();
		JLabel titre = new JLabel("Liste des questions les plus importantes");
		titre_panel.add(titre);
		questions.addElement(" Je ne parviens pas à lancer le logiciel. Comment faire ?");
		questions.addElement("Je souhaite voir des news. Comment faire ?");
		questions.addElement("Je n'ai pas d'articles dans la colonne de droite. Pourquoi ?");
		questions.addElement("Comment voir des articles de news en mode hors-ligne ?");
		questions.addElement("Je n'ai pas de compte. Comment créer un compte ?");
		questions.addElement("Comment modifier mon mot de passe ?");
		reponses.addElement("Veuillez vous assurer que votre systeme possede une version de Java superieure"
				+ "a 1.7, et que vous disposez des privileges necessaires pour executer ce genre de"
				+ "fichier. Double-cliquez sur l'icone du logiciel afin de le lancer.");
		reponses.addElement("Tout d'abord, connectez vous a votre compte grace aux champs dans le bandeau"
				+ "superieur bleu. Ensuite cliquez sur l'une des tendances dans la liste de gauche. La"
				+ "liste de droite se remplira. Double cliquez alors sur l'article que vous souhaitez"
				+ "voir. Votre navigateur Web s'ouvrira (un navigateur web est necessaire pour voir des articles de news )");
		reponses.addElement("Selectionnez d'abord une tendance en cliquant dessus. Si la liste de droite reste"
				+ "vide, cliquez d'abord sur le bouton recherche avancee 1. Apres une dizaine de"
				+ "secondes, la liste devrait etre remplie. Si ce n'est pas le cas; cliquez sur le bouton"
				+ "recherche avancee 2. Apres une trentaine de secondes, la liste devrait etre"
				+ "remplie. Si ce n'est toujours pas le cas, il n'y a malheureusement pas news"
				+ "relatives a la tendance selectionnee.");
		reponses.addElement("Connectez vous a votre compte grace aux champs dans le bandeau superieur"
				+ "bleu. Ensuite, cliquez sur l'onglet Sauvegarde dans le bandeau superieur. Vous"
				+ "trouverez l'ensemble des articles sauvegardes. Double-cliquez sur l'un pour le"
				+ "visualiser. Votre afficheur de document PDF s'ouvrira (un afficheur de document"
				+ "PDF est necessaire pour utiliser cette fonctionnalitee).");
		reponses.addElement("Une fois que le logiciel a ete lance, et que vous apercevez les 3 listes, vous"
				+ "pouvez creer un nouveau compte dans le bandeau superieur bleu. Entrez un nom"
				+ "d'utilisateur dans le champ Username, ainsi qu'un mot de passe dans le champ"
				+ "Password. Cliquez ensuite sur New pour finaliser la creation de votre compte."
				+ "Vous pouvez desormais acceder a l'ensemble des fonctionnalites du logiciel."
				+ "Utilisez les memes identifiants lors de votre prochaine connexion.");
		reponses.addElement("Si vous n'avez pas les privileges necessaires, demandez a l'administrateur du"
				+ "logiciel. Seul lui peut changer votre mot de passe."
				+ "Apres que l'administrateur se soit authentifie grace aux champs du bandeau"
				+ "superieur, il peut cliquer sur le bouton Gestion. Une fenetre va alors s'ouvrir,"
				+ "indiquant l'ensemble des comptes crees. En selectionnant un compte (un seul clic"
				+ "sur l'element), vous pourrez alors modifier les informations de ce compte (username/password).");
				
		
		JScrollPane selection_question = new JScrollPane();
        final DefaultListModel listeModel_question = new DefaultListModel();
		final JList liste_question = new JList(listeModel_question);
		selection_question.setViewportView(liste_question);
		selection_question.setPreferredSize(new Dimension(450,200));
		// remplit la liste avec les questions
        for (String q : questions)
        {
            listeModel_question.addElement(q);
        }
		JScrollPane selection_reponse = new JScrollPane();
        final DefaultListModel listeModel_reponse = new DefaultListModel();
        final JList liste_reponse = new JList(listeModel_reponse);
        selection_reponse.setViewportView(liste_reponse);
        selection_reponse.setPreferredSize(new Dimension(450,200));
		// Placement des differents widgets 
				global_panel.add(titre_panel, BorderLayout.NORTH);	
				global_panel.add(selection_question, BorderLayout.WEST);
				global_panel.add(selection_reponse, BorderLayout.EAST);
				p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
			    p1.add(bande_sup);
			    p1.add(global_panel);
				MouseListener mouseListener = new MouseAdapter() 
		        {
				    public void mouseClicked(MouseEvent e) 
		            {
				    	if (e.getClickCount() == 1) 
		                {
		                    // enleve tous les elements de la liste reponse
		                    listeModel_reponse.removeAllElements();
		                    // Recupere l'indice de l'item selectionne pour avoir la reponse
		                    int index = (int) liste_question.getSelectedIndex();
		                    String reponse=reponses.get(index);
		                    String reponse_decoupe = Utils.formateReponse(reponse);
		                    listeModel_reponse.addElement(reponse_decoupe);

		                    
		                   }
		                   
		            	}
		           
				};
				liste_question.addMouseListener(mouseListener);
    }	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

	        public void run() {
	        	JFrame f= new JFrame();
	        	Aide p = new Aide();
				
	        	f.setVisible(true);	
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.add(p);
				f.setSize(1000, 300);
				f.setLocationRelativeTo(null);
	        	
	        }
	    });
	}
    
}
