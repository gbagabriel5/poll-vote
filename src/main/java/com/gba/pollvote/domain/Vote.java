package com.gba.pollvote.domain;

import lombok.*;
import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean status;

    @ManyToOne(optional=false)
    private Session session;

    @ManyToOne(optional=false)
    private Associate associate;
}
