package restaurant;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Order objects include a {@link Menu} object that is used for ordering items
 * and other relevant information.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */
public class Order {
    /**
     * A menu contains the ordered items and quantity.
     */
    public Menu order;

    // Making order menu private doesn't make sense since Menu class already handles
    // all the functions needed.
    // TODO Unlike the order menu above, discountRate could be private, but I'm too
    // lazy to write getter and setters now.

    /**
     * The membership discount that is applied to the order.
     */
    public double discountRate = 0.1;

    /**
     * Whether the order is made by the customer holding the membership or not.
     */
    public boolean isMember;

    /**
     * The tax rate that is applied to the order.
     */
    public double taxRate = 0.07;

    /**
     * The reservation behind the order.
     */
    public Reservation reservation;

    /**
     * Whether the order is active or not.
     */
    private boolean active;

    private Staff staff;

    /**
     * The total sales price per order 
     */
    private double totalPrice;

    // Disable default constructor.
    private Order() { }

    /**
     * Construct an order from a reservation.
     *
     * @param reservation The reservation from which the order will be created. Can
     *                    not be {@code null}.
     * @param staff       The staff who created the order.
     * @throws IllegalArgumentException If reservation or staff is {@code null}.
     */
    public Order(Reservation reservation, Staff staff) {
        this.reservation = reservation;
        reservation.time = LocalDateTime.now();
        this.staff = staff;
        order = new Menu(reservation.time.toString(),"");
        active = true;
    }

    public void addItem(MenuComponent item, int quantity){
        if(item instanceof MenuBundle){
            order.addChild(new MenuBundle((MenuBundle) item));
        } else {
            order.addChild(new MenuLeaf((MenuLeaf) item));
        }
        item.setQuantity(quantity);
    }

    /**
     * Prints the information of the order to standard output in a user-friendly
     * way.
     */
    public void print() {
        // TODO
        System.out.println("Restaurant 0.0");
        System.out.printf("Server:%s\t\tTable:%d\n", staff.getId(), reservation.tableId);
        String t = String.join(" ", reservation.time.toString().split("T"));
        System.out.printf("Time:%s\n", t);
        System.out.println("--------------------------------------");
        totalPrice = 0;
        for(int i = 0; i < order.getChildrenCount(); i++){
            order.getChild(i).print();
            totalPrice += order.getChild(i).getTotalPrice();
        }
        System.out.println("--------------------------------------");

        System.out.printf("\t\tSub-Total:\t%.2f\n", totalPrice);
    }

    /**
     * Prints an invoice of the order to standard output in a user-friendly way and
     * set the order to inactive.
     */
    public void printInvoice(boolean isMember) {
        if(!active) return;
        active = false;
        print();

        if(isMember){
            double discount = totalPrice * discountRate;
            totalPrice -= discount;
            System.out.printf("Membership discount:\t%.2f\n", discount);
        }

        double serviceCharge = totalPrice * 0.1;
        double tax = totalPrice * 1.1 * taxRate;

        System.out.printf("\t\tService Charge:\t%.2f\n", serviceCharge);
        System.out.printf("\t\tTax:\t%.2f\n", tax);


        System.out.printf("\t\tTOTAL:\t%.2f\n", totalPrice + serviceCharge + tax);
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    public int getStaffId(){
        return staff.getId();
    }
}