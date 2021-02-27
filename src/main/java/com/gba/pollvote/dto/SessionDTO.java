package com.gba.pollvote.dto;

import lombok.*;
import java.util.Date;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {
    private Long id;

    private Integer sessionDuration;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT-4")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT-4")
    private Date endDate;

    private PollDTO pollDTO;
}