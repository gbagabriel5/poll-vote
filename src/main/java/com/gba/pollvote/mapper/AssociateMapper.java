package com.gba.pollvote.mapper;

import com.gba.pollvote.dto.AssociateDTO;

public class AssociateMapper {
    public com.gba.pollvote.domain.Associate convertToEntity(AssociateDTO dto) {
        com.gba.pollvote.domain.Associate entity = new com.gba.pollvote.domain.Associate();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        return entity;
    }

    public AssociateDTO convertToDTO(com.gba.pollvote.domain.Associate entity) {
        AssociateDTO dto = new AssociateDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCpf(entity.getCpf());
        return dto;
    }
}