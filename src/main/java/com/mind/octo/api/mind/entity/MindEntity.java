package com.mind.octo.api.mind.entity;

import com.mind.octo.api.user.entity.OctoUserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "minds")
@Getter
@Setter
@NoArgsConstructor
public class MindEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    private String icon;

    private String color;

    @Column(nullable = false)
    private boolean archived = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private OctoUserEntity user;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
}