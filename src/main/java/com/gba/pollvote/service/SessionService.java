package com.gba.pollvote.service;

import com.gba.pollvote.domain.Session;
import com.gba.pollvote.dto.VoteResultDTO;
import java.util.List;

public interface SessionService {
    Session create(Session session);
    List<Session> getAll();
    VoteResultDTO getSessionResultById(Long id);
}