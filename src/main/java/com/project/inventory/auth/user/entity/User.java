package com.project.inventory.auth.user.entity;


import com.project.inventory.auth.role.entity.Role;
import com.project.inventory.common.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class User extends AuditableEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "user_name",nullable = false,unique = true,length = 50)
    private String userName;

    @Column(name = "password", nullable = false,length = 100)
    private String password;

    @Column(name = "locked",nullable = false)
    private boolean locked;

    @Column(name = "login_attempts")
    private int loginAttempts;

    @Column(name = "administrator",nullable = false)
    private boolean administrator;

    @Column(name = "code_verification",length = 7)
    private String codeVerification;

    @Column(name = "profile_image")
    private String profileImage;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "user_role",schema = "main",
            joinColumns = @JoinColumn(name = "user_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id",nullable = false),
            uniqueConstraints = @UniqueConstraint(name = "uc_user_id_role_id",columnNames = {"user_id","role_id"})
    )
    Set<Role> roles;

    public void updateLoginAttempts(int attempts){
        this.loginAttempts = attempts;
    }

    public void updateLocked(boolean locked){
        this.locked = locked;
    }

    public void addCodeVerification(String codeVerification){
        this.codeVerification = codeVerification;
    }
}
