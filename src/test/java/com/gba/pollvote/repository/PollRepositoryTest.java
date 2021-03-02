package com.gba.pollvote.repository;

import com.gba.pollvote.domain.Poll;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DataJpaTest
public class PollRepositoryTest {
    @Mock
    protected PollRepository pollRepository;

    @Test
    void getByName() {
        String name = "teste";
        Poll poll = Poll.builder().id(1L).name(name).build();

        Mockito.when(pollRepository.findByName(name)).thenReturn(Optional.of(poll));
        Optional<Poll> pollReturn = pollRepository.findByName(name);

        Assertions.assertThat(Optional.of(poll)).isEqualTo(pollReturn);
    }
}
