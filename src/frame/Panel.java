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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import files.FileUser;
import menuItems.Item;
import menuItems.PizzaOrder;
import technical.Global;
import technical.ItemCheckBox;
import technical.ItemRenderer;

public class Panel {
	
	public static List<PizzaOrder> allOrders = new ArrayList<>();
	static JPanel contentWrapper;
    static JButton bRegister;
    static JButton bLookUp;
    static JButton bSettings;
	static ResourceBundle bundle;
	static JFrame f;
	
	private static JPanel createColumnCard() {
	    JPanel card = new JPanel() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = -918757145678230349L;

			@Override
	        protected void paintComponent(Graphics g) {
	            Graphics2D g2 = (Graphics2D) g.create();
	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            // Semi-transparent white background for a modern "glass" look
	            g2.setColor(new Color(255, 255, 255, 180)); 
	            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
	            g2.dispose();
	        }
	    };
	    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
	    card.setOpaque(false);
	    // Standard padding inside each column
	    card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
	    return card;
	}
	
	private static JPanel createItemWithSpinner(Item item) {
	    JPanel row = new JPanel();
	    row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
	    row.setOpaque(false);

	    JLabel label = new JLabel(
	        item.getName() + "  €" + String.format("%.2f", item.getPrice())
	    );
	    label.setFont(new Font(Global.lanFont, Font.PLAIN, 16));

	    JSpinner spinner = new JSpinner(
	        new SpinnerNumberModel(0, 0, 10, 1)
	    );
	    spinner.setMaximumSize(new Dimension(70, 30));

	    row.add(label);
	    row.add(Box.createHorizontalGlue());
	    row.add(spinner);

	    // store reference for later price calculation
	    row.putClientProperty("item", item);
	    row.putClientProperty("spinner", spinner);

	    return row;
	}
	
	private static void resetForm(JTextField n, JTextField p, JTextField a, JCheckBox d, List<ItemCheckBox> t, List<JPanel> ex, JLabel price) {
	    n.setText("");
	    p.setText("");
	    a.setText("");
	    a.setEnabled(false);
	    d.setSelected(false);
	    for (ItemCheckBox cb : t) cb.setSelected(false);
	    for (JPanel panel : ex) ((JSpinner)panel.getClientProperty("spinner")).setValue(0);
	    price.setText("€0.00");
	}

	static double calculateTotal(
	        JComboBox<Item> cbCrust,
	        JComboBox<Item> cbSauce,
	        List<ItemCheckBox> toppingBoxes,
	        JComboBox<String> cbSize
	) {
	    double total = 0.0;

	    Item crust = (Item) cbCrust.getSelectedItem();
	    Item sauce = (Item) cbSauce.getSelectedItem();

	    if (crust != null) total += crust.getPrice();
	    if (sauce != null) total += sauce.getPrice();

	    for (ItemCheckBox cb : toppingBoxes) {
	        if (cb.isSelected()) total += cb.getItem().getPrice();
	    }

	    // Apply size multiplier
	    double[] multipliers = {1.0, 1.2, 1.5, 1.8, 2.0};
	    int selectedIndex = cbSize.getSelectedIndex(); // 0 to 4
	    total *= multipliers[selectedIndex];

	    return total;
	}


	private static String getLangCode(String languageName) {
	    switch(languageName) {
	        case "Latviešu": return "lv";
	        case "Русский":  return "ru";
	        default:         return "en";
	    }
	}

	
    static ImageIcon loadIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(Panel.class.getResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
	
	@SuppressWarnings("unused")
	static Component OptionSwitch(String option, PizzaOrder orderToEdit) {
	    JPanel panel = new JPanel() {
	        private static final long serialVersionUID = -918757145678230349L;

	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            Graphics2D g2 = (Graphics2D) g.create();
	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            g2.setColor(getBackground());
	            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
	            g2.dispose();
	        }
	    };

	    panel.setOpaque(false);
	    panel.setBackground(new Color(240, 240, 240));

	    switch (option) {
	        case "register":
	            // --- COLUMN 1: Pizza Builder ---
	            JPanel colPizza = createColumnCard();
	            JLabel lPizza = new JLabel(bundle.getString("iPizza"));
	            lPizza.setFont(new Font(Global.lanFont, Font.BOLD, 32));
	            lPizza.setAlignmentX(Component.CENTER_ALIGNMENT);
	            colPizza.add(lPizza);
	            colPizza.add(Box.createVerticalStrut(20));
	            
	            JCheckBox cbIncludePizza = new JCheckBox(bundle.getString("wanOrderPizza"), true);
	            cbIncludePizza.setFont(new Font(Global.lanFont, Font.BOLD, 16));
	            cbIncludePizza.setOpaque(false);
	            cbIncludePizza.setAlignmentX(Component.CENTER_ALIGNMENT);
	            colPizza.add(cbIncludePizza);
	            colPizza.add(Box.createVerticalStrut(10));

	            JLabel lCrust = new JLabel(bundle.getString("lCrust"));
	            lCrust.setFont(new Font(Global.lanFont, Font.BOLD, 18));
	            lCrust.setAlignmentX(Component.CENTER_ALIGNMENT);
	            colPizza.add(lCrust);

	            Item[] crusts = {
	                new Item(bundle.getString("iCrustThin"), 1.00, loadIcon("/images/items/crustThin.png", 60, 60)),
	                new Item(bundle.getString("iCrustFilled"), 3.00, loadIcon("/images/items/crustFilled.png", 60, 60)),
	                new Item(bundle.getString("iCrustThick"), 1.00, loadIcon("/images/items/crustThick.jpg", 60, 60))
	            };
	            JComboBox<Item> cbCrust = new JComboBox<>(crusts);
	            cbCrust.setRenderer(new ItemRenderer());
	            cbCrust.setMaximumSize(new Dimension(300, 50));
	            cbCrust.setAlignmentX(Component.CENTER_ALIGNMENT);
	            colPizza.add(cbCrust);
	            colPizza.add(Box.createVerticalStrut(15));

	            JLabel lSauce = new JLabel(bundle.getString("lSauce"));
	            lSauce.setFont(new Font(Global.lanFont, Font.BOLD, 18));
	            lSauce.setAlignmentX(Component.CENTER_ALIGNMENT);
	            colPizza.add(lSauce);

	            Item[] sauces = {
	                new Item(bundle.getString("iTomataSauce"), 1.00, loadIcon("/images/items/tomatoSauce.jpg", 60, 60)),
	                new Item(bundle.getString("iMayo"), 1.00, loadIcon("/images/items/mayo.jpg", 60, 60)),
	                new Item(bundle.getString("ibbqSauce"), 1.00, loadIcon("/images/items/bbqSauce.jpg", 60, 60))
	            };
	            JComboBox<Item> cbSauce = new JComboBox<>(sauces);
	            cbSauce.setRenderer(new ItemRenderer());
	            cbSauce.setMaximumSize(new Dimension(300, 50));
	            cbSauce.setAlignmentX(Component.CENTER_ALIGNMENT);
	            colPizza.add(cbSauce);

	            JPanel pToppings = new JPanel();
	            pToppings.setLayout(new BoxLayout(pToppings, BoxLayout.Y_AXIS));
	            pToppings.setAlignmentX(Component.CENTER_ALIGNMENT);
	            pToppings.setOpaque(false);
	            TitledBorder tbToppings = BorderFactory.createTitledBorder(bundle.getString("tToppings"));
	            tbToppings.setTitleFont(new Font(Global.lanFont, Font.BOLD, 18));
	            pToppings.setBorder(tbToppings);

	            Item[] toppings = {
	                new Item(bundle.getString("tCheese"), 0.50, null),
	                new Item(bundle.getString("tPepperoni"), 1.20, null),
	                new Item(bundle.getString("tMushrooms"), 0.70, null),
	                new Item(bundle.getString("tOnions"), 0.40, null),
	                new Item(bundle.getString("tOlives"), 0.60, null)
	            };
	            List<ItemCheckBox> toppingBoxes = new ArrayList<>();
	            for (Item t : toppings) {
	                ItemCheckBox cb = new ItemCheckBox(t);
	                pToppings.add(cb);
	                toppingBoxes.add(cb);
	            }
	            pToppings.setMaximumSize(new Dimension(300, pToppings.getPreferredSize().height));
	            colPizza.add(Box.createVerticalStrut(15));
	            colPizza.add(pToppings);

	            String[] sizes = {"10 cm", "14 cm", "18 cm", "22 cm", "26 cm"};
	            JComboBox<String> cbSize = new JComboBox<>(sizes);
	            cbSize.setMaximumSize(new Dimension(300, 40));
	            colPizza.add(Box.createVerticalStrut(15));
	            colPizza.add(cbSize);
	            colPizza.add(Box.createVerticalGlue());

	            // --- COLUMN 2: Extras ---
	            JPanel colExtras = createColumnCard();
	            JLabel lDrinksHeader = new JLabel(bundle.getString("lDrinks"));
	            lDrinksHeader.setFont(new Font(Global.lanFont, Font.BOLD, 28));
	            lDrinksHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
	            colExtras.add(lDrinksHeader);

	            Item[] drinks = { new Item("Cola", 1.50, null), new Item("Fanta", 1.50, null), new Item(bundle.getString("dWater"), 1.00, null) };
	            Item[] snacks = {
	                new Item(bundle.getString("sMozzarellaSticks"), 3.50, null),
	                new Item(bundle.getString("sGarlicBread"), 2.80, null),
	                new Item(bundle.getString("sOnionRings"), 3.00, null)
	            };

	            List<JPanel> extraPanels = new ArrayList<>();
	            for (Item drink : drinks) {
	                JPanel p = createItemWithSpinner(drink);
	                extraPanels.add(p);
	                colExtras.add(p);
	            }

	            colExtras.add(Box.createVerticalStrut(20));
	            JLabel lSnacksHeader = new JLabel(bundle.getString("lSnacks"));
	            lSnacksHeader.setFont(new Font(Global.lanFont, Font.BOLD, 28));
	            lSnacksHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
	            colExtras.add(lSnacksHeader);

	            for (Item snack : snacks) {
	                JPanel p = createItemWithSpinner(snack);
	                extraPanels.add(p);
	                colExtras.add(p);
	            }
	            colExtras.add(Box.createVerticalGlue());

	            // --- COLUMN 3: Customer Info & Summary ---
	            JPanel colCustomer = createColumnCard();
	            JLabel lCustHeader = new JLabel(bundle.getString("lCustHeader"));
	            lCustHeader.setFont(new Font(Global.lanFont, Font.BOLD, 28));
	            lCustHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
	            colCustomer.add(lCustHeader);

	            JTextField tfName = new JTextField();
	            tfName.setBorder(BorderFactory.createTitledBorder(bundle.getString("tfName")));
	            tfName.setMaximumSize(new Dimension(300, 50));
	            colCustomer.add(tfName);

	            JTextField tfPhone = new JTextField();
	            tfPhone.setBorder(BorderFactory.createTitledBorder(bundle.getString("tfPhone")));
	            tfPhone.setMaximumSize(new Dimension(300, 50));
	            colCustomer.add(tfPhone);

	            JCheckBox cbDelivery = new JCheckBox(bundle.getString("cbDelivery"));
	            cbDelivery.setAlignmentX(Component.LEFT_ALIGNMENT);
	            cbDelivery.setOpaque(false);
	            colCustomer.add(cbDelivery);

	            JTextField tfAddress = new JTextField();
	            tfAddress.setMaximumSize(new Dimension(300, 40));
	            tfAddress.setEnabled(false);
	            colCustomer.add(tfAddress);

	            colCustomer.add(Box.createVerticalGlue());
	            JLabel lTotalPrice = new JLabel("€0.00");
	            lTotalPrice.setFont(new Font(Global.lanFont, Font.BOLD, 36));
	            lTotalPrice.setAlignmentX(Component.CENTER_ALIGNMENT);
	            colCustomer.add(lTotalPrice);

	            JButton bConfirm = new JButton(bundle.getString("bConfirm"));
	            bConfirm.setFont(new Font(Global.lanFont, Font.BOLD, 20));
	            bConfirm.setMaximumSize(new Dimension(250, 50));
	            bConfirm.setAlignmentX(Component.CENTER_ALIGNMENT);
	            colCustomer.add(bConfirm);

	            // --- LISTENERS ---
	            final double[] deliveryFee = {0.0};
	            ActionListener priceUpdater = e -> {
	                double total = 0.0;
	                
	                // Only calculate pizza price if the checkbox is selected
	                if (cbIncludePizza.isSelected()) {
	                    total += calculateTotal(cbCrust, cbSauce, toppingBoxes, cbSize);
	                }

	                for (JPanel p : extraPanels) {
	                    Item item = (Item) p.getClientProperty("item");
	                    JSpinner spinner = (JSpinner) p.getClientProperty("spinner");
	                    total += item.getPrice() * (Integer) spinner.getValue();
	                }
	                total += deliveryFee[0];
	                lTotalPrice.setText(String.format("€%.2f", total));
	            };

	            // Add the listener to the new checkbox so the price updates instantly
	            cbIncludePizza.addActionListener(priceUpdater);

	            cbDelivery.addActionListener(e -> {
	                tfAddress.setEnabled(cbDelivery.isSelected());
	                // Only randomize fee if it's a fresh click, otherwise keep existing if editing logic requires it
	                // Simplified here: Always 5-25 range on toggle
	                deliveryFee[0] = cbDelivery.isSelected() ? 5 + (Math.random() * 20) : 0.0;
	                priceUpdater.actionPerformed(null);
	            });

	            cbCrust.addActionListener(priceUpdater);
	            cbSauce.addActionListener(priceUpdater);
	            cbSize.addActionListener(priceUpdater);
	            for (ItemCheckBox cb : toppingBoxes) cb.addActionListener(priceUpdater);
	            for (JPanel p : extraPanels) ((JSpinner)p.getClientProperty("spinner")).addChangeListener(ev -> priceUpdater.actionPerformed(null));

	            // --- CONFIRM BUTTON LOGIC ---
	            bConfirm.addActionListener(e -> {
	                String name = tfName.getText().trim();
	                String phone = tfPhone.getText().trim();
	                String addr = tfAddress.getText().trim();

	                if (name.isEmpty() || phone.isEmpty() || (cbDelivery.isSelected() && (addr.isEmpty() || addr.equals("Address...")))) {
	                    JOptionPane.showMessageDialog(f, bundle.getString("errMesNotAllInputs"), "Error", 0);
	                    return;
	                }

	                PizzaOrder order = new PizzaOrder();
	                order.customerName = name;
	                order.phone = phone;
	                order.address = cbDelivery.isSelected() ? addr : "Pickup";

	                // Logic for optional Pizza
	                if (cbIncludePizza.isSelected()) {
	                    order.crust = (Item) cbCrust.getSelectedItem();
	                    order.sauce = (Item) cbSauce.getSelectedItem();
	                    order.size = (String) cbSize.getSelectedItem();
	                    order.toppings = new ArrayList<>();
	                    for (ItemCheckBox t : toppingBoxes) if (t.isSelected()) order.toppings.add(t.getItem());
	                } else {
	                    order.crust = null;
	                    order.sauce = null;
	                    order.size = "No Pizza";
	                    order.toppings = new ArrayList<>();
	                }
	                
	                order.toppings = new ArrayList<>();
	                for (ItemCheckBox t : toppingBoxes) if (t.isSelected()) order.toppings.add(t.getItem());

	                order.extras = new ArrayList<>();
	                for (JPanel p : extraPanels) {
	                    int qty = (int)((JSpinner)p.getClientProperty("spinner")).getValue();
	                    if(qty > 0) order.extras.add(new PizzaOrder.OrderDetail((Item)p.getClientProperty("item"), qty));
	                }
	                
	                order.totalPrice = Double.parseDouble(lTotalPrice.getText().replace("€", "").replace(",", "."));

	                allOrders.add(order);
	                resetForm(tfName, tfPhone, tfAddress, cbDelivery, toppingBoxes, extraPanels, lTotalPrice);
	                cbIncludePizza.setSelected(true); // Reset the checkbox
	                JOptionPane.showMessageDialog(f, "Order Confirmed!");
	            });

	         // ==========================================
	         // LOGIC: PRE-FILL FORM IF EDITING
	         // ==========================================
	         if (orderToEdit != null) {
	             tfName.setText(orderToEdit.customerName);
	             tfPhone.setText(orderToEdit.phone);
	             
	             if (!orderToEdit.address.equals("Pickup")) {
	                 cbDelivery.setSelected(true);
	                 tfAddress.setEnabled(true);
	                 tfAddress.setText(orderToEdit.address);
	                 deliveryFee[0] = 5.0; 
	             }
	             
	             // START OF PIZZA CHECK
	             if (orderToEdit.crust != null) {
	                 cbIncludePizza.setSelected(true); 
	                 
	                 // Match Crust
	                 for(int i=0; i<cbCrust.getItemCount(); i++) {
	                     if(cbCrust.getItemAt(i).getName().equals(orderToEdit.crust.getName())) {
	                         cbCrust.setSelectedIndex(i); break;
	                     }
	                 }
	                 
	                 // Match Sauce
	                 if (orderToEdit.sauce != null) {
	                     for(int i=0; i<cbSauce.getItemCount(); i++) {
	                         if(cbSauce.getItemAt(i).getName().equals(orderToEdit.sauce.getName())) {
	                             cbSauce.setSelectedIndex(i); break;
	                         }
	                     }
	                 }
	                 
	                 cbSize.setSelectedItem(orderToEdit.size);

	                 // Match Toppings
	                 for (ItemCheckBox box : toppingBoxes) {
	                     for (Item t : orderToEdit.toppings) {
	                         if (box.getItem().getName().equals(t.getName())) {
	                             box.setSelected(true);
	                         }
	                     }
	                 }
	             } else {
	                 // IMPORTANT: Uncheck if there is no pizza
	                 cbIncludePizza.setSelected(false);
	             }
	             // END OF PIZZA CHECK (Old duplicate code was here - REMOVE IT)

	             // Match Extras (Drinks/Snacks) - Keep this outside the pizza check
	             for (JPanel p : extraPanels) {
	                 Item item = (Item) p.getClientProperty("item");
	                 JSpinner spinner = (JSpinner) p.getClientProperty("spinner");
	                 
	                 if (orderToEdit.extras != null) {
	                     for (PizzaOrder.OrderDetail extra : orderToEdit.extras) {
	                         if (extra.item.getName().equals(item.getName())) {
	                             spinner.setValue(extra.quantity);
	                         }
	                     }
	                 }
	             }
	             
	             priceUpdater.actionPerformed(null);
	         }

	            JPanel wrapper = new JPanel();
	            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
	            wrapper.setOpaque(false);
	            wrapper.add(colPizza);
	            wrapper.add(Box.createHorizontalStrut(20));
	            wrapper.add(colExtras);
	            wrapper.add(Box.createHorizontalStrut(20));
	            wrapper.add(colCustomer);

	            panel.setLayout(new BorderLayout());
	            panel.add(wrapper, BorderLayout.CENTER);
	            break;

	        case "lookUp":
	            JPanel mainLookUp = new JPanel(new BorderLayout());
	            mainLookUp.setOpaque(false);
	            mainLookUp.setBorder(new EmptyBorder(20, 20, 20, 20));

	            JLabel head = new JLabel(bundle.getString("head"));
	            head.setFont(new Font(Global.lanFont, Font.BOLD, 28));
	            mainLookUp.add(head, BorderLayout.NORTH);

	            JPanel list = new JPanel();
	            list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
	            list.setOpaque(false);

	            for (PizzaOrder o : allOrders) {
	                JPanel card = new JPanel(new BorderLayout());
	                card.setMaximumSize(new Dimension(1000, 80));
	                card.setBackground(Color.WHITE);
	                card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

	                String info = String.format("<html><span style='font-size:14px'><b>%s</b> (%s)</span><br/><span style='color:gray'>%s</span> - <b>€%.2f</b></html>", 
	                        o.customerName, o.phone, o.address, o.totalPrice);
	                JLabel lInfo = new JLabel(info);
	                lInfo.setBorder(new EmptyBorder(0, 10, 0, 0));
	                card.add(lInfo, BorderLayout.CENTER);

	                JPanel acts = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	                acts.setOpaque(false);
	                JButton bDel = new JButton(bundle.getString("delete"));
	                JButton bMod = new JButton(bundle.getString("modify"));

	                bDel.addActionListener(e -> {
	                    allOrders.remove(o);
	                    // Refresh view
	                    contentWrapper.removeAll();
	                    contentWrapper.add(OptionSwitch("lookUp", null), BorderLayout.CENTER);
	                    contentWrapper.revalidate(); contentWrapper.repaint();
	                });

	                bMod.addActionListener(e -> {
	                    // 1. Remove the old order temporarily (it will be re-added as new upon Confirm)
	                    allOrders.remove(o);
	                    
	                    // 2. Adjust Tab Buttons
	                    bRegister.setEnabled(false);
	                    bLookUp.setEnabled(true);
	                    bSettings.setEnabled(true);
	                    
	                    // 3. Load Register view with data
	                    contentWrapper.removeAll();
	                    contentWrapper.add(OptionSwitch("register", o), BorderLayout.CENTER);
	                    contentWrapper.revalidate();
	                    contentWrapper.repaint();
	                });

	                acts.add(bMod); acts.add(bDel);
	                card.add(acts, BorderLayout.EAST);
	                list.add(card); list.add(Box.createVerticalStrut(10));
	            }

	            JScrollPane scroll = new JScrollPane(list);
	            scroll.setOpaque(false);
	            scroll.getViewport().setOpaque(false);
	            scroll.setBorder(null);
	            
	            mainLookUp.add(scroll, BorderLayout.CENTER);
	            panel.setLayout(new BorderLayout());
	            panel.add(mainLookUp, BorderLayout.CENTER);
	            break;

	        case "settings":
	            JPanel pLanguage = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
	            JLabel lLanguage = new JLabel(bundle.getString("lLanguage"));
	            String[] languages = {"Latviešu", "English", "Русский"};
	            
	            JComboBox<String> cbLanguages = new JComboBox<>(languages);
	            String language = Global.locale.getLanguage();
	            
	            cbLanguages.setFont(new Font(Global.lanFont, Font.PLAIN, 18));
	            cbLanguages.setOpaque(false);
	            
	            // Set initial selection based on current locale
	            switch(language) {
	                case "lv": cbLanguages.setSelectedItem("Latviešu"); break;
	                case "en": cbLanguages.setSelectedItem("English"); break;
	                case "ru": cbLanguages.setSelectedItem("Русский"); break;
	            }
	            
	            // This listener saves the choice immediately when changed
	            cbLanguages.addActionListener(e -> {
	                String selectedStr = (String) cbLanguages.getSelectedItem();
	                String langCode = getLangCode(selectedStr);
	                FileUser.settingsWriter("lan", langCode);
	            });
	            
	            lLanguage.setFont(new Font(Global.lanFont, Font.PLAIN, 18));
	            pLanguage.setOpaque(false);
	            pLanguage.add(lLanguage);
	            pLanguage.add(cbLanguages);

	            JButton rButton = new JButton(bundle.getString("rButton")); 
	            rButton.setFont(new Font(Global.lanFont, Font.PLAIN, 18));
	            
	            rButton.addActionListener(e -> {
	                // Get current selection and ensure it is saved before restart
	                String selectedStr = (String) cbLanguages.getSelectedItem();
	                String langCode = getLangCode(selectedStr);
	                
	                FileUser.settingsWriter("lan", langCode);
	                Global.settingsUpdater();
	                
	                // Refresh the UI
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
			private static final long serialVersionUID = 8638768058679045493L;
			@Override
			protected void paintComponent(Graphics g) {
				 super.paintComponent(g);
	                Graphics2D g2d = (Graphics2D) g.create();
	                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
	                g2d.setColor(Color.gray); 
	                g2d.fillRect(0, 0, getWidth(), getHeight());
	                g2d.dispose();
			}
		};
		
		JPanel pBackground = new JPanel() {
			private static final long serialVersionUID = -1969924032365564895L;
			Image background = new ImageIcon(getClass().getResource("/images/test2.jpg")).getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        // FIXED: Removed "JPanel" from the start. Now it assigns to the static class variable.
        contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBorder(new EmptyBorder(40, 40, 40, 40));
        contentWrapper.setOpaque(false);

        trans.add(contentWrapper, BorderLayout.CENTER);

			if(screenSize.width == 1920 && screenSize.height == 1080) {
				f.setExtendedState(JFrame.MAXIMIZED_BOTH);
				f.setUndecorated(true);
			} else
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
            // FIXED: Removed "JButton" from the start.
			bRegister = new JButton(bundle.getString("register"));
			bLookUp = new JButton(bundle.getString("lookup_orders"));
			bSettings = new JButton(bundle.getString("settings"));
			
			JPanel pOptions = new JPanel();		
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
					contentWrapper.add(OptionSwitch("register", null), BorderLayout.CENTER);
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
					contentWrapper.add(OptionSwitch("lookUp", null), BorderLayout.CENTER);
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
					contentWrapper.add(OptionSwitch("settings", null), BorderLayout.CENTER);
					contentWrapper.revalidate();
					contentWrapper.repaint();
					contentWrapper.setBorder(new EmptyBorder(40, 40, 40, 1300));
				}
			});
			
			f.add(pOptions, BorderLayout.NORTH);
			
			Global.closeWindow(f);
			trans.setOpaque(false);
			f.add(trans, BorderLayout.CENTER);

            // INITIAL VIEW: Set a default view so the screen isn't blank on startup
            bRegister.setEnabled(false);
            contentWrapper.add(OptionSwitch("register", null), BorderLayout.CENTER);

			f.setVisible(true);
	}
}