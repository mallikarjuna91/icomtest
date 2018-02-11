package com.arjun.intercom.service;

import com.arjun.intercom.models.Coordinates;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SphereMetricsTest {

    @Test
    public void shouldFetchAllCoordinatesInRadiansOnce() throws Exception {
        Coordinates c1 = Mockito.mock(Coordinates.class);
        Coordinates c2 = Mockito.mock(Coordinates.class);

        SphereMetrics sphereMetrics = new SphereMetrics();
        sphereMetrics.getDistanceBetweenCoordinates(c1, c2);
        verify(c1, times(1)).getLatitudeInRadians();
        verify(c1, times(1)).getLongitudeInRadians();
        verify(c2, times(1)).getLatitudeInRadians();
        verify(c2, times(1)).getLongitudeInRadians();
    }

    @Test
    public void shouldCalculateDistanceAccurately() throws Exception {
        Double expectedValue = 1.0007543419214024;
        Coordinates c1 = new Coordinates(0.0,0.0);
        Coordinates c2 = new Coordinates(0.0, 0.009);

        SphereMetrics sphereMetrics = new SphereMetrics();
        Double distance = sphereMetrics.getDistanceBetweenCoordinates(c1, c2);
        assertEquals(expectedValue, distance);
    }

    @Test
    public void shouldCalculateDistanceAccuratelyEvenIfCoordinatesHaveNegativeValues() throws Exception {
        Double expectedValue = 1.0007543419214024;
        Coordinates c1 = new Coordinates(0.0,0.0);
        Coordinates c2 = new Coordinates(0.0, -0.009);

        SphereMetrics sphereMetrics = new SphereMetrics();
        Double distance = sphereMetrics.getDistanceBetweenCoordinates(c1, c2);
        assertEquals(expectedValue, distance);
    }

    @Test
    public void distanceShouldBeZeroIfTheCoordinatesAreSame() throws Exception {
        Double expectedValue = 0.0;
        Coordinates c1 = new Coordinates(1.0,-1.0);
        Coordinates c2 = new Coordinates(1.0, -1.0);

        SphereMetrics sphereMetrics = new SphereMetrics();
        Double distance = sphereMetrics.getDistanceBetweenCoordinates(c1, c2);
        System.out.println(distance);
        assertEquals(expectedValue, distance);
    }
}