package com.gba.pollvote.controller;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.dto.PollDTO;
import com.gba.pollvote.mapper.PollMapper;
import com.gba.pollvote.service.PollService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Poll Controller")
@RestController
@RequestMapping(value = "/poll")
public class PollController {

    @Autowired
    private final PollService pollService;
    private final PollMapper pollMapper = new PollMapper();

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @PostMapping
    @ApiOperation(value = "Create Poll")
    public PollDTO create(@ApiParam(value = "Poll", required = true) @RequestBody @Validated PollDTO dto) {
        Poll entity = pollMapper.convertToEntity(dto);
        return pollMapper.convertToDTO(pollService.create(entity));
    }
}