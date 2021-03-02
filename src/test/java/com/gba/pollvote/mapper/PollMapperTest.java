package com.gba.pollvote.mapper;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.dto.PollDTO;
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
public class PollMapperTest {

    @Mock
    private PollMapper pollMapper;

    @Test
    void convertToEntity() {
        PollDTO dto = PollDTO.builder()
                .id(1L)
                .name("teste").build();
        Poll entity = Poll.builder()
                .id(dto.getId())
                .name(dto.getName()).build();

        Mockito.when(pollMapper.convertToEntity(dto)).thenReturn(entity);
        Poll entityReturn = pollMapper.convertToEntity(dto);

        Assertions.assertThat(entity).isEqualTo(entityReturn);
    }

    @Test
    void convertToDTO() {
        Poll entity = Poll.builder()
                .id(1L)
                .name("teste").build();
        PollDTO dto = PollDTO.builder()
                .id(entity.getId())
                .name(entity.getName()).build();

        Mockito.when(pollMapper.convertToDTO(entity)).thenReturn(dto);
        PollDTO dtoReturn = pollMapper.convertToDTO(entity);

        Assertions.assertThat(dto).isEqualTo(dtoReturn);
    }
}