package com.gba.pollvote.service;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.exception.InvalidCpfException;

import java.util.List;
import java.util.Optional;

public interface AssociateService {
    Associate create(Associate associate) throws InvalidCpfException;
    List<Associate> getAll();
    Optional<Associate> getById(Long id);
    boolean checkIfIsAbleToVote(String cpf);
}
