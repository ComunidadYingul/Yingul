package com.valework.yingul.model.security;

import com.valework.yingul.model.Yng_User;

import javax.persistence.*;

/**
 * Created by z00382545 on 10/20/16.
 */

@Entity
@Table(name="user_role")
public class Yng_UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userRoleId;

    public Yng_UserRole(Yng_User user, Yng_Role role) {
        this.user = user;
        this.role = role;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Yng_User user;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Yng_Role role;

    public Yng_UserRole() {}

    public long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Yng_User getUser() {
        return user;
    }

    public void setUser(Yng_User user) {
        this.user = user;
    }

    public Yng_Role getRole() {
        return role;
    }

    public void setRole(Yng_Role role) {
        this.role = role;
    }


}
