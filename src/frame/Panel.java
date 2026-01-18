package frame;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import files.FileUser;
import global.Global;

public class Panel {
	
	static ResourceBundle bundle;
	static JFrame f;
	
	@SuppressWarnings("unused")
	static Component OptionSwitch(String option) {
		JPanel panel = new JPanel() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = -918757145678230349L;

			@Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);

		        Graphics2D g2 = (Graphics2D) g.create();
		        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                            RenderingHints.VALUE_ANTIALIAS_ON);

		        g2.setColor(getBackground());
		        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

		        g2.dispose();
		    }
		};

		panel.setOpaque(false);
		panel.setBackground(new Color(240, 240, 240));

	    
	    
	    switch (option) {
	        case "register":
	            panel.add(new JLabel("Register"));
	            break;

	        case "lookUp":
	            panel.add(new JLabel("Look Up"));
	            break;

	        case "settings":
	        	/*
	        	 * language change
	        	 */
	        	JPanel pLanguage = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
	        	JLabel lLanguage = new JLabel(bundle.getString("lLanguage"));
	        	String[] languages = {"Latviešu", "English"};
	        	
	        	JComboBox<String> cbLanguages = new JComboBox<String>(languages);
	        	
	        	String language = Global.locale.getLanguage();
	        	
	        	cbLanguages.setFont(new Font(Global.lanFont, Font.PLAIN, 18));
	        	cbLanguages.setOpaque(false);
	        	switch(language) {
	        	case "lv":
	        		cbLanguages.setSelectedItem("Latviešu");
	        		break;
	        		
	        	case "en":
	        		cbLanguages.setSelectedItem("English");
	        		break;
	        	}
	        	
	        	cbLanguages.addActionListener(e -> {
	        	    String selectedLanguage = (String) cbLanguages.getSelectedItem();
	        	    
	        	    switch(selectedLanguage) {
		        	case "Latviešu":
		        		selectedLanguage = "lv";
		        		break;
		        		
		        	case "English":
		        		selectedLanguage = "en";
		        		break;
		        	}
	        	    
	        	    FileUser.settingsWriter("lan", selectedLanguage);
	        	    
	        	    
	        	});
	        	
	        	
	        	lLanguage.setFont(new Font(Global.lanFont, Font.PLAIN, 18));
	        	
	        	pLanguage.setOpaque(false);
	        	pLanguage.add(lLanguage);
	        	pLanguage.add(cbLanguages);

	            panel.setLayout(new BorderLayout());
	            panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 20, 20));
	            panel.add(pLanguage, BorderLayout.NORTH);
	            
	            /*
	             * restart button
	             */
	            
	            JButton rButton = new JButton(bundle.getString("rButton")); 
	            rButton.setFont(new Font(Global.lanFont, Font.PLAIN, 18));
	            
	            rButton.addActionListener(e -> {
	                // STEP A: Get the current selection from the UI
	                String selected = (String) cbLanguages.getSelectedItem();
	                String langCode = selected.equals("Latviešu") ? "lv" : "en";
	                
	                // STEP B: Save it to the file so it persists
	                FileUser.settingsWriter("lan", langCode);
	                
	                // STEP C: Reload the bundle in Global.java
	                // This is the most important part! It updates Global.bundle in memory.
	                Global.settingsUpdater();
	                
	                // STEP D: The "Hard Reset"
	                f.dispose();         // Close the current window
	                Panel.panel();       // Launch a brand new window using the updated bundle
	            });

	            // Layout
	            panel.setLayout(new BorderLayout());
	            panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 20, 20));
	            
	            panel.add(pLanguage, BorderLayout.NORTH);
	            
	            // Putting the button in a panel to control its size
	            JPanel pRestart = new JPanel(new FlowLayout(FlowLayout.LEFT));
	            pRestart.setOpaque(false);
	            pRestart.add(rButton);
	            panel.add(pRestart, BorderLayout.CENTER);
	            
	            break;
	    }

	    return panel;
	}

	
	@SuppressWarnings("unused")
	static void TopButton(JButton b) {		
		b.setFont(new Font(Global.lanFont, Font.BOLD, 30));
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		b.setUI(new BasicButtonUI());
		b.setForeground(new Color(10, 10, 10));
		b.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(new Color(210, 210, 210), 2, true),
			    BorderFactory.createEmptyBorder(10, 10, 10, 10)
			));
		b.setRolloverEnabled(true);


		
		b.addMouseListener(new MouseListener() {
		

			@Override
			public void mouseClicked(MouseEvent e) {
				if(!b.isEnabled()) return;

				
				b.setBackground(new Color(200, 200, 200));

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(!b.isEnabled()) return;

				b.setBackground(new Color(190, 190, 190));

				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(!b.isEnabled()) return;

				b.setBackground(new Color(255, 170, 170));

				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if(!b.isEnabled()) return;
				
				b.setContentAreaFilled(true);
				b.setBorderPainted(true);

				b.setBackground(new Color(220, 220, 220));
				
				
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if(!b.isEnabled()) return;

				b.setBackground(new Color(240, 240, 240));

				b.setBorderPainted(false);
				
				
			}
			
		});
		
		b.addPropertyChangeListener("enabled", evt -> {
		    if (!b.isEnabled()) {

		        b.setBorderPainted(false);
		        b.setContentAreaFilled(false);
		    }
		});

	}
	
	public static void panel() {
		bundle = Global.bundle;
		f = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		JPanel trans = new JPanel(new BorderLayout()) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8638768058679045493L;

			@Override
			protected void paintComponent(Graphics g) {
				 super.paintComponent(g);
	                Graphics2D g2d = (Graphics2D) g.create();
	                // Set alpha here (0.0f = fully transparent, 1.0f = fully opaque)
	                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
	                g2d.setColor(Color.gray); // panel color
	                g2d.fillRect(0, 0, getWidth(), getHeight());
	                g2d.dispose();
			}
		};
		
		JPanel pBackground = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -1969924032365564895L;
			Image background = new ImageIcon(getClass().getResource("/images/test2.jpg")).getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        	};
        
        	JPanel contentWrapper = new JPanel(new BorderLayout());
        	contentWrapper.setBorder(new EmptyBorder(40, 40, 40, 40));
        	contentWrapper.setOpaque(false);

        	trans.add(contentWrapper, BorderLayout.CENTER);

        	
			if(screenSize.width == 1920 && screenSize.height == 1080) {
				f.setExtendedState(JFrame.MAXIMIZED_BOTH);
				f.setUndecorated(true);
			}else
				f.setSize(1920, 1080);
			
			f.setContentPane(pBackground);
			f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			f.setLayout(new BorderLayout());
			f.setLocationRelativeTo(null);
			f.setResizable(false);
			f.setTitle(bundle.getString("JFrame_title"));
			f.setIconImage(new ImageIcon(Main.class.getResource("/images/appImage.png")).getImage().getScaledInstance(-1, -1, Image.SCALE_SMOOTH));
			
			
			
			/*
			 * top bar
			 */
			
			JPanel pOptions = new JPanel();
			
			JButton bRegister = new JButton(bundle.getString("register"));
			JButton bLookUp = new JButton(bundle.getString("lookup_orders"));
			JButton bSettings = new JButton(bundle.getString("settings"));

			pOptions.setLayout(new BoxLayout(pOptions, BoxLayout.X_AXIS));
			
			TopButton(bRegister);
			TopButton(bLookUp);
			TopButton(bSettings);
			
	        pOptions.setBorder(new EmptyBorder(20, 0, 20, 0));
	        pOptions.setBackground(new Color(240, 240, 240));
				
			pOptions.add(Box.createHorizontalStrut(50));
			pOptions.add(bRegister);
			pOptions.add(Box.createHorizontalStrut(50));
			pOptions.add(bLookUp);
			pOptions.add(Box.createHorizontalGlue());
			pOptions.add(bSettings);
			pOptions.add(Box.createHorizontalStrut(50));
			
			bRegister.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					bRegister.setEnabled(false);
					bLookUp.setEnabled(true);
					bSettings.setEnabled(true);
					contentWrapper.removeAll();
					contentWrapper.add(OptionSwitch("register"), BorderLayout.CENTER);
					contentWrapper.revalidate();
					contentWrapper.repaint();


				}
				
			});
			
			bLookUp.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					bRegister.setEnabled(true);
					bLookUp.setEnabled(false);
					bSettings.setEnabled(true);
					contentWrapper.removeAll();
					contentWrapper.add(OptionSwitch("lookUp"), BorderLayout.CENTER);
					contentWrapper.revalidate();
					contentWrapper.repaint();


				}
				
			});
			
			bSettings.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					bRegister.setEnabled(true);
					bLookUp.setEnabled(true);
					bSettings.setEnabled(false);
					contentWrapper.removeAll();
					contentWrapper.add(OptionSwitch("settings"), BorderLayout.CENTER);
					contentWrapper.revalidate();
					contentWrapper.repaint();
					contentWrapper.setBorder(new EmptyBorder(40, 40, 40, 600));

				}
				
			});
			
			f.add(pOptions, BorderLayout.NORTH);
			
			/*
			 * Window close
			 */
			
			Global.closeWindow(f);
			
			trans.setOpaque(false);
			
			


			
			f.add(trans, BorderLayout.CENTER);
			f.setVisible(true);
	}
}
