package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import files.FileUser;
import technical.Global;
import technical.SoundPlayer;

public class Main {

    static ResourceBundle bundle;
    static JPanel pBackground;

    
    private static JPanel BackgroundPanel(String imagePath) {
        return new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private Image background = new ImageIcon(getClass().getResource(imagePath)).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }

    
    public static void main(String[] args) {
        try {
            // 1. Load settings and bundle FIRST
            Global.startUpSettings(); 
            bundle = Global.bundle;

            // 2. Only THEN start the UI thread
            SwingUtilities.invokeLater(() -> {
                try {
                    startUI();
                } catch (Exception e) {
                    showError(e);
                }
            });
        } catch (Exception e) {
            showError(e);
        }
    }

    @SuppressWarnings("unused")
	private static void startUI() {

        JFrame f = new JFrame();
        
        SoundPlayer player = new SoundPlayer();


        pBackground = BackgroundPanel("/images/logInBackground.png");
        f.setContentPane(pBackground);


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

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        if (screenSize.width == 1920 && screenSize.height == 1080) {
            f.setExtendedState(JFrame.MAXIMIZED_BOTH);
            f.setUndecorated(true);
        } else {
            f.setSize(1920, 1080);
        }

        f.setContentPane(pBackground);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.setLayout(new BorderLayout());
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setTitle(bundle.getString("JFrame_title"));
        f.setIconImage(
                new ImageIcon(
                        Main.class.getResource("/images/appImage.png")
                ).getImage()
        );

        /*
         * LOGIN PANEL
         */

        JPanel plogInMenu = new JPanel();
        JLabel llogInText = new JLabel(bundle.getString("llogInText"));
        JLabel lloginInfoText = new JLabel(bundle.getString("lloginInfoText"));
        JLabel llogInTextIncorrect = new JLabel(bundle.getString("llogInTextIncorrect"));
        JTextField tidInput = new JTextField();

        plogInMenu.setLayout(new BoxLayout(plogInMenu, BoxLayout.Y_AXIS));
        plogInMenu.setOpaque(false);
        plogInMenu.setBorder(BorderFactory.createEmptyBorder(310, 100, 0, 0));

        llogInText.setFont(new Font(Global.lanFont, Font.BOLD, 50));
        llogInText.setForeground(new Color(240, 240, 240));
        llogInText.setAlignmentX(Component.LEFT_ALIGNMENT);

        lloginInfoText.setFont(new Font(Global.lanFont, Font.PLAIN, 18));
        lloginInfoText.setForeground(new Color(240, 240, 240));
        lloginInfoText.setAlignmentX(Component.LEFT_ALIGNMENT);

        llogInTextIncorrect.setFont(new Font(Global.lanFont, Font.BOLD, 18));
        llogInTextIncorrect.setForeground(new Color(0, 0, 0));
        llogInTextIncorrect.setAlignmentX(Component.LEFT_ALIGNMENT);

        tidInput.setMaximumSize(new Dimension(300, 40));
        tidInput.setFont(new Font(Global.lanFont, Font.BOLD, 18));
        tidInput.setForeground(new Color(40, 40, 40));
        tidInput.setAlignmentX(Component.LEFT_ALIGNMENT);

        // TEMP VALUE
        tidInput.setText("1000");

        tidInput.addActionListener(e -> {
            String id = tidInput.getText().trim();
            
            if (id.equals("1987")) {
            	
            	plogInMenu.setVisible(false);
            	
                try {
                    pBackground.getClass().getMethod("setBackgroundImage", String.class).invoke(pBackground, "/images/goldenFreddy.jpg");
                    player.loadFromClasspath("/sound/goldenFreddyScream.wav");
                    player.play(() -> {
                        System.exit(0);
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            
            if (!FileUser.checkEmployee(id)) {
                tidInput.setText("");
                llogInTextIncorrect.setForeground(new Color(250, 30, 30));
            } else {
                llogInTextIncorrect.setForeground(new Color(0, 0, 0));
                f.setVisible(false);
                Panel.panel();
            }
        });

        tidInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && tidInput.getText().isEmpty()) {
                    e.consume();
                }
            }
        });

        plogInMenu.add(llogInText);
        plogInMenu.add(lloginInfoText);
        plogInMenu.add(Box.createVerticalStrut(50));
        plogInMenu.add(llogInTextIncorrect);
        plogInMenu.add(tidInput);

        f.add(plogInMenu);

        Global.closeWindow(f);
        f.setVisible(true);
    }

    private static void showError(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(
                null,
                e.toString(),
                "Startup Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
