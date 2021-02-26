package com.gba.pollvote.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {
    private Long id;
    private Boolean status;
    private SessionDTO sessionDTO;
    private AssociateDTO associateDTO;
}