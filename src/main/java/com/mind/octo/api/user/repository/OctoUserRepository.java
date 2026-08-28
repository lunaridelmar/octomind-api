package com.mind.octo.api.user.repository;

import com.mind.octo.api.user.entity.OctoUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OctoUserRepository extends JpaRepository<OctoUserEntity, Long> {

    Optional<OctoUserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}