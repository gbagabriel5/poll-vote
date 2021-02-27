package com.gba.pollvote.controller;

import com.gba.pollvote.domain.Session;
import com.gba.pollvote.dto.SessionDTO;
import com.gba.pollvote.mapper.SessionMapper;
import com.gba.pollvote.service.SessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
    public SessionDTO create(@ApiParam(value = "Session", required = true) @RequestBody @Validated SessionDTO dto) {
        Session entity = sessionMapper.convertToEntity(dto);
        return sessionMapper.convertToDTO(sessionService.create(entity));
    }

    @GetMapping
    @ApiParam(value = "List All Sessions")
    public List<SessionDTO> findAll() {
        return sessionMapper.convertToListDTO(sessionService.getAll());
    }
}
