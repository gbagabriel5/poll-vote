package com.gba.pollvote.service.impl;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.domain.Vote;
import com.gba.pollvote.repository.AssociateRepository;
import com.gba.pollvote.repository.SessionRepository;
import com.gba.pollvote.repository.VoteRepository;
import com.gba.pollvote.service.AssociateService;
import com.gba.pollvote.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    protected VoteRepository voteRepository;

    @Autowired
    protected SessionRepository sessionRepository;

    @Autowired
    protected AssociateRepository associateRepository;

    @Autowired
    protected AssociateService associateService;

    @Override
    public Boolean vote(Vote vote) throws TimeoutException, EntityNotFoundException {
        voteValidation(vote);
        voteRepository.save(vote);
        return true;
    }

    private void voteValidation(Vote vote) throws TimeoutException, EntityNotFoundException {
        Optional<Session> session = sessionRepository.findById(vote.getSession().getId());
        Optional<Associate> associate = associateRepository.findById(vote.getAssociate().getId());

        if(associate.isPresent()) {
            associateService.checkIfIsAbleToVote(associate.get().getCpf());
            if(session.isPresent()) {

                long minutesBetween = ChronoUnit.MINUTES.between(session.get().getStartDate(), LocalDateTime.now());

                if(minutesBetween > session.get().getSessionDuration())
                    throw new TimeoutException("Sessao encerrada!");

                if(voteRepository.findByAssociateIdAndSessionId(vote.getAssociate().getId(), vote.getSession().getId()).isPresent())
                    throw new EntityExistsException("Este Associado ja votou.");
            } else {
                throw new EntityNotFoundException("Sessao nao encontrada!");
            }
        } else {
            throw new EntityNotFoundException("Associado nao encontrado!");
        }
    }
}