package com.ee172.team2.liwen.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.ee172.team2.lpt.model.Activity;
import com.ee172.team2.lpt.model.Order;
import com.ee172.team2.lpt.model.Reserve;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberId;

	private String memberName;

	private String memberEmail;

	private String memberPwd;

	// 二次確認密码，在後端進行二次確認，不需保存到資料庫
	@Transient // 指示JPA不需將被標記的字段（或方法）持久化到資料庫中，不會生成相應的資料庫欄位
	private String confirmPassword;

	private Integer memberPhone;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 轉換前端 String 日期到 Java 端日期格式
	@Temporal(TemporalType.TIMESTAMP)
	private Date memberBirth;

	private String memberGender;

	private String memberAddress;

	@Lob // 告訴PO此物件要被持久化為BLOB或CLOB(字串)，以有效的存除和檢索
	@JsonIgnore // 進行 JSON 序列化時，不要序列化本屬性(序列化：轉換為JSON格式)
	private byte[] memberPhoto;

	// 允許在 MemberController 中使用 MultipartFile 處理檔案上傳，同時將上傳的 byte[] 數據設置到 memberPhoto
	// 屬性
	@Transient
	private MultipartFile memberPhotoFile;

	private Integer permission; // 0 未啟用，1 啟用，2 禁用

	private boolean enabled;

	@Temporal(TemporalType.TIMESTAMP)
	private Date memberCt;

//	一個會員只能選擇一個興趣
//	@JsonManagedReference 	//避免 JSON 序列化時的循環參照
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "insId", referencedColumnName = "insId")
	@ToString.Exclude
	private Interest insId;
	
	// 新增方法用於設定 insId
    public void setInsId(Interest interest) {
        this.insId = interest;
        if (interest != null) {
            interest.getMember().add(this);
        }
    }

	private String loginType;

	@JsonIgnore
	@JsonManagedReference("member-reserve")
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reserve> reserves;

	@JsonIgnore
	@JsonManagedReference("member-order")
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> orders;

	@JsonIgnore
	@JsonManagedReference("member-activity")
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Activity> activities;

}
