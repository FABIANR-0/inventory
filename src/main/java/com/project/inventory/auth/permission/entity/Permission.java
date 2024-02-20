package com.project.inventory.auth.permission.entity;

import com.project.inventory.auth.module.entity.Module;
import com.project.inventory.auth.role.entity.Role;
import com.project.inventory.common.audit.AuditableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "permission")
public class Permission extends AuditableEntity {

    @Id
    @GeneratedValue
    @Column(name = "permission_id")
    private UUID permissionId;

    @Column(name = "title",nullable = false,length = 100)
    private String title;

    @Column(name = "permission_name",nullable = false,unique = true)
    private String permissionName;

    @Transient
    private boolean checked;

    @ManyToMany(mappedBy = "permissions",cascade = CascadeType.REFRESH)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Role> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id",nullable = false)
    private Module module;
}
