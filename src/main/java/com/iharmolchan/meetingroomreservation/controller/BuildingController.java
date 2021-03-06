package com.iharmolchan.meetingroomreservation.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.dto.BuildingTO;
import com.iharmolchan.meetingroomreservation.model.Building;
import com.iharmolchan.meetingroomreservation.service.BuildingService;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/buildings")
@Slf4j
public class BuildingController {
    private final ModelMapper modelMapper;
    private final BuildingService buildingService;

    @JsonView(DefaultView.GET.class)
    @GetMapping
    public List<BuildingTO> getAll() {
        log.info("Returning all buildings.");
        return buildingService.getAll().stream().map(this::convertToDto).toList();
    }

    @JsonView(DefaultView.GET.class)
    @GetMapping("/{id}")
    public BuildingTO getById(@PathVariable Long id) {
        log.info("Returning building with id: {}.", id);
        return convertToDto(buildingService.getById(id));
    }


    @PostMapping
    public BuildingTO createBuilding(@JsonView(DefaultView.CREATE.class) @RequestBody BuildingTO buildingTo) {
        log.info("Creating building: {}", buildingTo);
        Building building = convertToEntity(buildingTo);
        return convertToDto(buildingService.save(building));
    }

    @PutMapping
    public BuildingTO updateBuilding(@JsonView(DefaultView.UPDATE.class) @RequestBody BuildingTO buildingTo) {
        log.info("Updating building: {}.", buildingTo);
        Building building = convertToEntity(buildingTo);
        return convertToDto(buildingService.save(building));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("Deleting building with id: {}.", id);
        buildingService.deleteById(id);
    }

    private BuildingTO convertToDto(Building building) {
        log.debug("Converting building {} to DTO.", building);
        return modelMapper.map(building, BuildingTO.class);
    }

    private Building convertToEntity(BuildingTO buildingTO) {
        log.debug("Converting building DTO {} to entity.", buildingTO);
        return modelMapper.map(buildingTO, Building.class);
    }

}
