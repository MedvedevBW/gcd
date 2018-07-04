package com.bwsw.test.gcd.entities;

public enum GcdCalculationStatus {
    completed ("COMPLETED"),
    notCompleted ("NOT_COMPLETED"),
    error ("ERROR");

    private final String name;

    private GcdCalculationStatus(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
