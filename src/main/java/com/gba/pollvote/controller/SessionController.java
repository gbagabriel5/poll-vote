package com.gba.pollvote.controller;

import com.gba.pollvote.domain.Session;
import com.gba.pollvote.dto.SessionDTO;
import com.gba.pollvote.dto.VoteResultDTO;
import com.gba.pollvote.dto.custom.SessionCustomDTO;
import com.gba.pollvote.exception.DefaultException;
import com.gba.pollvote.mapper.SessionMapper;
import com.gba.pollvote.service.SessionService;
import io.swagger.annotations.*;
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
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public ResponseEntity<SessionDTO> create(
            @ApiParam(value = "Session", required = true)
            @RequestBody @Validated SessionCustomDTO dto
    ) {
        Session entity = sessionMapper.convertToCustomEntity(dto);
        return new ResponseEntity<>(
                sessionMapper.convertToDTO(sessionService.create(entity)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("getvoteresult/{sessionId}")
    @ApiParam(value = "Get Session Result")
    public ResponseEntity<VoteResultDTO> getSessionResultById(
            @PathVariable(value = "sessionId") Long id
    ) {
        try {
            return new ResponseEntity<>(
                    sessionService.getSessionResultById(id),
                    HttpStatus.OK
            );
        } catch (Throwable throwable) {
            throw new DefaultException(throwable.getMessage());
        }
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
