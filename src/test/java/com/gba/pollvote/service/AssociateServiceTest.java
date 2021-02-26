package com.gba.pollvote.service;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.exception.InvalidCpfException;
import com.gba.pollvote.repository.AssociateRepository;
import com.gba.pollvote.service.impl.AssociateServiceImpl;
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
class AssociateServiceTest {

    @Mock
    AssociateRepository repository;
    @InjectMocks
    AssociateServiceImpl service;

    @BeforeEach
    public void setUp(){}

    @Test
    void createAssociate() throws Exception {

        Associate associate = Associate.builder().name("Gabriel").cpf("04182914201").build();

        Mockito.when(service.create(associate)).thenReturn(associate);
        Associate expectedAssociate = service.create(associate);

        assertThat(associate).isEqualTo(expectedAssociate);
    }

    @Test
    void createAssociateWithInvalidCpf() {

        Associate associate = Associate.builder().name("Gabriel").cpf("04182914202").build();

        Assertions.assertThrows(InvalidCpfException.class,
                () -> Mockito.when(service.create(associate)).thenReturn(associate)
        );
    }

    @Test
    void createAssociateWithDuplicatedCpf() {

        Associate associate = Associate.builder().name("Gabriel").cpf("04182914201").build();

        Mockito.when(repository.findByCpf("04182914201")).thenReturn(Optional.of(associate));

        Assertions.assertThrows(EntityExistsException.class,
                () -> service.create(associate)
        );
    }

    @Test
    void createAssociateWithDuplicatedName() {

        Associate associate = Associate.builder().name("Gabriel").cpf("04182914201").build();

        Mockito.when(repository.findByName("Gabriel")).thenReturn(Optional.of(associate));

        Assertions.assertThrows(EntityExistsException.class,
                () -> service.create(associate)
        );
    }

    @Test
    void getAllAssociates() {
        List<Associate> list = new ArrayList<>();
        list.add(Associate.builder().name("Gabriel").cpf("04182914201").build());
        list.add(Associate.builder().name("Jucelino").cpf("32065817291").build());

        Mockito.when(service.getAll()).thenReturn(list);
        List<Associate> listExpected = service.getAll();

        Assertions.assertEquals(listExpected, list);
    }

    @Test
    void getAssociateById() {
        Long id = 1L;
        Associate associate = Associate.builder().id(id).name("Gabriel").cpf("04182914201").build();

        Mockito.when(service.getById(id)).thenReturn(Optional.of(associate));
        Optional<Associate> expectedAssociate = service.getById(id);

        assertThat(expectedAssociate).isNotNull();
    }
}
