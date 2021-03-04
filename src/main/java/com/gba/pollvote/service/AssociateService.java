package com.gba.pollvote.service;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.exception.NotFoundException;
import java.util.List;

public interface AssociateService {
    Associate create(Associate associate);
    Associate update(Associate associate);
    List<Associate> getAll();
    boolean checkIfIsAbleToVote(String cpf) throws NotFoundException;
}