package com.gba.pollvote.service;

import com.gba.pollvote.domain.Poll;
import java.util.List;

public interface PollService {
    Poll create(Poll poll);
    Poll update(Poll poll);
    List<Poll> getAll();
}