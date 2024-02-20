package com.project.inventory.auth.role.entity;

import com.project.inventory.auth.permission.entity.Permission;
import com.project.inventory.auth.user.entity.User;


import com.project.inventory.common.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "role")
public class Role extends AuditableEntity {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private UUID roleId;

    @Column(name = "role_name",nullable = false,unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles",cascade = CascadeType.REFRESH)
    Set<User> users;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "role_permission",schema = "main",
            joinColumns = @JoinColumn(name = "role_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "permission_id",nullable = false),
            uniqueConstraints = @UniqueConstraint(name = "uc_role_id_permission_id",columnNames = {"role_id","permission_id"})
    )
    private Set<Permission> permissions;
}
