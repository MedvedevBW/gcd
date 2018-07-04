package com.bwsw.test.gcd.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "gcd_arguments")
public final class GcdArgumentsBase {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private Long first;

    @NotNull
    @Column
    private Long second;

    public GcdArgumentsBase() {}

    public GcdArgumentsBase(long first, long second) {
        if (first < second) {
            this.setFirst(first);
            this.setSecond(second);
        } else {
            this.setFirst(second);
            this.setSecond(first);
        }

    }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
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

    public String toString() {
        return "GcdArgumentsBase( id: " + id +
                ", first: " + first + ", second: " + second + ")";
    }
}
