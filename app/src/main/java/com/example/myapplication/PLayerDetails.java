package com.example.myapplication;

public class PLayerDetails {

    private String name;
    private String position;
    private String number;

    public PLayerDetails(String name, String position, String number) {
        this.name = name;
        this.position = position;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
