package com.gba.pollvote.controller;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.dto.AssociateDTO;
import com.gba.pollvote.mapper.AssociateMapper;
import com.gba.pollvote.service.AssociateService;
import io.swagger.annotations.*;
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
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public ResponseEntity<AssociateDTO> create(
            @ApiParam(value = "Associate", required = true)
            @RequestBody @Validated AssociateDTO dto
    ) {
        Associate entity = associateMapper.convertToEntity(dto);
        return new ResponseEntity<>(associateMapper.convertToDTO(associateService.create(entity)), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation(value = "Update Associate")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public ResponseEntity<AssociateDTO> update(
            @ApiParam(value = "Associate", required = true)
            @RequestBody @Validated AssociateDTO dto
    ) {
        Associate entity = associateMapper.convertToEntity(dto);
        return new ResponseEntity<>(associateMapper.convertToDTO(associateService.update(entity)), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiParam(value = "List All Associates")
    public ResponseEntity<List<AssociateDTO>> findAll() {
        return new ResponseEntity<>(associateMapper.convertToListDTO(associateService.getAll()), HttpStatus.OK);
    }
}
