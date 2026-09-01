package com.mind.octo.api.mindcombination.entity;

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
@Table(name = "mind_combinations")
@Getter
@Setter
@NoArgsConstructor
public class MindCombinationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private OctoUserEntity user;

    @ManyToMany
    @JoinTable(
            name = "mind_combination_minds",
            joinColumns = @JoinColumn(name = "combination_id"),
            inverseJoinColumns = @JoinColumn(name = "mind_id")
    )
    private Set<MindEntity> minds = new HashSet<>();

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
    }
}