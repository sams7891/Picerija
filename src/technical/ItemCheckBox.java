package technical;

import javax.swing.JCheckBox;

import menuItems.Item;

public class ItemCheckBox extends JCheckBox {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8873385768425725048L;
	private final Item item;

    public ItemCheckBox(Item item) {
        super(formatText(item));
        this.item = item;
        setOpaque(false);
    }

    private static String formatText(Item item) {
        return item.getName() + "  €" + String.format("%.2f", item.getPrice());
    }

    public Item getItem() {
        return item;
    }
}