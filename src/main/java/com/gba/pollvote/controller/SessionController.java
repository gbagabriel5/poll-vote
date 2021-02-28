package com.gba.pollvote.controller;

import com.gba.pollvote.domain.Session;
import com.gba.pollvote.dto.SessionDTO;
import com.gba.pollvote.mapper.SessionMapper;
import com.gba.pollvote.service.SessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(value = "Session Controller")
@RestController
@RequestMapping(value = "/session")
public class SessionController {

    @Autowired
    protected SessionService sessionService;
    private final SessionMapper sessionMapper = new SessionMapper();

    @PostMapping
    @ApiOperation(value = "Create Session")
    public ResponseEntity<SessionDTO> create(
            @ApiParam(value = "Session", required = true)
            @RequestBody @Validated SessionDTO dto
    ) {
        Session entity = sessionMapper.convertToEntity(dto);
        return new ResponseEntity<>(
                sessionMapper.convertToDTO(sessionService.create(entity)),
                HttpStatus.OK
        );
    }

    @GetMapping
    @ApiParam(value = "List All Sessions")
    public ResponseEntity<List<SessionDTO>> findAll() {
        return new ResponseEntity<>(
                sessionMapper.convertToListDTO(sessionService.getAll()),
                HttpStatus.OK
        );
    }
}
