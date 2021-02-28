package com.gba.pollvote.dto.custom;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionCustomDTO {
    private Long pollId;
    private Integer sessionDuration;
}
