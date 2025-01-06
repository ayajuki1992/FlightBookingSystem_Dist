package bcu.cmp5332.bookingsystem.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    
    private int id; // customer id
    private String name; // customer name
    private String phone; // customer phone number
    private final List<Booking> bookings = new ArrayList<>(); // list of bookings
    
    // constructor
    public Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
    
    // getter for id
    public int getId() {
        return id;
    }

    // setter for id
    public void setId(int id) {
        this.id = id;
    }

    // getter for name
    public String getName() {
        return name;
    }

    // setter for name
    public void setName(String name) {
        this.name = name;
    }

    // getter for phone
    public String getPhone() {
        return phone;
    }

    // setter for phone
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // getter for bookings
    public List<Booking> getBookings() {
        return bookings;
    }
    
    // method to add a booking
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    // method to remove a booking
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }
    
    // method to get the booking for a specific flight
    public Booking getBookingForFlight(Flight flight) {
        for (Booking booking : bookings) {
            if (booking.getFlight().equals(flight)) {
                return booking; // Return the booking for this flight
            }
        }
        return null; // If no such booking exists
    }
}
