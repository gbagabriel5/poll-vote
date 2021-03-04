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

    private LocalDateTime endDate;

    @ManyToOne(optional=false)
    private Poll poll;
}