package com.zosh.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User customer;

    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;

    private Long totalAmount;

    private String orderstatus;

    private Date createdAt;

    @ManyToOne
    private Address deliveryAddress;

    @OneToMany
    private List<OrderItems> Items;

   // private Payment paymentl

    private int totalItems;

    private Long totalPrice;




}
