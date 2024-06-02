package com.fewservices.buslivefare;

public class BusFare {
    private Long id;
    private int fare;

    public BusFare(Long id, int fare) {
        this.id = id;
        this.fare = fare;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }
}

