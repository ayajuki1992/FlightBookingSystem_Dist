package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class BookingDataManager implements DataManager {
    
    public final String RESOURCE = "./resources/data/bookings.txt";

    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        // Booking data format: customerId::flightNumber::bookingDate
        try (BufferedReader br = new BufferedReader(new FileReader(RESOURCE))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split each line by "::"
                String[] parts = line.split("::");
                if (parts.length == 3) {
                    int customerId = Integer.parseInt(parts[0]);  // Customer ID
                    String flightNumber = parts[1];                // Flight Number
                    LocalDate bookingDate = LocalDate.parse(parts[2]); // Booking Date

                    // Retrieve the customer and flight from the system
                    Customer customer = fbs.getCustomerByID(customerId);
                    Flight flight = fbs.getFlightByNumber(flightNumber);

                    // If both customer and flight exist, create a booking
                    if (customer != null && flight != null) {
                        Booking booking = new Booking(customer, flight, bookingDate);
                        fbs.addBooking(booking);  // Add the booking to the system
                    } else {
                        throw new FlightBookingSystemException("Customer or Flight not found for booking.");
                    }
                } else {
                    // Invalid data format in the file
                    throw new FlightBookingSystemException("Invalid booking data format in bookings.txt.");
                }
            }
        }
    }

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        // Open the file in append mode
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESOURCE, true))) {
            // Iterate over all bookings and write them to the file
            for (Booking booking : fbs.getBookings()) {
                // Format: customerId::flightNumber::bookingDate
                String line = booking.getCustomer().getId() + "::"
                        + booking.getFlight().getFlightNumber() + "::"
                        + booking.getBookingDate();
                bw.write(line);
                bw.newLine();  // Write each booking on a new line
            }
        }
    }
}