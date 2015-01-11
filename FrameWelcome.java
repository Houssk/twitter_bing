package tse.fi2.info4.tbek;

import java.awt.*;

import javax.swing.*;

public class FrameWelcome extends JFrame {

    Thread monThread;
    int rappidite;
    private JLabel label1;
    private JProgressBar progress;

    private static FrameWelcome frameWelcome;
    
    public FrameWelcome() {
        initComponents();
        this.setLocation(300, 100);
        this.setVisible(true);
        this.setTitle("Chargement...");
    }

    private void initComponents() {

        progress = new JProgressBar();
        label1 = new JLabel();
   
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        progress.setBackground(new Color(255, 255, 255));
        progress.setForeground(new Color(64,153,255));
        progress.setStringPainted(true);

        label1.setFont(new Font("Aharoni", 1, 18)); 
        label1.setForeground(new Color(0, 0, 0));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setText("All rights reserved  2014-2015 ");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(progress, GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(label1)
                                .addGap(139, 139, 139))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(label1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(progress,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }

 
    public void go(int rappid) {
        rappidite = rappid;
        // Cr�ation de thread
        monThread = new Thread(new MonRunnable());
        monThread.start();
		Thread t = new Thread() {
    		public void run() {
    			System.out.println(Panel_acc.nom);
    			if(Panel_acc.id_user==1){
    				Interface_admin ad= new Interface_admin();
    				setVisible(false);
    			}
    			else{
    				Interface_utilisateur ut= new Interface_utilisateur();
    				setVisible(false);
    			}
    		}
    		};
    	t.start(); 
    }

    public class MonRunnable implements Runnable {

        public void run() {
            for (int j = 1; j <= 100; j++) // on fait une boucle pour que la JProgressBar "avance"
            {
                progress.setValue(j);
                try {
                    monThread.sleep(rappidite);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
              
        }
        
    }

    
    public static void main(String[] args) {
		 EventQueue.invokeLater(new Runnable() {

		        public void run() {
		        	FrameWelcome auth =new FrameWelcome(); 
		        	auth.go(50);
		        }
		    });
	 }

}
