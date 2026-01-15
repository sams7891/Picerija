package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import files.Files;

public class Main {


	
	public static void main(String[] args) {
		
		/*
		 * Deklerēšana priekš Main metodes un pamatiem
		 */
		
		JFrame f = new JFrame();
		JFrame fClose = new JFrame();
		
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
		
		//Vēlāk nomainīt uz maināmu mainīgu
		Locale locale = Locale.of("lv");
		ResourceBundle bundle = ResourceBundle.getBundle("text.text", locale);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		
		/*
		 * Main koda daļa
		 */
		
		try {
		    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
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
		 * Darbinieka ielogošanās lapa
		 */
		
		JPanel plogInMenu = new JPanel();
		JLabel llogInText = new JLabel(bundle.getString("llogInText"));
		JLabel llogInTextIncorrect = new JLabel(bundle.getString("llogInTextIncorrect"));
		JTextField tidInput = new JTextField();
		
		
		f.setLayout(new BorderLayout());
		
		plogInMenu.setLayout(new BoxLayout(plogInMenu, BoxLayout.Y_AXIS));
		plogInMenu.setOpaque(false);
		
		llogInText.setOpaque(false);
		llogInText.setFont(new Font("Arial", Font.BOLD, 50));
		llogInText.setForeground(new Color(240, 240, 240));
		llogInText.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		plogInMenu.setBorder(BorderFactory.createEmptyBorder(310, 100, 0, 0));
		plogInMenu.add(llogInText, BorderLayout.WEST);
		plogInMenu.add(Box.createVerticalStrut(50));
		
		llogInTextIncorrect.setOpaque(false);
		llogInTextIncorrect.setFont(new Font("Arial", Font.BOLD, 18));
		llogInTextIncorrect.setForeground(new Color(0, 0, 0));
		llogInTextIncorrect.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		tidInput.setAlignmentX(Component.LEFT_ALIGNMENT);
		tidInput.setMaximumSize(new Dimension(bundle.getString("tidInput").length() * 15, 40));
		tidInput.setFont(new Font("Arial", Font.BOLD, 18));
		tidInput.setForeground(new Color(40, 40, 40));
		
		tidInput.addActionListener(e ->{
			String idIevade = tidInput.getText();

			if(!Files.checkEmployee(idIevade)) {
				tidInput.setText("");
				llogInTextIncorrect.setForeground(new Color(250, 30, 30));

			}else {
				llogInTextIncorrect.setForeground(new Color(0, 0, 0));

			}
			
		});
		
		tidInput.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
		            if (tidInput.getText().isEmpty()) {
		                e.consume();
		            }
		        }
		    }
		});

		
		// pievienot registra failam
		
		plogInMenu.add(llogInTextIncorrect);
		plogInMenu.add(tidInput);
		f.add(plogInMenu);
		
		/*
		 * Close window
		 */
		
		f.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        fClose.setSize(600, 400);
		        fClose.setLocationRelativeTo(f);
		        fClose.setAlwaysOnTop(true);
		        fClose.setResizable(false);
		        fClose.setTitle(bundle.getString("JFrame_title_default"));
				fClose.setIconImage(new ImageIcon(Main.class.getResource("/images/appImage.png")).getImage());

		        JPanel pBackground = new JPanel() {
		            /**
					 * 
					 */
					private static final long serialVersionUID = 2648849948302194781L;
					Image background = new ImageIcon(getClass().getResource("/images/exit.jpg")).getImage();

		            @Override
		            protected void paintComponent(Graphics g) {
		                super.paintComponent(g);
		                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		            }
		        };
		        pBackground.setLayout(new BorderLayout());

		        JLabel lText = new JLabel(bundle.getString("fClose_text"));
		        lText.setFont(new Font("Arial", Font.BOLD, 20));
		        lText.setHorizontalAlignment(SwingConstants.CENTER);
		        lText.setForeground(new Color(240, 240, 240));
		        lText.setBorder(new EmptyBorder(10, 10, 10, 10));
		        
		        pBackground.add(lText, BorderLayout.NORTH);

		        
		        JPanel pButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 10));
		        pButton.setOpaque(false);
		        
		        JButton bClose = new JButton(bundle.getString("fClose_exit"));
		        bClose.setPreferredSize(new Dimension(100, 30));
		        bClose.addActionListener(ev -> System.exit(0));

		        JButton bCancel = new JButton(bundle.getString("fClose_cancel"));
		        bCancel.setPreferredSize(new Dimension(100, 30));
		        bCancel.addActionListener(ev -> fClose.dispose());

		        pButton.add(bClose);
		        pButton.add(bCancel);

		        fClose.setContentPane(pBackground);
		        fClose.add(pButton, BorderLayout.SOUTH);
		        
		        if (fClose.isVisible()) 
		        	Toolkit.getDefaultToolkit().beep();
		        
		        fClose.setVisible(true);
		    }
		});
		
		f.setVisible(true);
	}
}
