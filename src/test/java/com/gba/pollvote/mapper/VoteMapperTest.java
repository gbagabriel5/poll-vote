package com.gba.pollvote.mapper;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.domain.Vote;
import com.gba.pollvote.dto.custom.VoteCustomDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VoteMapperTest {

    @Mock
    private VoteMapper voteMapper;

    @Test
    void convertToEntity() {
        VoteCustomDTO dto = VoteCustomDTO.builder()
                .status(true)
                .associateId(1L)
                .sessionId(1L).build();
        Vote entity = Vote.builder()
                .status(dto.getStatus())
                .associate(Associate.builder().id(dto.getAssociateId()).build())
                .session(Session.builder().id(dto.getSessionId()).build()).build();

        Mockito.when(voteMapper.convertToEntity(dto)).thenReturn(entity);
        Vote entityReturn = voteMapper.convertToEntity(dto);

        Assertions.assertThat(entity).isEqualTo(entityReturn);
    }
}