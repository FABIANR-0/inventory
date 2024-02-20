package com.project.inventory.auth.person.entity;

import com.project.inventory.auth.user.entity.User;
import com.project.inventory.common.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "person")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Person extends AuditableEntity {

    @Id
    private UUID personId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "lastname", nullable = false, length = 50)
    private String lastname;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @ManyToOne
    @JoinColumn(name = "document_type_id", nullable = false)
    private DocumentType documentType;

    @Column(name = "document_number", nullable = false, length = 15, unique = true)
    private String documentNumber;

    @Column(name = "phone", nullable = false, length = 13)
    private String phone;

    @Column(name = "direction", length = 100)
    private String direction;

    @Column(name = "birthdate", length = 20)
    private LocalDate birthdate;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "charge", length = 100)
    private String charge;

    public Person(String name, String lastname, String email, String documentNumber, String phone, String direction, LocalDate birthdate, String city, String charge) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.documentNumber = documentNumber;
        this.phone = phone;
        this.direction = direction;
        this.birthdate = birthdate;
        this.city = city;
        this.charge = charge;
    }

    public Person() {

    }

    public static Person create(String name, String lastname, String email, String documentNumber, String phone, String direction, LocalDate birthdate, String city, String charge){
        return new Person(
                name,
                lastname,
                email,
                documentNumber,
                phone,
                direction,
                birthdate,
                city,
                charge
        );
    }
    public void update(String name, String lastname, DocumentType documentType, String documentNumber, String phone, String email, String valueDirection, LocalDate birthdate,String city, String charge) {
        this.name = name;
        this.lastname = lastname;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.phone = phone;
        this.email = email;
        this.direction = valueDirection;
        this.birthdate = birthdate;
        this.city = city;
        this.charge = charge;
    }

    public void update(String name, String lastname, DocumentType documentType, String documentNumber, String phone, String email, String charge) {
        this.name = name;
        this.lastname = lastname;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.phone = phone;
        this.email = email;
        this.charge = charge;
    }

}
