package com.gba.pollvote.service;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.domain.Vote;
import com.gba.pollvote.exception.EntityExistsException;
import com.gba.pollvote.exception.TimeoutException;
import com.gba.pollvote.repository.AssociateRepository;
import com.gba.pollvote.repository.SessionRepository;
import com.gba.pollvote.repository.VoteRepository;
import com.gba.pollvote.service.impl.VoteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.gba.pollvote.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private VoteServiceImpl voteService;

    @Mock
    private AssociateRepository associateRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private AssociateService associateService;

    private final LocalDateTime beforeLocalDateTime = LocalDateTime.of(2021, 1, 1, 12, 0);
    private final LocalDateTime afterLocalDateTime = LocalDateTime.of(2023, 1, 1, 12, 0);

    @Test
    void createVote() {

        Associate associate = Associate.builder().id(1L).name("Gabriel").cpf("04182914201").build();
        Poll poll = Poll.builder().id(1L).name("teste").build();


        Session session = Session.builder()
                .id(1L)
                .endDate(afterLocalDateTime)
                .poll(poll).build();

        Vote vote = Vote.builder().id(1L).status(true).associate(associate).session(session).build();

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(associateRepository.findById(1L)).thenReturn(Optional.of(associate));
        when(associateService.checkIfIsAbleToVote("04182914201")).thenReturn(true);
        when(voteRepository.findByAssociateIdAndSessionId(1L, 1L)).thenReturn(Optional.empty());

        boolean voteResult =  voteService.vote(vote);

        assertThat(voteResult).isTrue();
    }

    @Test
    void createVoteNotFoundAssociateError() {

        Associate associate = Associate.builder().id(1L).name("Gabriel").cpf("04182914201").build();
        Poll poll = Poll.builder().id(1L).name("teste").build();


        Session session = Session.builder()
                .id(1L)
                .endDate(afterLocalDateTime)
                .poll(poll).build();

        Vote vote = Vote.builder().id(1L).status(true).associate(associate).session(session).build();

        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
        when(associateRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> voteService.vote(vote));
    }

    @Test
    void createVoteNotFoundSessionError() {

        Associate associate = Associate.builder().id(1L).name("Gabriel").cpf("04182914201").build();
        Poll poll = Poll.builder().id(1L).name("teste").build();


        Session session = Session.builder()
                .id(1L)
                .endDate(afterLocalDateTime)
                .poll(poll).build();

        Vote vote = Vote.builder().id(1L).status(true).associate(associate).session(session).build();

        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
        when(associateRepository.findById(1L)).thenReturn(Optional.of(associate));

        assertThrows(NotFoundException.class, () -> voteService.vote(vote));
    }

    @Test
    void CreateVoteIfVoteExists() {

        Associate associate = Associate.builder().id(1L).name("Gabriel").cpf("04182914201").build();
        Poll poll = Poll.builder().id(1L).name("teste").build();


        Session session = Session.builder()
                .id(1L)
                .endDate(afterLocalDateTime)
                .poll(poll).build();

        Vote vote = Vote.builder().id(1L).status(true).associate(associate).session(session).build();

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(associateRepository.findById(1L)).thenReturn(Optional.of(associate));
        when(associateService.checkIfIsAbleToVote("04182914201")).thenReturn(true);
        when(voteRepository.findByAssociateIdAndSessionId(1L, 1L)).thenReturn(Optional.of(vote));

        assertThrows(EntityExistsException.class, () -> voteService.vote(vote));
    }

    @Test
    void CreateVoteWithExpiredDate() {

        Associate associate = Associate.builder().id(1L).name("Gabriel").cpf("04182914201").build();
        Poll poll = Poll.builder().id(1L).name("teste").build();


        Session session = Session.builder()
                .id(1L)
                .endDate(beforeLocalDateTime)
                .poll(poll).build();

        Vote vote = Vote.builder().id(1L).status(true).associate(associate).session(session).build();

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(associateRepository.findById(1L)).thenReturn(Optional.of(associate));
        when(associateService.checkIfIsAbleToVote("04182914201")).thenReturn(true);

        assertThrows(TimeoutException.class, () -> voteService.vote(vote));
    }

    @Test
    void tryToCreateVoteWithAssociateUnableVote() {

        Associate associate = Associate.builder().id(2L).name("Jucelino").cpf("44154818181").build();
        Poll poll = Poll.builder().id(1L).name("teste").build();

        Session session = Session.builder()
                .endDate(LocalDateTime.now().plusMinutes(1))
                .poll(poll).build();

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        Vote vote = Vote.builder().id(1L).status(true).associate(associate).session(session).build();

        assertThrows(NotFoundException.class, () -> voteService.vote(vote));
    }
}