package com.gba.pollvote.service.impl;

import com.gba.pollvote.config.ErrorsInfo;
import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.exception.EntityExistsException;
import com.gba.pollvote.repository.PollRepository;
import com.gba.pollvote.service.PollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PollServiceImpl implements PollService {

    @Autowired
    protected PollRepository pollRepository;

    private final Logger logger = LoggerFactory.getLogger(PollServiceImpl.class);

    @Override
    public Poll create(Poll poll) {
        if(pollRepository.findByName(poll.getName()).isPresent()) {
            logger.error(ErrorsInfo.EXCEPTION_ERROR+ ErrorsInfo.POLL_EXIST);
            throw new EntityExistsException(ErrorsInfo.POLL_EXIST);
        } else {
            poll = pollRepository.save(poll);
        }
        return poll;
    }

    @Override
    public Poll update(Poll poll) {
        Optional<Poll> pollReturn = pollRepository.findByName(poll.getName());
        if(pollReturn.isPresent() && !pollReturn.get().getId().equals(poll.getId())) {
            logger.error(ErrorsInfo.EXCEPTION_ERROR+ ErrorsInfo.POLL_EXIST);
            throw new EntityExistsException(ErrorsInfo.POLL_EXIST);
        } else {
            poll = pollRepository.save(poll);
        }
        return poll;
    }

    @Override
    public List<Poll> getAll(){
        return pollRepository.findAll();
    }
}