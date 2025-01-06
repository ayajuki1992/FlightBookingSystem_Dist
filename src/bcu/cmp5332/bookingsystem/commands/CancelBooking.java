package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.BookingDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.*;  
import java.io.IOException;

public class CancelBooking implements Command {

    private final int customerId;       // ID of the customer canceling the booking
    private final String flightNumber;  // Flight number for the booking to cancel

    // Constructor to initialize fields
    public CancelBooking(int customerId, String flightNumber) {
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

        // Find the booking for this customer on the given flight
        Booking booking = customer.getBookingForFlight(flight);
        if (booking == null) { // If no such booking exists, throw an error
            throw new FlightBookingSystemException("No booking found for Customer ID " + customerId + " on Flight: " + flightNumber);
        }

        // Remove the Booking from the Customer's list and Flight's passenger list
        customer.removeBooking(booking);       // Remove from customer's bookings
        flight.removePassenger(customer);      // Remove customer from flight's passengers
        fbs.removeBooking(booking);            // Remove from the FlightBookingSystem's bookings

        // Now update the system data to reflect the cancellation
        BookingDataManager bookingDataManager = new BookingDataManager();
        try {
            // Store the data of all bookings in the system after cancellation
            bookingDataManager.storeData(fbs);  // Write all bookings to the file 
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Error saving booking data after cancellation.");
        }

        // Confirm success to the user
        System.out.println("Booking successfully canceled for Customer ID: " + customerId + " on Flight: " + flightNumber);
    }
}
