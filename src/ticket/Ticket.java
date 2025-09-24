package ticket;

import contract.Bookable;
import contract.Payable;
import showpiece.Showpiece;
import payment.PaymentMethod;

import java.math.BigDecimal;
import java.util.Objects;

public final class Ticket extends Showpiece implements Bookable, Payable {

    private static int counter = 1000;

    private final int seatNumber;
    private BigDecimal price;
    private boolean occupied;
    private TicketType ticketType;
    private PaymentMethod paymentMethod;

    //ticket must have seatnumber and price mentioned.
    public Ticket(int seatNumber, BigDecimal price) {
        this.seatNumber = seatNumber;
        this.price = price;
        this.occupied = false;
        this.ticketType = TicketType.STANDARD;
        this.paymentMethod = PaymentMethod.CASH;
        counter++;
    }

    public Ticket(int seatNumber, BigDecimal price, TicketType ticketType) {
        this.seatNumber = seatNumber;
        this.price = price;
        this.occupied = false;
        this.ticketType = ticketType;
        this.paymentMethod = PaymentMethod.CASH;
        counter++;
    }

    public Ticket(int seatNumber, BigDecimal price, TicketType ticketType, PaymentMethod paymentMethod) {
        this.seatNumber = seatNumber;
        this.price = price;
        this.occupied = false;
        this.ticketType = ticketType;
        this.paymentMethod = paymentMethod;
        counter++;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean occupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public static int getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "Ticket{seat=" + seatNumber + ", price=" + price + "}";
    }

    @Override
    public boolean isValid() {
        return seatNumber > 0
                && price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public String getDisplayname() {
        return "s";
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatNumber, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Ticket ticket)) return false;
        return Objects.equals(seatNumber, ticket.seatNumber)
                && Objects.equals(price, ticket.price);
    }

    @Override
    public void book() {
        this.occupied = true;
        System.out.println("Booked ticket seat: " + seatNumber);
    }

    public boolean processPayment(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Invalid payment amount for seat " + seatNumber);
            return false;
        }
        
        BigDecimal totalAmount = paymentMethod != null ? paymentMethod.getTotalAmount(price) : price;
        System.out.println("Processing payment of $" + totalAmount + " for ticket at seat " + seatNumber + 
                         " using " + (paymentMethod != null ? paymentMethod.getMethodName() : "Unknown method"));
        
        if (paymentMethod != null && paymentMethod.requiresProcessing()) {
            System.out.println("Payment processing fee: $" + paymentMethod.calculateFee(price));
        }
        
        return true;
    }

    public BigDecimal calculateFinalPrice() {
        return ticketType != null ? ticketType.calculateFinalPrice(price) : price;
    }

    public String getTicketTypeInfo() {
        return ticketType != null ? ticketType.getPriceInfo() : "Standard: $" + price;
    }

    public boolean discounted() {
        return ticketType != null && ticketType.discounted();
    }

    public boolean premium() {
        return ticketType != null && ticketType.premium();
    }

    public String getPaymentMethodInfo() {
        return paymentMethod != null ? paymentMethod.getDescription() : "No payment method set";
    }

    public boolean digitalPayment() {
        return paymentMethod != null && paymentMethod.digital();
    }

    public boolean traditionalPayment() {
        return paymentMethod != null && paymentMethod.traditional();
    }
}
