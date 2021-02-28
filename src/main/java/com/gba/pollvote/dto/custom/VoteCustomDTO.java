package com.gba.pollvote.dto.custom;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteCustomDTO {
    private Boolean status;
    private Long associateId;
    private Long sessionId;
}
