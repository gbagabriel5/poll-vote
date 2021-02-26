package com.gba.pollvote.mapper;

import com.gba.pollvote.domain.Vote;
import com.gba.pollvote.dto.VoteDTO;

public class VoteMapper {
    public Vote convertToEntity(VoteDTO dto) {
        Vote entity = new Vote();
        entity.setId(dto.getId());
        entity.setStatus(dto.getStatus());
        if(dto.getAssociateDTO() != null)
            entity.setAssociate(new AssociateMapper().convertToEntity(dto.getAssociateDTO()));
        if(dto.getSessionDTO() != null)
            entity.setSession(new SessionMapper().convertToEntity(dto.getSessionDTO()));
        return entity;
    }

    public VoteDTO convertToDTO(Vote entity) {
        VoteDTO dto = new VoteDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        if(entity.getAssociate() != null)
            dto.setAssociateDTO(new AssociateMapper().convertToDTO(entity.getAssociate()));
        if(entity.getSession() != null)
            dto.setSessionDTO(new SessionMapper().convertToDTO(entity.getSession()));
        return dto;
    }
}