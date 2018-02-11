package com.arjun.intercom.service;

import com.arjun.intercom.models.Coordinates;
import com.arjun.intercom.models.Customer;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerRangeManager {
    private CustomerDetailsParser customerDetailsParser;
    private SphereMetrics sphereMetrics;

    public CustomerRangeManager(CustomerDetailsParser customerDetailsParser, SphereMetrics sphereMetrics) {
        this.customerDetailsParser = customerDetailsParser;
        this.sphereMetrics = sphereMetrics;
    }

    private List<Customer> getOrderedList(List<Customer> customers){
        Collections.sort(customers, (c1, c2) -> c1.getUserId().compareTo(c2.getUserId()));
        return customers;
    }

    /**
     *
     * @param currentCoordinates
     * @param br
     * @param range
     * @return
     */
    public List<Customer> retrieveCustomersInRange(Coordinates currentCoordinates, BufferedReader br, Double range) throws Exception {
        List<Customer> customersInRange = new ArrayList<>();
        try {
            List<Customer> customers = customerDetailsParser.parse(br);
            List<Customer> finalCustomersInRange = customersInRange;
            customers.forEach(customer -> {
                Double distance =  sphereMetrics.getDistanceBetweenCoordinates(currentCoordinates, customer.getCoordinates());
                if(distance< range) {
                    finalCustomersInRange.add(customer);
                }
            });

            // Sort all the customers based on userId
            customersInRange = getOrderedList(finalCustomersInRange);
        }  catch (Exception e) {
           System.out.println("Exception occurred" + e);
           throw e;
        }
        return customersInRange;
    }
}
