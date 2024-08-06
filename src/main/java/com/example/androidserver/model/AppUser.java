package com.example.androidserver.model;

import jakarta.persistence.*;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "APP_USER")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "SEC_NUM")
    private String secNum;

    @Column(name = "NAM")
    private String nam;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "REG_YMD")
    private Date regYmd;

    @Column(name = "ROCK_YN")
    private String rockYn;

    @Column(name = "LOGIN_CHECK")
    private Integer loginChk;

    @Column(name = "REMARK")
    private String remark;
}
