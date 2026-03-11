package ru.bsuedu.cad.lab.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_name", unique = false, nullable = false, length = 100)
    private String name;

    @Column(name = "customer_email", unique = true, nullable = false, length = 250)
    private String email;

    @Column(name = "customer_phone", unique = true,nullable = false, length = 50)
    private String phone;

    @Column(name = "customer_address", unique = false,nullable = true, length = 300)
    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
//    @JoinColumn(name = "ORDERS_ID")
    private List<Orders> orders = new ArrayList<>();

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}
