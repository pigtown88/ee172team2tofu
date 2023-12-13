package com.ee172.team2.steven.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name="FoodOptions")
public class FoodOptions {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;



    @Column(name = "name", nullable = true, length = 100)
    private String name;

    @Column(name = "vendor", nullable = true, length = 100)
    private String vendor;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_buy_id")
    @JsonBackReference
    private GroupBuys groupBuy;


    @OneToMany(mappedBy = "foodOption", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PurchaseRecords> purchaseRecords = new ArrayList<>();



}
