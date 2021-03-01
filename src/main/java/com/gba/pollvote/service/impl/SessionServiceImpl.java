package com.gba.pollvote.service.impl;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.domain.Vote;
import com.gba.pollvote.dto.VoteResultDTO;
import com.gba.pollvote.repository.PollRepository;
import com.gba.pollvote.repository.SessionRepository;
import com.gba.pollvote.repository.VoteRepository;
import com.gba.pollvote.service.Producer;
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

    @Autowired
    protected VoteRepository voteRepository;

    @Autowired
    protected Producer producer;

    @Override
    public Session create(Session session) {
        Optional<Poll> poll = pollRepository.findById(session.getPoll().getId());
        if(poll.isPresent())
            session.setPoll(poll.get());
        else
            throw new EntityNotFoundException("Pauta nao encontrada!");
        session.setStartDate(LocalDateTime.now());
        if(session.getSessionDuration() == null)
            session.setSessionDuration(1);
        return sessionRepository.save(session);
    }

    @Override
    public VoteResultDTO getSessionResultById(Long id) throws EntityNotFoundException {
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
            throw new EntityNotFoundException("Sessao nao encontrada");
        }
    }

    @Override
    public List<Session> getAll() {
        return sessionRepository.findAll();
    }
}