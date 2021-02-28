package com.gba.pollvote.service;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.repository.PollRepository;
import com.gba.pollvote.service.impl.PollServiceImpl;
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
import javax.persistence.EntityExistsException;
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

    @BeforeEach
    public void setUp(){}

    @Test
    void createPoll() {

        Poll poll = Poll.builder().name("Teste").build();

        Mockito.when(pollService.create(poll)).thenReturn(poll);
        Poll expectedAssociate = pollService.create(poll);

        assertThat(poll).isEqualTo(expectedAssociate);
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
    void getAllPolls() {
        List<Poll> list = new ArrayList<>();
        list.add(Poll.builder().name("Teste").build());
        list.add(Poll.builder().name("Teste1").build());

        Mockito.when(pollService.getAll()).thenReturn(list);
        List<Poll> listExpected = pollService.getAll();

        Assertions.assertEquals(listExpected, list);
    }
}
