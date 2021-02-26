package com.gba.pollvote.mapper;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.domain.Session;
import com.gba.pollvote.dto.PollDTO;
import com.gba.pollvote.dto.SessionDTO;

public class SessionMapper {

    public Session convertToEntity(SessionDTO dto) {
        Session entity = new Session();
        entity.setId(dto.getId());
        entity.setStartVote(dto.getStartVote());
        entity.setEndVote(dto.getEndVote());
        if(dto.getPollDTO() != null) {
            Poll poll =  new Poll();
            poll.setId(dto.getPollDTO().getId());
            poll.setName(dto.getPollDTO().getName());
            entity.setPoll(poll);
        }
        return entity;
    }

    public SessionDTO convertToDTO(Session entity) {
        SessionDTO dto = new SessionDTO();
        dto.setId(entity.getId());
        dto.setStartVote(entity.getStartVote());
        dto.setEndVote(entity.getEndVote());
        if(entity.getPoll() != null) {
            PollDTO pollDTO =  new PollDTO();
            pollDTO.setId(entity.getPoll().getId());
            pollDTO.setName(entity.getPoll().getName());
            dto.setPollDTO(pollDTO);
        }
        return dto;
    }
}
