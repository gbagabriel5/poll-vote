package com.gba.pollvote.service.impl;

import com.gba.pollvote.client.UserClient;
import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.dto.AbleToVoteDto;
import com.gba.pollvote.exception.InvalidCpfException;
import com.gba.pollvote.mapper.AssociateMapper;
import com.gba.pollvote.repository.AssociateRepository;
import com.gba.pollvote.service.AssociateService;
import com.gba.pollvote.utils.ValidateCpf;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AssociateServiceImpl implements AssociateService {

    @Autowired
    protected AssociateRepository associateRepository;

    @Autowired
    protected UserClient userClient;

    private final AssociateMapper associateMapper = new AssociateMapper();

    @Override
    public Associate create(Associate associate) throws InvalidCpfException {
        validAssociate(associate);
        return associateRepository.save(associate);
    }

    private void validAssociate(Associate associate) throws InvalidCpfException {
        if(!associate.getName().isEmpty()) {
            if (associateRepository.findByName(associate.getName()).isPresent())
                throw new EntityExistsException("Nome ja cadastrado!");
        } else {
            throw new NullPointerException("Preencha o campo nome!");
        }

        if(!associate.getCpf().isEmpty()) {
            ValidateCpf.isCPF(associate.getCpf());
            if (associateRepository.findByCpf(associate.getCpf()).isPresent())
                throw new InvalidCpfException("Cpf ja cadastrado!");
        } else {
            throw new NullPointerException("Preencha o campo cpf!");
        }

    }

    @Override
    public Associate update(Associate associate) throws InvalidCpfException {
        validAssociateToUpdate(associate);
        return associateRepository.save(associate);
    }

    private void validAssociateToUpdate(Associate associate) throws InvalidCpfException {
        if(!associate.getName().isEmpty()) {
            Optional<Associate> associateNameReturn = associateRepository.findByName(associate.getName());
            if (associateNameReturn.isPresent() && !associateNameReturn.get().getId().equals(associate.getId())) {
                throw new EntityExistsException("Nome ja cadastrado!");
            }
        }
        if(!associate.getCpf().isEmpty()) {
            ValidateCpf.isCPF(associate.getCpf());
            Optional<Associate> associateCpfReturn = associateRepository.findByCpf(associate.getCpf());
            if (associateCpfReturn.isPresent() && !associateCpfReturn.get().getId().equals(associate.getId())) {
                throw new InvalidCpfException("Cpf ja cadastrado!");
            }
        }
    }

    @Override
    public List<Associate> getAll() {
        return associateRepository.findAll();
    }

    @Override
    public boolean checkIfIsAbleToVote(String cpf) throws EntityNotFoundException {
        AbleToVoteDto flagAbleToVoteDto;
        try{
            flagAbleToVoteDto = userClient.isAbleToVote(cpf);
        }catch (FeignException e){
            throw new EntityNotFoundException("Usuario incapaz de votar!");
        }
        return flagAbleToVoteDto.getStatus().equals("ABLE_TO_VOTE");
    }
}