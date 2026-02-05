package com.br.biblioteca.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @Column(length = 26, nullable = false, updatable = false)
    protected String id;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = gerarId();
        }
    }

    protected String gerarId() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 26);
    }
}
