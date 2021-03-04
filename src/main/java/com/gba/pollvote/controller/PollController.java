package com.gba.pollvote.controller;

import com.gba.pollvote.domain.Poll;
import com.gba.pollvote.dto.PollDTO;
import com.gba.pollvote.mapper.PollMapper;
import com.gba.pollvote.service.PollService;
import io.swagger.annotations.*;
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
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public ResponseEntity<PollDTO> create(
            @ApiParam(value = "Poll", required = true)
            @RequestBody @Validated PollDTO dto
    ) {
        Poll entity = pollMapper.convertToEntity(dto);
        return new ResponseEntity<>(pollMapper.convertToDTO(pollService.create(entity)), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation(value = "Update Poll")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public ResponseEntity<PollDTO> update(
            @ApiParam(value = "Poll", required = true)
            @RequestBody @Validated PollDTO dto
    ) {
        Poll entity = pollMapper.convertToEntity(dto);
        return new ResponseEntity<>(pollMapper.convertToDTO(pollService.update(entity)), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiParam(value = "List All Polls")
    public ResponseEntity<List<PollDTO>> findAll() {
        return new ResponseEntity<>(pollMapper.convertToListDTO(pollService.getAll()), HttpStatus.OK);
    }
}