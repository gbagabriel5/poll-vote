package com.gba.pollvote.repository;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.domain.Vote;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DataJpaTest
public class VoteRepositoryTest {
    @Mock
    protected VoteRepository voteRepository;

    @Test
    void getByAssociateIdAndSessionId() {
        Long id = 1L;

        Associate associate = Associate.builder().id(id).name("Gabriel").cpf("04182914201").build();
        Poll poll = Poll.builder().id(id).name("teste").build();

        Session session = Session.builder()
                .id(id)
                .sessionDuration(1)
                .poll(poll)
                .startDate(LocalDateTime.now()).build();

        Vote vote = Vote.builder().id(id).status(true).associate(associate).session(session).build();

        Mockito.when(voteRepository.findByAssociateIdAndSessionId(id,id)).thenReturn(Optional.of(vote));
        Optional<Vote> voteReturn = voteRepository.findByAssociateIdAndSessionId(id,id);

        Assertions.assertThat(Optional.of(vote)).isEqualTo(voteReturn);
    }

    @Test
    void getBySessionId() {
        Long id = 1L;

        Associate associate = Associate.builder().id(id).name("Gabriel").cpf("04182914201").build();
        Poll poll = Poll.builder().id(id).name("teste").build();

        Session session = Session.builder()
                .id(id)
                .sessionDuration(1)
                .poll(poll)
                .startDate(LocalDateTime.now()).build();

        List<Vote> voteList = new ArrayList<>();
        voteList.add(Vote.builder().id(id).status(true).associate(associate).session(session).build());

        Mockito.when(voteRepository.findBySessionId(id)).thenReturn(voteList);
        List<Vote> voteListReturn = voteRepository.findBySessionId(id);

        Assertions.assertThat(voteList).isEqualTo(voteListReturn);
    }
}

