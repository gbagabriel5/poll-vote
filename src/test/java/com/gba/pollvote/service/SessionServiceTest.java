package com.gba.pollvote.service;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.domain.Vote;
import com.gba.pollvote.dto.VoteResultDTO;
import com.gba.pollvote.exception.NotFoundException;
import com.gba.pollvote.repository.PollRepository;
import com.gba.pollvote.repository.SessionRepository;
import com.gba.pollvote.repository.VoteRepository;
import com.gba.pollvote.service.impl.SessionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SessionServiceTest {

    @InjectMocks
    private SessionServiceImpl sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private PollRepository pollRepository;

    @Mock
    private VoteRepository voteRepository;

    @Test
    void createSession() {
        Poll poll = Poll.builder().id(1L).name("teste").build();

        Mockito.when(pollRepository.findById(1L)).thenReturn(Optional.of(poll));
        Optional<Poll> pollSession = pollRepository.findById(1L);

        Session session = Session.builder()
                .endDate(LocalDateTime.now().plusMinutes(1))
                .poll(pollSession.get()).build();

        Mockito.when(sessionService.create(session)).thenReturn(session);
        Session expectedAssociate = sessionService.create(session);

        assertThat(session).isEqualTo(expectedAssociate);
    }

    @Test
    void createSessionWithNotFoundPoll() {
        Poll poll = Poll.builder().id(13L).name("teste").build();
        Session session = Session.builder()
                .endDate(LocalDateTime.now().plusMinutes(1))
                .poll(poll).build();
        Mockito.when(pollRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> sessionService.checkIfPollExist(session));
    }

    @Test
    void getSessionResultById() {
        Poll poll = Poll.builder().id(1L).name("teste").build();

        var session = Session.builder()
                .id(1L)
                .endDate(LocalDateTime.now().plusMinutes(1))
                .poll(poll)
                .build();

        var associate = Associate.builder().id(1L).name("Gabriel").cpf("04182914201").build();

        var voteList = Collections.singletonList(
                Vote.builder()
                    .id(1L)
                    .status(true)
                    .associate(associate)
                    .session(session).build()
        );

        var voteResultDTO = VoteResultDTO.builder()
                .sessionId(session.getId())
                .pauta(poll.getName())
                .yesVotes(1)
                .noVotes(0)
                .totalVotes(1).build();

        Mockito.when(sessionRepository.findById(session.getId())).thenReturn(Optional.of(session));
        Mockito.when(voteRepository.findBySessionId(session.getId())).thenReturn(voteList);
        var sessionResult = sessionService.getSessionResultById(1L);

        assertThat(sessionResult.getSessionId()).isEqualTo(voteResultDTO.getSessionId());
        assertThat(sessionResult.getPauta()).isEqualTo(voteResultDTO.getPauta());
        assertThat(sessionResult.getYesVotes()).isEqualTo(voteResultDTO.getYesVotes());
        assertThat(sessionResult.getNoVotes()).isEqualTo(voteResultDTO.getNoVotes());
        assertThat(sessionResult.getTotalVotes()).isEqualTo(voteResultDTO.getTotalVotes());
    }

    @Test
    void getSessionResultByIdNotFound() {
        Mockito.when(sessionRepository.findById(13L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> sessionService.getSessionResultById(1L));
    }

    @Test
    void getAllSessions() {
        List<Session> list = new ArrayList<>();
        list.add(Session.builder()
                .endDate(LocalDateTime.now().plusMinutes(1))
                .poll(Poll.builder().id(1L).name("teste").build()).build());
        list.add(Session.builder()
                .endDate(LocalDateTime.now().plusMinutes(2))
                .poll(Poll.builder().id(2L).name("teste1").build()).build());

        Mockito.when(sessionService.getAll()).thenReturn(list);
        List<Session> listExpected = sessionService.getAll();

        Assertions.assertEquals(listExpected, list);
    }
}