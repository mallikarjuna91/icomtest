package com.arjun.intercom.service;

import com.arjun.intercom.models.Coordinates;
import com.arjun.intercom.models.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerRangeManagerTest {


    @Test()
    public void shouldCallTheParserOnceWhileRetrievingCustomersWithCorrectArguments() throws Exception {
        CustomerDetailsParser parser = Mockito.mock(CustomerDetailsParser.class);
        SphereMetrics sphereMetrics = Mockito.mock(SphereMetrics.class);
        CustomerRangeManager customerRangeManager = new CustomerRangeManager(parser, sphereMetrics);
        BufferedReader br = null;
        customerRangeManager.retrieveCustomersInRange(new Coordinates(0.0,0.0), br, 100.0);
        verify(parser, times(1)).parse(br);
    }

    @Test()
    public void shouldCallTheSphereMetricsForCalculationWhileRetrievingCustomers() throws Exception {
        List<Customer> stubCordinatesList  = new ArrayList<>();
        stubCordinatesList.add(new Customer(Long.valueOf(1), "testname", new Coordinates(1.0,2.0)));
        stubCordinatesList.add(new Customer(Long.valueOf(3), "testname2", new Coordinates(2.0,4.0)));

        Coordinates currentCoordinates = new Coordinates(0.0, 0.0);
        CustomerDetailsParser parser = Mockito.mock(CustomerDetailsParser.class);

        when(parser.parse(any())).thenReturn(stubCordinatesList);

        SphereMetrics sphereMetrics = Mockito.mock(SphereMetrics.class);
        CustomerRangeManager customerRangeManager = new CustomerRangeManager(parser, sphereMetrics);
        BufferedReader br = null;
        customerRangeManager.retrieveCustomersInRange(currentCoordinates, br, 100.0);
        verify(sphereMetrics, times(2)).getDistanceBetweenCoordinates(eq(currentCoordinates), anyObject());
    }

    @Test()
    public void shouldRetrieveCustomersOnlyWithinGivenRange() throws Exception {
        List<Customer> stubCordinatesList  = new ArrayList<>();
        Coordinates coordinates1 = new Coordinates(1.0, 2.0);
        Customer customer1 = new Customer(Long.valueOf(1), "testname", coordinates1);
        stubCordinatesList.add(customer1);
        Coordinates coordinates2 = new Coordinates(2.0, 4.0);
        Customer customer2 = new Customer(Long.valueOf(3), "testname2", coordinates2);
        stubCordinatesList.add(customer2);

        Coordinates currentCoordinates = new Coordinates(0.0, 0.0);

        CustomerDetailsParser parser = Mockito.mock(CustomerDetailsParser.class);
        SphereMetrics sphereMetrics = Mockito.mock(SphereMetrics.class);

        when(parser.parse(any())).thenReturn(stubCordinatesList);
        when(sphereMetrics.getDistanceBetweenCoordinates(currentCoordinates, coordinates1)).thenReturn(50.0);
        when(sphereMetrics.getDistanceBetweenCoordinates(currentCoordinates, coordinates2)).thenReturn(120.0);


        CustomerRangeManager customerRangeManager = new CustomerRangeManager(parser, sphereMetrics);
        BufferedReader br = null;
        List<Customer> retrievedCustomers = customerRangeManager.retrieveCustomersInRange(currentCoordinates, br, 100.0);

        assertEquals(1, retrievedCustomers.size());
        assertEquals(customer1, retrievedCustomers.get(0));
    }

    @Test()
    public void shouldRetrieveCustomersInAscendingOrderOfUserIds() throws Exception {
        List<Customer> stubCordinatesList  = new ArrayList<>();
        Coordinates coordinates1 = new Coordinates(1.0, 2.0);
        Customer customer1 = new Customer(Long.valueOf(100), "testname", coordinates1);
        stubCordinatesList.add(customer1);
        Coordinates coordinates2 = new Coordinates(2.0, 4.0);
        Customer customer2 = new Customer(Long.valueOf(20), "testname2", coordinates2);
        stubCordinatesList.add(customer2);
        Coordinates coordinates3 = new Coordinates(4.0, 6.0);
        Customer customer3 = new Customer(Long.valueOf(9), "testname3", coordinates3);
        stubCordinatesList.add(customer3);

        Coordinates currentCoordinates = new Coordinates(0.0, 0.0);

        CustomerDetailsParser parser = Mockito.mock(CustomerDetailsParser.class);
        SphereMetrics sphereMetrics = Mockito.mock(SphereMetrics.class);

        when(parser.parse(any())).thenReturn(stubCordinatesList);
        when(sphereMetrics.getDistanceBetweenCoordinates(any(), any())).thenReturn(50.0);

        CustomerRangeManager customerRangeManager = new CustomerRangeManager(parser, sphereMetrics);
        BufferedReader br = null;
        List<Customer> retrievedCustomers = customerRangeManager.retrieveCustomersInRange(currentCoordinates, br, 100.0);

        assertEquals(3, retrievedCustomers.size());
        assertEquals(customer3, retrievedCustomers.get(0));
        assertEquals(customer2, retrievedCustomers.get(1));
        assertEquals(customer1, retrievedCustomers.get(2));
    }
}