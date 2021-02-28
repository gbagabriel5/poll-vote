package com.gba.pollvote.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteResultDTO {
    private Long sessionId;
    private String pauta;
    private int yesVotes;
    private int noVotes;
    private int totalVotes;
}