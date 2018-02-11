package com.arjun.intercom.service;

import com.arjun.intercom.models.Coordinates;
import com.arjun.intercom.models.Customer;
import com.arjun.intercom.utils.AppConstants;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class CustomerDetailsParser {

    public List<Customer> parse(BufferedReader br) throws Exception {
        JSONParser jsonParser = new JSONParser();
        List<Customer> customers = new ArrayList<>();
        try {
            String currentLine;
            while((currentLine = br.readLine())!=null) {
                JSONObject object = (JSONObject) jsonParser.parse(currentLine);
                String custName = (String) object.get(AppConstants.NAME);
                Long userId =  (Long)object.get(AppConstants.USERID);
                Double latitude = Double.valueOf((String)object.get(AppConstants.LATITUDE));
                Double longitude = Double.valueOf((String)object.get(AppConstants.LONGITUDE));
                Customer customer = new Customer(userId, custName, new Coordinates(latitude, longitude));
                customers.add(customer);
            }
        }
        catch(ClassCastException e){
            System.out.print("Invalid Field Type" + e);
            throw e;
        } catch(NullPointerException e){
            System.out.print("Null Pointer exception While parsing file" + e);
            throw e;
        } catch (ParseException e) {
            System.out.print("Invalid json" + e);
            throw e;
        }
        return customers;
    }
}
