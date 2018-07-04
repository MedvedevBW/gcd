package com.bwsw.test.gcd.entities;

import java.io.Serializable;

public final class GcdCalculationRequest implements Serializable {
    private long first;
    private long second;
    private long id;

    public GcdCalculationRequest(long first, long second, long id) {
        this.first = first;
        this.second = second;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getSecond() {
        return second;
    }

    public long getFirst() {
        return first;
    }
}
