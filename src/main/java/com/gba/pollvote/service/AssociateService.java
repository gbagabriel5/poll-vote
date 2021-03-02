package com.gba.pollvote.service;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.exception.InvalidCpfException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface AssociateService {
    Associate create(Associate associate) throws InvalidCpfException;
    Associate update(Associate associate) throws InvalidCpfException;
    List<Associate> getAll();
    boolean checkIfIsAbleToVote(String cpf) throws EntityNotFoundException;
}
