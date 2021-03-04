package com.gba.pollvote.service;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.repository.PollRepository;
import com.gba.pollvote.service.impl.PollServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.gba.pollvote.exception.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PollServiceTest {

    @Mock
    protected PollRepository pollRepository;

    @InjectMocks
    protected PollServiceImpl pollService;

    @Test
    void createPoll() {

        Poll poll = Poll.builder().name("Teste").build();

        Mockito.when(pollService.create(poll)).thenReturn(poll);
        Poll expectedPoll = pollService.create(poll);

        assertThat(poll).isEqualTo(expectedPoll);
    }

    @Test
    void createPollWithDuplicatedName() {

        Poll poll = Poll.builder().name("Teste").build();

        Mockito.when(pollRepository.findByName("Teste")).thenReturn(Optional.of(poll));

        Assertions.assertThrows(EntityExistsException.class,
                () -> pollService.create(poll)
        );
    }

    @Test
    void updatePoll() {
        Poll poll = Poll.builder().id(1L).name("Testando").build();

        Mockito.when(pollService.create(poll)).thenReturn(poll);
        Poll expectedPoll = pollService.update(poll);

        assertThat(poll).isEqualTo(expectedPoll);
    }

    @Test
    void updatePollWithNameExists() {
        String name = "Teste";

        Poll poll = Poll.builder().id(1L).name(name).build();

        Mockito.when(pollRepository.findByName(name)).thenReturn(Optional.of(poll));

        Assertions.assertThrows(EntityExistsException.class,
                () -> pollService.create(poll)
        );
    }

    @Test
    void getAllPolls() {
        List<Poll> list = new ArrayList<>();
        list.add(Poll.builder().name("Teste").build());
        list.add(Poll.builder().name("Teste1").build());

        Mockito.when(pollService.getAll()).thenReturn(list);
        List<Poll> listExpected = pollService.getAll();

        Assertions.assertEquals(listExpected, list);
    }
}
