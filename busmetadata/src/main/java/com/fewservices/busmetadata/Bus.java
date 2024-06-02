package com.fewservices.busmetadata;


import jakarta.persistence.*;

@Entity
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Long id;

    @Column(name = "plate_number")
    private String licensePlate;

    // Default constructor
    public Bus() {}

    // Parameterized constructor
    public Bus(Long id, String licensePlate) {
        this.id = id;
        this.licensePlate = licensePlate;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String plateNumber) {
        this.licensePlate = plateNumber;
    }
}



