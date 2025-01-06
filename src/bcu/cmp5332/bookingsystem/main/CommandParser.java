package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.commands.CancelBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.commands.Help;
import bcu.cmp5332.bookingsystem.commands.ListCustomers;
import bcu.cmp5332.bookingsystem.commands.ListFlights;
import bcu.cmp5332.bookingsystem.commands.LoadGUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CommandParser {

    public static Command parse(String line) throws IOException, FlightBookingSystemException {
        try {
            String[] parts = line.split(" ", 3);
            String cmd = parts[0];

            if (cmd.equals("addflight")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Flight Number: ");
                String flighNumber = reader.readLine();
                System.out.print("Origin: ");
                String origin = reader.readLine();
                System.out.print("Destination: ");
                String destination = reader.readLine();

                LocalDate departureDate = parseDateWithAttempts(reader);

                // return a new AddFlight command with the provided flight details
                return new AddFlight(flighNumber, origin, destination, departureDate);

            } else if (cmd.equals("addcustomer")) {
                // read customer details from the input
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Customer Name: ");
                String name = reader.readLine();
                System.out.print("Customer Phone: ");
                String phone = reader.readLine();
                // return a new AddCustomer command with the provided customer details
                return new AddCustomer(name, phone);

            } else if (cmd.equals("loadgui")) {
                return new LoadGUI();
            } else if (parts.length == 1) {
                if (line.equals("listflights")) {
                    return new ListFlights();
                } else if (line.equals("listcustomers")) {
                    return new ListCustomers();

                } else if (line.equals("help")) {
                    return new Help();
                }
            } else if (parts.length == 2) {
                int id = Integer.parseInt(parts[1]);

                if (cmd.equals("showflight")) {

                } else if (cmd.equals("showcustomer")) {

                }
            } else if (parts.length == 3) {
                if (cmd.equals("addbooking")) {
                    try {
                        int customerId = Integer.parseInt(parts[1]);
                        String flightNumber = parts[2];  // flightNumber is passed as a string directly
                        return new AddBooking(customerId, flightNumber);  // customerId as int, flightNumber as String
                    } catch (NumberFormatException e) {
                        throw new FlightBookingSystemException("Invalid customer ID or flight number format.");
                    }
                } else if (cmd.equals("editbooking")) {

                } else if (cmd.equals("cancelbooking")) {
                    try {
                        int customeriD = Integer.parseInt(parts[1]);
                        String flightNumber = parts[2];  // flightNumber is passed as a string directly
                        return new CancelBooking(customeriD, flightNumber);
                    } catch (Exception e) {
                        throw new FlightBookingSystemException("Invalid customer ID or flight number format.");
                    }
                } else if (cmd.equals("showbookings")) {
                    Path path = Paths.get("bookings.txt");
                    try {
                        // Read and print all lines from the file
                        Files.lines(path).forEach(System.out::println);
                    } catch (IOException e) {
                        System.err.println("Error reading the file: " + e.getMessage());
                    }
                }
            }

        } catch (NumberFormatException ex) {

        }

        throw new FlightBookingSystemException("Invalid command.");
    }

    private static LocalDate parseDateWithAttempts(BufferedReader br, int attempts) throws IOException, FlightBookingSystemException {
        if (attempts < 1) {
            throw new IllegalArgumentException("Number of attempts should be higher than 0");
        }
        while (attempts > 0) {
            attempts--;
            System.out.print("Departure Date (\"YYYY-MM-DD\" format): ");
            try {
                LocalDate departureDate = LocalDate.parse(br.readLine());
                return departureDate;
            } catch (DateTimeParseException dtpe) {
                System.out.println("Date must be in YYYY-MM-DD format. " + attempts + " attempts remaining...");
            }
        }

        throw new FlightBookingSystemException("Incorrect departure date provided. Cannot create flight.");
    }

    private static LocalDate parseDateWithAttempts(BufferedReader br) throws IOException, FlightBookingSystemException {
        return parseDateWithAttempts(br, 3);
    }
}
