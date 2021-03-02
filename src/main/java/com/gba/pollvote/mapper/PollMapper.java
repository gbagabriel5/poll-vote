package com.gba.pollvote.mapper;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.dto.PollDTO;

public class PollMapper implements GenericMapper<Poll, PollDTO> {
    public Poll convertToEntity(PollDTO dto) {
        return Poll.builder()
                .id(dto.getId())
                .name(dto.getName()).build();
    }

    public PollDTO convertToDTO(Poll entity) {
        return PollDTO.builder()
                .id(entity.getId())
                .name(entity.getName()).build();
    }
}
