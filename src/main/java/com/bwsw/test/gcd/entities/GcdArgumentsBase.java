package com.bwsw.test.gcd.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "gcd_arguments")
public class GcdArgumentsBase {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column
    private long first;

    @NotBlank
    @Column
    private long second;

    public GcdArgumentsBase() {
    }

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
}
