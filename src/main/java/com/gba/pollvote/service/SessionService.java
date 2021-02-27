package com.gba.pollvote.service;

import com.gba.pollvote.domain.Session;
import java.util.List;

public interface SessionService {
    Session create(Session session);
    List<Session> getAll();
}