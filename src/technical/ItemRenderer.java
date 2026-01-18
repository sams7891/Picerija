package technical;

import javax.swing.*;

import menuItems.Item;

import java.awt.*;

public class ItemRenderer extends JLabel implements ListCellRenderer<Item> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7275975458175060125L;

	public ItemRenderer() {
        setOpaque(true); // important for background highlight
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Item> list,
            Item value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        if (value != null) {
            setText(value.getName() + "€" + value.getPrice());
            setIcon(value.getIcon());
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
