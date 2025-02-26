package com.mlc.vdsr.entity.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Parent class for JPA Entities
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    /**
     * Entity's id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Timestamp when the entity was created.
     */
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdDate;

    /**
     * Timestamp when the entity was last modified.
     */
    @LastModifiedDate
    @Column(nullable = false)
    private Instant modifiedDate;

    /**
     * Soft delete flag.
     */
    @Column(nullable = false)
    private boolean deleted = false;
}