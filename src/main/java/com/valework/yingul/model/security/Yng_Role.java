package com.valework.yingul.model.security;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by z00382545 on 10/20/16.
 */

@Entity
public class Yng_Role {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roleId;

    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Yng_UserRole> userRoles = new HashSet<>();

    public Yng_Role() {

    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Yng_UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<Yng_UserRole> userRoles) {
        this.userRoles = userRoles;
    }


}
