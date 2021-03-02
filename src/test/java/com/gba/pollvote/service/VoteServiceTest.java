package com.gba.pollvote.service;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.domain.Vote;
import com.gba.pollvote.repository.AssociateRepository;
import com.gba.pollvote.repository.PollRepository;
import com.gba.pollvote.repository.SessionRepository;
import com.gba.pollvote.repository.VoteRepository;
import com.gba.pollvote.service.impl.VoteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VoteServiceTest {
    @Mock
    protected VoteRepository voteRepository;

    @InjectMocks
    protected VoteServiceImpl voteService;

    @Mock
    protected AssociateRepository associateRepository;

    @Mock
    protected PollRepository pollRepository;

    @Mock
    protected SessionRepository sessionRepository;


    @Test
    void CreateVote() throws EntityNotFoundException {

        Associate associate = Associate.builder().name("Gabriel").cpf("04182914201").build();
        Poll poll = Poll.builder().id(1L).name("teste").build();

        Session session = Session.builder()
                .sessionDuration(1)
                .poll(poll)
                .startDate(LocalDateTime.now()).build();

        Mockito.when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        Vote vote = Vote.builder().id(1L).status(true).associate(associate).session(session).build();

        Assertions.assertThrows(EntityNotFoundException.class, () -> voteService.vote(vote));
    }

    @Test
    void tryToCreateVoteWithAssociateUnableVote() {

        Associate associate = Associate.builder().id(2L).name("Jucelino").cpf("44154818181").build();
        Poll poll = Poll.builder().id(1L).name("teste").build();

        Session session = Session.builder()
                .sessionDuration(1)
                .poll(poll)
                .startDate(LocalDateTime.now()).build();

        Mockito.when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        Vote vote = Vote.builder().id(1L).status(true).associate(associate).session(session).build();

        Assertions.assertThrows(EntityNotFoundException.class, () -> voteService.vote(vote));
    }
}
