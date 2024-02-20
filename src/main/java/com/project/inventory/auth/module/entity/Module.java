package com.project.inventory.auth.module.entity;

import com.project.inventory.auth.permission.entity.Permission;
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
@Table(name = "module")
public class Module extends AuditableEntity {

    @Id
    @GeneratedValue
    @Column(name = "module_id")
    private UUID moduleId;

    @Column(name = "module_name",unique = true,nullable = false,length = 20)
    private String moduleName;

    @Column(name = "ico",unique = true,nullable = false)
    private String ico;

    @Column(name = "description",nullable = false,length = 200)
    private String description;

    @Column(name = "orden",nullable = false,unique = true)
    private int order;

    @OneToMany(mappedBy = "module")
    private Set<Permission> permissions;
}
