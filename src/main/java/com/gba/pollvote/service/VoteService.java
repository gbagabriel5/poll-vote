package com.gba.pollvote.service;

import com.gba.pollvote.domain.Vote;
import javax.persistence.EntityNotFoundException;
import java.util.concurrent.TimeoutException;

public interface VoteService {
    Vote vote(Vote vote) throws TimeoutException, EntityNotFoundException;
}