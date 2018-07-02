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

    public GcdResult(Long id, String status) {
        this.id = id;
        this.status = status;
    }


    @Id
    @Column
    private Long id;

    @Column
    private String status;

    public String getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
