package com.br.biblioteca.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class UserEntity extends BaseEntity {

    @Column(name = "name", length = 256, nullable = false)
    private String name;

    @Column(name = "email", length = 512, nullable = false)
    private String email;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "inactived_at")
    private LocalDateTime inactivedAt;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

}
