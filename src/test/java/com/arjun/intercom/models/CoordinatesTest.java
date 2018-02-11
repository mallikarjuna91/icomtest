package com.arjun.intercom.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class CoordinatesTest {

    @Test
    public void shouldReturnCoordinatesInRadiansWhenCalled() throws Exception {

        double latitude = 30;
        double longitude = 40;
        Coordinates coordinates = new Coordinates(latitude, longitude);
        assertEquals(coordinates.getLatitudeInRadians(), Double.valueOf(Math.toRadians(latitude)));
        assertEquals(coordinates.getLongitudeInRadians(), Double.valueOf(Math.toRadians(longitude)));
    }
}