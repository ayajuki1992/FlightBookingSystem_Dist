package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.util.List;

public class ListCustomers implements Command {

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) {
        List<Customer> customers = flightBookingSystem.getCustomers();
        for (Customer customer : customers) {
            System.out.println("ID: " + customer.getId());
            System.out.println("Name: " + customer.getName());
            System.out.println("Phone: " + customer.getPhone());
            System.out.println("Bookings: " + customer.getBookings().size());
            System.out.println("---------------------------");
        }
    }
}