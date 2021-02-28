package com.gba.pollvote.dto;

import lombok.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {
    private Long id;
    private Integer sessionDuration;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private PollDTO pollDTO;
}