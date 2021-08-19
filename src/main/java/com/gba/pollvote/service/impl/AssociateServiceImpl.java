package com.gba.pollvote.service.impl;

import com.gba.pollvote.client.UserClient;
import com.gba.pollvote.config.ErrorsInfo;
import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.dto.AbleToVoteDto;
import com.gba.pollvote.exception.EntityExistsException;
import com.gba.pollvote.exception.InvalidCpfException;
import com.gba.pollvote.exception.NotFoundException;
import com.gba.pollvote.exception.NullPointerException;
import com.gba.pollvote.repository.AssociateRepository;
import com.gba.pollvote.service.AssociateService;
import com.gba.pollvote.utils.ValidateCpf;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssociateServiceImpl implements AssociateService {

    @Autowired
    protected AssociateRepository associateRepository;

    @Autowired
    protected UserClient userClient;

    private final Logger logger = LoggerFactory.getLogger(AssociateServiceImpl.class);

    @Override
    public Associate create(Associate associate){
        validAssociate(associate);
        return associateRepository.save(associate);
    }

    private void validAssociate(Associate associate) throws InvalidCpfException {
        if(associate.getName() != null && !associate.getName().isEmpty()) {
            if (associateRepository.findByName(associate.getName()).isPresent()) {
                logger.error(ErrorsInfo.EXCEPTION_ERROR+ ErrorsInfo.NAME_FOUND);
                throw new EntityExistsException(ErrorsInfo.NAME_FOUND);
            }
        } else {
            logger.error(ErrorsInfo.EXCEPTION_ERROR+ ErrorsInfo.NULL_NAME);
            throw new NullPointerException(ErrorsInfo.NULL_NAME);
        }

        if(associate.getCpf() != null) {
            ValidateCpf.isCPF(associate.getCpf());
            if (associateRepository.findByCpf(associate.getCpf()).isPresent()) {
                logger.error(ErrorsInfo.EXCEPTION_ERROR+ ErrorsInfo.CPF_FOUND);
                throw new InvalidCpfException(ErrorsInfo.CPF_FOUND);
            }
        } else {
            logger.error(ErrorsInfo.EXCEPTION_ERROR+ ErrorsInfo.NULL_CPF);
            throw new NullPointerException(ErrorsInfo.NULL_CPF);
        }
    }

    @Override
    public Associate update(Associate associate) {
        return associateRepository.save(validateAndGetAssociateToUpdate(associate));
    }

    private Associate validateAndGetAssociateToUpdate(Associate associate) throws InvalidCpfException {
        if (associate.getName() == null || associate.getName().isEmpty()) {
            logger.error(ErrorsInfo.EXCEPTION_ERROR + ErrorsInfo.NULL_NAME);
            throw new NullPointerException(ErrorsInfo.NULL_NAME);
        }
        if (associate.getCpf() != null) {
            ValidateCpf.isCPF(associate.getCpf());
            var associateCpfReturn = associateRepository.findByCpf(associate.getCpf());
            if (associateCpfReturn.isEmpty()) {
                logger.error(ErrorsInfo.EXCEPTION_ERROR+ ErrorsInfo.CPF_NOT_FOUND);
                throw new InvalidCpfException(ErrorsInfo.CPF_NOT_FOUND);
            } else {
                return Associate.builder()
                        .id(associateCpfReturn.get().getId())
                        .cpf(associateCpfReturn.get().getCpf())
                        .name(associate.getName())
                        .build();
            }
        }
        if (associate.getId() == null) {
            logger.error(ErrorsInfo.EXCEPTION_ERROR+ ErrorsInfo.NULL_PARAMETERS);
            throw new NullPointerException(ErrorsInfo.NULL_PARAMETERS);
        } else {
            var associateReturn = associateRepository.getOne(associate.getId());
            return Associate.builder()
                    .id(associateReturn.getId())
                    .cpf(associateReturn.getCpf())
                    .name(associate.getName())
                    .build();
        }
    }

    @Override
    public List<Associate> getAll() {
        return associateRepository.findAll();
    }

    @Override
    public boolean checkIfIsAbleToVote(String cpf) throws NotFoundException {
        AbleToVoteDto flagAbleToVoteDto;
        try {
            flagAbleToVoteDto = userClient.isAbleToVote(cpf);
        } catch (FeignException e) {
            logger.error(ErrorsInfo.EXCEPTION_ERROR+ ErrorsInfo.UNABLE_TO_VOTE);
            throw new NotFoundException(ErrorsInfo.UNABLE_TO_VOTE, e);
        }
        return flagAbleToVoteDto.getStatus().equals(ErrorsInfo.ABLE_TO_VOTE);
    }
}