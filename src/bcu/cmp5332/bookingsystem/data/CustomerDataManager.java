package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomerDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/customers.txt";
    
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
    try (BufferedReader br = new BufferedReader(new FileReader(RESOURCE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("::");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String phone = parts[2];
                    Customer customer = new Customer(id, name, phone);
                    fbs.addCustomer(customer);
                } else {
                    throw new FlightBookingSystemException("Invalid customer data format.");
                }
            }
        }    
    }

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESOURCE))) {
            List<Customer> customers = fbs.getCustomers();
            for (Customer customer : customers) {
                bw.write(customer.getId() + "::" + customer.getName() + "::" + customer.getPhone());
                bw.newLine();
            }
        }   
    }



public static int getHighestCustomerId() throws IOException {
    int highestId = 0;
    try (BufferedReader br = new BufferedReader(new FileReader("./resources/data/customers.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("::");
            if (parts.length == 3) {
                int id = Integer.parseInt(parts[0]);
                if (id > highestId) {
                    highestId = id;
                }
            }
        }
    }
    return highestId;
    }
}