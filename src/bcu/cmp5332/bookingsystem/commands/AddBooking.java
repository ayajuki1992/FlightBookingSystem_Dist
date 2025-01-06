package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.BookingDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.*;  
import java.io.IOException;
import java.time.LocalDate;

public class AddBooking implements Command {

    private final int customerId;       // ID of the customer making the booking
    private final String flightNumber;  // Flight number for the booking

    // Constructor to initialize fields
    public AddBooking(int customerId, String flightNumber) {
        this.customerId = customerId;
        this.flightNumber = flightNumber;
    }

    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        // Retrieve the Customer by ID
        Customer customer = fbs.getCustomerByID(customerId);
        if (customer == null) { // If customer doesn't exist, throw an error
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }

        // Retrieve the Flight by flight number
        Flight flight = fbs.getFlightByNumber(flightNumber);
        if (flight == null) { // If flight doesn't exist, throw an error
            throw new FlightBookingSystemException("Flight with number " + flightNumber + " not found.");
        }

        // Create a new Booking with today's date
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(customer, flight, bookingDate);

        // Add the Booking to the Customer's list and Flight's passenger list
        customer.addBooking(booking);       // Add to customer's bookings
        flight.addPassenger(customer);      // Add customer to flight's passengers
        fbs.addBooking(booking);            // Add to the FlightBookingSystem's bookings

        // Now store this new booking to the file
        BookingDataManager bookingDataManager = new BookingDataManager();
        try {
            // Store the data of all bookings in the system (not just the newly added one)
            bookingDataManager.storeData(fbs);  // Write all bookings to the file 
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Error saving booking data.");
        }
        // Confirm success to the user
        System.out.println("Booking successfully created for Customer ID: " + customerId + " on Flight: " + flightNumber + " (" + bookingDate + ")");
    }
}
