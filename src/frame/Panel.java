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
import java.util.ArrayList;
import java.util.List;
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
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import files.FileUser;
import menuItems.Item;
import menuItems.PizzaSize;
import technical.Global;
import technical.ItemCheckBox;
import technical.ItemRenderer;

public class Panel {
	
	static ResourceBundle bundle;
	static JFrame f;
	
	static double calculateTotal(
	        JComboBox<PizzaSize> cbSize,
	        JComboBox<Item> cbCrust,
	        JComboBox<Item> cbSauce,
	        List<ItemCheckBox> toppingBoxes
	) {
	    double total = 5.0;

	    Item crust = (Item) cbCrust.getSelectedItem();
	    Item sauce = (Item) cbSauce.getSelectedItem();
	    PizzaSize size = (PizzaSize) cbSize.getSelectedItem();

	    if (crust != null) total += crust.getPrice();
	    if (sauce != null) total += sauce.getPrice();

	    for (ItemCheckBox cb : toppingBoxes) {
	        if (cb.isSelected()) {
	            total += cb.getItem().getPrice();
	        }
	    }

	    if (size != null) {
	        total *= size.getMultiplier();
	    }

	    return total;
	}


	
    static ImageIcon loadIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(Panel.class.getResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
	
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
	        JPanel pRegister1 = new JPanel();
	        pRegister1.setLayout(new BoxLayout(pRegister1, BoxLayout.Y_AXIS));
	        pRegister1.setOpaque(false);
	        pRegister1.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
	        
	        /*
	         * Size
	         */
	        
	        PizzaSize[] sizes = {
	        	    new PizzaSize(10, 1.0),
	        	    new PizzaSize(14, 1.3),
	        	    new PizzaSize(18, 1.6),
	        	    new PizzaSize(22, 1.9),
	        	    new PizzaSize(26, 2.2)
	        	};

	        	JLabel lSize = new JLabel(bundle.getString("lSize"));
	        	lSize.setFont(new Font(Global.lanFont, Font.PLAIN, 20));
	        	lSize.setAlignmentX(Component.LEFT_ALIGNMENT);
	        	pRegister1.add(lSize);

	        	JComboBox<PizzaSize> cbSize = new JComboBox<>(sizes);
	        	cbSize.setFont(new Font(Global.lanFont, Font.PLAIN, 15));
	        	cbSize.setAlignmentX(Component.LEFT_ALIGNMENT);
	        	cbSize.setPreferredSize(new Dimension(240, cbSize.getPreferredSize().height));
	        	cbSize.setMaximumSize(new Dimension(240, cbSize.getPreferredSize().height));


	        	pRegister1.add(cbSize);
	        	pRegister1.add(Box.createVerticalStrut(20));

	        
	        /*
	         * Crust
	         */
	        
	        JLabel lPizza = new JLabel(bundle.getString("iPizza"));
	        lPizza.setFont(new Font(Global.lanFont, Font.BOLD, 40));
	        lPizza.setAlignmentX(Component.LEFT_ALIGNMENT);
	        pRegister1.add(lPizza);
	        pRegister1.add(Box.createVerticalStrut(20));
	        
	        JLabel lCrust = new JLabel(bundle.getString("lCrust"));
	        lCrust.setFont(new Font(Global.lanFont, Font.PLAIN, 20));
	        lCrust.setAlignmentX(Component.LEFT_ALIGNMENT);
	        pRegister1.add(lCrust);
	        
	        Item[] crusts = {
	            new Item(bundle.getString("iCrustThin"), 1.00, loadIcon("/images/items/crustThin.png", 80, 80)),
	            new Item(bundle.getString("iCrustFilled"), 3.00, loadIcon("/images/items/crustFilled.png", 80, 80)),
	            new Item(bundle.getString("iCrustThick"), 1.50, loadIcon("/images/items/crustThick.jpg", 80, 80))
	        };

	        JComboBox<Item> cbCrust = new JComboBox<>(crusts);
	        cbCrust.setRenderer(new ItemRenderer());
	        cbCrust.setFont(new Font(Global.lanFont, Font.PLAIN, 15));
	        cbCrust.setAlignmentX(Component.LEFT_ALIGNMENT);
	        cbCrust.setMaximumSize(cbCrust.getPreferredSize());
	        pRegister1.add(cbCrust);
	        pRegister1.add(Box.createVerticalStrut(20));
	        
	        /*
	         * Sauce
	         */
	        
	        JLabel lSauce = new JLabel(bundle.getString("lSauce"));
	        lSauce.setFont(new Font(Global.lanFont, Font.PLAIN, 20));
	        lSauce.setAlignmentX(Component.LEFT_ALIGNMENT);
	        pRegister1.add(lSauce);
	        
	        
	        Item[] sauce = {
	            new Item(bundle.getString("iTomataSauce"), 1.50, loadIcon("/images/items/tomatoSauce.jpg", 80, 80)),
	            new Item(bundle.getString("iMayo"), 1.00, loadIcon("/images/items/mayo.jpg", 80, 80)),
	            new Item(bundle.getString("ibbqSauce"), 1.50, loadIcon("/images/items/bbqSauce.jpg", 80, 80))
	        };

	        JComboBox<Item> cbSauce = new JComboBox<>(sauce);
	        cbSauce.setRenderer(new ItemRenderer());
	        cbSauce.setFont(new Font(Global.lanFont, Font.PLAIN, 15));
	        cbSauce.setAlignmentX(Component.LEFT_ALIGNMENT);
	        cbSauce.setMaximumSize(cbSauce.getPreferredSize());
	        pRegister1.add(cbSauce);
	        
	        /*
	         * Toppings
	         */
	        
	        JPanel pToppings = new JPanel();
	        pToppings.setLayout(new BoxLayout(pToppings, BoxLayout.Y_AXIS));
	        pToppings.setOpaque(false);
	        
	        
	        TitledBorder tbBorderToppings = BorderFactory.createTitledBorder(bundle.getString("tToppings"));
	        tbBorderToppings.setTitleFont(new Font(Global.lanFont, Font.PLAIN, 20));
	        pToppings.setBorder(tbBorderToppings);

	        Item[] toppings = {
	        	    new Item(bundle.getString("tCheese"), 1.0, null),
	        	    new Item(bundle.getString("tPepperoni"), 1.50, null),
	        	    new Item(bundle.getString("tMushrooms"), 1.70, null),
	        	    new Item(bundle.getString("tOnions"), 1.00, null),
	        	    new Item(bundle.getString("tOlives"), 0.65, null)
	        	};


	        List<ItemCheckBox> toppingBoxes = new ArrayList<>();

	        for (Item topping : toppings) {
	            ItemCheckBox cb = new ItemCheckBox(topping);
	            cb.setFont(new Font(Global.lanFont, Font.PLAIN, 15));
	            cb.setAlignmentX(Component.LEFT_ALIGNMENT);

	            pToppings.add(cb);
	            pToppings.add(Box.createVerticalStrut(10));

	            toppingBoxes.add(cb);
	        }

	        
	        
	        pToppings.add(Box.createVerticalGlue());

	        pRegister1.add(Box.createVerticalStrut(20));
	        pToppings.setMaximumSize(pToppings.getPreferredSize());
	        pRegister1.add(pToppings);

	        pRegister1.add(Box.createVerticalGlue());
	        
	        /*
	         * Price
	         */
	        
	        JPanel pSummary = new JPanel();
	        pSummary.setLayout(new BoxLayout(pSummary, BoxLayout.Y_AXIS));
	        pSummary.setOpaque(false);
	        pSummary.setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true),
	                new EmptyBorder(20, 20, 20, 20)
	        ));
	        pSummary.setPreferredSize(new Dimension(300, 200));

	        // Price label
	        JLabel lTotalTitle = new JLabel(bundle.getString("lTotalPrice"));
	        lTotalTitle.setFont(new Font(Global.lanFont, Font.PLAIN, 20));
	        lTotalTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

	        JLabel lTotalPrice = new JLabel("€5.00");
	        lTotalPrice.setFont(new Font(Global.lanFont, Font.BOLD, 32));
	        lTotalPrice.setAlignmentX(Component.CENTER_ALIGNMENT);
	        ActionListener priceUpdater = e -> {
	            double total = calculateTotal(cbSize, cbCrust, cbSauce, toppingBoxes);
	            lTotalPrice.setText("€" + String.format("%.2f", total));
	        };

	        cbSize.addActionListener(priceUpdater);
	        cbCrust.addActionListener(priceUpdater);
	        cbSauce.addActionListener(priceUpdater);

	        for (ItemCheckBox cb : toppingBoxes) {
	            cb.addActionListener(priceUpdater);
	        }

	        priceUpdater.actionPerformed(null);



	        // Add button
	        JButton bAddToCart = new JButton(bundle.getString("bAddToCart"));
	        bAddToCart.setFont(new Font(Global.lanFont, Font.BOLD, 20));
	        bAddToCart.setAlignmentX(Component.CENTER_ALIGNMENT);
	        bAddToCart.setMaximumSize(new Dimension(220, 50));

	        // Layout
	        pSummary.add(lTotalTitle);
	        pSummary.add(Box.createVerticalStrut(10));
	        pSummary.add(lTotalPrice);
	        pSummary.add(Box.createVerticalStrut(30));
	        pSummary.add(bAddToCart);
	        
	        /*
	         * Wrapper
	         */
	        JPanel wrapper = new JPanel();
	        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
	        wrapper.setOpaque(false);

	        wrapper.add(pRegister1);
	        wrapper.add(Box.createHorizontalStrut(50));
	        wrapper.add(pSummary);
	        wrapper.add(Box.createHorizontalGlue());


	        panel.setLayout(new BorderLayout());
	        panel.add(wrapper, BorderLayout.CENTER);
	        
	        break;


	        case "lookUp":
	            
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
	                String selected = (String) cbLanguages.getSelectedItem();
	                String langCode = selected.equals("Latviešu") ? "lv" : "en";
	                
	                FileUser.settingsWriter("lan", langCode);
	                
	                Global.settingsUpdater();
	                
	                f.dispose();
	                Panel.panel();
	            });

	            panel.setLayout(new BorderLayout());
	            panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 20, 20));
	            
	            panel.add(pLanguage, BorderLayout.NORTH);
	            
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
					contentWrapper.setBorder(new EmptyBorder(40, 40, 40, 40));


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
					contentWrapper.setBorder(new EmptyBorder(40, 40, 40, 40));


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
					contentWrapper.setBorder(new EmptyBorder(40, 40, 40, 1300));

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
