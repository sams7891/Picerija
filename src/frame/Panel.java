package frame;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import global.Global;

public class Panel {
	
	static void TopButton(JButton b) {
		Locale locale = Locale.of("lv");
		ResourceBundle bundle = ResourceBundle.getBundle("text.text", locale);
		
		b.setMaximumSize(new Dimension(bundle.getString("register").length() * 15, 40));
		b.setFont(new Font("Arial", Font.PLAIN, 30));
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		b.setBorder(BorderFactory.createLineBorder(new Color(145, 145, 145), 2, true));
		b.setUI(new BasicButtonUI());
		
		
		b.addMouseListener(new MouseListener() {
		

			@Override
			public void mouseClicked(MouseEvent e) {
				b.setBackground(new Color(170, 170, 170));

			}

			@Override
			public void mousePressed(MouseEvent e) {
				b.setBackground(new Color(150, 150, 150));

				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				b.setBackground(new Color(150, 150, 150));

				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				b.setContentAreaFilled(true);
				b.setBorderPainted(true);

				b.setBackground(new Color(150, 150, 150));
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				b.setContentAreaFilled(false);
				b.setBorderPainted(false);
				
			}
			
		});
	}
	
	public static void panel() {
		
		JFrame f = new JFrame();
		Locale locale = Locale.of("lv");
		ResourceBundle bundle = ResourceBundle.getBundle("text.text", locale);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		JPanel pBackground = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -1969924032365564895L;
			Image background = new ImageIcon(getClass().getResource("/images/LogInBackground.png")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        	};
        
        
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

			
			pOptions.add(Box.createHorizontalStrut(50));
			pOptions.add(bRegister);
			pOptions.add(Box.createHorizontalStrut(50));
			pOptions.add(bLookUp);
			pOptions.add(Box.createHorizontalGlue());
			pOptions.add(bSettings);
			pOptions.add(Box.createHorizontalStrut(50));
			
			
			f.add(pOptions, BorderLayout.NORTH);
			
			/*
			 * Window close
			 */
			
			Global.closeWindow(f);
			
			JPanel pRegister = new JPanel() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 8638768058679045493L;

				@Override
				protected void paintComponent(Graphics g) {
					 super.paintComponent(g);
		                Graphics2D g2d = (Graphics2D) g.create();
		                // Set alpha here (0.0f = fully transparent, 1.0f = fully opaque)
		                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		                g2d.setColor(Color.white); // panel color
		                g2d.fillRect(0, 0, getWidth(), getHeight());
		                g2d.dispose();
				}
			};
			pRegister.setOpaque(false);
			
			pRegister.add(Box.createHorizontalStrut(50));
			f.add(pRegister, BorderLayout.CENTER);
			f.setVisible(true);
	}
}
