package com.ee172.team2.steven.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Data
@Entity
@Table(name="GroupBuys")
public class GroupBuys {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "title", nullable = true, length = 255)
    private String title;

    @Column(name = "description", nullable = true, length = -1)
    private String description;



    @Column(name = "deadline", nullable = true)
    private Timestamp deadline;


    @OneToMany(mappedBy = "groupBuy", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<FoodOptions> foodOptions = new ArrayList<>();


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "initiator_id", referencedColumnName = "empId")
    private Employee initiator;


}
