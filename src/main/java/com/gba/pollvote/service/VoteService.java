package com.gba.pollvote.service;

import com.gba.pollvote.domain.Vote;

public interface VoteService {
    Boolean vote(Vote vote);
}