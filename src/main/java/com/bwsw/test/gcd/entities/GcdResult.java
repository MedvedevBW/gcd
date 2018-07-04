package com.bwsw.test.gcd.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "result")
public class GcdResult {

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private GcdArgumentsBase arguments;

    public GcdResult() {}

    public GcdResult(Long id, Long result) {
        this.id = id;
        this.status = GcdCalculationStatus.completed.toString();
        this.result = result;
    }

    public GcdResult(Long id, GcdCalculationStatus status) {
        this.id = id;
        this.status = status.toString();
    }

    public GcdResult(Long id, String message) {
        this.id = id;
        this.status = GcdCalculationStatus.error.toString();
        this.error = message;
    }

    @Id
    @Column
    private Long id;

    @Column
    private String status;

    @Column
    private String error;

    @Column
    private Long result;

    public String getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public void setStatus(GcdCalculationStatus status) {
        this.status = status.toString();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    public String toString() {
        return "GcdResult(id: " + id + ", status: " + status + ", error: " + error + ", result: " + result + ")";
    }
}
