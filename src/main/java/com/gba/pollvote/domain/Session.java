package com.gba.pollvote.domain;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Session implements BaseEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer sessionDuration;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToOne
    private Poll poll;
}
