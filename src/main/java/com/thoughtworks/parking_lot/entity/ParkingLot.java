package com.thoughtworks.parking_lot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
public class ParkingLot {

    @Id
    @NotNull
    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "INT(5) CHECK (capacity >= 0)")
    private Integer capacity;

    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
