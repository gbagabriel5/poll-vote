package com.gba.pollvote.service;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.domain.Vote;
import com.gba.pollvote.dto.VoteResultDTO;
import com.gba.pollvote.repository.PollRepository;
import com.gba.pollvote.repository.SessionRepository;
import com.gba.pollvote.repository.VoteRepository;
import com.gba.pollvote.service.impl.SessionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SessionServiceTest {

    @InjectMocks
    protected SessionServiceImpl sessionService;

    @Mock
    protected SessionRepository sessionRepository;

    @Mock
    protected PollRepository pollRepository;

    @Mock
    protected VoteRepository voteRepository;

    @BeforeEach
    public void setUp(){}

    @Test
    void createSession() {
        Poll poll = Poll.builder().id(1L).name("teste").build();

        Mockito.when(pollRepository.findById(1L)).thenReturn(Optional.of(poll));
        Optional<Poll> pollSession = pollRepository.findById(1L);

        Session session = Session.builder()
                .sessionDuration(1)
                .poll(pollSession.get())
                .startDate(LocalDateTime.now()).build();

        Mockito.when(sessionService.create(session)).thenReturn(session);
        Session expectedAssociate = sessionService.create(session);

        assertThat(session).isEqualTo(expectedAssociate);
    }

    @Test
    void getAllSessions() {
        List<Session> list = new ArrayList<>();
        list.add(Session.builder()
                .sessionDuration(1)
                .startDate(LocalDateTime.now())
                .poll(Poll.builder().id(1L).name("teste").build()).build());
        list.add(Session.builder()
                .sessionDuration(2)
                .startDate(LocalDateTime.now())
                .poll(Poll.builder().id(2L).name("teste1").build()).build());

        Mockito.when(sessionService.getAll()).thenReturn(list);
        List<Session> listExpected = sessionService.getAll();

        Assertions.assertEquals(listExpected, list);
    }

    @Test
    void getVoteResult() {
        Long id = 1L;

        Session session = Session.builder()
                .sessionDuration(1)
                .poll(Poll.builder().name("teste").build())
                .startDate(LocalDateTime.now()).build();
        Mockito.when(sessionRepository.findById(id)).thenReturn(Optional.of(session));

        List<Vote> voteList = new ArrayList<>();
        voteList.add(Vote.builder()
                        .status(true)
                        .session(session)
                        .associate(Associate.builder()
                                .id(1L).name("Gabriel")
                                .cpf("04182914201").build()).build());

        Mockito.when(voteRepository.findBySessionId(id)).thenReturn(voteList);

        VoteResultDTO voteResult = sessionService.getSessionResultById(id);

        assertThat(voteResult).isNotNull();
    }
//    @Test
//    void getVoteResultWithWrongSessionIdException() throws EntityNotFoundException{
//        Long id = 1L;
//
//        Session session = Session.builder()
//                .sessionDuration(1)
//                .poll(Poll.builder().name("teste").build())
//                .startDate(LocalDateTime.now()).build();
//        Mockito.when(sessionRepository.findById(id)).thenReturn(Optional.of(session));
//
//        Assertions.assertThrows(
//                EntityNotFoundException.class,
//                () -> sessionService.getSessionResultById(id)
//        );
//
//    }
}
