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
        Session entity = new Session();
        entity.setId(dto.getId());
        entity.setSessionDuration(dto.getSessionDuration());
        entity.setStartDate(dto.getStartDate());
        if(dto.getPollDTO() != null) {
            entity.setPoll(Poll.builder()
                            .id(dto.getPollDTO().getId())
                            .name(dto.getPollDTO().getName()).build());
        }
        return entity;
    }

    @Override
    public SessionDTO convertToDTO(Session entity) {
        SessionDTO dto = new SessionDTO();
        dto.setId(entity.getId());
        dto.setSessionDuration(entity.getSessionDuration());
        dto.setStartDate(entity.getStartDate());
        if(entity.getPoll() != null) {
            dto.setPollDTO(PollDTO.builder()
                    .id(entity.getPoll().getId())
                    .name(entity.getPoll().getName()).build());
        }
        return dto;
    }
}
