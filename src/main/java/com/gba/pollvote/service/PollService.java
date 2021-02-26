package com.gba.pollvote.service;

import com.gba.pollvote.domain.Poll;

import java.util.List;
import java.util.Optional;

public interface PollService {
    Poll create(Poll poll);
    List<Poll> getAll();
    Optional<Poll> getById(Long id);
}
