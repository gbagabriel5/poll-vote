package com.gba.pollvote.repository;

import com.gba.pollvote.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByAssociateIdAndSessionId(Long associateId, Long sessionId);
}
