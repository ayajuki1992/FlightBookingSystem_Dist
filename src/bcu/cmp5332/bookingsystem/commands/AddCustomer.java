package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;

public class AddCustomer implements Command {

    private final String name;
    private final String phone;

    public AddCustomer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        try {
            // Get the next ID dynamically
            int nextId = getNextCustomerId(flightBookingSystem);

            // Create and add the new customer
            Customer customer = new Customer(nextId, name, phone);
            flightBookingSystem.addCustomer(customer);

            // Save changes to the file
            FlightBookingSystemData.store(flightBookingSystem);

            // Confirm addition
            System.out.println("Customer added successfully with ID: " + nextId);

        } catch (IOException ex) {
            throw new FlightBookingSystemException("Error saving customer data.");
        }
    }

    // Helper method to calculate the next available ID
    private int getNextCustomerId(FlightBookingSystem flightBookingSystem) {
        return flightBookingSystem.getCustomers()
                .stream()
                .mapToInt(Customer::getId)
                .max()
                .orElse(0) + 1; // Increment by 1
    }
}