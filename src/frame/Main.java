package frame;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {


	
	public static void main(String[] args) {
		
		/*
		 * Deklerēšana priekš Main metodes
		 */
		
		JFrame f = new JFrame();
		JFrame fClose = new JFrame();
		JPanel sanuPogas = new JPanel();
		
		/*
		 * Main koda daļa
		 */
		
		f.setSize(1920, 1080);
		f.setDefaultCloseOperation(0);
		f.setLayout(null);
		f.setLocationRelativeTo(null);
		
		f.setVisible(true);
		
		/*
		 * Close window checker
		 */
		
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				fClose.setSize(380, 240);
				fClose.setLocationRelativeTo(f);;
				fClose.setAlwaysOnTop(true);
				
				if(fClose.isVisible())
					Toolkit.getDefaultToolkit().beep();
				
				fClose.setVisible(true);
			}
		});
	}
}
