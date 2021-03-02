package com.gba.pollvote.mapper;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.dto.AssociateDTO;

public class AssociateMapper implements GenericMapper<Associate, AssociateDTO> {
    @Override
    public Associate convertToEntity(AssociateDTO dto) {
        return Associate.builder()
                .id(dto.getId())
                .name(dto.getName())
                .cpf(dto.getCpf()).build();
    }

    @Override
    public AssociateDTO convertToDTO(Associate entity) {
        return AssociateDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cpf(entity.getCpf()).build();
    }
}