package com.gba.pollvote.repository;

import com.gba.pollvote.domain.Associate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociateRepository extends JpaRepository<Associate, Long> {
    Optional<Object> findByCpf(String cpf);

    Optional<Object> findByName(String name);
}
