package com.micwsx.project.advertise.rabbitmq;

/**
 * @author Michael
 * @create 8/11/2020 11:28 AM
 */
public class TestMessage {
    private int id;
    private String name;
    private String city;

    public TestMessage(int id,String name, String city) {
        this.id=id;
        this.name = name;
        this.city = city;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "TestMessage{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
