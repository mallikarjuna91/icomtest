package com.arjun.intercom.models;

public class Coordinates {
    private Double latitude;
    private Double longitude;

    public Coordinates(Double lattitude, Double longitude) {
        this.longitude = longitude;
        this.latitude = lattitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public static Double degreeToRadians(Double degree) {

        return Math.toRadians(degree);
    }

    public Double getLatitudeInRadians() {
        return Math.toRadians(this.latitude);
    }

    public Double getLongitudeInRadians() {
        return Math.toRadians(this.longitude);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
