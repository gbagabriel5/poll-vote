package com.gba.pollvote.service;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.repository.PollRepository;
import com.gba.pollvote.repository.SessionRepository;
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

}
