package com.micwsx.project.advertise.utility;

public class BinaryTuple<T1,T2> extends Tuple<T1> {

    private T2 item2;

    public BinaryTuple(T1 item1, T2 item2) {
        super(item1);
        this.item2 = item2;
    }

    public T2 getItem2() {
        return item2;
    }

    public void setItem2(T2 item2) {
        this.item2 = item2;
    }
}
