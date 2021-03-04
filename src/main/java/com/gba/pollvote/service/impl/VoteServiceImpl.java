package com.gba.pollvote.service.impl;

import com.gba.pollvote.config.ErrorsInfo;
import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.domain.Vote;
import com.gba.pollvote.exception.EntityExistsException;
import com.gba.pollvote.exception.NotFoundException;
import com.gba.pollvote.exception.TimeoutException;
import com.gba.pollvote.repository.AssociateRepository;
import com.gba.pollvote.repository.SessionRepository;
import com.gba.pollvote.repository.VoteRepository;
import com.gba.pollvote.service.AssociateService;
import com.gba.pollvote.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

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

    private final Logger logger = LoggerFactory.getLogger(VoteServiceImpl.class);

    @Override
    public Boolean vote(Vote vote) {
        voteValidation(vote);
        voteRepository.save(vote);
        return true;
    }

    private void voteValidation(Vote vote) {
        Optional<Session> session = sessionRepository.findById(vote.getSession().getId());
        Optional<Associate> associate = associateRepository.findById(vote.getAssociate().getId());

        if(associate.isPresent()) {
            associateService.checkIfIsAbleToVote(associate.get().getCpf());
            if(session.isPresent()) {
                if(LocalDateTime.now().isAfter(session.get().getEndDate())) {
                    logger.error(ErrorsInfo.EXCEPTION_ERROR+ ErrorsInfo.SESSION_ENDS);
                    throw new TimeoutException(ErrorsInfo.SESSION_ENDS);
                }
                if(voteRepository.findByAssociateIdAndSessionId(
                        vote.getAssociate().getId(),
                        vote.getSession().getId()).isPresent()
                ) {
                    logger.error(ErrorsInfo.EXCEPTION_ERROR+ ErrorsInfo.ASSOCIATE_VOTE_EXISTS);
                    throw new EntityExistsException(ErrorsInfo.ASSOCIATE_VOTE_EXISTS);
                }
            } else {
                logger.error(ErrorsInfo.EXCEPTION_ERROR + ErrorsInfo.SESSION_NOT_FOUND);
                throw new NotFoundException(ErrorsInfo.SESSION_NOT_FOUND);
            }
        } else {
            logger.error(ErrorsInfo.EXCEPTION_ERROR+ ErrorsInfo.ASSOCIATE_NOT_FOUND);
            throw new NotFoundException(ErrorsInfo.ASSOCIATE_NOT_FOUND);
        }
    }
}