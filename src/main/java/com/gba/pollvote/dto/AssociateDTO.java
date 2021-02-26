package com.gba.pollvote.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociateDTO {
    private Long id;
    private String name;
    private String cpf;
}