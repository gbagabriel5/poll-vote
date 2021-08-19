package com.gba.pollvote.service;

import com.gba.pollvote.client.UserClient;
import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.dto.AbleToVoteDto;
import com.gba.pollvote.exception.InvalidCpfException;
import com.gba.pollvote.exception.NotFoundException;
import com.gba.pollvote.exception.NullPointerException;
import com.gba.pollvote.repository.AssociateRepository;
import com.gba.pollvote.service.impl.AssociateServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.gba.pollvote.exception.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AssociateServiceTest {

    @Mock
    private AssociateRepository associateRepository;

    @InjectMocks
    private AssociateServiceImpl associateService;

    @Mock
    private UserClient userClient;

    @Test
    void createAssociate() {
        Associate associate = Associate.builder().name("Gabriel").cpf("04182914201").build();

        when(associateService.create(associate)).thenReturn(associate);
        Associate expectedAssociate = associateService.create(associate);

        assertThat(associate).isEqualTo(expectedAssociate);
    }

    @Test
    void createAssociateWithInvalidCpf() {
        Associate associate = Associate.builder().name("Gabriel").cpf("04182914202").build();

        assertThrows(InvalidCpfException.class,
                () -> when(associateService.create(associate)).thenReturn(associate)
        );
    }

    @Test
    void createAssociateWithDuplicatedCpf() {
        Associate associate = Associate.builder().name("Gabriel").cpf("04182914201").build();

        when(associateRepository.findByCpf("04182914201")).thenReturn(Optional.of(associate));

        assertThrows(InvalidCpfException.class,
                () -> associateService.create(associate)
        );
    }

    @Test
    void createAssociateWithNullCpf() {
        Associate associate = Associate.builder().name("Gabriel").build();

        assertThrows(NullPointerException.class,
                () -> associateService.create(associate)
        );
    }

    @Test
    void createAssociateWithDuplicatedName() {
        Associate associate = Associate.builder().name("Gabriel").cpf("04182914201").build();

        when(associateRepository.findByName("Gabriel")).thenReturn(Optional.of(associate));

        assertThrows(EntityExistsException.class,
                () -> associateService.create(associate)
        );
    }

    @Test
    void createAssociateWithNullName() {
        Associate associate = Associate.builder().id(1L).name(null).cpf("04182914201").build();

        when(associateRepository.findByCpf("04182914201")).thenReturn(Optional.of(associate));

        assertThrows(NullPointerException.class,
                () -> associateService.create(associate)
        );
    }

    @Test
    void createAssociateWithEmptyName() {
        Associate associate = Associate.builder().id(1L).name("").cpf("04182914201").build();

        when(associateRepository.findByCpf("04182914201")).thenReturn(Optional.of(associate));

        assertThrows(NullPointerException.class,
                () -> associateService.create(associate)
        );
    }

    @Test
    void updateAssociateWithNullName() {
        Associate associate = Associate.builder().id(1L).name(null).cpf("04182914201").build();

        when(associateRepository.findByCpf("04182914201")).thenReturn(Optional.of(associate));

        assertThrows(NullPointerException.class,
                () -> associateService.update(associate)
        );
    }

    @Test
    void updateAssociateWithEmptyName() {
        Associate associate = Associate.builder().id(1L).name("").cpf("04182914201").build();

        when(associateRepository.findByCpf("04182914201")).thenReturn(Optional.of(associate));

        assertThrows(NullPointerException.class,
                () -> associateService.update(associate)
        );
    }

    @Test
    void updateAssociateWithCpfNotRegistered() {
        Associate associate = Associate.builder().id(1L).name("Gabriel").cpf("04182914201").build();

        assertThrows(InvalidCpfException.class,
                () -> associateService.update(associate)
        );
    }

    @Test
    void updateAssociateByCpf() {
        Associate associate = Associate.builder().id(1L).name("Gabriel").cpf("04182914201").build();

        when(associateRepository.findByCpf("04182914201")).thenReturn(Optional.of(associate));

        assertDoesNotThrow(() -> associateService.update(associate));
    }

    @Test
    void updateAssociateById() {
        Associate associate = Associate.builder().id(1L).name("Gabriel").build();

        when(associateRepository.getOne(1L)).thenReturn(associate);

        assertDoesNotThrow(() -> associateService.update(associate));
    }

    @Test
    void updateAssociateWithoutCpfAndId() {
        Associate associate = Associate.builder().name("Gabriel").build();
        assertThrows(NullPointerException.class,
                () -> associateService.update(associate)
        );
    }

    @Test
    void getAllAssociates() {
        List<Associate> list = new ArrayList<>();
        list.add(Associate.builder().name("Gabriel").cpf("04182914201").build());
        list.add(Associate.builder().name("Jucelino").cpf("32065817291").build());

        when(associateService.getAll()).thenReturn(list);
        List<Associate> listExpected = associateService.getAll();

        assertEquals(listExpected, list);
    }

    @Test
    void checkIfIsAbleToVote() {
        AbleToVoteDto ableToVoteDto = AbleToVoteDto.builder().status("ABLE_TO_VOTE").build();

        when(userClient.isAbleToVote("04182914201")).thenReturn(ableToVoteDto);
        boolean status = associateService.checkIfIsAbleToVote("04182914201");

        assertThat(status).isTrue();
    }

    @Test
    void checkIfIsAbleToVoteFeignException() {
        when(userClient.isAbleToVote(null)).thenThrow(FeignException.class);

        assertThrows(NotFoundException.class, () -> associateService.checkIfIsAbleToVote(null));
    }
}
