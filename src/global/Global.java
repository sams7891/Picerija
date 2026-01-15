package global;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import frame.Main;

public class Global {
	
	public static void closeWindow(JFrame f) {
		JFrame fClose = new JFrame();
		
		Locale locale = Locale.of("lv");

		ResourceBundle bundle = ResourceBundle.getBundle("text.text", locale);

		
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
	}
}
