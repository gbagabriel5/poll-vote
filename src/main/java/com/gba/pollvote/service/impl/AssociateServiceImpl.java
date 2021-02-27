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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AssociateServiceImpl implements AssociateService {

    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private UserClient userClient;

    private AssociateMapper associateMapper = new AssociateMapper();

    private Logger logger = LoggerFactory.getLogger(AssociateServiceImpl.class);

    @Override
    public Associate create(Associate associate) throws InvalidCpfException {
        validAssociate(associate);
        return associateRepository.save(associate);
    }

    private void validAssociate(Associate associate) throws InvalidCpfException {
        ValidateCpf.isCPF(associate.getCpf());

        if(associateRepository.findByCpf(associate.getCpf()).isPresent()){
            logger.error("cpf.exist");
            throw new EntityExistsException("cpf.exist");
        }

        if(associateRepository.findByName(associate.getName()).isPresent()){
            logger.error("name.exist");
            throw new EntityExistsException("name.exist");
        }
    }

    @Override
    public List<Associate> getAll() {
        return associateRepository.findAll();
    }

    @Override
    public Optional<Associate> getById(Long id) {
        Optional<Associate> associate = associateRepository.findById(id);
        if(associate.isEmpty())
            throw new EntityNotFoundException("associate.not.found");
        return associate;
    }

    @Override
    public boolean checkIfIsAbleToVote(String cpf) {
        AbleToVoteDto flagAbleToVoteDto;
        try{
            flagAbleToVoteDto = userClient.isAbleToVote(cpf);
        }catch (FeignException e){
            throw new EntityNotFoundException("user.not.found");
        }
        return flagAbleToVoteDto.getStatus().equals("ABLE_TO_VOTE");
    }
}