package com.sergiomarrero.madridshops.domain.model;


public final class ShopJava {
    private int id;
    private String name;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ShopJava(String name) {
        this.name = name;
    }

    {
        ShopJava shop1 = new ShopJava("Shop1");
        shop1.setAddress("Address1");
    }
}
