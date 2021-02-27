package com.gba.pollvote.mapper;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.dto.PollDTO;

public class PollMapper implements GenericMapper<Poll, PollDTO> {
    public Poll convertToEntity(PollDTO dto) {
        Poll entity = new Poll();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public PollDTO convertToDTO(Poll entity) {
        PollDTO dto = new PollDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
