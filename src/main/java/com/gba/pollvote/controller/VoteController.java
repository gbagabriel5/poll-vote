package com.gba.pollvote.controller;

import com.gba.pollvote.domain.Vote;
import com.gba.pollvote.dto.VoteDTO;
import com.gba.pollvote.dto.custom.VoteCustomDTO;
import com.gba.pollvote.exception.DefaultException;
import com.gba.pollvote.mapper.VoteMapper;
import com.gba.pollvote.service.VoteService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Vote Controller")
@RestController
@RequestMapping(value = "/vote")
public class VoteController {

    @Autowired
    protected VoteService voteService;

    private final VoteMapper voteMapper = new VoteMapper();

    @PostMapping
    @ApiOperation(value = "Create Vote")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public ResponseEntity<Boolean> create(
            @ApiParam(value = "Vote", required = true)
            @RequestBody @Validated VoteCustomDTO dto
    ) {
        try {
            return new ResponseEntity<>(
                    voteService.vote(voteMapper.convertToEntity(dto)),
                    HttpStatus.CREATED
            );
        } catch (Throwable throwable) {
            throw new DefaultException(throwable.getMessage());
        }
    }
}
