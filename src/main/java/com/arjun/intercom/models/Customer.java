package com.arjun.intercom.models;

public class Customer {

    private Long userId;
    private String name;
    private Coordinates coordinates;


    public Customer(Long userId, String name, Coordinates coordinates) {
        this.userId = userId;
        this.name = name;
        this.coordinates = coordinates;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
