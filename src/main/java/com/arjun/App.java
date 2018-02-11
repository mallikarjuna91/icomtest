package com.arjun;

import com.arjun.intercom.models.Coordinates;
import com.arjun.intercom.models.Customer;
import com.arjun.intercom.service.CustomerDetailsParser;
import com.arjun.intercom.service.CustomerRangeManager;
import com.arjun.intercom.service.SphereMetrics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class App {

    public static void main(String[] args) throws Exception {

        Coordinates c = new Coordinates(53.3381985,-6.2592576);

        //Service classes instantiation that is passed to the CustomerRangeManager class.
        CustomerDetailsParser customerDetailsParser = new CustomerDetailsParser();
        SphereMetrics sphereMetrics =  new SphereMetrics();

        CustomerRangeManager rangeManager = new CustomerRangeManager(customerDetailsParser, sphereMetrics);

        //Read the stream and pass it to CustomerRangeManager.
        String filePath = "/path/customer.json";
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);


        List<Customer> customerList = rangeManager.retrieveCustomersInRange(c, br, 100.0);

        System.out.println("All the Customers in given range: ");
        customerList.forEach(customer -> {
            System.out.println("UserId: " + customer.getUserId());
            System.out.println("Name: " + customer.getName());
            System.out.println("--------------------------");
        });


    }

}
