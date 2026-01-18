package technical;

import java.awt.Component;
import javax.swing.*;
import menuItems.Item;
import java.awt.BorderLayout;

public class ItemRenderer extends JPanel implements ListCellRenderer<Item> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1053774049451414466L;
	private JLabel iconLabel = new JLabel();
    private JLabel textLabel = new JLabel();

    public ItemRenderer() {
        setLayout(new BorderLayout(5, 0)); // horizontal gap between icon and text
        add(iconLabel, BorderLayout.WEST);
        add(textLabel, BorderLayout.CENTER);
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {

        if (value != null) {
            iconLabel.setIcon(value.getIcon());

            // HTML for multi-line text
            textLabel.setText("<html>" + value.getName() + "<br>€" + String.format("%.2f", value.getPrice()) + "</html>");


        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            textLabel.setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            textLabel.setForeground(list.getForeground());
        }

        return this;
    }
}
