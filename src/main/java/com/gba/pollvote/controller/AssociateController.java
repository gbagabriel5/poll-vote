package com.gba.pollvote.controller;

import com.gba.pollvote.domain.Associate;
import com.gba.pollvote.dto.AssociateDTO;
import com.gba.pollvote.exception.NotFoundException;
import com.gba.pollvote.mapper.AssociateMapper;
import com.gba.pollvote.service.AssociateService;
import com.gba.pollvote.service.MessagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityExistsException;
import java.util.Optional;

@Api(value = "Associate Controller")
@RestController
@RequestMapping(value = "/associate")
public class AssociateController {
    @Autowired
    private final AssociateService associateService;
    private final AssociateMapper associateMapper = new AssociateMapper();
    @Autowired
    private MessagesService messageService;

    public AssociateController(AssociateService associateService) {
        this.associateService = associateService;
    }

    @PostMapping
    @ApiOperation(value = "Create Associate")
    public AssociateDTO create(@ApiParam(value = "Associate", required = true) @RequestBody @Validated AssociateDTO dto) {
        Associate entity = associateMapper.convertToEntity(dto);
        try {
            return associateMapper.convertToDTO(associateService.create(entity));
        } catch (Throwable throwable) {
            throw new EntityExistsException(
                    "Error: "+ throwable.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Find Associate by id")
    public ResponseEntity<AssociateDTO> findById(@PathVariable Long id) {
        Optional<Associate> associate = associateService.getById(id);
        return associate.map(
                value -> ResponseEntity.ok().body(associateMapper.convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
