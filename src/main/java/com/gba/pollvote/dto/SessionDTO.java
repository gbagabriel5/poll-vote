package com.gba.pollvote.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {
    private Long id;
    private Integer sessionDuration;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT-4")
    private LocalDateTime startDate;

    private PollDTO pollDTO;
}