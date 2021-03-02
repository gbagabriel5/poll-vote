package com.gba.pollvote.mapper;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.dto.AssociateDTO;
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
public class AssociateMapperTest {

    @Mock
    private AssociateMapper associateMapper;

    @Test
    void convertToEntity() {
        AssociateDTO dto = AssociateDTO.builder()
                .id(1L)
                .name("Gabriel")
                .cpf("04182914201").build();
        Associate entity = Associate.builder()
                .id(dto.getId())
                .name(dto.getName())
                .cpf(dto.getCpf()).build();

        Mockito.when(associateMapper.convertToEntity(dto)).thenReturn(entity);
        Associate entityReturn = associateMapper.convertToEntity(dto);

        Assertions.assertThat(entity).isEqualTo(entityReturn);
    }

    @Test
    void convertToDTO() {
        Associate entity = Associate.builder()
                .id(1L)
                .name("Gabriel")
                .cpf("04182914201").build();
        AssociateDTO dto = AssociateDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cpf(entity.getCpf()).build();

        Mockito.when(associateMapper.convertToDTO(entity)).thenReturn(dto);
        AssociateDTO dtoReturn = associateMapper.convertToDTO(entity);

        Assertions.assertThat(dto).isEqualTo(dtoReturn);
    }
}