package com.gba.pollvote.mapper;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.domain.Vote;
import com.gba.pollvote.dto.custom.VoteCustomDTO;

public class VoteMapper {
    public Vote convertToEntity(VoteCustomDTO dto) {
        return Vote.builder()
                .status(dto.getStatus())
                .associate(Associate.builder().id(dto.getAssociateId()).build())
                .session(Session.builder().id(dto.getSessionId()).build()).build();
    }
}