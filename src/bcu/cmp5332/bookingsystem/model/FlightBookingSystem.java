package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.*;

public class FlightBookingSystem {
    
    // System date set to 2024-11-11
    private final LocalDate systemDate = LocalDate.parse("2024-11-11");
    
    // Maps to store customers and flights
    private final Map<Integer, Customer> customers = new TreeMap<>();
    private final Map<Integer, Flight> flights = new TreeMap<>();
    
    // List to store bookings
    private final List<Booking> bookings = new ArrayList<>();
    

    // Get the system date
    public LocalDate getSystemDate() {
        return systemDate;
    }

    // ============================
    // CUSTOMER MANAGEMENT
    // ============================

    // Get a list of all customers
    public List<Customer> getCustomers() {
        List<Customer> out = new ArrayList<>(customers.values());
        return Collections.unmodifiableList(out);
    }

    // Get a customer by its ID
    public Customer getCustomerByID(int id) throws FlightBookingSystemException {
        if (!customers.containsKey(id)) {
            throw new FlightBookingSystemException("There is no customer with ID: " + id);
        }
        return customers.get(id);
    }

    // Add a customer to the system
    public void addCustomer(Customer customer) throws FlightBookingSystemException {
        if (customers.containsKey(customer.getId())) {
            throw new FlightBookingSystemException("Duplicate customer ID: " + customer.getId());
        }
        customers.put(customer.getId(), customer);
    }

    // ============================
    // FLIGHT MANAGEMENT
    // ============================

    // Get a list of all flights
    public List<Flight> getFlights() {
        List<Flight> out = new ArrayList<>(flights.values());
        return Collections.unmodifiableList(out);
    }

    // Get a flight by its ID
    public Flight getFlightByID(int id) throws FlightBookingSystemException {
        if (!flights.containsKey(id)) {
            throw new FlightBookingSystemException("There is no flight with ID: " + id);
        }
        return flights.get(id);
    }

    // Get a flight by its flight number
    public Flight getFlightByNumber(String flightNumber) throws FlightBookingSystemException {
        for (Flight flight : flights.values()) { // Iterate through all flights
            if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) { // Match flight number
                return flight; // Return matching flight
            }
        }
        throw new FlightBookingSystemException("No flight found with number: " + flightNumber);
    }

    // Add a flight to the system
    public void addFlight(Flight flight) throws FlightBookingSystemException {
        if (flights.containsKey(flight.getId())) {
            throw new FlightBookingSystemException("Duplicate flight ID: " + flight.getId());
        }
        for (Flight existing : flights.values()) {
            if (existing.getFlightNumber().equalsIgnoreCase(flight.getFlightNumber()) 
                && existing.getDepartureDate().isEqual(flight.getDepartureDate())) {
                throw new FlightBookingSystemException("A flight with the same number and departure date already exists.");
            }
        }
        flights.put(flight.getId(), flight);
    }

    // ==========================
    // ADD/GET BOOKINGS METHODS
    // ==========================

    // Add a new booking to the system
    public void addBooking(Booking booking) throws FlightBookingSystemException {
        bookings.add(booking);
    }

    // Get a list of all bookings
    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);  // Return an unmodifiable view
    }

    // Remove a booking from the system

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }
}
