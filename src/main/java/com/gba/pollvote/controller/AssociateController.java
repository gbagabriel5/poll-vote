package com.gba.pollvote.controller;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.dto.AssociateDTO;
import com.gba.pollvote.exception.DefaultException;
import com.gba.pollvote.mapper.AssociateMapper;
import com.gba.pollvote.service.AssociateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(value = "Associate Controller")
@RestController
@RequestMapping(value = "/associate")
public class AssociateController {

    @Autowired
    protected AssociateService associateService;

    private final AssociateMapper associateMapper = new AssociateMapper();

    @PostMapping
    @ApiOperation(value = "Create Associate")
    public ResponseEntity<AssociateDTO> create(
            @ApiParam(value = "Associate", required = true)
            @RequestBody @Validated AssociateDTO dto
    ) {
        Associate entity = associateMapper.convertToEntity(dto);
        try {
            return new ResponseEntity<>(
                    associateMapper.convertToDTO(associateService.create(entity)),
                    HttpStatus.OK
            );
        } catch (Throwable throwable) {
            throw new DefaultException(throwable.getMessage());
        }
    }

    @GetMapping
    @ApiParam(value = "List All Associates")
    public ResponseEntity<List<AssociateDTO>> findAll() {
        return new ResponseEntity<>(
                associateMapper.convertToListDTO(associateService.getAll()),
                HttpStatus.OK
        );
    }
}
