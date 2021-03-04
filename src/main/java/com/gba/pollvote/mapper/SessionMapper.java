package com.gba.pollvote.mapper;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.dto.PollDTO;
import com.gba.pollvote.dto.SessionDTO;
import com.gba.pollvote.dto.custom.SessionCustomDTO;
import java.time.LocalDateTime;

public class SessionMapper implements GenericMapper<Session,SessionDTO>{

    public Session convertToCustomEntity(SessionCustomDTO dto) {
        Session session = new Session();
        session.setPoll(Poll.builder().id(dto.getPollId()).build());
        if(dto.getSessionDuration()!=null)
            session.setEndDate(LocalDateTime.now().plusMinutes(dto.getSessionDuration()));
        else
            session.setEndDate(LocalDateTime.now().plusMinutes(1));
        return session;
    }

    @Override
    public Session convertToEntity(SessionDTO dto) {
        return Session.builder()
                .id(dto.getId())
                .endDate(dto.getEndDate())
                .poll(Poll.builder()
                        .id(dto.getPollDTO().getId())
                        .name(dto.getPollDTO().getName()).build()).build();
    }

    @Override
    public SessionDTO convertToDTO(Session entity) {
        return SessionDTO.builder()
                .id(entity.getId())
                .endDate(entity.getEndDate())
                .pollDTO(PollDTO.builder()
                        .id(entity.getPoll().getId())
                        .name(entity.getPoll().getName()).build()).build();
    }
}
