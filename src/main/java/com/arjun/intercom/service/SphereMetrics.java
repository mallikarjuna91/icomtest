package com.arjun.intercom.service;

import com.arjun.intercom.models.Coordinates;

public class SphereMetrics {

    private final Double RADIUS_EARTH = 6371.0;

    /**
     * Calculating central angle used for calculating Spherical Distance.
     * @param c1
     * @param c2
     * @return
     */
    private Double getCentralAngle(Coordinates c1, Coordinates c2){
        Double phi1 =c1.getLatitudeInRadians();
        Double phi2 = c2.getLatitudeInRadians();

        Double gamma1 = c1.getLongitudeInRadians();
        Double gamma2 = c2.getLongitudeInRadians();

        Double deltaGamma = Math.abs(gamma2-gamma1);

        Double productSinAngles = Math.sin(phi1) * Math.sin(phi2);
        Double productCosAngles = Math.cos(phi1) * Math.cos(phi2) * Math.cos(deltaGamma);
        Double centralAngle =  Math.acos(productSinAngles + productCosAngles);

        return centralAngle;
    }

    /**
     *
     * @param c1
     * @param c2
     * @return
     */
    public Double getDistanceBetweenCoordinates(Coordinates c1, Coordinates c2){
        Double distance =  RADIUS_EARTH * getCentralAngle(c1, c2);
        return distance;
    }
}
