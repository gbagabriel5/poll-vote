package com.gba.pollvote.controller;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.dto.PollDTO;
import com.gba.pollvote.exception.DefaultException;
import com.gba.pollvote.mapper.PollMapper;
import com.gba.pollvote.service.PollService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(value = "Poll Controller")
@RestController
@RequestMapping(value = "/poll")
public class PollController {

    @Autowired
    protected PollService pollService;
    private final PollMapper pollMapper = new PollMapper();

    @PostMapping
    @ApiOperation(value = "Create Poll")
    public ResponseEntity<PollDTO> create(
            @ApiParam(value = "Poll", required = true)
            @RequestBody @Validated PollDTO dto
    ) {
        try {
            Poll entity = pollMapper.convertToEntity(dto);
            return new ResponseEntity<>(
                    pollMapper.convertToDTO(pollService.create(entity)),
                    HttpStatus.OK
            );
        } catch (Throwable throwable) {
            throw new DefaultException(throwable.getMessage());
        }
    }

    @GetMapping
    @ApiParam(value = "List All Polls")
    public ResponseEntity<List<PollDTO>> findAll() {
        return new ResponseEntity<>(
                pollMapper.convertToListDTO(pollService.getAll()),
                HttpStatus.OK
        );
    }
}