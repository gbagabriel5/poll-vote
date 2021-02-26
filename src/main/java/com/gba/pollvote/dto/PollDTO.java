package com.gba.pollvote.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PollDTO {
    private Long id;
    private String name;
}