package com.project.inventory.auth.person.entity;

import com.project.inventory.common.audit.AuditableEntity;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "document_type")
@EntityListeners(AuditingEntityListener.class)
public class DocumentType extends AuditableEntity {

    @Id
    @GeneratedValue
    @Column(name = "type_document_id", nullable = false)
    private UUID typeDocumentId;

    @Column(name = "name")
    private String name;


}
