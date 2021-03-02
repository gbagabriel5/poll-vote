package com.gba.pollvote.repository;

import com.gba.pollvote.domain.Associate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DataJpaTest
public class AssociateRepositoryTest {
    @Mock
    protected AssociateRepository associateRepository;

    @Test
    void getByCpf() {
        String cpf = "04182914201";
        Associate associate = Associate.builder().id(1L).name("Gabriel").cpf(cpf).build();

        Mockito.when(associateRepository.findByCpf(cpf)).thenReturn(Optional.of(associate));
        Optional<Associate> associateReturn = associateRepository.findByCpf(cpf);

        Assertions.assertThat(Optional.of(associate)).isEqualTo(associateReturn);
    }

    @Test
    void getByName() {
        String name = "Gabriel";
        Associate associate = Associate.builder().id(1L).name(name).cpf("04182914201").build();

        Mockito.when(associateRepository.findByName(name)).thenReturn(Optional.of(associate));
        Optional<Associate> associateReturn = associateRepository.findByName(name);

        Assertions.assertThat(Optional.of(associate)).isEqualTo(associateReturn);
    }
}
