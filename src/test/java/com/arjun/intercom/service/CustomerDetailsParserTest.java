package com.arjun.intercom.service;

import com.arjun.intercom.models.Coordinates;
import com.arjun.intercom.models.Customer;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.*;

import java.util.List;

import static org.junit.Assert.*;

public class CustomerDetailsParserTest {

    private final String MOCK_JSON =
            "{\"latitude\": \"52\", \"user_id\": 12, \"name\": \"Christina McArdle\", \"longitude\": \"-6.043701\"}\n" +
            "{\"latitude\": \"51.92893\", \"user_id\": 1, \"name\": \"Alice Cahill\", \"longitude\": \"-10.27699\"}";

    @Test
    public void shouldReturnCorrectNumberOfCustomers() throws Exception {

        InputStream is = new ByteArrayInputStream(MOCK_JSON.getBytes());

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        CustomerDetailsParser customerDetailsParser = new CustomerDetailsParser();
        List<Customer> customers = customerDetailsParser.parse(br);
        assertEquals(2, customers.size());
    }


    @Test
    public void shouldReturnCustomerWithCorrectParsedValue() throws Exception {

        InputStream is = new ByteArrayInputStream(MOCK_JSON.getBytes());

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Long userId = Long.valueOf(12);
        Customer expectedCustomer = new Customer(userId, "Christina McArdle",
                new Coordinates(52.0,-6.043701));
        CustomerDetailsParser customerDetailsParser = new CustomerDetailsParser();

        List<Customer> customers = customerDetailsParser.parse(br);
        assertEquals(expectedCustomer.toString(), customers.get(0).toString());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfAFiledIsMissing() throws Exception {
        String invalidJson =
                "{\"latitude\": \"52\", \"user_id\": 12, \"name\": \"Christina McArdle\"}\n";
        InputStream is = new ByteArrayInputStream(invalidJson.getBytes());


        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        CustomerDetailsParser customerDetailsParser = new CustomerDetailsParser();

        customerDetailsParser.parse(br);
    }

    @Test(expected = ClassCastException.class)
    public void shouldThrowClassCastExceptionIfAnyFiledHasInvalidType() throws Exception {
        String invalidJson =
                "{\"latitude\": 52, \"user_id\": 12, \"name\": \"Christina McArdle\", \"longitude\": \"-6.043701\"}\n";
        InputStream is = new ByteArrayInputStream(invalidJson.getBytes());

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        CustomerDetailsParser customerDetailsParser = new CustomerDetailsParser();
        customerDetailsParser.parse(br);
    }

    @Test(expected = ParseException.class)
    public void shouldThrowParseExceptionIfJsonIsInvalid() throws Exception {
        String invalidJson =
                "{\"latitude\": 52, \"user_id\": 12, \"name\": \"Christina McArdle\", \"longitude\": \"-6.043701\"\n";
        InputStream is = new ByteArrayInputStream(invalidJson.getBytes());


        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        CustomerDetailsParser customerDetailsParser = new CustomerDetailsParser();
        customerDetailsParser.parse(br);
    }
}