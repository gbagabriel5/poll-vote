package com.gba.pollvote.mapper;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.dto.PollDTO;
import com.gba.pollvote.dto.SessionDTO;
import com.gba.pollvote.dto.custom.SessionCustomDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SessionMapperTest {

    @Mock
    private SessionMapper sessionMapper;

    @Test
    void convertToCustomEntity() {
        SessionCustomDTO dto = SessionCustomDTO.builder().pollId(1L).sessionDuration(1).build();

        Session entity = Session.builder()
                .poll(Poll.builder().id(dto.getPollId()).build())
                .endDate(LocalDateTime.now().plusMinutes(dto.getSessionDuration())).build();

        Mockito.when(sessionMapper.convertToCustomEntity(dto)).thenReturn(entity);
        Session entityReturn = sessionMapper.convertToCustomEntity(dto);

        Assertions.assertThat(entity).isEqualTo(entityReturn);
    }

    @Test
    void convertToEntity() {
        SessionDTO dto = SessionDTO.builder()
                .id(1L)
                .endDate(LocalDateTime.now().plusMinutes(1))
                .pollDTO(PollDTO.builder()
                        .id(1L)
                        .name("Teste").build()).build();

        Session entity = Session.builder()
                .id(dto.getId())
                .endDate(dto.getEndDate())
                .poll(Poll.builder()
                        .id(dto.getPollDTO().getId())
                        .name(dto.getPollDTO().getName()).build())
                .build();

        Mockito.when(sessionMapper.convertToEntity(dto)).thenReturn(entity);
        Session entityReturn = sessionMapper.convertToEntity(dto);

        Assertions.assertThat(entity).isEqualTo(entityReturn);
    }

    @Test
    void convertToDTO() {
        Session entity = Session.builder()
                .id(1L)
                .endDate(LocalDateTime.now().plusMinutes(1))
                .poll(Poll.builder()
                        .id(1L)
                        .name("Teste").build()).build();

        SessionDTO dto = SessionDTO.builder()
                .id(entity.getId())
                .endDate(entity.getEndDate())
                .pollDTO(PollDTO.builder()
                        .id(entity.getPoll().getId())
                        .name(entity.getPoll().getName()).build()).build();

        Mockito.when(sessionMapper.convertToDTO(entity)).thenReturn(dto);
        SessionDTO dtoReturn = sessionMapper.convertToDTO(entity);

        Assertions.assertThat(dto).isEqualTo(dtoReturn);
    }
}