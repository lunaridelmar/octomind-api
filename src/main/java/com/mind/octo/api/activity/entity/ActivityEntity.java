package com.mind.octo.api.activity.entity;

import com.mind.octo.api.mind.entity.MindEntity;
import com.mind.octo.api.user.entity.OctoUserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
public class ActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private boolean completed = false;

    @ManyToMany
    @JoinTable(
            name = "activity_minds",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "mind_id")
    )
    private Set<MindEntity> minds = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private OctoUserEntity user;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant completedAt;

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
    }
}