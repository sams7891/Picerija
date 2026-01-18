package menuItems;

public class PizzaSize {

    private final int diameter;
    private final double multiplier;

    public PizzaSize(int diameter, double multiplier) {
        this.diameter = diameter;
        this.multiplier = multiplier;
    }

    public int getDiameter() {
        return diameter;
    }

    public double getMultiplier() {
        return multiplier;
    }

    @Override
    public String toString() {
        return diameter + " cm";
    }
}
