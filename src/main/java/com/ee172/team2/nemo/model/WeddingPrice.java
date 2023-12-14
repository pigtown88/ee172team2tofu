package com.ee172.team2.nemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author tofu
 */
@Data
@Entity
@Table(name = "WeddingPrice")
public class WeddingPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 价格
     */
    private Double price;
    
    /**
     * 方案類型
     */
    private String name;
    
    /**
     * 桌數
     */
    private int tablecount;
    
    /**
     * 是否包含攝影師
     */
    private boolean includephotographer;
    
    /**
     * 是否包含背板
     */
    private Boolean includebackboard;
    
    /**
     * 攝影師類別
     */
    private String photographernote;
    
    /**
     * 背板類別
     */
    private String backboardnote;
    
    

    // Constructors, getters, and setters
}
