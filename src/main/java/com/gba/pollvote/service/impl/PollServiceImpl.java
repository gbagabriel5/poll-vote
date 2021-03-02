package com.gba.pollvote.service.impl;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.repository.PollRepository;
import com.gba.pollvote.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class PollServiceImpl implements PollService {

    @Autowired
    protected PollRepository pollRepository;

    @Override
    public Poll create(Poll poll) {
        if(pollRepository.findByName(poll.getName()).isPresent())
            throw new EntityExistsException("Pauta ja cadastrada!");
        else
            poll = pollRepository.save(poll);
        return poll;
    }

    @Override
    public Poll update(Poll poll) {
        Optional<Poll> pollReturn = pollRepository.findByName(poll.getName());
        if(pollReturn.isPresent() && !pollReturn.get().getId().equals(poll.getId()))
            throw new EntityExistsException("Pauta ja cadastrada!");
        else
            poll = pollRepository.save(poll);
        return poll;
    }

    @Override
    public List<Poll> getAll(){
        return pollRepository.findAll();
    }
}
