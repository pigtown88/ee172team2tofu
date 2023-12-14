package com.ee172.team2.nemo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "weddingCouple")
public class WeddingCouple {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer weddingId;

    private String brideName;

    private String groomName;

    private String brideParentName;

    private String groomParentName;

    @OneToMany(mappedBy = "weddingCouple")
    private List<WeddingGuest> weddingGuests;
    
    /**
     * 價格id
     */
    private Integer priceId;
    
    // Constructors, getters, and setters
}
