package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException, FlightBookingSystemException {
        
        // Load FlightBookingSystem data
        FlightBookingSystem fbs = FlightBookingSystemData.load();

        // Debugging: Check how many customers are loaded initially
        System.out.println("Customers loaded: " + fbs.getCustomers().size());

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Flight Booking System");
        System.out.println("Enter 'help' to see a list of available commands.");
        while (true) {
            System.out.print("> ");
            String line = br.readLine().trim().toLowerCase(); // Trim whitespace and convert to lowercase

            // Exit the program
            if (line.equals("exit")) {
                break;
            }

            try {
                // Parse and execute the command
                Command command = CommandParser.parse(line);
                command.execute(fbs);

                // Debugging: Check if the command executed
                System.out.println("Command executed successfully: " + line);

            } catch (FlightBookingSystemException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception ex) {
                // Catch all other exceptions for debugging
                ex.printStackTrace();
            }
        }

        // Save system state before exiting
        FlightBookingSystemData.store(fbs);
        System.exit(0);
    }
}