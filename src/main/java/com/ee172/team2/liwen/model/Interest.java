package com.ee172.team2.liwen.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "interest")
public class Interest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer insId;

	private String insName;
	
//	一個興趣可以被多個會員選擇
//	@JsonBackReference 	//避免 JSON 序列化時的循環參照
	@OneToMany(mappedBy = "insId", cascade = CascadeType.ALL)
//	@ToString(exclude = {"member"})
	private List<Member> member = new ArrayList<>(); // 確保初始化
	
	@Override
    public String toString() {
        return "Interest{" +
                "insId=" + insId +
                ", insName='" + insName + '\'' +
                ", member=EXCLUDED" +
                '}';
    }

}
