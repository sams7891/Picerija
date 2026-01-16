package frame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import global.Global;

public class Panel {
	
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
			Image background = new ImageIcon(getClass().getResource("/images/logInBackground.png")).getImage();

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
			

			
			/*
			 * Window close
			 */
			
			Global.closeWindow(f);
			
			f.setVisible(true);
	}
}
