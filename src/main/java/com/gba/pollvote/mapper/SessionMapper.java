package com.gba.pollvote.mapper;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.dto.PollDTO;
import com.gba.pollvote.dto.SessionDTO;
import com.gba.pollvote.dto.custom.SessionCustomDTO;

public class SessionMapper implements GenericMapper<Session,SessionDTO>{

    public Session convertToCustomEntity(SessionCustomDTO dto) {
        return Session.builder()
                .poll(Poll.builder().id(dto.getPollId()).build())
                .sessionDuration(dto.getSessionDuration()).build();
    }

    @Override
    public Session convertToEntity(SessionDTO dto) {
        return Session.builder()
                .id(dto.getId())
                .sessionDuration(dto.getSessionDuration())
                .startDate(dto.getStartDate())
                .poll(Poll.builder()
                        .id(dto.getPollDTO().getId())
                        .name(dto.getPollDTO().getName()).build()).build();
    }

    @Override
    public SessionDTO convertToDTO(Session entity) {
        return SessionDTO.builder()
                .id(entity.getId())
                .sessionDuration(entity.getSessionDuration())
                .startDate(entity.getStartDate())
                .pollDTO(PollDTO.builder()
                        .id(entity.getPoll().getId())
                        .name(entity.getPoll().getName()).build()).build();
    }
}
