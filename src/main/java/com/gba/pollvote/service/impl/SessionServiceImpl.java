package com.gba.pollvote.service.impl;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.repository.PollRepository;
import com.gba.pollvote.repository.SessionRepository;
import com.gba.pollvote.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    protected SessionRepository sessionRepository;

    @Autowired
    protected PollRepository pollRepository;

    @Override
    public Session create(Session session) {
        Optional<Poll> poll = pollRepository.findById(session.getPoll().getId());
        if(poll.isPresent())
            session.setPoll(poll.get());
        else
            throw new EntityNotFoundException("poll.not.found");
        session.setStartDate(LocalDateTime.now());
        if(session.getSessionDuration() == null)
            session.setSessionDuration(1);
        return sessionRepository.save(session);
    }

    @Override
    public List<Session> getAll() {
        return sessionRepository.findAll();
    }
}