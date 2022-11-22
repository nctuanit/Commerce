package com.bach.Commerce.entity;

import com.bach.Commerce.common.enums.AuthProvider;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user", schema = "commerce")
@Getter
@Setter
public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final long OTP_VALID_DURATION = 5 * 60 * 1000; // 5 phút
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "password")
    private String password;
    @Column(name = "mail")
    private String username;
    @Column(name = "role")
    private String role;
    @Column(name = "enabled")
    private Boolean enabled;
    @Column(name = "created_time", updatable = false)
    private Date created_time;
    @Column(name = "name")
    private String name;
    private String phone;
    private String city;
    private String address;
    private String state;
    private int country_id;
    private String postal_code;
    private String avatar;
    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthProvider authProvider;
    @Column(name = "one_time_password")
    private String oneTimePassword;
    @Column(name = "otp_requested_time")
    private Date otpRequestedTime;


    public User(int id, String password, String username, String role, Boolean enabled, Date created_time, String name,
                String phone, String city, String address, String state, int country_id, String postal_code, String avatar,
                AuthProvider authProvider) {
        super();
        this.id = id;
        this.password = password;
        this.username = username;
        this.role = role;
        this.enabled = enabled;
        this.created_time = created_time;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.address = address;
        this.state = state;
        this.country_id = country_id;
        this.postal_code = postal_code;
        this.avatar = avatar;
        this.authProvider = authProvider;
    }


    public User() {

    }

    @Transient
    public String getAvatarImagePath() {
        if (avatar == null) return null;

        return "/avatar-images/" + id + "/" + avatar;
    }

    public boolean isOTPRequired() {
        if (this.oneTimePassword == null) {
            return false;
        }

        long otpRequestedTimeMillis = this.otpRequestedTime.getTime();

        if (otpRequestedTimeMillis + OTP_VALID_DURATION < System.currentTimeMillis()) { //Thời gian request + 5 phút nhỏ hơn thời gian hiện tại => hết hiệu lực mã OTP
            return false; //OTP hết hạn
        }
        return true;
    }
}
