package com.bwsw.test.gcd.entities;

import javax.validation.constraints.Min;

public final class GcdArgumentsApi {
    private final String errorMessage = "Please insert positive number";
    @Min(value=1, message = errorMessage)
    long first;
    @Min(value=1, message = errorMessage)
    long second;

    public GcdArgumentsApi() {
    }

    public GcdArgumentsApi(long first, long second) {
        this.setFirst(first);
        this.setSecond(second);
    }

    public long getFirst() {
        return first;
    }

    public void setFirst(long first) {
        this.first = first;
    }

    public long getSecond() {
        return second;
    }

    public void setSecond(long second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
