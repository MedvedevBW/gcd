package com.bwsw.test.gcd.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

public class GcdArgumentsApi {
    long first;
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
