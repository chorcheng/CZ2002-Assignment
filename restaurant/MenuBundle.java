package restaurant;

import java.util.ArrayList;

/**
 * The bundle item (promotional package) in restaurant menu.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */
public class MenuBundle extends MenuComponent {
    /**
     * The price of the bundle.
     */
    private double price;

    /**
     * The quantity of the bundle. Mainly used in orders.
     */
    private int quantity;

    /**
     * The content (children) of the bundle.
     */
    private ArrayList<MenuComponent> children;

    /**
     * @param name        The name of the component.
     * @param description The description of the component.
     */
    public MenuBundle(int code, String name, String description) {
        super(code, name, description);
        children = new ArrayList<>();
    }

    public MenuBundle(MenuBundle mb) {
        this(mb.code, mb.name, mb.description);
        children = new ArrayList<>(mb.children);
        this.price = mb.price;
    }

    /**
     * Returns the price of the bundle.
     *
     * @return The price of the bundle in SGD (should always be rounded to two
     *         decimal places).
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the bundle.
     *
     * @param price The price of the bundle in SGD.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    /**
     * Returns the quantity of the bundle (ordered).
     *
     * @return The quantity of the bundle (ordered).
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the bundle.
     *
     * @param quantity The quantity of the bundle.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns a child component.
     *
     * @param index The index of the component to be returned.
     * @return The indexed component.
     * @throws IllegalArgumentException If the index is out of range.
     */
    public MenuComponent getChild(int index) {
        if (index < 0 || index >= children.size()) {
            throw new IllegalArgumentException();
        } else
            return children.get(index);
    }

    /**
     * Returns the number of children in the bundle.
     *
     * @return The number of children in the bundle.
     */
    public int getChildrenCount() {
        return children.size();
    }

    /**
     * Adds a component as child.
     *
     * @param child The component to be added as a child.
     */
    public void addChild(MenuComponent child) {
        children.add(child);
    }

    /**
     * Removes a child component by index.
     *
     * @param index The index of the component to be removed.
     * @throws IllegalArgumentException If the index is out of range.
     */
    public void removeChild(int index) {
        if (index < 0 || index >= children.size()) {
            throw new IllegalArgumentException();
        } else
            children.remove(index);
    }

    /**
     * Prints the content of the bundle to standard output in a user-friendly way.
     */
    public void print() {
        System.out.printf("%d\t%s\t%.2f\n", quantity, name, getTotalPrice());
        for (MenuComponent mc : children) {
            System.out.printf("\t%s\n", mc.name);
        }
    }

    public void print(boolean isInvoice) {
        if (isInvoice) {
            print();
            return;
        }
        for (MenuComponent mc : children) {
            System.out.printf("%04d\t%s\n", mc.code, mc.name);
        }

    }

    public int contains(int itemCode) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).code == itemCode)
                return i;
        }
        return -1;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof MenuBundle mb) {
            return this.name.compareTo(mb.name) == 0 && this.price == mb.price;
        } else
            return false;
    }
}
