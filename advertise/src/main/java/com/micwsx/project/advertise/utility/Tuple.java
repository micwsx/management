package com.micwsx.project.advertise.utility;

public class Tuple<T1> {
    public T1 item1;

    public Tuple(T1 item1) {
        this.item1 = item1;
    }

    public T1 getItem1() {
        return item1;
    }

    public void setItem1(T1 item1) {
        this.item1 = item1;
    }
}
