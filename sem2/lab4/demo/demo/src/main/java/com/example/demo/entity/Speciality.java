package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "specialities", schema = "public")
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "specialities_id_generator")
    @SequenceGenerator(name = "specialities_id_generator", sequenceName = "specialities_id_seq", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 150, unique = true)
    private String name;

    @OneToMany(mappedBy = "speciality")
    private List<Doctor> doctors = new ArrayList<>();

    public Speciality() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

}
