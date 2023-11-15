package com.appfitgym.model.entities;

import com.appfitgym.model.enums.UserRoleEnum;
import jakarta.persistence.*;



@Entity
@Table(name = "user_role")
public class UserRole  extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;



    public UserRoleEnum getRole() {
        return role;
    }

    public UserRole setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}