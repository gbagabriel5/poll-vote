package com.gba.pollvote.client;

import com.gba.pollvote.dto.AbleToVoteDto;
import feign.Param;
import feign.RequestLine;

public interface UserClient {
    @RequestLine("GET /{cpf}")
    AbleToVoteDto isAbleToVote(@Param("cpf") String cpf);
}