package com.gba.pollvote.service.impl;

import com.gba.pollvote.config.ErrorsInfo;
import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.domain.Vote;
import com.gba.pollvote.dto.VoteResultDTO;
import com.gba.pollvote.exception.NotFoundException;
import com.gba.pollvote.repository.PollRepository;
import com.gba.pollvote.repository.SessionRepository;
import com.gba.pollvote.repository.VoteRepository;
import com.gba.pollvote.producer.Producer;
import com.gba.pollvote.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    protected SessionRepository sessionRepository;

    @Autowired
    protected PollRepository pollRepository;

    @Autowired
    protected VoteRepository voteRepository;

    @Autowired
    protected Producer producer;

    private final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);

    @Override
    public Session create(Session session) {
        return sessionRepository.save(checkIfPollExist(session));
    }

    public Session checkIfPollExist(Session session) {
        Optional<Poll> poll = pollRepository.findById(session.getPoll().getId());
        if(poll.isPresent()) {
            session.setPoll(poll.get());
            return session;
        } else {
            logger.error(ErrorsInfo.EXCEPTION_ERROR+ ErrorsInfo.POLL_NOT_FOUND);
            throw new NotFoundException(ErrorsInfo.POLL_NOT_FOUND);
        }
    }
    @Override
    public VoteResultDTO getSessionResultById(Long id) {
        Optional<Session> session = sessionRepository.findById(id);

        if(session.isPresent()) {
            List<Vote> voteList = voteRepository.findBySessionId(id);

            int yesVotes = (int) voteList.stream().filter(v -> v.getStatus().equals(true)).count();
            int noVotes = (int) voteList.stream().filter(v -> v.getStatus().equals(false)).count();

            VoteResultDTO voteResultDTO = VoteResultDTO.builder()
                    .sessionId(id)
                    .pauta(session.get().getPoll().getName())
                    .yesVotes(yesVotes)
                    .noVotes(noVotes)
                    .totalVotes(yesVotes + noVotes).build();

            producer.sendMessage(
                    "Pauta: "+voteResultDTO.getPauta() +
                            ", Resultado= Sim:"+yesVotes+
                            " Nao:"+noVotes+
                            " Total:" +voteResultDTO.getTotalVotes()
            );
            return voteResultDTO;
        } else {
            logger.error(ErrorsInfo.EXCEPTION_ERROR+ ErrorsInfo.SESSION_NOT_FOUND);
            throw new NotFoundException(ErrorsInfo.SESSION_NOT_FOUND);
        }
    }

    @Override
    public List<Session> getAll() {
        return sessionRepository.findAll();
    }
}